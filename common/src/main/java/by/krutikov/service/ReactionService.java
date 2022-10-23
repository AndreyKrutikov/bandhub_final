package by.krutikov.service;

import by.krutikov.domain.Reaction;

import java.util.List;


public interface ReactionService {
    Reaction findByFromProfileAndToProfile(Long fromProfileId, Long toProfileId);

    List<Reaction> findReactionsByFromProfile(Long fromProfileId);

    List<Reaction> findReactionsByToProfile(Long toProfileId);

    void deleteByFromProfileIdAndToProfileId(Long fromProfileId, Long toProfileId);

    Reaction createReaction(Reaction reaction);
}
