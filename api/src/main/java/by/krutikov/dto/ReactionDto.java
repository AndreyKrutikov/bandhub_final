package by.krutikov.dto;

import lombok.Data;

@Data
public class ReactionDto {
    private Long toProfileId;
    private String reactionType;
}
