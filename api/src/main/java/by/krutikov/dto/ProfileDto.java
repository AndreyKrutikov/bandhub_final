package by.krutikov.dto;

import by.krutikov.domain.Account;
import by.krutikov.domain.ExperienceLevel;
import by.krutikov.domain.InstrumentType;
import by.krutikov.domain.Media;
import lombok.Data;

@Data
public class ProfileDto {
    private String displayedName;
    private Account account;
    private double lon;
    private double lat;
    private String phoneNumber;
    private InstrumentType instrument;
    private ExperienceLevel experienceLevel;
    private Media media;
    private String description;
}
