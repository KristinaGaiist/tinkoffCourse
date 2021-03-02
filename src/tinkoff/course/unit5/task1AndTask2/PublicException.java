package unit5.task1AndTask2;

import java.io.IOException;

public class PublicException extends IOException {

    public PublicException(String message) {
        super(message);
    }

    public PublicException(String message, Throwable cause) {
        super(message, cause);
    }
}
