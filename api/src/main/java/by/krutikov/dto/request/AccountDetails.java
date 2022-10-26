package by.krutikov.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AccountDetails {
    @NotNull
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,20}$",
            message = "4 to 20 characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;
}
