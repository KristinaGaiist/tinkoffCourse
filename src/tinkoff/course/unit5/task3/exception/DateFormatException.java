package unit5.task3.exception;

import unit5.task3.ExceptionMessage;

public class DateFormatException extends RuntimeException {

    public DateFormatException(String date) {
        super(String.format(ExceptionMessage.ILLEGAL_DATE, date));
    }
}
