package by.krutikov.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String login;
    private String password;
    private String email;
}
