package glassa.tacoma.uw.edu.moviesproject.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import glassa.tacoma.uw.edu.moviesproject.R;

/**
 * This activity holds the whole movie rating list fragment set.  Its invoked from
 * TabHostActivity.
 */
public class MovieItemActivity extends AppCompatActivity implements MovieItemFragment.OnListFragmentInteractionListener {
    /**
     * The list of rated movies belongs to the user with this username string.
     */
    private String mTargetUser;
    /**
     * The list of rated movies belongs to the user with this username string.
     */
    private String mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        mCurrentUser = getIntent().getStringExtra("CURRENT_USER");
        mTargetUser = getIntent().getStringExtra("TARGET_USER");

        Log.i("MovieItemActivity", "Current User: " + mCurrentUser);
        Log.i("MovieItemActivity", "Target User: " + mTargetUser);

        if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
            MovieItemFragment movieItemFragment = new MovieItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_item_frag_container, movieItemFragment)
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(MovieItem item) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("CURRENT_USER", mCurrentUser);
        intent.putExtra("TARGET_USER", mTargetUser);
        intent.putExtra("MOVIE_TITLE", item.getmMovieName());
        intent.putExtra("MOVIE_ID", item.getmMovieID());

        startActivity(intent);
    }

    /**
     * Getter for Current User.
     *
     * @return target user
     */
    public String getmTargetUser() {
        return mTargetUser;
    }

    /**
     * Getter for Current User.
     *
     * @return target user
     */
    public String getmCurrentUser() {
        return mCurrentUser;
    }

}
