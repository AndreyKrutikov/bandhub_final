package by.krutikov.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MediaDetailsResponse {
    private long id;
    private long profileId;
    private String photoUrl;
    private String demoUrl;
    private Timestamp dateCreated;
    private Timestamp dateModified;
}
