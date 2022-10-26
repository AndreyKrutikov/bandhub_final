package by.krutikov.dto.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserProfileDetailsResponse {
    private long id;
    private long accountId;
    private String displayedName;
    private double lon;
    private double lat;
    private String phoneNumber;
    private String instrument;
    private String experience;
    private String description;
    private Boolean isVisible;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List<MediaDetailsResponse> media;
    private List<PostReactionResponse> myReactions;
    private List<PostReactionResponse> othersReactions;
}
