package by.krutikov.dto.request;

import lombok.Data;

@Data
public class PostReactionRequest {
    private Long toProfileId;
    private String reactionType;
}
