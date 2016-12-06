package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glassa.tacoma.uw.edu.moviesproject.search.SearchUserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4FindUsers extends Fragment {

    String mCurrentUser;

    /**
     * Instantiates a new Tab 4 find users.
     */
    public Tab4FindUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = new Intent(getActivity(), SearchUserActivity.class);

        mCurrentUser = ((TabHostActivity)getActivity()).getmCurrentUser();
        intent.putExtra("CURRENT_USER", mCurrentUser);

        startActivity(intent);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab4_find_users, container, false);
    }

}
