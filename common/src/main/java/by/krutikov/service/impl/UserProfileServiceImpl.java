package by.krutikov.service.impl;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.repository.UserProfileRepository;
import by.krutikov.service.MediaService;
import by.krutikov.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final MediaService mediaService;

    @Override
    public UserProfile createUserProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @Override
    public UserProfile updateUserProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    @Override
    public void deleteById(Long id) {
        userProfileRepository.deleteById(id);
    }

    @Override
    public UserProfile findById(Long id) {
        return userProfileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    @Override
    public List<UserProfile> findAllByDistanceTo(Point userLocation) {
        return userProfileRepository.findAllByDistanceTo(userLocation);
    }

    @Override
    public UserProfile addMedia(UserProfile profile, Media media) {
        profile.setMedia(media);
        media.setUserProfile(profile);
        return userProfileRepository.save(profile);
    }
}
