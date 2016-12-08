package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.movie.MovieActivity;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * The fragment of the first tab of the TabHost on MainActivity. This handles the
 * public feed function.
 */
public class Tab1Home extends Fragment {
    private ListView listFeed;
    private List<String> feedList;
    private static final String FEED_URL = "http://cssgate.insttech.washington.edu/~_450team2/list.php?cmd=rate_feed";

    private static final String TAG = "Tab1Home.java";
    Button b1;
    private int mCurrentMovieID;
    private String mCurrentMovieName;

    SharedPreferencesHelper mSharedPreferencesHelper;

    /**
     * Instantiates a new Tab 1 home.
     */
    public Tab1Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1_home, container, false);
        getActivity().setTitle("Home");
        run(FEED_URL, v);
        b1 = (Button) v.findViewById(R.id.logout_button);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        mSharedPreferencesHelper = new SharedPreferencesHelper(
                sharedPreferences);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferenceEntry entry1 = new SharedPreferenceEntry(false,"");
                mSharedPreferencesHelper.savePersonalInfo(entry1);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    void run(String theURL, View v) {

        GetFeedTask task = new GetFeedTask();
        task.execute(new String[]{theURL.toString()});

        final ArrayList<String> feedList = new ArrayList<>();
        final ArrayList<Integer> movieIdList = new ArrayList<>();
        final ArrayList<String> movieNameList = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(URLDecoder.decode(task.get(), "UTF-8" ));
            Log.i(TAG, "Array Length: " + jArray.length());
            for (int j = 0; j < jArray.length(); j++) {

                JSONObject json_data = jArray.getJSONObject(j);
                StringBuilder sb = new StringBuilder();


                String username = json_data.getString("Username");
                String movieName = json_data.getString("Name");
                int movieId = json_data.getInt("MovieID");

                sb.append(username + " just rated " + movieName + ".");
                Log.i(TAG, "Current JSON String: " + sb.toString());
                feedList.add(sb.toString());
                movieIdList.add(movieId);
                movieNameList.add(movieName);
            }
        }
        catch (Exception e) {
            Log.e("", "" + e);
        }
        Log.i(TAG, "1");
        ListView feedListView = (ListView) v.findViewById(R.id.user_feed_list_view);
        Log.i(TAG, "2");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, feedList);
        Log.i(TAG, "3");
        feedListView.setAdapter(arrayAdapter);
        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MovieActivity.class);
                mCurrentMovieName = movieNameList.get(position);
                myIntent.putExtra("MOVIE_TITLE", mCurrentMovieName);
                myIntent.putExtra("CURRENT_USER", ((TabHostActivity)getActivity()).getmCurrentUser());
                mCurrentMovieID = movieIdList.get(position);
                myIntent.putExtra("MOVIE_ID", mCurrentMovieID);

                startActivity(myIntent);
            }
        });
    }

    private class GetFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    java.net.URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}