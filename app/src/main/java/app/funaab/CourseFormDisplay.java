package app.funaab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.oadex.app.R;

import java.util.ArrayList;
import java.util.List;

public class CourseFormDisplay extends AppCompatActivity
{
    private ListView courseListView;
    private List<Result> courseList = new ArrayList<>();
    private ResultAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_form_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseListView = (ListView)findViewById(R.id.courseDisplay_listView);
        //courseAdapter
    }

    public void saveCourseForm(View view)
    {
    }
}
