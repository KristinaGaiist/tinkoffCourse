package unit2.task1.cmd;

public class ParseHelper {

    public static int parseStringToInteger(String stringNumber) {
        try {
            return Integer.parseInt(stringNumber);
        } catch (NumberFormatException e) {
            throw new NullPointerException(Messages.ERROR_STRING_FOR_PARSE_TO_INT);
        }
    }

    public static double parseStringToDouble(String stringNumber) {
        try {
            return Double.parseDouble(stringNumber);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(Messages.ERROR_STRING_FOR_PARSE_TO_DOUBLE);
        }
    }
}
