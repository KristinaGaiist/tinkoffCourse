package unit5.task3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseHelper {

    public static Date parseStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}
