package app.funaab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation
{
    public static final String UNDERGRADUATE_ID_PATTERN = "(\\d{8})";
    public static final String POSTGRADUATE_ID_PATTERN = "([pP]{1}[gG]{1}\\d+)";
    public static final String STAFF_ID_PATTERN = "(([sS]|jJ)[pP]\\d+)";
    public static final String PUBLIC_ID_PATTERN = "(^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)";

    public static boolean userIDValidated(String userID)
    {
        return
        matchPattern(userID, UNDERGRADUATE_ID_PATTERN + "|" + STAFF_ID_PATTERN + "|" + POSTGRADUATE_ID_PATTERN );

    }
    public  static UserType getUserType(String userID)
    {
        if(matchPattern(userID,UNDERGRADUATE_ID_PATTERN))
            return UserType.UNDERGRADUATE;
        else if(matchPattern(userID,STAFF_ID_PATTERN))
            return UserType.STAFF;
        else if(matchPattern(userID, POSTGRADUATE_ID_PATTERN))
            return UserType.POSTGRADUATE;
        else
            return UserType.PUBLIC;
    }

    public static boolean matchPattern(String userID, String pattern)
    {
        Pattern expression;
        Matcher matcher;
        expression = Pattern.compile(pattern);
        matcher = expression.matcher(userID);
        if(matcher.matches())
            return true;
        else
            return  false;
    }
}



