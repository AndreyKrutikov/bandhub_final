package by.krutikov.service.impl;

import by.krutikov.domain.Account;
import by.krutikov.domain.Role;
import by.krutikov.domain.enums.SystemRoles;
import by.krutikov.repository.AccountRepository;
import by.krutikov.repository.RoleRepository;
import by.krutikov.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;

import static by.krutikov.domain.enums.SystemRoles.ROLE_ANONYMOUS;
import static by.krutikov.domain.enums.SystemRoles.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final SystemRoles DEFAULT_USER_ROLE = ROLE_USER;
    private static final SystemRoles DEFAULT_ANONYMOUS_ROLE = ROLE_ANONYMOUS;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public Account createAccount(Account account) {
        addRole(account, roleRepository.findRoleByRoleName(DEFAULT_USER_ROLE)
                .orElseThrow(EntityNotFoundException::new));
        addRole(account, roleRepository.findRoleByRoleName(DEFAULT_ANONYMOUS_ROLE)
                .orElseThrow(EntityNotFoundException::new));

        return accountRepository.save(account);
    }

    @Override
    public void addRole(Account account, Role role) {
        if (account.getRoles() == null) {
            account.setRoles(new HashSet<>());
        }
        account.getRoles().add(role);
        role.getAccounts().add(account);
    }

    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
