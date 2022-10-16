package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.AuthRequestDto;
import by.krutikov.repository.AccountRepository;
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
    private final AccountRepository accountRepository;

    @GetMapping//admin, moderator
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", accountService.findAll()), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", accountRepository.findById(id)), HttpStatus.OK
        );
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<Object> findByEmail(@RequestParam String email) {
        return new ResponseEntity<>(
                Collections.singletonMap("account", accountRepository.findByEmail(email)), HttpStatus.OK
        );
    }

    @PostMapping
    @Transactional//all users
    public ResponseEntity<Object> createAccount(@RequestBody AuthRequestDto body) {
        Account account = new Account();
        account.setEmail(body.getEmail());
        account.setPassword(body.getPassword());

        account = accountService.createAccount(account);

        return new ResponseEntity<>(
                Collections.singletonMap("account created", account), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")//admin//moderator//registereduser //patch better?
    @Transactional
    public ResponseEntity<Object> updateAccount(@PathVariable Long id,
                                                @RequestBody AuthRequestDto body) {
        Account currentAccount = accountService.findById(id);
        currentAccount.setEmail(body.getEmail());
        currentAccount.setPassword(body.getPassword());
        currentAccount = accountService.updateAccount(currentAccount);

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