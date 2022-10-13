package by.krutikov.controller;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.domain.enums.ReactionType;
import by.krutikov.dto.ReactionDto;
import by.krutikov.repository.ReactionRepository;
import by.krutikov.repository.RoleRepository;
import by.krutikov.repository.UserProfileRepository;
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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/profiles/{id}/reactions")
@RequiredArgsConstructor
public class ReactionsController {

    private final UserProfileRepository profileRepository;
    private final ReactionRepository reactionRepository;


    @GetMapping
    public ResponseEntity<Object> getAllReactions(@PathVariable Long id) {
        UserProfile profile = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Set<Reaction> myReactions = profile.getMyReactions();
        Set<Reaction> othersReactions = profile.getOthersReactions();

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("my reactions", myReactions);
        model.put("others reactions", othersReactions);

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Object> putReaction(@PathVariable Long id, @RequestBody ReactionDto reactionDto) {
        Timestamp now = new Timestamp(new Date().getTime());
        UserProfile profileFrom = profileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        UserProfile profileTo = profileRepository.findById(reactionDto.getToProfileId()).orElseThrow(EntityNotFoundException::new);

        Reaction reaction = new Reaction();
        reaction.setFromProfile(profileFrom);
        reaction.setToProfile(profileTo);
        reaction.setReactionType(ReactionType.valueOf(reactionDto.getReactionType()));
        reaction.setDateCreated(now);
        reaction.setDateModified(now);

        reaction = reactionRepository.save(reaction);


        Set<Reaction> myReactions = profileFrom.getMyReactions();
        myReactions.add(reaction);
        Set<Reaction> othersReactions = profileTo.getOthersReactions();

        Map<String, Object> model = new LinkedHashMap<>();
        model.put("my reactions", myReactions);
        model.put("others reactions", othersReactions);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


}
