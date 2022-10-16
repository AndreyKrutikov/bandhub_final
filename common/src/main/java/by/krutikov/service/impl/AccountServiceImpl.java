package by.krutikov.service.impl;

import by.krutikov.domain.Account;
import by.krutikov.domain.enums.SystemRoles;
import by.krutikov.repository.AccountRepository;
import by.krutikov.repository.RoleRepository;
import by.krutikov.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final SystemRoles DEFAULT_ACCOUNT_ROLE = SystemRoles.ROLE_USER;
    private static final SystemRoles DEFAULT_ANONYMOUS_ROLE = SystemRoles.ROLE_ANONYMOUS;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public Account createAccount(Account account) {
        account = accountRepository.save(account);
        Long accountId = account.getId();

        accountRepository.createRoleRow(
                accountId,
                roleRepository.findRoleIdByRoleName(DEFAULT_ACCOUNT_ROLE.getName())
        );
        accountRepository.createRoleRow(
                accountId,
                roleRepository.findRoleIdByRoleName(DEFAULT_ANONYMOUS_ROLE.getName())
        );

        return accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
    }

    public Account updateAccount(Account account) {
        //account = accountRepository.save(account);
        //Long accountId = account.getId();

        return accountRepository.save(account);
                //accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

}
