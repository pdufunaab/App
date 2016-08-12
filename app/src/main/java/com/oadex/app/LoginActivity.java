package com.oadex.app;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.funaab.Authentication;
import app.funaab.Validation;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{



    // UI references.
    private AutoCompleteTextView userID;
    private EditText passwordView;
    private View progressView;
    private View loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        passwordView = (EditText) findViewById(R.id.password);
        userID = (AutoCompleteTextView) findViewById(R.id.userID);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);


        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
         {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    //attemptLogin();
                    return true;
                }
                return false;
            }
        });

        signInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(Validation.userIDValidated(userID.getText().toString()))
                {
                    new Authentication(getApplicationContext(),userID.getText().toString(),passwordView.getText().toString()).execute("");
                }
                else
                {
                    Toast.makeText(getApplication(),"Invalid user ID pattern",Toast.LENGTH_LONG).show();
                }

                //attemptLogin();
            }
        });

        loginView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }


     //Set up the {@link android.app.ActionBar}, if the API is available.

   /*  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
   private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
*/
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 1000 ;

            loginView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

