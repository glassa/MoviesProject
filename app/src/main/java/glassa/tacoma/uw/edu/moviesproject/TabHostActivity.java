package glassa.tacoma.uw.edu.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import glassa.tacoma.uw.edu.moviesproject.follow_item.FollowItemActivity;

/**
 * This is the activity that holds the TabHost. This allows for tab navigation.
 */
public class TabHostActivity extends AppCompatActivity {

    private FragmentTabHost TabHost;


    /**
     * The oncreate method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);



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

    public void viewFollowingUsers(View view) {
        Toast.makeText(view.getContext(), "viewing users following you", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "following users clicked");
//        if (findViewById(R.id.realtabcontent)!= null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.hometabhost, new FollowingFragment())
//                    .commit();
//        }

        String username = getIntent().getStringExtra("USERNAME");
        Log.i("TabHostActivity", "Current User: " + username);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWERS", true);
        i.putExtra("USERNAME", username);
        startActivity(i);
    }

    public void viewUsersImFollowing(View view) {
        Toast.makeText(view.getContext(), "viewing users you are following", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "view users i'm following");

        String username = getIntent().getStringExtra("USERNAME");

        Log.i("TabHostActivity", "Current User: " + username);

        Intent i = new Intent(this, FollowItemActivity.class);
        i.putExtra("FOLLOWING", true);
        i.putExtra("USERNAME", username);
        startActivity(i);


    }
    public void viewRatedMovies(View view) {
        Toast.makeText(view.getContext(), "viewing movies you've rated", Toast.LENGTH_SHORT)
                .show();
        Log.i("home", "rate movies clicked");
//        if (findViewById(R.id.realtabcontent)!= null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.hometabhost, new FollowingFragment())
//                    .commit();
//        }
    }


}