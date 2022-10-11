package by.krutikov.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class AuthResponseDto {
    private String email;
    private String token;
}
