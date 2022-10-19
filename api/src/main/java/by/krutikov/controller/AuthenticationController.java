package by.krutikov.controller;

import by.krutikov.dto.request.AccountInfo;
import by.krutikov.dto.response.AuthResponse;
import by.krutikov.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils tokenUtils;
    private final UserDetailsService userProvider;

    //    @ApiOperation(value = "Login user in system", notes = "Return Auth-Token with user login")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Successful authorization"),
//            @ApiResponse(code = 400, message = "Request error"),
//            @ApiResponse(code = 500, message = "Server error")
//    })
    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AccountInfo request) {

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
}

