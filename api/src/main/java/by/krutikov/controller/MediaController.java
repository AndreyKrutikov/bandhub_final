package by.krutikov.controller;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.MediaDetails;
import by.krutikov.mappers.MediaMapper;
import by.krutikov.service.MediaService;
import by.krutikov.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;

    private final UserProfileService profileService;
    private final MediaMapper mapper;

    @Operation(summary = "Get all media",
            description = "Get all media, admin/moderator use only",
            parameters = {
            })
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all media", mediaService.findAll()), HttpStatus.OK
        );
    }

    @Operation(summary = "Get media by id",
            description = "Get media by id, admin/moderator use only",
            parameters = {
            })
    @GetMapping("/by-profile-id")
    public ResponseEntity<Object> getAllByProfileId(@RequestParam(name = "profileId") Long id) {
        return new ResponseEntity<>(
                Collections.singletonMap("by profile id", mediaService.findAllByUserProfileId(id)),
                HttpStatus.OK
        );
    }


    @Operation(summary = "Update media by media id",
            description = "Update user profile media, available for logged-in user",
            parameters = {
            })
    @PutMapping("/{id}")
    @Transactional//all users
    public ResponseEntity<Object> updateMedia(@PathVariable Long id,
                                              @RequestBody MediaDetails updateInfo) {
        Media media = mediaService.findById(id);
        mapper.update(media, updateInfo);
        media.getUserProfile().getMedia().add(media);

        media = mediaService.updateMedia(media);

        return new ResponseEntity<>(
                Collections.singletonMap("media updated", media), HttpStatus.OK
        );
    }


    @Operation(summary = "Delete media by id",
            description = "Delete media, admin/moderator/logged-in-user use only",
            parameters = {

            })
    @DeleteMapping("/{id}")//admin//moderator//registereduser
    @Transactional
    public ResponseEntity<Object> deleteMedia(@PathVariable Long id) {
        Media media = mediaService.findById(id);
        UserProfile profile = media.getUserProfile();
        profile.getMedia().remove(media);

        mediaService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
