package by.krutikov.controller.personalendpoints;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetailsUpdate;
import by.krutikov.dto.response.AccountDetailsResponse;
import by.krutikov.mappers.AccountMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class PersonalEndpointAccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @Operation(summary = "Show account info",
            description = "Endpoint shows account information of authorised user")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Personal account details found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access is denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping()
    public ResponseEntity<Object> showAccountDetails(Principal principal) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);
        AccountDetailsResponse response = mapper.map(myAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Update credentials",
            description = "Endpoint for updating account credentials; user must be authorised")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Account updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @PutMapping()
    @Transactional
    public ResponseEntity<Object> updateCredentials(Principal principal,
                                                    @Valid @RequestBody AccountDetailsUpdate updateInfo) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);
        mapper.update(myAccount, updateInfo);

        myAccount = accountService.updateAccount(myAccount);
        AccountDetailsResponse response = mapper.map(myAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account updated", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Delete account",
            description = "Endpoint for deleting account; user must be authorised. " +
                    "All bound entities such as user media, likes, profile info are cascade deleted")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "204",
            description = "Account successfully deleted"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @DeleteMapping()
    @Transactional
    public ResponseEntity<Object> deleteAccount(Principal principal) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);
        accountService.deleteById(myAccount.getId());
        //PRINCIPAL = null?
        return ResponseEntity.noContent().build();
    }
}