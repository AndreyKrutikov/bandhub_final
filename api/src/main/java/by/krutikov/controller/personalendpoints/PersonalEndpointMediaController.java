package by.krutikov.controller.personalendpoints;

import by.krutikov.domain.Account;
import by.krutikov.domain.Media;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.MediaDetails;
import by.krutikov.dto.request.MediaDetailsUpdate;
import by.krutikov.dto.response.MediaDetailsResponse;
import by.krutikov.mappers.MediaMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import by.krutikov.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-media")
public class PersonalEndpointMediaController {
    private final MediaService mediaService;
    private final AccountService accountService;
    private final MediaMapper mapper;

    @Operation(summary = "Show all profile media",
            description = "Endpoint shows list of user media, if there is; user must be authorised")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Media details",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MediaDetailsResponse.class))
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping()
    public ResponseEntity<Object> showAllMedia(Principal principal) {
        String email = PrincipalUtil.getEmail(principal);
        Account myAccount = accountService.findByEmail(email);
        Set<Media> media = myAccount.getUserProfile().getMedia();

        List<MediaDetailsResponse> response = mapper.toList(media);

        return new ResponseEntity<>(
                Collections.singletonMap("all profile media", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Create new media",
            description = "Add new media to user profile. Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "201",
            description = "New media created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MediaDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @PostMapping
    @Transactional
    public ResponseEntity<Object> createMedia(Principal principal,
                                              @Valid @RequestBody MediaDetails createInfo) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        Media newMedia = mapper.map(createInfo);
        newMedia.setUserProfile(myProfile);
        newMedia = mediaService.createMedia(newMedia);

        MediaDetailsResponse response = mapper.map(newMedia);

        return new ResponseEntity<>(
                Collections.singletonMap("media created", response), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Create new media",
            description = "Add new media to user profile. Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "New media created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MediaDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Media not found",
            content = @Content
    )
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateMedia(Principal principal,
                                              @PathVariable Long id,
                                              @Valid @RequestBody MediaDetailsUpdate updateInfo) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        Media toBeUpdated = myProfile.getMedia()
                .stream()
                .filter(m -> id.equals(m.getId()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);//todo throwing persistence exception

        mapper.update(toBeUpdated, updateInfo);
        toBeUpdated = mediaService.updateMedia(toBeUpdated);

        MediaDetailsResponse response = mapper.map(toBeUpdated);

        return new ResponseEntity<>(
                Collections.singletonMap("media updated", response), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Delete media",
            description = "Delete media, Account owner authorities only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "204",
            description = "Media successfully deleted"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Media not found",
            content = @Content
    )
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteMedia(Principal principal,
                                              @PathVariable Long id) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        Media toBeDeleted = myProfile.getMedia()
                .stream()
                .filter(media -> id.equals(media.getId()))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);//todo throwing persistence exception

        mediaService.deleteById(toBeDeleted.getId());

        return ResponseEntity.noContent().build();
    }
}
