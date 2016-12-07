package glassa.tacoma.uw.edu.moviesproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import glassa.tacoma.uw.edu.moviesproject.follow_item.FollowItemActivity;
import glassa.tacoma.uw.edu.moviesproject.movie.MovieItemActivity;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * This is the activity that holds the TabHost. This allows for tab navigation.
 */
public class TabHostActivity extends AppCompatActivity {

    private FragmentTabHost TabHost;
    private static final String TAG = "TabHostActivity.java";
    /**
     * The current User's username.
     */
    String mCurrentUser;
    SharedPreferencesHelper mSharedPreferencesHelper;



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
        Log.i(TAG, "Current User: " + mCurrentUser);

        TabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        TabHost.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View v) {}

            @Override
            public void onViewAttachedToWindow(View v) {
                TabHost.getViewTreeObserver().removeOnTouchModeChangeListener(TabHost);
            }
        });


        TabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        TabHost.addTab(TabHost.newTabSpec("tab1").setIndicator("", getResources().getDrawable(R.drawable.ic_home_black_48dp)),
                Tab1Home.class, null);
//        TabHost.addTab(TabHost.newTabSpec("tab2").setIndicator("", getResources().getDrawable(R.drawable.ic_star_half_black_48dp)),
//                Tab2RateMovies.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab3").setIndicator("", getResources().getDrawable(R.drawable.ic_movies)),
                Tab3FindMovies.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab4").setIndicator("", getResources().getDrawable(R.drawable.ic_person_black_48dp)),
                Tab4FindUsers.class, null);
        TabHost.addTab(TabHost.newTabSpec("tab5").setIndicator("", getResources().getDrawable(R.drawable.ic_settings_black_48dp)),
                Tab5Settings.class, null);

    }

    public String getmCurrentUser() {
        return mCurrentUser;
    }

    public void setmCurrentUser(String mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }
    /**
     * View following users.
     *
     * @param view the view
     */
    public void viewFollowingUsers(View view) {
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
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(
                sharedPreferences);
        SharedPreferenceEntry entry1 = new SharedPreferenceEntry(false,"");
        mSharedPreferencesHelper.savePersonalInfo(entry1);
        Intent i = new Intent(this, LoginFragment.class);
        startActivity(i);


        getSupportFragmentManager().beginTransaction()
               .add(R.id.hometabhost, new LoginFragment())
               .commit();
    }

    public void OnPause(){
        super.onPause();
        this.finish();
    }
}