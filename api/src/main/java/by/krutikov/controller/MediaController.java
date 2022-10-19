package by.krutikov.controller;

import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.MediaRequest;
import by.krutikov.repository.MediaRepository;
import by.krutikov.repository.UserProfileRepository;
import by.krutikov.service.UserProfileService;
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
    private  final UserProfileService userProfileService;
    private final UserProfileRepository profileRepository;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(
                Collections.singletonMap("all media", mediaRepository.findAll()), HttpStatus.OK
        );
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
