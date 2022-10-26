package by.krutikov.controller.exceptionhandler;

import by.krutikov.util.UUIDGenerator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(1)
                .errorMessage("General error: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(2)
                .errorMessage("Empty result: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingRequestParameterException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(3)
                .errorMessage("Missing request parameters: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(4)
                .errorMessage("No entity found: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    //new UsernameNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(5)
                .errorMessage("Username not found: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUDataIntegrityViolationException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(6)
                .errorMessage("Object already exists: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(7)
                .errorMessage("Bad request arguments: " + e.getMessage())
                .exceptionClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }
}
