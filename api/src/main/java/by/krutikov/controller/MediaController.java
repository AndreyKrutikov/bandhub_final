package by.krutikov.controller;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.MediaRequestDto;
import by.krutikov.repository.MediaRepository;
import by.krutikov.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaRepository mediaRepository;

    private final UserProfileRepository profileRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all media", mediaRepository.findAll()), HttpStatus.OK
        );
    }

    @PostMapping("/{id}/create")
    @Transactional
    public ResponseEntity<Object> addMedia(@PathVariable Long id, @RequestBody MediaRequestDto requestDto) {
        Timestamp now = new Timestamp(new Date().getTime());

        UserProfile userProfile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Media media = new Media();
        media.setPhotoUrl(requestDto.getPhotoUrl());
        media.setDemoUrl(requestDto.getDemoUrl());
        media.setDateCreated(now);
        media.setDateModified(now);

        Media createdMedia = mediaRepository.save(media);
        userProfile.setMedia(createdMedia);

        return ResponseEntity.ok(Collections.singletonMap("media", createdMedia));
    }


//        @PostMapping("/{id}")
//    public ResponseEntity<Map<Object, Object>> uploadUserPhoto(@PathVariable Long id,
//                                                               @RequestBody MultipartFile file) throws IOException {
//
//        HibernateUser user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
//        byte[] imageBytes = file.getBytes();
//        String imageLink = amazonUploadFileService.uploadFile(imageBytes, id);
//
//        user.setPhotoLink(imageLink);
//        userRepository.save(user);
//
//        return new ResponseEntity<>(Collections.singletonMap("imageLink", imageLink), HttpStatus.CREATED);
//    }

}
