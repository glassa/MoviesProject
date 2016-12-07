package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import glassa.tacoma.uw.edu.moviesproject.movie.MovieActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3FindMovies extends Fragment {

    private final String URL="http://cssgate.insttech.washington.edu/~_450team2/movieSearcher.php";
    private String myPass = "";
    String mCurrentUser;
    private static final String TAG = "Tab3FindMovies.java";
    /**
     * The M current movie id.
     */
    int mCurrentMovieID;

    /**
     * Instantiates a new Tab 3 find movies.
     */
    public Tab3FindMovies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Find Movies");

        View v = inflater.inflate(R.layout.fragment_tab3_find_movies, container, false);

        mCurrentUser = ((TabHostActivity) getActivity()).getmCurrentUser();


        final EditText myInput= (EditText)v.findViewById(R.id.search_movie_bar);
        Button mySearch = (Button)v.findViewById(R.id.search_movie_button);
        mySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myS = myInput.getText().toString();
                myPass = URL + "?movie=" + myS;
                Log.i(TAG, "string: " + myS);

                Log.i(TAG, "pass: " + myPass);
                run(myPass, v);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    public void run(String theURL, View v) {
        Log.i(TAG, "URL: " + theURL);
        getFilteredMovieTask getFilteredMovieTask = new getFilteredMovieTask();
        getFilteredMovieTask.execute(new String[]{theURL.toString()});
    }

    private class getFilteredMovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            Log.i(TAG, "ASYNC METHOD");
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

        @Override
        protected void onPostExecute(String JSONresult) {

            if (JSONresult.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), JSONresult, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            final ArrayList<String> arrayList = new ArrayList<>();
            final ArrayList<Integer> idList = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(JSONresult);
                for (int j = 0; j < jArray.length(); j++) {
                    JSONObject json_data = jArray.getJSONObject(j);
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();


                    String username = json_data.getString("Name");
                    int id = json_data.getInt("MovieID");

                    Log.i(TAG, "username: " + username + " movie id: " + id);
                    sb.append(username);

                    sb2.append(id);


                    arrayList.add(sb.toString());
                    idList.add(id);
                }
            }
            catch (Exception e) {
                Toast.makeText(getActivity(), "Sorry, we don't have that movie yet.", Toast.LENGTH_SHORT).show();
            }

            ListView list=(ListView) getView().findViewById(R.id.find_user_lv);
            Log.i(TAG, "List: " + arrayList.toString());
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, arrayList);
            Log.i(TAG, "1");
            try {
                list.setAdapter(arrayAdapter);
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
            Log.i(TAG, "2");
            try {
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG, "3");
                        Intent myIntent = new Intent(getActivity(), MovieActivity.class);
                        myIntent.putExtra("MOVIE_TITLE", arrayList.get(position));
                        //write something here to transfer current user to ProfileActivity
                        myIntent.putExtra("CURRENT_USER", mCurrentUser);
                        mCurrentMovieID = idList.get(position);
                        myIntent.putExtra("MOVIE_ID", mCurrentMovieID);

                        startActivity(myIntent);
                    }
                });
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        }
    }
}