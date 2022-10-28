package by.krutikov.dto.response;

import by.krutikov.domain.enums.SystemRoles;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoleDetailsResponse {
    private long id;
    private SystemRoles roleName;
    private Timestamp dateCreated;
    private Timestamp dateModified;
}
