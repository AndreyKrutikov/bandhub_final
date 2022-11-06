package by.krutikov.controller;

import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.response.AuthResponse;
import by.krutikov.security.jwt.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userProvider;

    // TODO: 31.10.22 document this

    //    @ApiOperation(value = "Login user in system", notes = "Return Auth-Token with user login")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Successful authorization"),
//            @ApiResponse(code = 400, message = "Request error"),
//            @ApiResponse(code = 500, message = "Server error")
//    })
    @Operation(summary = "Login user",
            description = "Login endpoint. Returns JWT token string & user email")
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signInUser(@RequestBody AccountDetails request) {

        /*Check login and password*/
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        /*Generate token with answer to user*/
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .email(request.getEmail())
                        .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getEmail())))
                        .build()
        );
    }

    @Operation(summary = "Logout user",
            description = "Logout endpoint. Destroys token & invalidates authentication")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = ""
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping("/logout")
    public ResponseEntity<Object> logoutUser(@RequestHeader(X_AUTH_TOKEN) String token) {
       // SecurityContextHolder.getContext().setAuthentication(null);
        tokenUtils.destroyToken(token);

        return ResponseEntity.ok("You are logged out, this token = " + token);
    }

}

