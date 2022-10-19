package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.request.AccountInfo;
import by.krutikov.dto.response.CreateAccountResponse;
import by.krutikov.mappers.AccountMapper;
import by.krutikov.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper;

    @GetMapping//admin, moderator
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", accountService.findAll()), HttpStatus.OK
        );
    }

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

        //think whether to return account or account response

        return new ResponseEntity<>(
                Collections.singletonMap("account updated", currentAccount), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")//admin//moderator//registereduser
    @Transactional
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}