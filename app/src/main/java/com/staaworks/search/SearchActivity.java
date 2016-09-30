package com.staaworks.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oadex.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ADEKOYA759 on 16-Sep-16
 */
public class SearchActivity extends AppCompatActivity {


    private enum constants {
        link("http://demo8092140.mockable.io/staaService/msg?"),
        savedStateKey("currentSearchJsonString"),
        sharedPreferencesFileName("dir_searches"),
        sharedPreferencesLastEntryKey("last_entry");


        public String value;
        constants(String value) {
            this.value = value;
        }

    }


    private TextView contact_info_textView;
    private ListView contact_search_listview;
    private AutoCompleteTextView searchEditText;
    private ImageView searchButton;
    private String searchText = "", previousSearchText = "";
    private SharedPreferences preferences;
    private boolean canSearch = true;
    private List<String> searches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        preferences = getSharedPreferences(constants.sharedPreferencesFileName.value, MODE_PRIVATE);


        contact_search_listview = (ListView) findViewById(R.id.contact_search_listview);
        contact_search_listview.setVisibility(View.INVISIBLE);


        contact_info_textView = (TextView) findViewById(R.id.contact_info_textView);
        contact_info_textView.setVisibility(View.VISIBLE);
        contact_info_textView.setText(R.string.contact_search_entry_error);


        searchEditText = (AutoCompleteTextView) findViewById(R.id.searchEditText);


        searchButton = (ImageView) findViewById(R.id.searchButton);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canSearch) {

                    doSearch();

                    if (!searchText.equals("")) {
                        previousSearchText = searchText;
                    }
                    canSearch = false;


                    new CountDownTimer(1000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {}

                        @Override
                        public void onFinish() {
                            canSearch = true;
                        }
                    }.start();
                }
            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            private String ancient,current;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ancient = searchEditText.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                current = searchEditText.getText().toString();

                if (!current.equals(ancient)) {
                    canSearch = true;
                }
            }
        });


        Object[] s = preferences.getAll().keySet().toArray();
        searches = new ArrayList<>(s.length);

        for (Object o : s) {
            String search = ((String) o).replaceAll("_", " ");

            if (!searches.contains(search)) {
                searches.add(search);
            }
        }




        if (savedInstanceState != null) {
            String json = savedInstanceState.getString(constants.savedStateKey.value);
            if (json != null)
                doJob(json);
        }

        else if (searches.size() != 0) {
            String json = getFromSharedPreferences(constants.sharedPreferencesLastEntryKey.value);

            if (!json.equals("")) {
                doJob(json);
            }
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.search_dropdown, R.id.search_dropdown_textview, searches) {

            private int pos;

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View current = super.getView(position, convertView, parent);
                pos = position;

                ImageView delBTN = (ImageView) current.findViewById(R.id.search_delete_button);
                TextView dropdownTextView = (TextView) current.findViewById(R.id.search_dropdown_textview);

                delBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searches.remove(pos);
                        notifyDataSetChanged();
                        removeFromSharedPreferences(getItem(pos));
                    }
                });

                dropdownTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doJob(getFromSharedPreferences(getItem(pos)));
                        contact_search_listview.requestFocus();
                    }
                });
                return current;
            }
        };

        searchEditText.setAdapter(adapter);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }


    protected String makeLink(String RequestParameters) {

        String params = RequestParameters.replaceAll(" ", "+").toLowerCase(Locale.US);
        return constants.link.value + "searchterm=" + params;

    }


    protected void doSearch() {

        searchText = searchEditText.getText().toString();
        if (searchText.equals(previousSearchText)) return;

        if (!searchText.equals("")) {
            SearchResultLoader task = new SearchResultLoader(this);
            task.execute(makeLink(searchText));
            Toast.makeText(SearchActivity.this, "Search Execution Started", Toast.LENGTH_SHORT).show();
        }

        PersonAdapter adapter =((PersonAdapter) contact_search_listview.getAdapter());

        if (adapter != null) saveToSharedPreferences(searchText, adapter.getJsonString());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PersonAdapter adapter = (PersonAdapter) contact_search_listview.getAdapter();
        if (adapter != null) {
            String json = adapter.getJsonString();
            outState.putString(constants.savedStateKey.value, json);
            saveToSharedPreferences(constants.sharedPreferencesLastEntryKey.value, json);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
    }

    private void doJob(String json) {
        SearchResultLoader task = new SearchResultLoader(this);
        Toast.makeText(SearchActivity.this, "Task Executed From Preferences", Toast.LENGTH_SHORT).show();
        task.onPostExecute(json);
    }



    private void saveToSharedPreferences(String key, String content) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key.replaceAll(" ", "_"), content);
        editor.apply();
    }

    private String getFromSharedPreferences(String key) {
        return preferences.getString(key.replaceAll(" ", "_"), "Unknown Format");
    }


    private void removeFromSharedPreferences(String key) {
        preferences.edit().remove(key.replaceAll(" ", "_")).apply();
    }

}
