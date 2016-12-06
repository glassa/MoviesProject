package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Home extends Fragment {
    private ListView listFeed;
    private List<String> feedList;
    Button b1;
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
}