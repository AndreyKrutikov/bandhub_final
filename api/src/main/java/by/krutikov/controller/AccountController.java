package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.request.UpdateAccountStatusRequest;
import by.krutikov.dto.response.AccountDetailsResponse;
import by.krutikov.mappers.AccountMapper;
import by.krutikov.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collections;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @Operation(summary = "Get all accounts",
            description = "Get all accounts, pageable. Admin/moderator use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Accounts found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = AccountDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Admin/moderator authorities only",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<Object> findAll(@ParameterObject Pageable pageable) {
        Page<Account> result = accountService.findAll(pageable);

        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", mapper.toResponseList(result)), HttpStatus.OK
        );
    }

    @Operation(summary = "Get account by id",
            description = "Get account by id, admin/moderator use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Account found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Admin/moderator/account_owner authorities only",
            content = @Content
    )
    @PreAuthorize(
            "hasAnyRole('ADMIN', 'MODERATOR') " +
                    "or principal.username.equals(@accountServiceImpl.findById(#id).email)"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Valid @PathVariable @NotNull @Positive Long id) {
        AccountDetailsResponse byId = mapper.map(accountService.findById(id));

        return new ResponseEntity<>(
                Collections.singletonMap("account", byId), HttpStatus.OK
        );
    }

    @Operation(summary = "Find account by email",
            description = "Find account by email, admin/moderator use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Account found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Admin/moderator authorities only",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/find-by-email")
    public ResponseEntity<Object> findByEmail(@RequestParam @NotNull @Email String email) {
        AccountDetailsResponse byEmail = mapper.map(accountService.findByEmail(email));

        return new ResponseEntity<>(
                Collections.singletonMap("account", byEmail), HttpStatus.OK
        );
    }

    @Operation(summary = "Update credentials by account id",
            description = "Update account credentials")
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
            responseCode = "404",
            description = "Account not found",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Authorised user only",
            content = @Content
    )
    @PutMapping("/{id}")
    @PreAuthorize("principal.username.equals(@accountServiceImpl.findById(#id).email)")
    @Transactional
    public ResponseEntity<Object> updateCredentials(@PathVariable Long id,
                                                    @Valid @RequestBody AccountDetails updateInfo) {
        Account currentAccount = accountService.findById(id);
        mapper.update(currentAccount, updateInfo);

        currentAccount = accountService.updateAccount(currentAccount);

        AccountDetailsResponse updated = mapper.map(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account updated", updated), HttpStatus.OK
        );
    }

    @Operation(summary = "Update account status by id",
            description = "Set account status locked or unlocked, admin/moderator use only")
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
            responseCode = "404",
            description = "Account not found",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Authorised user only",
            content = @Content
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Transactional
    public ResponseEntity<Object> updateStatus(@PathVariable Long id,
                                               @Valid @RequestBody UpdateAccountStatusRequest request) {
        Account currentAccount = accountService.findById(id);
        currentAccount.setIsLocked(request.getIsLocked());

        currentAccount = accountService.updateAccount(currentAccount);

        AccountDetailsResponse updated = mapper.map(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account status updated", updated), HttpStatus.OK
        );
    }

    @Operation(summary = "Delete account by id",
            description = "Delete account, admin/moderator/authorised user use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAnyRole('ADMIN', 'MODERATOR') " +
                    "or principal.username.equals(@accountServiceImpl.findById(#id).email)"
    )
    @Transactional
    public ResponseEntity<Object> deleteAccount(@Valid @PathVariable @NotNull @Positive Long id) {
        accountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
