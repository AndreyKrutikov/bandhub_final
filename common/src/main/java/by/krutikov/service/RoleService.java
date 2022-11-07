package by.krutikov.service;

import by.krutikov.domain.Role;
import by.krutikov.domain.enums.SystemRoles;

import java.util.List;

public interface RoleService {
    List<Role> findRolesByAccountId(Long id);

    Role findRoleByRoleName(SystemRoles roleName);
}
