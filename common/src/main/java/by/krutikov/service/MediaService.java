package by.krutikov.service;

import by.krutikov.domain.Media;

import java.util.List;

public interface MediaService {
    Media createMedia(Media media);

    Media updateMedia(Media media);

    Media findById(Long id);

    List<Media> findAll();

    void deleteById(Long id);

    void addMedia();
}
