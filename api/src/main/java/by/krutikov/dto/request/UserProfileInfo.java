package by.krutikov.dto.request;

import lombok.Data;

@Data
public class UserProfileInfo {
    private String displayedName;
    private long accountId;
    private double lon;
    private double lat;
    private String phoneNumber;
    private String instrument;
    private String experience;
    private String description;
}
