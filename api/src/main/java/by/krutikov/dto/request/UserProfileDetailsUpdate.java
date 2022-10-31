package by.krutikov.dto.request;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
import by.krutikov.validation.PhoneNumber;
import by.krutikov.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// TODO: 31.10.22 check @NotBlank over displayedName and description fields
@Data
public class UserProfileDetailsUpdate {
   // @NotBlank
    @Size(min = 1, max = 255)
    private String displayedName;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double lon;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double lat;

    @PhoneNumber
    private String phoneNumber;

    @ValueOfEnum(enumClass = InstrumentType.class, message = "Unsupported instrument type")
    private String instrument;

    @ValueOfEnum(enumClass = ExperienceLevel.class, message = "Unsupported experience level")
    private String experience;

    //@NotBlank
    @Size(min = 1, max = 255)
    private String description;
}
