package app.funaab;

import java.sql.Date;
import java.text.DateFormat;

/**
 * Created by Malik on 8/16/2016.
 */
public class TimeTable
{
    public final String course;
    public final String venue;
    public final String date;

    public TimeTable(String course, String venue, String date)
    {
        this.course = course;
        this.venue  = venue;
        this.date = date;
    }
}
