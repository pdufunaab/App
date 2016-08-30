package app.funaab;

/**
 * Created by Malik on 8/30/2016.
 */
public class Result
{
    public final String courseCode;
    public final String score;
    public final String grade;
    public final String unit;

    public Result(String courseCode, String score, String unit, String grade)
    {
        this.courseCode = courseCode;
        this.score = score;
        this.unit = unit;
        this.grade = grade;
    }
}
