package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3FindMovies extends Fragment {


    /**
     * Instantiates a new Tab 3 find movies.
     */
    public Tab3FindMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = new Intent(getActivity(), SearchMovieActivity.class);
        startActivity(intent);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3_find_movies, container, false);
    }

}