package by.krutikov.service;

import by.krutikov.domain.UserProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfile profile);

    UserProfile updateUserProfile(UserProfile profile);

    void deleteById(Long id);

    UserProfile findById(Long id);

    List<UserProfile> findAll();
}
