package by.krutikov.controller.personalendpoints;

import by.krutikov.domain.UserProfile;
import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.dto.response.UserProfileDetailsResponse;
import by.krutikov.mappers.UserProfileMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import by.krutikov.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommended")
public class PersonalEndpointSearchController {
    private final UserProfileService profileService;
    private final AccountService accountService;
    private final UserProfileMapper mapper;


    @Operation(summary = "Get recommended user profiles",
            description = "Get recommended unseen user profiles, ordered by location")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Recommended profiles found",
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
    @GetMapping()
    public ResponseEntity<Object> findUnseenDistanceOrdered(Principal principal) {
        String email = PrincipalUtil.getUsername(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        List<UserProfile> allFoundProfiles = profileService.findAllDistanceOrdered(myProfile.getLocation());
        List<UserProfile> unseenProfiles = profileService.filterSeenBefore(myProfile, allFoundProfiles);
        List<UserProfileDetailsResponse> response = mapper.toResponseList(unseenProfiles);

        return ResponseEntity.ok(Collections.singletonMap("unseen profiles ordered by distance", response));
    }

    @Operation(summary = "Get recommended user profiles using filter parameters",
            description = "Get recommended unseen user profiles using filter parameters, ordered by location")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Recommended profiles found",
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
    @GetMapping("/specified")
    public ResponseEntity<Object> findUnseenFilteredAndDistanceOrdered(Principal principal,
                                                   @RequestParam ExperienceLevel experienceLevel,
                                                   @RequestParam InstrumentType instrumentType) {
        String email = PrincipalUtil.getUsername(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        List<UserProfile> allFoundProfiles = profileService.findByCriteriaDistanceOrdered(
                myProfile.getLocation(),
                instrumentType,
                experienceLevel
        );
        List<UserProfile> unseenProfiles = profileService.filterSeenBefore(myProfile, allFoundProfiles);
        List<UserProfileDetailsResponse> response = mapper.toResponseList(unseenProfiles);

        return ResponseEntity.ok(Collections.singletonMap("unseen filtered profiles ordered by distance", response));
    }
}
