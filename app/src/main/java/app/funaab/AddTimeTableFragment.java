package app.funaab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oadex.app.R;
import com.oadex.app.TimetableActivity;

import java.util.Calendar;

public class AddTimeTableFragment extends Fragment
{
    Alarm alarm;
    ArrayAdapter<String> spinnerAdapter;
    TimeTableHelper timeTableHelper;
    EditText courseCodeView;
    EditText courseTitleView;
    EditText venueView;
    TextView dayView;
    TimePicker timePicker;
    CheckBox alert_box;
    Spinner daySpinner;

    public AddTimeTableFragment()
    {

    }

    public static AddTimeTableFragment newInstance(Bundle bundle)
    {
        AddTimeTableFragment addTimeTableFragment = new AddTimeTableFragment();
        addTimeTableFragment.setArguments(bundle);
        return addTimeTableFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        timeTableHelper = new TimeTableHelper(getContext());
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.days_array));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);
        //alarm = new Alarm(getContext(),getArguments().getInt("requestCode",0));
    }

    public void saveCourse()
    {
        String courseCode,courseTitle,venue,day,time,alert;
        courseCode = courseCodeView.getText().toString();
        courseTitle = courseTitleView.getText().toString();
        venue = venueView.getText().toString();
        day = daySpinner.getSelectedItem().toString();
        timePicker.clearFocus();
        alert = String.valueOf(alert_box.isChecked());
        time = convertTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());

        if(!checkNullOrEmpty(courseCode,courseTitle,venue,day,time))
        {
            timeTableHelper.insert(courseCode,courseTitle,venue,day,time,alert);
            int requestCode = timeTableHelper.getRequestCode(courseCode,day);;
            if(alert_box.isChecked())
            {

                alarm.setAlarm(day,time,requestCode);
            }
            else
            {
                alarm.cancelAlarm(requestCode);
            }
            Toast.makeText(getContext(),courseTitle + " Successfully Added To TimeTable", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), TimetableActivity.class);
            clearViews(courseCodeView,courseTitleView,venueView,timePicker);
            startActivity(intent);
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
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_add_time_table, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        courseCodeView = (EditText) view.findViewById(R.id.course_code);
        courseTitleView = (EditText) view.findViewById(R.id.course_title);
        venueView = (EditText) view.findViewById(R.id.course_venue);
        dayView = (TextView) view.findViewById(R.id.course_day);
        timePicker = (TimePicker) view.findViewById(R.id.course_timePicker);
        alert_box = (CheckBox)view.findViewById(R.id.add_remind_checkbox);
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
