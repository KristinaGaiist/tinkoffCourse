package unit5.task1AndTask2;

public class PublicException extends Exception {

    public PublicException(String message) {
        super(message);
    }

    public PublicException(String message, Throwable cause) {
        super(message, cause);
    }
}
