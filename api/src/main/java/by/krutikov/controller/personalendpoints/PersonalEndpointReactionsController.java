package by.krutikov.controller.personalendpoints;

import by.krutikov.domain.Account;
import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.response.ReactionDetailsResponse;
import by.krutikov.dto.response.UserProfileDetailsResponse;
import by.krutikov.mappers.ReactionMapper;
import by.krutikov.mappers.UserProfileMapper;
import by.krutikov.security.util.PrincipalUtil;
import by.krutikov.service.AccountService;
import by.krutikov.service.ReactionService;
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
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.krutikov.security.CustomHeader.X_AUTH_TOKEN;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-reactions")
public class PersonalEndpointReactionsController {
    private final ReactionService reactionService;
    private final AccountService accountService;
    private final ReactionMapper mapper;
    private final UserProfileMapper profileMapper;

    @Operation(summary = "Show all profile reactions",
            description = "Show all profile reactions, Principal object required")
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
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping()
    public ResponseEntity<Object> showAllReactions(Principal principal) {
        String email = PrincipalUtil.getEmail(principal);
        Account myAccount = accountService.findByEmail(email);

        Set<Reaction> myReactions = myAccount.getUserProfile().getMyReactions();
        Set<Reaction> theirReactions = myAccount.getUserProfile().getOthersReactions();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("my reactions", mapper.toList(myReactions));
        response.put("reactions to my profile", mapper.toList(theirReactions));

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Show matching profiles",
            description = "Show all profiles having mutual like reactions, Principal object required")
    @Parameter(in = HEADER, name = X_AUTH_TOKEN, required = true)
    @ApiResponse(
            responseCode = "200",
            description = "Matching profiles",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserProfileDetailsResponse.class))
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied. Account owner authorities only",
            content = @Content
    )
    @GetMapping("/intersections")
    public ResponseEntity<Object> showMutualLikeReactionProfiles(Principal principal) {
        String email = PrincipalUtil.getEmail(principal);
        UserProfile myProfile = accountService.findByEmail(email).getUserProfile();

        List<UserProfile> matchingProfiles = reactionService.findLikeReactionMatchingProfiles(myProfile);

        List<UserProfileDetailsResponse> response = profileMapper.toResponseList(matchingProfiles);

        return ResponseEntity.ok(Collections.singletonMap("matching profiles", response));
    }
}
