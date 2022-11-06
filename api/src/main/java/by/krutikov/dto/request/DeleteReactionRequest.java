package by.krutikov.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class DeleteReactionRequest {
    @NotNull
    @Positive
    private long toProfileId;
}
