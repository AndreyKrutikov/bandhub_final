package by.krutikov.dto.response;

import by.krutikov.domain.Role;
import lombok.Data;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AccountDetailsResponse {
    private long id;
    private String email;
    private Long profileId;
    private Boolean isLocked;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List <Role> roles;
}
