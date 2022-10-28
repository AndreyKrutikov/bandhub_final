package by.krutikov.security.jwt;

import by.krutikov.configuration.JwtTokenConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {//todo do refactor!
    public static final String CREATED = "created";
    public static final String EXPIRES = "expires";
    public static final String ROLES = "roles";
    public static final String JWT = "JWT";
    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;
    private final JwtTokenConfig jwtTokenConfig;

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Date getCreatedDateFromToken(String token) {
        return (Date) getClaimsFromToken(token).get(CREATED);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtTokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(currentDate());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(CREATED, currentDate());
        claims.put(EXPIRES, expirationDate());//check this is ok
        claims.put(ROLES, getEncryptedRoles(userDetails));
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts
                .builder()
                //headers:
                .setHeader(generateJWTHeaders())
                .setClaims(claims)
                //payload:
                .setIssuedAt(currentDate())
                .setExpiration(expirationDate())
                //signature:
                .signWith(ALGORITHM, jwtTokenConfig.getSecret())
                .compact();
    }

    private Map<String, Object> generateJWTHeaders() {
        Map<String, Object> jwtHeaders = new LinkedHashMap<>();
        jwtHeaders.put("typ", JWT);
        jwtHeaders.put("alg", ALGORITHM.getValue());

        return jwtHeaders;
    }

    private List<String> getEncryptedRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replace("ROLE_", ""))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);

        return !(isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
                && !(isTokenExpired(token));
    }

    public String refreshToken(String token) {
        String refreshedToken = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, currentDate());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return username.equals(userDetails.getUsername());
    }

    private Date currentDate() {
        return new Date();
    }

    private Date expirationDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(MILLISECOND, jwtTokenConfig.getExpiration());

        return calendar.getTime();
    }
}
