package by.krutikov.controller;

import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.UserProfileDetails;
import by.krutikov.mappers.UserProfileMapper;
import by.krutikov.service.AccountService;
import by.krutikov.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class UserProfileController {
    private final UserProfileService profileService;
    private final AccountService accountService;
    private final UserProfileMapper mapper;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all profiles", mapper.toResponseList(profileService.findAll())), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("found by id", mapper.map(profileService.findById(id))), HttpStatus.OK
        );
    }

    @GetMapping("/{id}/find-distance")
    public ResponseEntity<Object> getAllByDistance(@PathVariable long id) {
        UserProfile userProfile = profileService.findById(id);
        Point userLocation = userProfile.getLocation();

        return new ResponseEntity<>(
                Collections.singletonMap("distance sorted",
                        mapper.toResponseList(profileService.findAllByDistanceTo(userLocation))), HttpStatus.OK
        );
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> createNewUserProfile(@Valid @RequestBody UserProfileDetails request) {
        UserProfile profile = mapper.map(request);
        profile.setAccount(accountService.findById(request.getAccountId()));
        profile = profileService.createUserProfile(profile);

        return new ResponseEntity<>(
                Collections.singletonMap("profile created", mapper.map(profile)), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateUserProfile(@PathVariable Long id,
                                                    @Valid @RequestBody UserProfileDetails updateInfo) {
        UserProfile currentProfile = profileService.findById(id);
        mapper.update(currentProfile, updateInfo);

        currentProfile = profileService.updateUserProfile(currentProfile);

        return new ResponseEntity<>(
                Collections.singletonMap("profile updated", mapper.map(currentProfile)), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")//admin//moderator//registereduser
    @Transactional
    public ResponseEntity<Object> deleteUserProfile(@PathVariable Long id) {
        profileService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
