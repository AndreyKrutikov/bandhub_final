package by.krutikov.service.impl;

import by.krutikov.domain.Reaction;
import by.krutikov.repository.ReactionRepository;
import by.krutikov.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;

    @Override
    public Reaction findByFromProfileAndToProfile(Long fromProfileId, Long toProfileId) {
        return reactionRepository.findByFromProfileAndToProfile(fromProfileId, toProfileId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Reaction> findReactionsByFromProfile(Long fromProfileId) {
        return reactionRepository.findReactionsByFromProfile(fromProfileId);
    }

    @Override
    public List<Reaction> findReactionsByToProfile(Long toProfileId) {
        return reactionRepository.findReactionsByToProfile(toProfileId);
    }

    @Override
    public void deleteByFromProfileIdAndToProfileId(Long fromProfileId, Long toProfileId) {
        reactionRepository.deleteByFromProfileIdAndToProfileId(fromProfileId,toProfileId);
    }

    @Override
    public Reaction createReaction(Reaction reaction) {
        return reactionRepository.save(reaction);
    }
}
