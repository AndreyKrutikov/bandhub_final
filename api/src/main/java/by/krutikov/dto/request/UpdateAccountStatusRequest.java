package by.krutikov.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAccountStatusRequest {
    @NotNull
    private Boolean isLocked;
}
