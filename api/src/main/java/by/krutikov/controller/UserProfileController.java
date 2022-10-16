package by.krutikov.controller;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.UserProfileDto;
import by.krutikov.repository.AccountRepository;
import by.krutikov.repository.UserProfileRepository;
import by.krutikov.service.AccountService;
import by.krutikov.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class UserProfileController {
    private final UserProfileService profileService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all profiles", profileService.findAll()), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("found by id", profileService.findById(id)), HttpStatus.OK
        );
    }

//    @GetMapping("/{id}/distance-sorted")
//    public ResponseEntity<Object> getAllByDistance(@PathVariable long id) {
//        UserProfile userProfile = profileService.findById(id);
//        Point userLocation = userProfile.getLocation();
//
//        return new ResponseEntity<>(
//                Collections.singletonMap("distance sorted",
//                        profileRepository.findAllProfilesOrderedByDistance(userLocation)), HttpStatus.OK
//        );
//    }

//    @GetMapping("/{id}/distance-sorted-not-player")
//    public ResponseEntity<Object> getByDistanceAndInstruments(@PathVariable long id) {
//        UserProfile userProfile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        Point userLocation = userProfile.getLocation();
//        String userInstrument  = userProfile.getInstrument().toString();
//
//        return new ResponseEntity<>(
//                Collections.singletonMap("instrument and distance sorted",
//                        profileRepository.findProfilesHavingOtherInstrumentOrderedByDistance(userLocation, userInstrument)), HttpStatus.OK
//        );
//    }


    @PostMapping
    @Transactional
    public ResponseEntity<Object> createNewUserProfile(@RequestBody UserProfileDto body) {

        UserProfile profile = new UserProfile();
        profile.setAccount(accountService.findById(body.getAccountId()));
        profile.setDisplayedName(body.getDisplayedName());
        profile.setLon(body.getLon());
        profile.setLat(body.getLat());
        profile.setPhoneNumber(body.getPhoneNumber());
        profile.setInstrument(InstrumentType.valueOf(body.getInstrument()));
        profile.setExperience(ExperienceLevel.valueOf(body.getExperienceLevel()));
        profile.setDescription(body.getDescription());

        return new ResponseEntity<>(
                Collections.singletonMap("profile created", profileService.createUserProfile(profile)), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateUserProfile(@PathVariable Long id,
                                                    @RequestBody UserProfileDto body) {

        UserProfile currentProfile = profileService.findById(id);
        currentProfile.setDisplayedName(body.getDisplayedName());
        currentProfile.setLon(body.getLon());
        currentProfile.setLat(body.getLat());
        currentProfile.setPhoneNumber(body.getPhoneNumber());
        currentProfile.setInstrument(InstrumentType.valueOf(body.getInstrument()));
        currentProfile.setExperience(ExperienceLevel.valueOf(body.getExperienceLevel()));
        currentProfile.setDescription(body.getDescription());

        return new ResponseEntity<>(
                Collections.singletonMap("profile updated", profileService.updateUserProfile(currentProfile)), HttpStatus.OK
        );
    }
}
