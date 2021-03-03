package unit5.task3.exception;

public class ApplicationRuntimeException extends RuntimeException {

    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
