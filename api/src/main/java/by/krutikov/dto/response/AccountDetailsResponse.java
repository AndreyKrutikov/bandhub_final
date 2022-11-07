package by.krutikov.dto.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AccountDetailsResponse {
    private Long id;
    private String email;
    private Long profileId;
    private Boolean isLocked;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List<RoleDetailsResponse> roles;
}
