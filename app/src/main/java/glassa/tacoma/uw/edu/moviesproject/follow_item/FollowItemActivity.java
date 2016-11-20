package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.profile.ProfileActivity;

public class FollowItemActivity extends AppCompatActivity implements FollowItemFragment.OnListFragmentInteractionListener{

    private String mCurrentUser;
    private Boolean mFollowingButton;
    private Boolean mFollowersButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_item);


        mCurrentUser = getIntent().getStringExtra("USERNAME");
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
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("CURRENT_USER", mCurrentUser);
        if (mFollowingButton) {
            intent.putExtra("TARGET_USER", item.getmUserB());
        } else if (mFollowersButton) {

            intent.putExtra("TARGET_USER", item.getmUserA());
        }


        startActivity(intent);

    }

    public String getmCurrentUser() {
        return mCurrentUser;
    }

    public Boolean getmFollowingButton() {
        return mFollowingButton;
    }

    public Boolean getmFollowersButton() {
        return mFollowersButton;
    }

    public void setActionBarTitle(String title) {
        getActionBar().setTitle(title);
    }
}
