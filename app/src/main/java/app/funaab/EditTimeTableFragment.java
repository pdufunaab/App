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


public class EditTimeTableFragment extends Fragment
{
    Alarm alarm;
    ArrayAdapter<String> spinnerAdapter;
    TimeTableHelper timeTableHelper;
    EditText editCourseCodeView;
    EditText editCourseTitleView;
    EditText editVenueView;
    TimePicker editTimePicker;
    CheckBox alertBox;
    Spinner daySpinner;



    public EditTimeTableFragment()
    {
        // Required empty public constructor
    }

    public static EditTimeTableFragment newInstance(Bundle bundle)
    {
        EditTimeTableFragment editFragment = new EditTimeTableFragment();
        editFragment.setArguments(bundle);
        return editFragment;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        timeTableHelper = new TimeTableHelper(getContext());
        spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.days_array));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(spinnerAdapter);
        alarm = new Alarm(getContext());

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

    public void editCourse()
    {
        String courseCode,courseTitle,venue,day,time,alert;
        courseCode = editCourseCodeView.getText().toString();
        courseTitle = editCourseTitleView.getText().toString();
        venue = editVenueView.getText().toString();
        day = daySpinner.getSelectedItem().toString();
        editTimePicker.clearFocus();
        time = convertTime(editTimePicker.getCurrentHour(), editTimePicker.getCurrentMinute());
        alert = String.valueOf(alertBox.isChecked());

        if(!checkNullOrEmpty(courseCode,courseTitle,venue,day,time))
        {
            if(alertBox.isChecked())
            {
                alarm.setAlarm(day,time,getArguments().getInt("requestCode",0));
            }
            else
            {
                alarm.cancelAlarm(getArguments().getInt("requestCode",0));
            }
            timeTableHelper.update(courseCode,courseTitle,venue,day,time,alert,getArguments().getString("day"));
            Toast.makeText(getContext(),courseTitle + " Successfully Updated", Toast.LENGTH_LONG).show();
            clearViews(editCourseCodeView,editCourseTitleView,editVenueView,editTimePicker);
        }
        else
        {
            Toast.makeText(getContext(),"Please Ensure All Fields Are Filled", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        editCourseCodeView = (EditText) view.findViewById(R.id.edit_course_code);
        editCourseTitleView = (EditText) view.findViewById(R.id.edit_course_title);
        editVenueView = (EditText) view.findViewById(R.id.edit_course_venue);
        editTimePicker = (TimePicker) view.findViewById(R.id.edit_course_timePicker);
        alertBox = (CheckBox)view.findViewById(R.id.edit_remind_checkbox);
        daySpinner = (Spinner)view.findViewById(R.id.edit_day_spinner);

        editCourseCodeView.setText(getArguments().getString("courseCode"));
        editCourseTitleView.setText(getArguments().getString("courseTitle"));
        editVenueView.setText(getArguments().getString("venue"));
        alertBox.setChecked(Boolean.valueOf(getArguments().getString("alert")));

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.edit_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editCourse();
                Intent intent  = new Intent(getContext(), TimetableActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit_time_table, container, false);
    }


}
