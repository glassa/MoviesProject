package glassa.tacoma.uw.edu.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import glassa.tacoma.uw.edu.moviesproject.follow_item.FollowItemActivity;
import glassa.tacoma.uw.edu.moviesproject.movie.MovieItemActivity;

import static android.R.attr.fragment;
import static android.R.attr.tag;

/**
 * This is the activity that holds the TabHost. This allows for tab navigation.
 */
public class TabHostActivity extends AppCompatActivity {

    private FragmentTabHost TabHost;
    /**
     * The current User's username.
     */
    String mCurrentUser;


    /**
     * The oncreate method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);


        mCurrentUser = getIntent().getStringExtra("USERNAME");

        TabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        TabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        TabHost.addTab(TabHost.newTabSpec("tab1").setIndicator("Home"),
                Tab1Home.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab2").setIndicator("Rate Movies"),
                Tab2RateMovies.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab3").setIndicator("Find Movies"),
                Tab3FindMovies.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab4").setIndicator("Find Users"),
                Tab4FindUsers.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab5").setIndicator("Settings"),
                Tab5Settings.class, null);
    }

    /**
     * View following users.
     *
     * @param view the view
     */
    public void viewFollowingUsers(View view) {
        Toast.makeText(view.getContext(), "viewing users following you", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "following users clicked");

        Log.i("TabHostActivity", "Current User: " + mCurrentUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWERS", true);
        i.putExtra("USERNAME", mCurrentUser);
        startActivity(i);
    }

    /**
     * View users im following.
     *
     * @param view the view
     */
    public void viewUsersImFollowing(View view) {
        Toast.makeText(view.getContext(), "viewing users you are following", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "view users i'm following");


        Log.i("TabHostActivity", "Current User: " + mCurrentUser);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWING", true);
        i.putExtra("USERNAME", mCurrentUser);
        startActivity(i);


    }

    /**
     * View rated movies.
     *
     * @param view the view
     */
    public void viewRatedMovies(View view) {
        Toast.makeText(view.getContext(), "viewing movies you've rated", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "rate movies clicked");

        Intent i = new Intent(this, MovieItemActivity.class);
        i.putExtra("CURRENT_USER", mCurrentUser);
        i.putExtra("TARGET_USER", mCurrentUser);
        startActivity(i);

    }

    public void launch(View v){
        DialogFragment fragment = null;
        if (v.getId() == R.id.change_user_button) {
            fragment = new ChangeUserInfoDialog();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment.show(fragmentManager, "launch");
        }
    }

    public void logout(View view) {
        Intent i = new Intent(this, LoginFragment.class);
        startActivity(i);


        getSupportFragmentManager().beginTransaction()
               .add(R.id.hometabhost, new LoginFragment())
               .commit();
    }


}