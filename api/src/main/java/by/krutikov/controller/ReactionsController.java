package by.krutikov.controller;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.dto.request.PostReactionRequest;
import by.krutikov.mappers.ReactionMapper;
import by.krutikov.service.ReactionService;
import by.krutikov.service.UserProfileService;
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/reactions/{id}")  //profiles/{id}/reactions ??? this.id = userprofileIdv //profiles/7/reactions
@RequiredArgsConstructor
public class ReactionsController {
    private final UserProfileService profileService;
    private final ReactionService reactionService;
    private final ReactionMapper mapper;

    @GetMapping
    public ResponseEntity<Object> getAllUserReactions(@PathVariable Long id) {
        UserProfile profile = profileService.findById(id);

        Set<Reaction> myReactions = profile.getMyReactions();
        Set<Reaction> othersReactions = profile.getOthersReactions();

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("profile id", id);
        model.put("my reactions", myReactions);
        model.put("others reactions", othersReactions);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> postReaction(@PathVariable Long id,
                                               @RequestBody PostReactionRequest request) {
        Reaction reaction = mapper.map(request);
        Long toId = reaction.getToProfile().getId();

        reactionService.deleteByFromProfileIdAndToProfileId(id, toId);

        UserProfile profileFrom = profileService.findById(id);
        reaction.setFromProfile(profileFrom);

        reaction = reactionService.createReaction(reaction);

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("new reaction from account id", id);
        model.put("to profile id", toId);
        model.put("reaction", reaction);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteReaction(@PathVariable Long id,
                                                 @RequestParam Long idLiked) {
        reactionService.deleteByFromProfileIdAndToProfileId(id, idLiked);

        return ResponseEntity.noContent().build();
    }
}
