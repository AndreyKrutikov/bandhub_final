package by.krutikov.service;

import by.krutikov.domain.Account;
import by.krutikov.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Account account);

    Account findById(Long id);

    Page<Account> findAll(Pageable pageable);

    void deleteById(Long id);

    Account findByEmail(String email);

    void addRole (Account account, Role role);
}
