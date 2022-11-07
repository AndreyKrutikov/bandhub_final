package by.krutikov.service.impl;

import by.krutikov.domain.Reaction;
import by.krutikov.domain.UserProfile;
import by.krutikov.domain.enums.ReactionType;
import by.krutikov.repository.ReactionRepository;
import by.krutikov.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;

    @Override
    public Reaction findByFromProfileAndToProfile(Long fromProfileId, Long toProfileId) {
        return reactionRepository.findByFromProfileAndToProfile(fromProfileId, toProfileId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Reaction> findReactionsByFromProfileId(Long fromProfileId) {
        return reactionRepository.findReactionsByFromProfile(fromProfileId);
    }

    @Override
    public List<Reaction> findReactionsByToProfileId(Long toProfileId) {
        return reactionRepository.findReactionsByToProfile(toProfileId);
    }

    @Override
    public void deleteByFromProfileIdAndToProfileId(Long fromProfileId, Long toProfileId) {
        reactionRepository.deleteByFromProfileIdAndToProfileId(fromProfileId, toProfileId);
    }

    @Override
    public Reaction createReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    @Override
    public List<UserProfile> findLikeReactionMatchingProfiles(UserProfile profile) {
        List<UserProfile> iLikedList = profile.getMyReactions()
                .stream()
                .filter(reaction -> ReactionType.LIKE.equals(reaction.getReactionType()))
                .map(Reaction::getToProfile)
                .collect(Collectors.toList());
        List<UserProfile> likedMeList = profile.getOthersReactions()
                .stream()
                .filter(reaction -> ReactionType.LIKE.equals(reaction.getReactionType()))
                .map(Reaction::getFromProfile)
                .collect(Collectors.toList());

        return iLikedList
                .stream()
                .filter(likedMeList::contains)
                .collect(Collectors.toList());
    }
}
