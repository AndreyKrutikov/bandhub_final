package by.krutikov.dto.response;

import by.krutikov.domain.enums.ReactionType;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostReactionResponse {
    private long id;
    private long fromProfileId;
    private long toProfileId;
    private ReactionType reactionType;
    private Timestamp dateCreated;
}
