package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * The fragment of the fourth tab of the TabHost on MainActivity. This handles the
 * settings actions of the app.
 */
public class Tab5Settings extends Fragment {

//    Button b1;
//    SharedPreferencesHelper mSharedPreferencesHelper;

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    SharedPreferencesHelper mSharedPreferencesHelper;

    /**
     * Instantiates a new Tab 5 settings.
     */
    public Tab5Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Settings");
        View v = inflater.inflate(R.layout.fragment_tab5_settings, container, false);

        b1 = (Button) v.findViewById(R.id.logout_button);
        b2 = (Button) v.findViewById(R.id.change_user_button);

        b4 = (Button) v.findViewById(R.id.share_button1);
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


        b4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fragment = new MessageFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.setting_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }});


    return v;
    }

}