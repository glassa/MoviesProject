package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glassa.tacoma.uw.edu.moviesproject.search.SearchMovieActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3FindMovies extends Fragment {

    String mCurrentUser;

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
        Intent intent = new Intent(getActivity(), SearchMovieActivity.class);
        mCurrentUser = ((TabHostActivity) getActivity()).getmCurrentUser();
        intent.putExtra("CURRENT_USER", mCurrentUser);
        startActivity(intent);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3_find_movies, container, false);
    }
}