package glassa.tacoma.uw.edu.moviesproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost TabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
