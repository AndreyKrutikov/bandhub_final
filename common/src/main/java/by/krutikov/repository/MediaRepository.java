package by.krutikov.repository;

import by.krutikov.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByUserProfileId(Long id);
}
