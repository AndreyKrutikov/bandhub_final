package by.krutikov.exception;

public class EntityNotFoundException extends RuntimeException {
    private final String customMessage;

    private final Integer errorCode;

    private final String exceptionId;

    public EntityNotFoundException(String customMessage, Integer errorCode, String exceptionId) {
        this.customMessage = customMessage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public EntityNotFoundException(String message, String customMessage, Integer errorCode, String exceptionId) {
        super(message);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public EntityNotFoundException(String message, Throwable cause, String customMessage, Integer errorCode, String exceptionId) {
        super(message, cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public EntityNotFoundException(Throwable cause, String customMessage, Integer errorCode, String exceptionId) {
        super(cause);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String customMessage, Integer errorCode, String exceptionId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.customMessage = customMessage;
        this.errorCode = errorCode;
        this.exceptionId = exceptionId;
    }

    @Override
    public String toString() {
        return "EntityNotFoundException{" +
                "customMessage='" + customMessage + '\'' +
                ", errorCode=" + errorCode +
                ", exceptionId='" + exceptionId + '\'' +
                "} " + super.toString();
    }
}
