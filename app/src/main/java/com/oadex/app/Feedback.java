package com.oadex.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ADEKOYA759 on 15-Aug-16.
 */
public class Feedback extends Activity  implements View.OnClickListener {
    EditText personalEmail, personsName, mySubject;
    String emailAdd, name, yourSubject;
    Button sendComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        initializeVars();
        sendComment.setOnClickListener(this);
    }
    private void initializeVars(){

        personalEmail = (EditText)findViewById(R.id.email);
        personsName = (EditText)findViewById(R.id.name);
        mySubject = (EditText)findViewById(R.id.subject);
        sendComment = (Button)findViewById(R.id.comment);
    }

    @Override
    public void onClick(View v) {
        convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated();
        String emailAddress[] = {emailAdd};
        String yourName = name;
        String mySubject = yourSubject;
    }
    private  void convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated(){
        emailAdd = personalEmail.getText().toString();
        name = personsName.getText().toString();
        yourSubject = mySubject.getText().toString();
    }
    protected void onPause(){
        super.onPause();
        finish();
    }
}
