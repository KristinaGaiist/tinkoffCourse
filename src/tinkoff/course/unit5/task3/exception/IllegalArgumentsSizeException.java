package unit5.task3.exception;

import unit5.task3.ExceptionMessage;

public class IllegalArgumentsSizeException extends ValidationException {

    public IllegalArgumentsSizeException() {
        super(ExceptionMessage.ILLEGAL_ARGUMENTS);
    }
}
