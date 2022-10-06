package by.krutikov.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private String displayedName;
    private long accountId;
    private double lon;
    private double lat;
    private String phoneNumber;
    private String instrument;
    private String experienceLevel;
    private long mediaId;
    private String description;
}
