package unit5.task3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import unit5.task3.exception.DateFormatException;

public class ParseHelper {

    public static Date parseStringToDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new DateFormatException(date);
        }
    }
}
