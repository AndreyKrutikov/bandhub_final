package by.krutikov.controller.commonendpoints;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.response.AccountDetailsResponse;
import by.krutikov.mappers.AccountMapper;
import by.krutikov.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class RegistrationController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @Operation(summary = "Create new account", description = "Add new user account to application")
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Illegal arguments supplied",
            content = @Content
    )
    @ApiResponse(
            responseCode = "409",
            description = "Provided email already exists",
            content = @Content
    )
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createNewAccount(@Valid @RequestBody AccountDetails registrationInfo) {
        Account newAccount = mapper.map(registrationInfo);
        newAccount = accountService.createAccount(newAccount);
        AccountDetailsResponse response = mapper.map(newAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account created", response), HttpStatus.CREATED
        );
    }
}
