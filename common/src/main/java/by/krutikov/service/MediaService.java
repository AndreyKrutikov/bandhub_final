package by.krutikov.service;

import by.krutikov.domain.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MediaService {
    Media createMedia(Media media);

    Media updateMedia(Media media);

    Media findById(Long id);

    List<Media> findAll();

    Page<Media> findAll(Pageable pageable);

    List<Media> findAllByUserProfileId(Long id);

    void deleteById(Long id);
}
