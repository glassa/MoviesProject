package glassa.tacoma.uw.edu.moviesproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import bolts.Task;
import glassa.tacoma.uw.edu.moviesproject.profile.ProfileActivity;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * Main activity class of the app.
 * When the app launches, this is where the intent filter points
 * This activity is the contains the AsyncTasks required by
 * LoginFragment and RegisterFragment used to access the database
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements
        RegisterFragment.UserAddListener, LoginFragment.LoginAddListener,
        /* LoginFragment.FacebookLoginListener,*/ MessageFragment.messageListener{

    /**
     * The member variable for Username.
     * It's used when calling intents to other activity's as a PutExtra call
     * in order to remember which user's info to display.
     */
    public static String mUsername;
    /**
     * A callback manager used in conjuction with the facebook log on.
     * Not currently used
     */
    //CallbackManager callbackManager;
    SharedPreferencesHelper mSharedPreferencesHelper;
    private final String TAG = "MainActivity";

    SQLiteDatabase mydatabase;
    Cursor resultSet;

    /**
     * OnCreate method to instantiate the fragment container,
     * and then take the user to the login fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());


        //callbackManager = CallbackManager.Factory.create();
        //AppEventsLogger.activateApp(this);
        mydatabase = openOrCreateDatabase("username", MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Username(Username VARCHAR, Current BOOLEAN);");

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(
                sharedPreferences);
        setContentView(R.layout.activity_main);
//        mLoginFrag = new LoginFragment();
        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }
    @Override
    public void onBackPressed(){

    }
    /**
     * Sets username.

     *
     * @param mUsername the member variable "Username"
     */

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }


    /**
     * Fragment interface that launches the registration Asynctask.
     * @param url the url to connect to the database, already pre-appended with the php? commands.
     */
    @Override
    public void addUser(String url) {
//
        AddUserTask task = new AddUserTask();
        task.execute(new String[]{url.toString()});
        getSupportFragmentManager().popBackStack();
    }

    /**
     * Fragment interface required by the messaging fragment.
     * Does nothing. Unsure if it can be removed without breaking things.
     */
    public void message(){

    }
    /**
     * Fragment interface that launches the login Asynctask.
     * @param url the url to connect to the database, already pre-appended with the php? commands.
     */
    public void addLogin(String url, String mUsername) {
        setmUsername(mUsername);
        LoginUserTask task = new LoginUserTask();
        task.execute(new String[]{url.toString()});
        getSupportFragmentManager().popBackStack();
    }
    /**
     * Fragment interface for when the user is already logged in.
     * It logs that the user whas already logged in, and starts an intent
     * to launch the TabHostActivity.
     * @param mUsername
     */
    public void sharedPrefLogin(String mUsername){
        Intent intent = new Intent(getApplicationContext(), TabHostActivity.class);
        Log.i("MainActivity", "username: " + mUsername);
        intent.putExtra("USERNAME", mUsername);
        startActivity(intent);
    }
    /**
     * The interface for the facebook login. Currently not in use.
     */
    /*
    public void facebookLogin() {
        Log.i(TAG, "facebookLogin");

        Intent intent = new Intent(getApplicationContext(), TabHostActivity.class);
        startActivity(intent);
    }
    */
    /**

     * AsyncTask to log the user in. On successful authentication, take the user to TabHostActivity.
     */
    private class LoginUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Connects the the database and opens an input stream to listen for the server's response.
         * If any exception is thrown, the response is changed to show the exception.
         * @param urls The pre-appended url to connect to the database.
         * @return Returns a response from the server in the form of a JSON object.
         *          The three possible responses are {result: "success"} and {result: "failure"}
         *          and " 'Unable to find user, Reason: ' + Exception.getMessage()"
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to find user, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
<<<<<<< HEAD
         * If it was, it takes the user to the TabHostActivity via an intent.
         * If not, it displays the exception.
=======
         * If it was, it takes the user to the TabHostActivity via an intent, stores the username
         * in a SQlite database if it does not already exist, and saves the user as logged in with
         * sharedpreferences.
         * If the user provided incorrect login info, they are marked as not logged in
         * via sharedpreferences, and a toast is displayed letting them know.
         * If something else causes an exception, they are informed, and a log is left.
>>>>>>> master
         *
         * @param result Passed by doInBackground. Used to determine if the user login was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext()
                            , "Welcome"
                            , Toast.LENGTH_LONG)
                            .show();

                    Intent intent = new Intent(getApplicationContext(), TabHostActivity.class);
                    Log.i("MainActivity", "username: " + mUsername);
                    intent.putExtra("USERNAME", mUsername);
                    resultSet = mydatabase.rawQuery("Select * from Username",null);
                    resultSet.moveToFirst();
                    boolean flag = false;
                    for (int i = 0; i < resultSet.getCount(); i++){
                        String liteUsername = resultSet.getString(i);
                        if (liteUsername.equalsIgnoreCase(mUsername)) {
                            flag = true;
                            String formatedString = String.format("UPDATE Username SET Username='%s', Current=true", mUsername);
                            mydatabase.execSQL(formatedString);
                        } else {
                            String formatedString = String.format("UPDATE Username SET Username='%s', Current=false", mUsername);
                            mydatabase.execSQL(formatedString);
                        }
                    }
                    if (flag = false){
                        String formatedString = String.format("INSERT INTO Username VALUES('%s', %b);", mUsername, true);
                        mydatabase.execSQL(formatedString);
                    }

                    SharedPreferenceEntry entry1 = new SharedPreferenceEntry(true, mUsername);
                    Log.i(TAG, entry1.getUsername());
                    if(entry1.isLoggedIn() == true){
                        Log.i(TAG, "True");
                    } else if(entry1.isLoggedIn() == false){
                        Log.i(TAG, "False");
                    }
                    if(entry1 != null) {
                        if(entry1.getUsername() != null) {
                            mSharedPreferencesHelper.savePersonalInfo(entry1);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Username or password is incorrect"
                            , Toast.LENGTH_LONG)
                            .show();
                    SharedPreferenceEntry entry1 = new SharedPreferenceEntry(false,"");
                    mSharedPreferencesHelper.savePersonalInfo(entry1);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();

                Log.wtf(TAG, e.getMessage());

            }
        }
    }
    /**
     * AsyncTask to register a new user. On successful registration,
     * take the user to TabHostActivity.
     */
    private class AddUserTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add user, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If it was, they are saved as logged in via Sharedpreferences, the username is stored in
         * a SQlite database, and the user is taken to the TabHostActivity.
         * If not, it displays and logs the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "User successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();

                    resultSet = mydatabase.rawQuery("Select * from Username",null);
                    resultSet.moveToFirst();
                    boolean flag = false;
                    for (int i = 0; i < resultSet.getCount(); i++){
                        String liteUsername = resultSet.getString(i);
                        if (liteUsername.equalsIgnoreCase(mUsername)) {
                            flag = true;
                        }
                    }
                    if (flag = false){
                        String formatedString = String.format("INSERT INTO Username VALUES('%s');", mUsername);
                        mydatabase.execSQL(formatedString);
                    }
                    SharedPreferenceEntry entry2 = new SharedPreferenceEntry(
                            true, mUsername);
                    mSharedPreferencesHelper.savePersonalInfo(entry2);

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                    SharedPreferenceEntry entry3 = new SharedPreferenceEntry(false,"");
                    mSharedPreferencesHelper.savePersonalInfo(entry3);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
                Log.wtf(TAG, e.getMessage());
            }
        }
    }

}