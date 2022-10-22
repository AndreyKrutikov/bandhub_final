package by.krutikov.dto.response;

import by.krutikov.domain.Role;
import by.krutikov.dto.request.AccountInfo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CreateAccountResponse extends AccountInfo {
    private Long id;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List <Role> roles;
}
