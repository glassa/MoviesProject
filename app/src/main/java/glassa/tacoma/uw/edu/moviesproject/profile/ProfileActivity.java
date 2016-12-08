package glassa.tacoma.uw.edu.moviesproject.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import glassa.tacoma.uw.edu.moviesproject.movie.MovieItemActivity;

/**
 * This is the activity that holds the profile of any user.  This is where users
 * can go to view another user's followers, view who they are following, view
 * what movies they have rated, or follow them themselves.
 */
public class ProfileActivity extends AppCompatActivity {
    /**
     * The base of the URL command to follow a user.
     */
    private static final String FOLLOW_ITEM_URL = "http://cssgate.insttech.washington.edu/~_450team2/followUser?";
    private static final String MATCHES_URL= "http://cssgate.insttech.washington.edu/~_450team2/list.php?cmd=matches";
    /**
     * The current user's username.
     */
    String mCurrentUser;
    /**
     * The target user's username.
     */
    String mTargetUser; //this should be set upon creation of this class

    /**
     * The textview holding the profile headline
     */
    TextView tv;

    /**
     * The Textview holding the percent match
     */
    TextView tv2;

    private static final String TAG = "ProfileActivity.java";

    int percentMatch;

    public int getPercentMatch() {
        return percentMatch;
    }

    public void setPercentMatch(double percentMatch) {
        this.percentMatch = (int) (percentMatch * 100);
        tv2 = (TextView) findViewById(R.id.match_tv);
        tv2.setText("You and " + mTargetUser + " have " + this.percentMatch + "% matching movie ratings");
    }

    /**
     * This is called at the start of the activity. It sets the current and target
     * users to what they were in the previous activity and sets the textview on the
     * layout xml.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUser = getIntent().getStringExtra("CURRENT_USER");
        mTargetUser = getIntent().getStringExtra("TARGET_USER");

        Log.i(TAG, "target user: " + mTargetUser);
        setContentView(R.layout.activity_profile);

        tv = (TextView) findViewById(R.id.target_user_id);
        Log.i(TAG, "ffjf" + mTargetUser + "fjfjf");
        tv.setText("Viewing " + mTargetUser + "'s profile!");
        checkMatches();
    }

    /**
     * The AsyncTask to follow a user.  This method connects to the database and inserts
     * into the database that the Current user follows the Target user.
     */
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
                    String s;
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

                    //check matches and differences -> insert to database
                    checkMatches();

                    Toast.makeText(getApplicationContext(), "You are now following " + mTargetUser
                            , Toast.LENGTH_SHORT)
                            .show();
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

    public void checkMatches() {
        CheckMatchesTask task = new CheckMatchesTask();
        task.execute(buildMatchesURL(this.findViewById(android.R.id.content)));

    }
    /**
     * Starts the AsyncTask to follow the user. Its triggored by the button "Follow"
     *
     * @param view the view
     */
    public void followUser(View view) {
        FollowUserTask task = new FollowUserTask();
        task.execute(buildFollowURL(view));
    }

    /**
     * Opens the list of those following the target user.
     *
     * @param view the view
     */
    public void viewFollowingUsers(View view) {
        Log.i("home", "following users clicked");
        Log.i("TabHostActivity", "Current User: " + mTargetUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWERS", true);
        i.putExtra("USERNAME", mTargetUser);
        startActivity(i);
    }

    /**
     * Opens the list of those that the target user follows.
     *
     * @param view the view
     */
    public void viewUsersImFollowing(View view) {
        Log.i("home", "view users i'm following");


        Log.i("TabHostActivity", "Current User: " + mTargetUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWING", true);
        i.putExtra("USERNAME", mTargetUser);
        startActivity(i);
    }

    /**
     * Opens the list of movies that the target user has rated.
     *
     * @param view the view
     */
    public void viewRatedMovies(View view) {
        Log.i("home", "rate movies clicked");
        Intent i = new Intent(this, MovieItemActivity.class);
        i.putExtra("TARGET_USER", mTargetUser);
        i.putExtra("CURRENT_USER", mCurrentUser);
        startActivity(i);
    }

    /**
     * Builds the URL for the FollowUser AsyncTask.  It creates the command for
     * @param v the current View
     * @return the URL String
     */
    private String buildFollowURL(View v) {

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

    /**
     * Builds the URL to execute the PHP / SQL command to get the number of
     * matches and number of differences in ratings between user A and user B.
     * @param v
     * @return
     */
    private String buildMatchesURL(View v) {

        StringBuilder sb = new StringBuilder(MATCHES_URL);

        try {
            sb.append("&UserA=");
            sb.append(URLEncoder.encode(mCurrentUser, "UTF-8"));

            sb.append("&UserB=");
            sb.append(URLEncoder.encode(mTargetUser, "UTF-8"));
            Log.i(TAG, "URL=" + sb.toString());
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }



    /**
     * The AsyncTask to follow a user.  This method connects to the database and inserts
     * into the database that the Current user follows the Target user.
     */
    private class CheckMatchesTask extends AsyncTask<String, Void, String> {

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
                    String s;
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

            Log.i(TAG, "Result: " + result);
            try {
                JSONArray arr = new JSONArray(result);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    double matches = 0, differences= 0, total = 0;
                    matches = obj.getInt("Matches");
                    differences = obj.getInt("Differences");
                    total = (matches + differences);
                    setPercentMatch((matches / total));
                    Log.i(TAG, "Matches: " + matches + "  Diff: " + differences + "Total: " + total);
                    Log.i(TAG, "Percentage Match: " + percentMatch);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error parsing matches " +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
