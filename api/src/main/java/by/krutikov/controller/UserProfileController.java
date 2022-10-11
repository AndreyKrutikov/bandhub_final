package by.krutikov.controller;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.UserProfileDto;
import by.krutikov.repository.AccountRepository;
import by.krutikov.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final UserProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all profiles", profileRepository.findAll()), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("found by id", profileRepository.findById(id)), HttpStatus.OK
        );
    }

    @GetMapping("/{id}/distance-sorted")
    public ResponseEntity<Object> getAllByDistance(@PathVariable long id) {
        UserProfile userProfile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Point userLocation = userProfile.getLocation();

        return new ResponseEntity<>(
                Collections.singletonMap("distance sorted",
                        profileRepository.findAllProfilesOrderedByDistance(userLocation)), HttpStatus.OK
        );
    }

    @GetMapping("/{id}/distance-sorted-not-player")
    public ResponseEntity<Object> getByDistanceAndInstruments(@PathVariable long id) {
        UserProfile userProfile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Point userLocation = userProfile.getLocation();
        String userInstrument  = userProfile.getInstrument().toString();

        return new ResponseEntity<>(
                Collections.singletonMap("instrument and distance sorted",
                        profileRepository.findProfilesHavingOtherInstrumentOrderedByDistance(userLocation, userInstrument)), HttpStatus.OK
        );
    }

    ///{id1}/REACTION
    


    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Object> createNewUserProfile(@RequestBody UserProfileDto body) {
        Timestamp now = new Timestamp(new Date().getTime());

        UserProfile profile = new UserProfile();
        profile.setDisplayedName(body.getDisplayedName());
        profile.setAccount(accountRepository.findById(body.getAccountId()).get());
        profile.setLon(body.getLon());
        profile.setLat(body.getLat());
        profile.setPhoneNumber(body.getPhoneNumber());
        profile.setInstrument(InstrumentType.valueOf(body.getInstrument()));
        profile.setExperience(ExperienceLevel.valueOf(body.getExperienceLevel()));
        profile.setMedia(null);
        profile.setDescription(body.getDescription());
        profile.setDateCreated(now);
        profile.setDateModified(now);
        profile.setIsVisible(true);

        UserProfile created = profileRepository.save(profile);

        Map<String, Object> model = new HashMap<>();
        model.put("profile created", created);
        model.put("profiles", profileRepository.findAll());

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }
}
