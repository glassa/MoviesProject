package glassa.tacoma.uw.edu.moviesproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2RateMovies extends Fragment {

    private String mCurrentUser;

    /**
     * Instantiates a new Tab 2 rate movies.
     */
    public Tab2RateMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        mCurrentUser = ((TabHostActivity) getActivity()).getmCurrentUser();
        getActivity().setTitle("Rate Movie");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab2_rate_movies, container, false);
    }

}