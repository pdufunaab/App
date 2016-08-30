package app.funaab;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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


        Bundle bundle = getIntent().getBundleExtra("bundle");
       fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EditTimeTableFragment editFragment = EditTimeTableFragment.newInstance(bundle);

        switch (getIntent().getStringExtra("fragment"))
        {
            case "Add":
                fragmentTransaction.replace(R.id.fragment_container,new AddTimeTableFragment());
                break;
            case "Edit":
                fragmentTransaction.replace(R.id.fragment_container,editFragment);
                break;
        }
        fragmentTransaction.commit();

    }

}
