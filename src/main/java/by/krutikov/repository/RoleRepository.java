package by.krutikov.repository;

import by.krutikov.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findById(Integer id);

    @Query(value = "select * from roles", nativeQuery = true)
    List<Role> findAll();
}
