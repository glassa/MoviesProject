package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import glassa.tacoma.uw.edu.moviesproject.R;

public class FollowItemActivity extends AppCompatActivity implements FollowItemFragment.OnListFragmentInteractionListener{

    public String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_item);

        mUsername = getIntent().getStringExtra("USERNAME");

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
}
