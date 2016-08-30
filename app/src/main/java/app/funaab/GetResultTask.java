package app.funaab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malik on 8/30/2016.
 */
public class GetResultTask extends AsyncTask<URL,Void,JSONObject>
{
    private List arrayList;
    private ListView listView;
    private ResultAdapter resultAdapter;
    private  Bundle bundle;

    public GetResultTask(ArrayList arrayList,ListView listView,ResultAdapter resultAdapter, Bundle bundle)
    {
        this.arrayList = arrayList;
        this.listView = listView;
        this.resultAdapter = resultAdapter;
        this.bundle = bundle;
    }
    @Override
    protected JSONObject doInBackground(URL... params)
    {
        HttpURLConnection connection = null;
        try
        {
           connection = (HttpURLConnection)params[0].openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                StringBuilder stringBuilder = new StringBuilder();
                try
                {
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while((line = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(line);
                    }

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return new JSONObject(stringBuilder.toString());
            }
            else
            {

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
           //connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject)
    {
        if(jsonObject != null)
        {
            convertJSONtoArrayList(jsonObject);
            resultAdapter.notifyDataSetChanged();
            listView.smoothScrollToPosition(0);
        }
    }


    private void convertJSONtoArrayList(JSONObject result)
    {
        arrayList.clear();
        try
        {
            JSONArray resultList = result.getJSONArray("resultList");

            for(int i = 0; i < resultList.length(); i++)
            {
                JSONObject resultObject = resultList.getJSONObject(i);
                String courseCode = resultObject.getString("courseCode");
                String score = resultObject.getString("score");
                String unit = resultObject.getString("unit");
                String grade = resultObject.getString("grade");

                arrayList.add(new Result(courseCode,score,unit,grade));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
