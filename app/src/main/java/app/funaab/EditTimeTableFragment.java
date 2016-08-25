package app.funaab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oadex.app.R;

import java.util.Calendar;


public class EditTimeTableFragment extends Fragment
{
    ArrayAdapter<String> arrayAdapter;
    TimeTableHelper timeTableHelper;
    EditText editCourseCodeView;
    EditText editCourseTitleView;
    EditText editVenueView;
    AutoCompleteTextView editDayView;
    TimePicker editTimePicker;

    public EditTimeTableFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        timeTableHelper = new TimeTableHelper(getContext());
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.days_array));
        editDayView.setAdapter(arrayAdapter);
    }

    public boolean checkNullOrEmpty(String courseCode, String courseTitle,String venue, String day, String time)
    {
        if((courseCode == null || courseCode.isEmpty()) || (courseTitle == null || courseTitle.isEmpty()) || (venue == null || venue.isEmpty()) || (day == null || day.isEmpty()) || (time == null || time.isEmpty()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public  String convertTime(int hour, int minute)
    {
        String format;
        if (hour == 0)
        {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12)
        {
            format = "PM";
        }
        else if (hour > 12)
        {
            hour -= 12;
            format = "PM";
        }
        else
        {
            format = "AM";
        }

        return hour + ":" + minute + ":" + format;
    }
    public void clearViews(EditText courseCode, EditText courseTitle, EditText venue, AutoCompleteTextView day, TimePicker time)
    {
        Calendar calendar = Calendar.getInstance();
        courseCode.setText("");
        courseTitle.setText("");
        venue.setText("");
        day.clearListSelection();
        time.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        time.setCurrentMinute(calendar.get(Calendar.MINUTE)); ;
    }

    public void editCourse()
    {
        String courseCode,courseTitle,venue,day,time;
        courseCode = editCourseCodeView.getText().toString();
        courseTitle = editCourseTitleView.getText().toString();
        venue = editVenueView.getText().toString();
        day = editDayView.getText().toString();
        editTimePicker.clearFocus();
        time = convertTime(editTimePicker.getCurrentHour(), editTimePicker.getCurrentMinute());

        if(!checkNullOrEmpty(courseCode,courseTitle,venue,day,time))
        {
            timeTableHelper.update(courseCode,courseTitle,venue,day,time);
            Toast.makeText(getContext(),courseTitle + " Successfully Updated", Toast.LENGTH_LONG).show();
            clearViews(editCourseCodeView,editCourseTitleView,editVenueView,editDayView,editTimePicker);
        }
        else
        {
            Toast.makeText(getContext(),"Please Ensure All Fields Are Filled", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        editCourseCodeView = (EditText) view.findViewById(R.id.edit_course_code);
        editCourseTitleView = (EditText) view.findViewById(R.id.edit_course_title);
        editVenueView = (EditText) view.findViewById(R.id.edit_course_venue);
        editDayView = (AutoCompleteTextView) view.findViewById(R.id.edit_course_day);
        editTimePicker = (TimePicker) view.findViewById(R.id.edit_course_timePicker);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.edit_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editCourse();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit_time_table, container, false);

    }


}
