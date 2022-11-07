package by.krutikov.service.impl;

import by.krutikov.domain.Role;
import by.krutikov.domain.enums.SystemRoles;
import by.krutikov.repository.RoleRepository;
import by.krutikov.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findRolesByAccountId(Long id) {
        return roleRepository.findRolesByAccountId(id);
    }

    @Override
    public Role findRoleByRoleName(SystemRoles roleName) {
        return roleRepository.findRoleByRoleName(roleName).orElseThrow(EntityNotFoundException::new);
    }
}
