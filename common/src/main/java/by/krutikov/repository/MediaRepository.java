package by.krutikov.repository;

import by.krutikov.domain.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findAllByUserProfileId(Long id);

    Page<Media> findAll(Pageable pageable);
}
