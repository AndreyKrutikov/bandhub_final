package by.krutikov.service;

import by.krutikov.domain.Media;

import java.util.List;

public interface MediaService {
    Media createMedia(Media media);

    Media updateMedia(Media media);

    Media findById(Long id);

    List<Media> findAll();

    List<Media> findAllByUserProfileId(Long id);

    void deleteById(Long id);
}
