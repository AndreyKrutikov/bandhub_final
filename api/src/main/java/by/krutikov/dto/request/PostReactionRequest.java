package by.krutikov.dto.request;

import by.krutikov.domain.enums.ReactionType;
import by.krutikov.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostReactionRequest {
    @NotNull
    @ValueOfEnum(enumClass = ReactionType.class, message = "Unsupported reaction type")
    private ReactionType reactionType;
}
