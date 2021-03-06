package com.oadex.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.staaworks.search.SearchActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create Navigation drawer and inlfate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        // Set behavior of Navigation drawer if the navigation view is not null
        if (navigationView != null)
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        // Set item in checked state
                        menuItem.setChecked(true);

                        // TODO: handle navigation
                        switch(menuItem.getItemId())
                        {
                            case R.id.profile:
                                Intent profileIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                                startActivity(profileIntent);
                                break;
                            case R.id.news:
                                Intent newsIntent = new Intent(getApplicationContext(), NewsActivity.class);
                                startActivity(newsIntent);
                                break;
                            case R.id.timetable:
                                Intent timeIntent = new Intent(getApplicationContext(), TimetableActivity.class);
                                startActivity(timeIntent);
                                break;
                            case R.id.contacts:
                                Intent contactsIntent = new Intent(getApplicationContext(), SearchActivity.class);
                                startActivity(contactsIntent);
                        }

                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });


        //Inflate the Fragments here.
        Intent intent = getIntent();
        Boolean validate = intent.getBooleanExtra("validated",false);
        if(validate){
            UnlockedFragment lockedfrag = new UnlockedFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,lockedfrag).commit();
        }
        else
        {
            LockedFragment lockedfrag = new LockedFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,lockedfrag).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        else if(id == R.id.action_login){
            Intent login = new Intent(this,LoginActivity.class);
            this.startActivity(login);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
