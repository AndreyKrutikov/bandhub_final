package by.krutikov.service;

import by.krutikov.domain.UserProfile;
import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfile profile);

    UserProfile updateUserProfile(UserProfile profile);

    void deleteById(Long id);

    UserProfile findById(Long id);

    //UserProfile findByAccountId(Long accountId);

    List<UserProfile> findAll();

    Page<UserProfile> findAll(Pageable pageable);

    List<UserProfile> findAllDistanceOrdered(Point userLocation);

    List<UserProfile> findByCriteriaDistanceOrdered(Point userLocation,
                                                    InstrumentType instrumentType,
                                                    ExperienceLevel experienceLevel);

    List<UserProfile> filterSeenBefore(UserProfile currentProfile, List<UserProfile> foundProfiles);
}
