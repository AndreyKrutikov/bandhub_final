package by.krutikov.controller.commonendpoints;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.PostReactionRequest;
import by.krutikov.dto.response.ReactionDetailsResponse;
import by.krutikov.mappers.ReactionMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import by.krutikov.service.ReactionService;
import by.krutikov.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactions/{profileId}")
public class ReactionsController {
    private final UserProfileService profileService;
    private final ReactionService reactionService;
    private final AccountService accountService;
    private final ReactionMapper mapper;

    @Operation(summary = "Get all reactions by profile id",
            description = "Get all profile reactions by profile id. Admin or moderator authorities")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Reaction details",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReactionDetailsResponse.class))
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Admin/moderator authorities only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Profile by path parameter not found",
            content = @Content
    )
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping()
    public ResponseEntity<Object> getAllProfileReactions(@Valid @PathVariable @NotNull @Positive Long profileId) {
        UserProfile profile = profileService.findById(profileId);

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("profile id", profile.getId());
        model.put("reactions of this profile", mapper.toList(profile.getMyReactions()));
        model.put("reactions to this profile ", mapper.toList(profile.getOthersReactions()));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Post reaction",
            description = "Post reaction to loved user profile by profile id")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "201",
            description = "New reaction created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ReactionDetailsResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Profile by path parameter not found",
            content = @Content
    )
    @PostMapping
    @Transactional
    public ResponseEntity<Object> postReaction(Principal principal,
                                               @Valid @PathVariable(name = "profileId") @NotNull @Positive Long idLiked,
                                               @Valid @RequestBody PostReactionRequest request) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile profileFrom = accountService.findByEmail(email).getUserProfile();
        UserProfile profileTo = profileService.findById(idLiked);
        Long thisId = profileFrom.getId();

        reactionService.deleteByFromProfileIdAndToProfileId(thisId, idLiked);

        Reaction newReaction = mapper.map(request);
        newReaction.setFromProfile(profileFrom);
        newReaction.setToProfile(profileTo);
        newReaction = reactionService.createReaction(newReaction);

        ReactionDetailsResponse response = mapper.map(newReaction);

        return new ResponseEntity<>(Collections.singletonMap("New reaction created", response), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete reaction",
            description = "Remove existing reaction by profile id")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "204",
            description = "Reaction deleted",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Profile by path parameter not found",
            content = @Content
    )
    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteReaction(Principal principal,
                                                 @Valid @PathVariable(name = "profileId") @NotNull @Positive Long idLiked) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile profileFrom = accountService.findByEmail(email).getUserProfile();
        Long thisId = profileFrom.getId();

        reactionService.deleteByFromProfileIdAndToProfileId(thisId, idLiked);

        return ResponseEntity.noContent().build();
    }
}
