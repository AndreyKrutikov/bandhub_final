package by.krutikov.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
