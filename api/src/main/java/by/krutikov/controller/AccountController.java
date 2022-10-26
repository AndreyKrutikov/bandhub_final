package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountDetails;
import by.krutikov.dto.request.UpdateAccountStatusRequest;
import by.krutikov.mappers.AccountMapper;
import by.krutikov.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @Operation(summary = "Get all accounts",
            description = "Get all accounts, admin/moderator use only",
            parameters = {
            })
//    @Params({
//            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
//            @ApiImplicitParam(name = "query", defaultValue = "query", required = false, paramType = "query", dataType = "string")
//    }))
    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", mapper.toList(accountService.findAll())), HttpStatus.OK
        );
    }

    @Operation(summary = "Get account by id",
            description = "Get account by id, admin/moderator use only",
            parameters = {
            })
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Valid @PathVariable @NotNull @Positive Long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", mapper.map(accountService.findById(id))), HttpStatus.OK
        );
    }

    @Operation(summary = "Get account by email",
            description = "Get account by email, admin/moderator use only",
            parameters = {
            })
    @GetMapping("/find-by-email")// method like this exists in security controller
    public ResponseEntity<Object> findByEmail(@RequestParam @NotNull @Email String email) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", mapper.map(accountService.findByEmail(email))), HttpStatus.OK
        );
    }

    @Operation(summary = "Create new account",
            description = "Add new user account to application",
            parameters = {
            })
    @PostMapping
    @Transactional//all users
    public ResponseEntity<Object> createAccount(@Valid @RequestBody AccountDetails createInfo) {
        Account account = mapper.map(createInfo);
        account = accountService.createAccount(account);

        return new ResponseEntity<>(
                Collections.singletonMap("account created", mapper.map(account)), HttpStatus.CREATED
        );
    }


    @Operation(summary = "Update credentials by account id",
            description = "Update account credentials, available for logged-in user",
            parameters = {
            })
    @PutMapping("/{id}")//registereduser //patch better?
    @Transactional
    public ResponseEntity<Object> updateAccount(@PathVariable Long id,
                                                @Valid @RequestBody AccountDetails updateInfo) {
        Account currentAccount = accountService.findById(id);
        mapper.update(currentAccount, updateInfo);

        currentAccount = accountService.updateAccount(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account updated", mapper.map(currentAccount)), HttpStatus.OK
        );
    }

    @Operation(summary = "Update account status by id",
            description = "Set account status locked or unlocked, admin/moderator use only",
            parameters = {

            })
    @PatchMapping("/{id}")//admin/moderator
    @Transactional
    public ResponseEntity<Object> updateStatus(@PathVariable Long id,
                                               @Valid @RequestBody UpdateAccountStatusRequest request) {
        Account currentAccount = accountService.findById(id);
        currentAccount.setIsLocked(request.getIsLocked());

        currentAccount = accountService.updateAccount(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account status updated", mapper.map(currentAccount)), HttpStatus.OK
        );
    }

    @Operation(summary = "Delete account by id",
            description = "Delete account, admin/moderator/logged-in-user use only",
            parameters = {

            })
    @DeleteMapping("/{id}")//admin//moderator//registereduser
    @Transactional
    public ResponseEntity<Object> deleteAccount(@Valid @PathVariable @NotNull @Positive Long id) {
        accountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}