package by.krutikov.dto.request;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.validation.PhoneNumber;
import by.krutikov.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double lon;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double lat;

    @NotNull
    @PhoneNumber
    private String phoneNumber;

    @NotNull
    @ValueOfEnum(enumClass = InstrumentType.class, message = "Unsupported instrument type")
    private String instrument;

    @NotNull
    @ValueOfEnum(enumClass = ExperienceLevel.class, message = "Unsupported experience level")
    private String experience;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;
}
