package by.krutikov.service.impl;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.repository.MediaRepository;
import by.krutikov.repository.UserProfileRepository;
import by.krutikov.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final UserProfileRepository profileRepository;


    @Override
    public Media createMedia(Media media) {
        return mediaRepository.save(media);
    }

    public void addProfile(Media media, UserProfile userProfile) {
        userProfile.setMedia(media);
        media.setUserProfile(userProfile);
    }

    @Override
    public Media updateMedia(Media media) {
        return mediaRepository.save(media);
    }

    @Override
    public Media findById(Long id) {
        return mediaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Media> findAll() {
        return mediaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        mediaRepository.deleteById(id);
    }

    @Override
    public void addMedia() {

    }
}
