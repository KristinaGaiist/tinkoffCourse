package unit8.utils;

import unit8.data.Message;
import unit8.exception.ValidationException;

public class ValidationUtil {

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw new ValidationException(Message.INVALID_ID_FORMAT);
        }
    }
}
