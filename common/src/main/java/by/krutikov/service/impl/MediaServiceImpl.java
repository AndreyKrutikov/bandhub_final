package by.krutikov.service.impl;

import by.krutikov.domain.Media;
import by.krutikov.repository.MediaRepository;
import by.krutikov.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;

    @Override
    public Media createMedia(Media media) {
        return mediaRepository.save(media);
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
    public Page<Media> findAll(Pageable pageable) {
        return mediaRepository.findAll(pageable);
    }

    @Override
    public List<Media> findAllByUserProfileId(Long id) {
        return mediaRepository.findAllByUserProfileId(id);
    }

    @Override
    public void deleteById(Long id) {
        mediaRepository.deleteById(id);
    }
}
