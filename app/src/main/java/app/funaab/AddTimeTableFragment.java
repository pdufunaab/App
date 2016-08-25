package app.funaab;

import android.app.TimePickerDialog;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oadex.app.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTimeTableFragment extends Fragment
{
    //ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> spinnerAdapter;
    TimeTableHelper timeTableHelper;
    EditText courseCodeView;
    EditText courseTitleView;
    EditText venueView;
    //AutoCompleteTextView dayView;
    TextView dayView;
    TimePicker timePicker;
    Spinner daySpinner;

    public AddTimeTableFragment()
    {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        timeTableHelper = new TimeTableHelper(getContext());
        spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.days_array));
        //arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.days_array));
        //dayView.setAdapter(arrayAdapter);
        daySpinner.setAdapter(spinnerAdapter);
    }

    public void saveCourse()
    {
        String courseCode,courseTitle,venue,day,time;
        courseCode = courseCodeView.getText().toString();
        courseTitle = courseTitleView.getText().toString();
        venue = venueView.getText().toString();
        //day = dayView.getText().toString();
        day = daySpinner.getSelectedItem().toString();
        timePicker.clearFocus();
        time = convertTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());

        if(!checkNullOrEmpty(courseCode,courseTitle,venue,day,time))
        {
            timeTableHelper.insert(courseCode,courseTitle,venue,day,time);
            Toast.makeText(getContext(),courseTitle + " Successfully Added To TimeTable", Toast.LENGTH_LONG).show();
            clearViews(courseCodeView,courseTitleView,venueView,timePicker);
        }
        else
        {
            Toast.makeText(getContext(),"Please Ensure All Fields Are Filled", Toast.LENGTH_LONG).show();
        }

    }

    public void clearViews(EditText courseCode, EditText courseTitle, EditText venue, TimePicker time)
    {
        Calendar calendar = Calendar.getInstance();
        courseCode.setText("");
        courseTitle.setText("");
        venue.setText("");
        //day.clearListSelection();
        time.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        time.setCurrentMinute(calendar.get(Calendar.MINUTE)); ;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_add_time_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        courseCodeView = (EditText) view.findViewById(R.id.course_code);
        courseTitleView = (EditText) view.findViewById(R.id.course_title);
        venueView = (EditText) view.findViewById(R.id.course_venue);
        //dayView = (AutoCompleteTextView) view.findViewById(R.id.course_day);
        dayView = (TextView) view.findViewById(R.id.course_day);
        timePicker = (TimePicker) view.findViewById(R.id.course_timePicker);
        daySpinner = (Spinner)view.findViewById(R.id.day_spinner);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveCourse();
            }
        });

    }
}
