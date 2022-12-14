package by.krutikov.security;

import by.krutikov.domain.Account;
import by.krutikov.domain.Role;
import by.krutikov.domain.enums.SystemRoles;
import by.krutikov.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Account userAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user registered by email " + email + " found."));
        /*We are creating Spring Security User object*/
        return new org.springframework.security.core.userdetails.User(
                userAccount.getEmail(),
                userAccount.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        userAccount.getRoles()
                                .stream()
                                .map(Role::getRoleName)
                                .map(SystemRoles::name)
                                .collect(Collectors.joining(","))
                )
        );
    }
}
