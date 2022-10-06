package by.krutikov.controller.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContainer {

    private String exceptionId;

    private String errorMessage;

    private int errorCode;

    private String e;
}
