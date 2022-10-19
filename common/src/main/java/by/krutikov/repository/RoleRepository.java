package by.krutikov.repository;

import by.krutikov.domain.Role;
import by.krutikov.domain.enums.SystemRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(
            value = "select r.id, r.name, r.date_created, r.date_modified " +
                    "from bandhub.roles r " +
                    "inner join bandhub.l_account_roles lar " +
                    "on r.id = lar.role_id " +
                    "where lar.account_id = :id",
            nativeQuery = true
    )
    List<Role> findRolesByAccountId(@Param("id") Long id);

    @Query(
            value = "select r.id " +
                    "from bandhub.roles r " +
                    "where r.name = :roleName",
            nativeQuery = true
    )
    Integer findRoleIdByRoleName(@Param("roleName") String roleName);

    Optional<Role> findRoleByRoleName(SystemRoles roleName);
}