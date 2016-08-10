package app.funaab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oadex.app.LoginActivity;
import com.oadex.app.MainActivity;
import com.oadex.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by Malik on 8/9/2016.
 */


// Keep track of the login task to ensure we can cancel it if requested.
/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class Authentication extends AsyncTask<String, Void, Boolean>
{
    private  String API_KEY;
    Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String webServiceUrl = "";
    private final String userID;
    private final String password;
    private  String serviceResponse;

    Authentication(Context context,String email, String password)
    {
        this.context = context;
        this.userID = email;
        this.password = password;
        API_KEY = context.getResources().getString(R.string.API_KEY);
        sharedPreferences = this.context.getSharedPreferences("Pref File", Context.MODE_PRIVATE);
    }

    public String authenticateID(String params)
    {
        HttpURLConnection connection  = null;
        try
        {
            URL url = new URL(params);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("userID",userID);
            connection.addRequestProperty("password",password);
            connection.addRequestProperty("API_KEY",API_KEY);
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            serviceResponse = stringBuilder.toString();
        }
        catch (Exception e)
        {
            Log.e("Error in Background", e.getMessage());
            connection.disconnect();

        }
        finally
        {
            connection.disconnect();

        }

        return  serviceResponse;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        // TODO: attempt authentication against a network service.

        try
        {
            // Simulate network access.

            JSONObject jsonObject = new JSONObject(authenticateID(webServiceUrl));
            if(jsonObject.getBoolean("validated") == true) {
                editor = sharedPreferences.edit();
                editor.putString("userID", jsonObject.getString("userID"));
                editor.putString("userToken",jsonObject.getString("userToken"));
                return true;
            }
            else
                return false;
        }
        catch(JSONException e)
        {
            return false;
        }
        catch (Exception e)
        {
            return false;
        }

        // TODO: register the new account here.
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        //showProgress(false);

        if (success)
        {

            // finish();
        }
        else
        {
            // passwordView.setError(getString(R.string.error_incorrect_password));
            //passwordView.requestFocus();
        }
    }

    @Override
    protected void onCancelled()
    {
        //showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final View loginView, final View progressView)
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

