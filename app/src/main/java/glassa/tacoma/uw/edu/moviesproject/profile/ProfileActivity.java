package glassa.tacoma.uw.edu.moviesproject.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.follow_item.FollowItemActivity;

public class ProfileActivity extends AppCompatActivity {
    private static final String FOLLOW_ITEM_URL
            = "http://cssgate.insttech.washington.edu/~_450team2/followUser?";

    String mCurrentUser;
    String mTargetUser; //this should be set upon creation of this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUser = getIntent().getStringExtra("CURRENT_USER");
        mTargetUser = getIntent().getStringExtra("TARGET_USER");

        Log.i("ProfileActivity", "target user: " + mTargetUser);


//        tv.append(mTargetUser);
        setContentView(R.layout.activity_profile);
        TextView tv = (TextView) findViewById(R.id.target_user_id);
        tv.setText("You are viewing " + mTargetUser + "'s profile!");


    }

    private class FollowUserTask extends AsyncTask<String, Void, String> {

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
         * If it was, it takes the user to the TabHostActivity via an intent.
         * If not, it displays the exception.
         *
         * @param result Passed by doInBackground. Used to determine if the user login was successful.
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "You are now following " + mTargetUser
                            , Toast.LENGTH_SHORT)
                            .show();
//                    Intent intent = new Intent(getApplicationContext(), TabHostActivity.class);
//                    Log.i("MainActivity", "username: " + mUsername);
//                    intent.putExtra("USERNAME", mUsername);
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "You are already following " + mTargetUser
                            , Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }

    public void followUser(View view) {
        FollowUserTask task = new FollowUserTask();
        task.execute(buildUserURL(view));
    }

    public void viewFollowingUsers(View view) {
        Toast.makeText(view.getContext(), "viewing users following you", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "following users clicked");
//        if (findViewById(R.id.realtabcontent)!= null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.hometabhost, new FollowingFragment())
//                    .commit();
//        }

        Log.i("TabHostActivity", "Current User: " + mTargetUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWERS", true);
        i.putExtra("USERNAME", mTargetUser);
        startActivity(i);
    }

    public void viewUsersImFollowing(View view) {
        Toast.makeText(view.getContext(), "viewing users you are following", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "view users i'm following");


        Log.i("TabHostActivity", "Current User: " + mTargetUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWING", true);
        i.putExtra("USERNAME", mTargetUser);
        startActivity(i);


    }
    public void viewRatedMovies(View view) {
        Toast.makeText(view.getContext(), "viewing movies you've rated", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "rate movies clicked");
//        if (findViewById(R.id.realtabcontent)!= null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.hometabhost, new FollowingFragment())
//                    .commit();
//        }
    }


    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(FOLLOW_ITEM_URL);

        try {
            sb.append("UserA=");
            sb.append(URLEncoder.encode(mCurrentUser, "UTF-8"));

            sb.append("&UserB=");
            sb.append(URLEncoder.encode(mTargetUser, "UTF-8"));
            Log.i("ProfileActivity", "URL=" + sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }
}
