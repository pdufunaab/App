package com.oadex.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import app.funaab.ResultDisplay;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment
{
    Bundle bundle;
    Spinner sessionSpinner;
    RadioGroup semesterRadioGroup;
    RadioButton checkedButton;
    FloatingActionButton floatingActionButton;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.prof_result,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        sessionSpinner = (Spinner) view.findViewById(R.id.select_session_spinner);
        semesterRadioGroup = (RadioGroup) view.findViewById(R.id.semester_radioGroup);
        checkedButton = (RadioButton)view.findViewById(semesterRadioGroup.getCheckedRadioButtonId());
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.displayResult_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bundle.putString("session",sessionSpinner.getSelectedItem().toString());
                bundle.putString("semester",checkedButton.getText().toString());
                displayResult(bundle);
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        bundle = new Bundle();
        super.onActivityCreated(savedInstanceState);
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.session_array));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sessionSpinner.setAdapter(spinnerAdapter);
    }

    public  void displayResult(Bundle bundle)
    {
        Intent intent = new Intent(getContext(), ResultDisplay.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
