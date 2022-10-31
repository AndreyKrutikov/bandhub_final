package by.krutikov.dto.request;

import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.domain.enums.ReactionType;
import by.krutikov.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PostReactionRequest {
    @NotNull
    @Positive
    private long toProfileId;

    @NotNull
    @ValueOfEnum(enumClass = ReactionType.class, message = "Unsupported reaction type")
    private String reactionType;
}
