package app.funaab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oadex.app.R;

public class TimeTableManagementActivity extends AppCompatActivity
{
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (getIntent().getStringExtra("fragment"))
        {
            case "Add":
                fragmentTransaction.replace(R.id.fragment_container,new AddTimeTableFragment());
                fragmentTransaction.commit();
            case "Edit":
                fragmentTransaction.replace(R.id.fragment_container,new EditTimeTableFragment());
                fragmentTransaction.commit();
        }

    }

}
