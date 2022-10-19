package by.krutikov.dto.response;

import by.krutikov.dto.request.AccountInfo;
import lombok.Data;

@Data
public class CreateAccountResponse extends AccountInfo {
    Long id;
}
