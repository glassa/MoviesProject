package glassa.tacoma.uw.edu.moviesproject.movie;

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

/**
 * This activity is the Movie Page where users can rate Like, Haven't seen, or Dislike.
 * They can also click the button to view the imdb page for this movie.
 * It is invoked by MovieItemActitivy from the "OnListFragmentInteraction" function.
 * Also by the Search function.
 */
public class MovieActivity extends AppCompatActivity {

    /**
     * The base of the URL command to follow a user.
     */
    private static final String RATE_MOVIE_URL = "http://cssgate.insttech.washington.edu/~_450team2/rateMovie?";
    /**
     * The list of rated movies belongs to the user with this username string.
     */
    String mTargetUser;
    /**
     * The M current movie.
     */
    String mCurrentMovie;
    /**
     * The M current movie id.
     */
    int mCurrentMovieID;

    /**
     * This is run on create of the activity.  It gets and initializes the fields of the class:
     * The target user's username, the current movie's title and the current movie's movie ID
     * as it is in the database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get current user.
        mTargetUser = getIntent().getStringExtra("CURRENT_USER");

        mCurrentMovie = getIntent().getStringExtra("MOVIE_TITLE");

        mCurrentMovieID = getIntent().getIntExtra("MOVIE_ID", 0);

        setContentView(R.layout.activity_movie);


        TextView tv = (TextView) findViewById(R.id.movie_page_title);
        tv.setText(mCurrentMovie);
    }

    /**
     * The AsyncTask to rate a movie.  This method connects to the database and inserts
     * into the database that target user has rated the target movie into the rate table.
     */
    private class RateMovieTask extends AsyncTask<String, Void, String> {

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
                    Toast.makeText(getApplicationContext(), "You just rated " + mCurrentMovie
                            , Toast.LENGTH_SHORT)
                            .show();

                } else {
                    Toast.makeText(getApplicationContext(), "Failed: You've already rated " + mCurrentMovie
                            , Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }

    /**
     * This is called when the LIKE button is clicked on the movie page.  It starts the
     * AsyncTask to write to the database.
     *
     * @param view the view
     */
    public void rateLike(View view) {
        RateMovieTask task = new RateMovieTask();
        task.execute(buildUserURL(view, 1));
    }

    /**
     * This is called when the HAVEN"T SEEN button is clicked on the movie page.  It starts the
     * AsyncTask to write to the database.
     *
     * @param view the view
     */
    public void rateNoSee(View view) {
        RateMovieTask task = new RateMovieTask();
        task.execute(buildUserURL(view, 2));
    }

    /**
     * This is called when the DISLIKE button is clicked on the movie page.  It starts the
     * AsyncTask to write to the database.
     *
     * @param view the view
     */
    public void rateDislike(View view) {
        RateMovieTask task = new RateMovieTask();
        task.execute(buildUserURL(view, 3));
    }

    /**
     * Builds the URL for the RateMovie AsyncTask.  It creates the command for rating the
     * target movie for the target user.
     * @param v
     * @return
     */
    private String buildUserURL(View v, int ratingNum) {

        StringBuilder sb = new StringBuilder(RATE_MOVIE_URL);

        try {
            sb.append("Username=");
            sb.append(URLEncoder.encode(mTargetUser, "UTF-8"));

            sb.append("&MovieID=" + mCurrentMovieID);

            sb.append("&Rating=" + ratingNum);

            Log.i("MovieActivity", "URL=" + sb.toString());
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

}
