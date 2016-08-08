package app.funaab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String STUDENT_ID_PATTERN = "(\\d{8})";
    private static final String STAFF_ID_PATTERN = "(([sS]|jJ)[pP]\\d+)";


    public static boolean userIDValidated(String userID) {
        Pattern expression;
        Matcher matcher;
        expression = Pattern.compile(STAFF_ID_PATTERN + "|" + STAFF_ID_PATTERN);
        matcher = expression.matcher(userID);
        if (matcher.matches())
            return true;
        else
            return false;
    }
}



