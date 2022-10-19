package by.krutikov.repository;

import by.krutikov.domain.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByFromProfileAndToProfile(Long fromProfileId, Long toProfileId);
}
