package app.funaab;

/**
 * Created by Malik on 8/16/2016.
 */
public class TimeTable
{
    private String courseCode;
    private String courseTitle;
    private String venue;
    private String time;
    private String day;
    private int dayIndex;
    private boolean alert;



    public TimeTable(String courseCode, String courseTitle, String venue, String day, String time, int dayIndex, boolean alert)
    {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.venue  = venue;
        this.day = day;
        this.time = time;
        this.dayIndex = dayIndex;
        this.alert = alert;
    }

    public String getCourseCode()
    {
        return courseCode;
    }
    public void setCourseCode(String courseCode)
    {
        this.courseCode = courseCode;
    }

    public String getCourseTitle()
    {
        return courseTitle;
    }
    public void setCourseTitle(String courseTitle)
    {
        this.courseTitle = courseTitle;
    }

    public String getVenue()
    {
        return venue;
    }
    public void setVenue(String venue)
    {
        this.venue = venue;
    }

    public boolean getAlert()
    {
        return alert;
    }
    public void setAlert(boolean alert)
    {
        this.alert = alert;
    }


    public String getDay()
    {
        return day;
    }
    public void setDay(String day)
    {
        this.day = day;
    }


    public int getDayIndex()
    {
        return dayIndex;
    }
    public void setDayIndex(int dayIndex)
    {
        this.dayIndex = dayIndex;
    }

}
