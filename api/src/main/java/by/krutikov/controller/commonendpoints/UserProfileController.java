package by.krutikov.controller.commonendpoints;

import by.krutikov.domain.UserProfile;
import by.krutikov.dto.response.UserProfileDetailsResponse;
import by.krutikov.mappers.UserProfileMapper;
import by.krutikov.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collections;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class UserProfileController {
    private final UserProfileService profileService;
    private final UserProfileMapper mapper;

    @Operation(summary = "Get user profiles",
            description = "Get all profiles, pageable. Admin/moderator use only")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Profiles found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = UserProfileDetailsResponse.class)
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
    public ResponseEntity<Object> findAllPageable(Pageable pageable) {
        Page<UserProfile> profilesPage = profileService.findAll(pageable);
        Page<UserProfileDetailsResponse> responsePage = profilesPage.map(mapper::map);

        return new ResponseEntity<>(
                Collections.singletonMap("all profiles", responsePage), HttpStatus.OK
        );
    }

    @Operation(summary = "Get user profile by id",
            description = "Get user profile by id. No authorities required ")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Profile found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = UserProfileDetailsResponse.class)
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Profile not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@Valid @PathVariable @NotNull @Positive Long id) {
        UserProfileDetailsResponse byId = mapper.map(profileService.findById(id));

        return new ResponseEntity<>(
                Collections.singletonMap("found by id", byId), HttpStatus.OK
        );
    }
}
