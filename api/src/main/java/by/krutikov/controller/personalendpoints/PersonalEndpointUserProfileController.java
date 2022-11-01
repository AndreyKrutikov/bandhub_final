package by.krutikov.controller.personalendpoints;

import by.krutikov.domain.Account;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.UserProfileDetails;
import by.krutikov.dto.request.UserProfileDetailsUpdate;
import by.krutikov.dto.response.UserProfileDetailsResponse;
import by.krutikov.mappers.UserProfileMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import by.krutikov.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-profile")
public class PersonalEndpointUserProfileController {
    private final UserProfileService profileService;
    private final AccountService accountService;
    private final UserProfileMapper mapper;

    @Operation(summary = "Show profile info",
            description = "Show profile info, Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Profile details",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserProfileDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping()
    public ResponseEntity<Object> showProfileDetails(Principal principal) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);
        UserProfile myProfile = myAccount.getUserProfile();
        UserProfileDetailsResponse response = mapper.map(myProfile);

        return new ResponseEntity<>(
                Collections.singletonMap("profile", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Create new user profile",
            description = "Create new user profile")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Profile created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserProfileDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Illegal arguments supplied",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied! Authorised user only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "409",
            description = "Profile already exists",
            content = @Content
    )
    @PostMapping()
    @Transactional
    public ResponseEntity<Object> createNewUserProfile(Principal principal,
                                                       @Valid @RequestBody UserProfileDetails request) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);

        UserProfile newProfile = mapper.map(request);
        newProfile.setAccount(myAccount);
        newProfile = profileService.createUserProfile(newProfile);

        UserProfileDetailsResponse response = mapper.map(newProfile);

        return new ResponseEntity<>(
                Collections.singletonMap("profile created", response), HttpStatus.CREATED
        );
    }

    @Operation(summary = "Update user profile",
            description = "Update user profile, Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Profile updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserProfileDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Illegal arguments supplied",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied (authorised user only)",
            content = @Content
    )
    @PutMapping()
    @Transactional
    public ResponseEntity<Object> updateUserProfile(Principal principal,
                                                    @Valid @RequestBody UserProfileDetailsUpdate updateInfo) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);

        UserProfile profileToUpdate = myAccount.getUserProfile();
        mapper.update(profileToUpdate, updateInfo);
        //Setting updated profile to account?
        profileToUpdate = profileService.updateUserProfile(profileToUpdate);

        UserProfileDetailsResponse response = mapper.map(profileToUpdate);

        return new ResponseEntity<>(
                Collections.singletonMap("profile updated", response), HttpStatus.OK
        );
    }

    @Operation(summary = "Delete profile",
            description = "Delete profile, Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "204",
            description = "Profile successfully deleted"
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @DeleteMapping()
    @Transactional
    public ResponseEntity<Object> deleteUserProfile(Principal principal) {
        String email = PrincipalUtil.getUsername(principal);
        Account myAccount = accountService.findByEmail(email);

        UserProfile profileToBeDeleted = myAccount.getUserProfile();
        profileService.deleteById(profileToBeDeleted.getId());

        return ResponseEntity.noContent().build();
    }
}
