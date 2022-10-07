package by.krutikov.repository;

import by.krutikov.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    @Modifying
    @Query(value = "insert into l_account_roles(account_id, role_id) values (:account_id, :role_id)", nativeQuery = true)
    void createRoleRow(@Param("account_id") Long userId, @Param("role_id") Integer roleId);

}
