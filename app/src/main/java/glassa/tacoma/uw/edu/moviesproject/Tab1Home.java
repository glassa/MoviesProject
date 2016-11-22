package glassa.tacoma.uw.edu.moviesproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Home extends Fragment {
    private ListView listFeed;
    private List<String> feedList;

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
        return v;
    }
}