package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import glassa.tacoma.uw.edu.moviesproject.R;

public class FollowItemActivity extends AppCompatActivity implements FollowItemFragment.OnListFragmentInteractionListener{

    private String mUsername;
    private Boolean mFollowingButton;
    private Boolean mFollowersButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_item);

        mUsername = getIntent().getStringExtra("USERNAME");
        mFollowingButton = getIntent().getBooleanExtra("FOLLOWING", false);
        mFollowersButton = getIntent().getBooleanExtra("FOLLOWERS", false);

        Log.i("FollowItemActivity", "Following = " + mFollowingButton.toString());
        Log.i("FollowItemActivity", "Followers = " + mFollowersButton.toString());

        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            FollowItemFragment FollowItemFragment = new FollowItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.follow_item_frag_container, FollowItemFragment)
                    .commit();
        }

    }

    @Override
    public void onListFragmentInteraction(FollowItem item) {

    }

    public String getmUsername() {
        return mUsername;
    }

    public Boolean getmFollowingButton() {
        return mFollowingButton;
    }

    public Boolean getmFollowersButton() {
        return mFollowersButton;
    }
}
