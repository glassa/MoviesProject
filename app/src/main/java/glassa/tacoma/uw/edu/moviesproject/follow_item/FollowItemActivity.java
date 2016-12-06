package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.profile.ProfileActivity;

/**
 * This is the host of the FollowItem fragments and the Following and Followers Fragment pages.
 */
public class FollowItemActivity extends AppCompatActivity implements FollowItemFragment.OnListFragmentInteractionListener{

    /**
     * Current user.
     */
    private String mCurrentUser;
    /**
     * Was the Following button clicked?
     */
    private Boolean mFollowingButton;
    /**
     * Was the Followers button clicked?
     */
    private Boolean mFollowersButton;


    /**
     * This method gets the info of current user and which button was clicked from the
     * "putExtra" from the TabHostActivity and assigns the information to the fields within
     * this class.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_item);

        mCurrentUser = getIntent().getStringExtra("USERNAME");
        mFollowingButton = getIntent().getBooleanExtra("FOLLOWING", false);
        mFollowersButton = getIntent().getBooleanExtra("FOLLOWERS", false);

        Log.i("FollowItemActivity", "Following = " + mFollowingButton.toString());
        Log.i("FollowItemActivity", "Followers = " + mFollowersButton.toString());

        if ((savedInstanceState == null) || (getSupportFragmentManager().findFragmentById(R.id.list) == null)) {
            FollowItemFragment FollowItemFragment = new FollowItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.follow_item_frag_container, FollowItemFragment)
                    .commit();
        }

    }

    /**
     * This method handles what happens when one of the items on the Following or Followers
     * list is clicked.  When it is clicked, the ProfileActivity of the clicked user is opened.
     */
    @Override
    public void onListFragmentInteraction(FollowItem item) {
        Intent intent = null;
        if (mFollowingButton) {
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("CURRENT_USER", mCurrentUser);
            intent.putExtra("TARGET_USER", item.getmUserB());
        } else if (mFollowersButton) {
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("CURRENT_USER", mCurrentUser);
            intent.putExtra("TARGET_USER", item.getmUserA());
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * Getter for Current User.
     *
     * @return current user
     */
    public String getmCurrentUser() {
        return mCurrentUser;
    }

    /**
     * Getter for if the following button was clicked.
     *
     * @return following button
     */
    public Boolean getmFollowingButton() {
        return mFollowingButton;
    }

    /**
     * Getter for if the followers button was clicked.
     *
     * @return followers button
     */
    public Boolean getmFollowersButton() {
        return mFollowersButton;
    }
}
