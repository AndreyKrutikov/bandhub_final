package by.krutikov.controller;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.PostReactionRequest;
import by.krutikov.dto.response.PostReactionResponse;
import by.krutikov.mappers.ReactionMapper;
import by.krutikov.service.ReactionService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reactions/{thisId}")  //profiles/{id}/reactions ??? this.id = userprofileIdv //profiles/7/reactions
@RequiredArgsConstructor
public class ReactionsController {
    private final UserProfileService profileService;
    private final ReactionService reactionService;
    private final ReactionMapper mapper;

    @Operation(summary = "Get all reactions by profile id",
            description = "Get all profile reactions by profile id",
            parameters = {

            })
    @GetMapping//all users
    public ResponseEntity<Object> getAllProfileReactions(@PathVariable Long thisId) {
        UserProfile profile = profileService.findById(thisId);

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("profile id", thisId);
        model.put("my reactions", mapper.toList(profile.getMyReactions()));
        model.put("others reactions", mapper.toList(profile.getOthersReactions()));

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Post reaction",
            description = "Post reaction to loved user profile by profile id",
            parameters = {
            })
    @PostMapping
    @Transactional//admin, registered user
    public ResponseEntity<Object> postReaction(@PathVariable Long thisId,
                                               @RequestBody PostReactionRequest request) {
        Reaction newReaction = mapper.map(request);
        Long idLiked = newReaction.getToProfile().getId();

        reactionService.deleteByFromProfileIdAndToProfileId(thisId, idLiked);

        UserProfile profileFrom = profileService.findById(thisId);
        newReaction.setFromProfile(profileFrom);

        newReaction = reactionService.createReaction(newReaction);

        return new ResponseEntity<>(Collections.singletonMap("New reaction created", mapper.map(newReaction)), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete reaction",
            description = "Remove existing reaction of profile",
            parameters = {
            })
    @DeleteMapping
    @Transactional//admin, registered
    public ResponseEntity<Object> deleteReaction(@PathVariable Long thisId,
                                                 @RequestParam Long idLiked) {
        reactionService.deleteByFromProfileIdAndToProfileId(thisId, idLiked);

        return ResponseEntity.noContent().build();
    }
}
