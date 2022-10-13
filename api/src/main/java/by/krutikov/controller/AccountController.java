package by.krutikov.controller;

import by.krutikov.domain.Account;
import by.krutikov.dto.AuthRequestDto;
import by.krutikov.repository.AccountRepository;
import by.krutikov.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all accounts", accountRepository.findAll()), HttpStatus.OK
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

    @PostMapping()
    @Transactional
    public ResponseEntity<Object> createAccount(@RequestBody AuthRequestDto body) {
        Timestamp now = new Timestamp(new Date().getTime());

        Account account = new Account();
        account.setPassword(body.getPassword());
        account.setEmail(body.getEmail());
        account.setDateCreated(now);
        account.setDateModified(now);
        account.setIsLocked(false);

        Account createdAccount = accountRepository.save(account);

        accountRepository.createRoleRow(createdAccount.getId(), roleRepository.findById(1).get().getId());

        Map<String, Object> model = new HashMap<>();
        model.put("account created", createdAccount);
        model.put("accounts", accountRepository.findAll());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@PathVariable Long id, @RequestBody  AuthRequestDto body) {
        Timestamp now = new Timestamp(new Date().getTime());

        Account currentAccount = accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        currentAccount.setPassword(body.getPassword());
        currentAccount.setEmail(body.getEmail());
        currentAccount.setDateModified(now);

        currentAccount = accountRepository.save(currentAccount);

        Map<String, Object> model = new HashMap<>();
        model.put("account updated", currentAccount);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}