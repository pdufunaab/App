package app.funaab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.oadex.app.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResultDisplay extends AppCompatActivity
{

    Bundle bundle;
    URL url;
    private ListView resultListView;
    private List<Result> resultList = new ArrayList<>();
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bundle = getIntent().getBundleExtra("bundle");
        try {
            url = new URL("");
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        resultListView = (ListView)findViewById(R.id.resultDisplay_listView);
        GetResultTask getResultTask = new GetResultTask((ArrayList)resultList,resultListView,resultAdapter,bundle);
        getResultTask.execute(url);
        resultAdapter = new ResultAdapter(this,resultList);
        resultListView.setAdapter(resultAdapter);
    }

    public void saveResult(View view)
    {

    }
}
