package by.krutikov.repository;

import by.krutikov.domain.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByFromProfileAndToProfile(Long fromProfileId, Long toProfileId);

    List<Reaction> findReactionsByFromProfile(Long fromProfileId);

    List<Reaction> findReactionsByToProfile(Long fromProfileId);

    @Modifying
    @Query(
            value = "delete from Reaction r " +
                    "where r.fromProfile.id=:fromId " +
                    "and r.toProfile.id=:toId"
    )
    void deleteByFromProfileIdAndToProfileId(@Param("fromId") Long fromId, @Param("toId") Long toId);
}
