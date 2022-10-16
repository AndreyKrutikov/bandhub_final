package by.krutikov.service;

import by.krutikov.domain.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Account account);

    Account findById(Long id);

    List<Account> findAll();

    void deleteById(Long aLong);
}
