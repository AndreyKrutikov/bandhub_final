package by.krutikov.dto.request;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.validation.PhoneNumber;
import by.krutikov.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class UserProfileDetails {
    @NotBlank
    @Size(min = 1, max = 255)
    private String displayedName;

    @NotNull
    @Positive
    private long accountId;

    @NotNull
    @Min(-180)
    @Max(180)
    private double lon;

    @NotNull
    @Min(-90)
    @Max(90)
    private double lat;

    @PhoneNumber
    private String phoneNumber;

    @ValueOfEnum(enumClass = InstrumentType.class, message = "Unsupported instrument type")
    private String instrument;

    @ValueOfEnum(enumClass = ExperienceLevel.class, message = "Unsupported experience level")
    private String experience;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
}
