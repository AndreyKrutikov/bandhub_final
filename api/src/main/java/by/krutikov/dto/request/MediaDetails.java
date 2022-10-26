package by.krutikov.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class MediaDetails {
    @NotNull
    @Positive
    private long profileId;
    private String photoUrl;
    private String demoUrl;
}
