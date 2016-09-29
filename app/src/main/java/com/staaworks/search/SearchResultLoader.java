package com.staaworks.search;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oadex.app.R;
import com.staaworks.custom_components.LoadingCircle;
import com.staaworks.util.InfiniteScrollListener;
import com.staaworks.util.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ahmad Alfawwaz on 9/17/2016
 */
public class SearchResultLoader  extends AsyncTask<String, Void, String> {

    private Activity activity;
    private People people, persons;
    private PersonAdapter adapter;
    private TextView textView;
    private LoadingCircle loadingCircle;
    private ListView listView;


    public SearchResultLoader(Activity activity) {
        this.activity = activity;
        textView = (TextView) activity.findViewById(R.id.contact_info_textView);
        loadingCircle = (LoadingCircle) activity.findViewById(R.id.loading_circle);
        loadingCircle.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(R.string.contact_searching);
        listView = (ListView) activity.findViewById(R.id.contact_search_listview);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String JsonString;
        if (Network.isConnected()) {
            JsonString = getJsonString(params[0]);
        }
        else JsonString = "Invalid Request";

        if (JsonString == null)
            JsonString = "Null JSON";
        else if (JsonString.equals(""))
            JsonString = "Empty JSON";

        System.out.println("CONTACT_JSON: " + JsonString);
        return JsonString;
    }

    @Override
    protected void onPostExecute(String JsonString) {
        super.onPostExecute(JsonString);

        if (JsonString.equals("Invalid Request")) {
            Toast.makeText(activity, "Loading Error", Toast.LENGTH_SHORT).show();
            textView.setText(R.string.contact_search_network_error);
            onCancelled();
            return;
        }


        if (JsonString.equals("Unknown Format")) {
            Toast.makeText(activity, "Unknown Format Exception", Toast.LENGTH_SHORT).show();
            textView.setText(R.string.contact_search_format_error);
            onCancelled();
            return;
        }


        if (JsonString.equals("Null JSON") || JsonString.equals("Empty JSON")) {
            Toast.makeText(activity, "Result Not Found", Toast.LENGTH_SHORT).show();
            textView.setText(R.string.contact_search_entry_error);
            onCancelled();
            return;
        }

        try {

            JSONObject jsonObject = new JSONObject(JsonString);
            people = parseJson(jsonObject);


            System.out.println("CONTACT_List_Size_AfterParse: " + people.size());
            persons = people.getNext();



            adapter = new PersonAdapter(activity, persons, JsonString);
            adapter.setNotifyOnChange(true);



            listView.setOnScrollListener(new ScrollListener());
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);


            textView.setVisibility(View.INVISIBLE);
            loadingCircle.setVisibility(View.INVISIBLE);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        loadingCircle.setVisibility(View.INVISIBLE);
    }

    public static String getJsonString(String urlString) {
        InputStream inputStream = null;
        String result = "";


        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            }
            else {
                result = "ERROR";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


    public static People parseJson(JSONObject jsonObject) throws JSONException {

        JSONArray peopleArray = jsonObject.getJSONArray("People");
        int size = peopleArray.length();
        String name, department, phone, email;
        People people = new People();
        System.out.println("CONTACT_ListSize: " + size);

        for (int i = 0; i < size; i++) {

            JSONObject currentObject = peopleArray.getJSONObject(i);


            try {
                name = currentObject.getString("name");
            } catch (JSONException ex) {
                name = "Anonymous";
            }




            try {
                department = currentObject.getString("department");
            } catch (JSONException ex) {
                department = "Hidden";
            }



            try {
                phone = currentObject.getString("phone");
            }
            catch (JSONException ex) {
                phone = null;
            }



            try {
                email = currentObject.getString("email");
            }
            catch (JSONException ex) {
                email = null;
            }

            System.out.println("CONTACT_SingleParseComplete");
            people.add(new Person(name, department, phone, email));
        }

        return people;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    private class ScrollListener extends InfiniteScrollListener {

        @Override
        public void loadMore(int page, int totalItemsCount) {
            People p = people.getNext();
            for (Person person: p) {
                persons.add(person);
            }
        }
    }


}
