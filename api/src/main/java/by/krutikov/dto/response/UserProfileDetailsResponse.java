package by.krutikov.dto.response;

import by.krutikov.domain.enums.ExperienceLevel;
import by.krutikov.domain.enums.InstrumentType;
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
    private InstrumentType instrument;
    private ExperienceLevel experience;
    private String description;
    private Boolean isVisible;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List<MediaDetailsResponse> media;
    private List<ReactionDetailsResponse> myReactions;
    private List<ReactionDetailsResponse> othersReactions;
}
