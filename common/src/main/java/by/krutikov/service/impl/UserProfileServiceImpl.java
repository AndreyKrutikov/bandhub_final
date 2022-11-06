package by.krutikov.service.impl;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.repository.UserProfileRepository;
import by.krutikov.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

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
    public Page<UserProfile> findAll(Pageable pageable) {
        return userProfileRepository.findAll(pageable);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileRepository.findAll();
    }

    @Override
    public List<UserProfile> findAllDistanceOrdered(Point userLocation) {
        return userProfileRepository.findDistanceOrdered(userLocation);
    }

    @Override
    public List<UserProfile> findByCriteriaDistanceOrdered(Point userLocation,
                                                           InstrumentType instrumentType,
                                                           ExperienceLevel experienceLevel) {
        return userProfileRepository.findByCriteriaDistanceOrdered(
                userLocation,
                instrumentType.getName(),
                experienceLevel.getName());
    }

    public List<UserProfile> filterSeenBefore(UserProfile currentProfile, List<UserProfile> foundProfiles) {
        List<UserProfile> seenProfiles = currentProfile.getMyReactions()
                .stream()
                .map(Reaction::getToProfile)
                .collect(Collectors.toList());
        foundProfiles.removeAll(seenProfiles);

        return foundProfiles;
    }
}
