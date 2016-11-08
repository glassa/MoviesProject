package glassa.tacoma.uw.edu.moviesproject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.movie.Movie;




/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Home extends Fragment {
    private ListView listFeed;
    private List<String> feedList;

    public Tab1Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        listFeed = (ListView) getView().findViewById(R.id.homeFeed);
//        feedList = new ArrayList<String>();


        return inflater.inflate(R.layout.fragment_tab1_home, container, false);
    }

    private class DownloadCoursesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }


        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

}