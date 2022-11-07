package by.krutikov.controller.commonendpoints;

import by.krutikov.domain.Media;
import by.krutikov.dto.response.MediaDetailsResponse;
import by.krutikov.mappers.MediaMapper;
import by.krutikov.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/media")
public class MediaController {
    private final MediaService mediaService;
    private final MediaMapper mapper;

    @Operation(summary = "Get all app media",
            description = "Get all app media, pageable. Admin/moderator use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Media found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = MediaDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Admin/moderator authorities only",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<Object> findAllAppMediaPageable(@ParameterObject Pageable pageable) {
        Page<Media> mediaPage = mediaService.findAll(pageable);
        Page<MediaDetailsResponse> responsePage = mediaPage.map(mapper::map);

        return new ResponseEntity<>(
                Collections.singletonMap("media pageable", responsePage), HttpStatus.OK
        );
    }

    @Operation(summary = "Get media by profile id",
            description = "Get media by profile id")
    @ApiResponse(
            responseCode = "200",
            description = "Media by profile id found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = MediaDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Profile by request parameter not found",
            content = @Content
    )
    @GetMapping("/by-profile-id")
    public ResponseEntity<Object> findAllMediaByProfileId(@RequestParam(name = "profileId") Long id) {
        List<Media> allByUserProfileId = mediaService.findAllByUserProfileId(id);
        List<MediaDetailsResponse> response = mapper.toList(allByUserProfileId);

        return new ResponseEntity<>(
                Collections.singletonMap("by profile id", response), HttpStatus.OK
        );
    }
}
