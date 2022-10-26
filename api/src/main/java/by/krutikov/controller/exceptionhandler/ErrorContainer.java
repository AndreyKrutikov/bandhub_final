package by.krutikov.controller.exceptionhandler;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorContainer {
    private String exceptionId;
    private String errorMessage;
    private int errorCode;
    private String exceptionClass;
}
