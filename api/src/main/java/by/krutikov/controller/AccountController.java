package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountInfo;
import by.krutikov.dto.request.UpdateAccountStatusRequest;
import by.krutikov.dto.response.CreateAccountResponse;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @Operation(summary = "Get all accounts",
            description = "Get all accounts, available for admin / moderator only",
            parameters = {


            })

//    @Params({
//            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
//            @ApiImplicitParam(name = "query", defaultValue = "query", required = false, paramType = "query", dataType = "string")
//    }))
    @GetMapping//admin, moderator
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", accountService.findAll()), HttpStatus.OK
        );
    }
//parameters = {
//                    @Parameter(in = "path", name = "subscriptionId",
//                            required = true, description = "parameter description",
//                            allowEmptyValue = true, allowReserved = true,
//                            schema = @Schema(
//                                    type = "string",
//                                    format = "uuid",
//                                    description = "the generated UUID",
//                                    accessMode = Schema.AccessMode.READ_ONLY)
//                    )},



    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", accountService.findById(id)), HttpStatus.OK
        );
    }

    @GetMapping("/find-by-email")// method like this exists in security controller
    public ResponseEntity<Object> findByEmail(@RequestParam String email) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", accountService.findByEmail(email)), HttpStatus.OK
        );
    }

    @PostMapping
    @Transactional//all users
    public ResponseEntity<Object> createAccount(@RequestBody AccountInfo createInfo) {
        Account account = mapper.map(createInfo);
        account = accountService.createAccount(account);

        CreateAccountResponse response = mapper.map(account);

        return new ResponseEntity<>(
                Collections.singletonMap("account created", response), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")//admin//moderator//registereduser //patch better?
    @Transactional
    public ResponseEntity<Object> updateAccount(@PathVariable Long id,
                                                @RequestBody AccountInfo updateInfo) {
        Account currentAccount = accountService.findById(id);
        mapper.update(currentAccount, updateInfo);

        currentAccount = accountService.updateAccount(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account updated", currentAccount), HttpStatus.OK
        );
    }

    @Operation(summary = "Update account status",
            description = "Set account status locked or unlocked; Admin/moderator use only",
            parameters = {


            })
    @PatchMapping("/{id}")//admin/moderator
    @Transactional
    public ResponseEntity<Object> updateStatus(@PathVariable Long id,
                                               @RequestBody UpdateAccountStatusRequest request) {
        Account currentAccount = accountService.findById(id);
        currentAccount.setIsLocked(request.getIsLocked());

        currentAccount = accountService.updateAccount(currentAccount);

        return new ResponseEntity<>(
                Collections.singletonMap("account status updated", currentAccount), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")//admin//moderator//registereduser
    @Transactional
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}