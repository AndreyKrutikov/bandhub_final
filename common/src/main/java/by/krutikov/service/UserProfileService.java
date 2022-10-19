package by.krutikov.service;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import org.locationtech.jts.geom.Point;

import java.util.List;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfile profile);

    UserProfile updateUserProfile(UserProfile profile);

    void deleteById(Long id);

    UserProfile findById(Long id);

    List<UserProfile> findAll();

    List<UserProfile> findAllByDistanceTo(Point userLocation);

    UserProfile addMedia(UserProfile profile, Media media);
}
