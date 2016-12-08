package glassa.tacoma.uw.edu.moviesproject.util;

/**
 * Created by aglas on 11/14/2016.
 */

/**
 * Model class containing personal information that will be
 * saved to SharedPreferences.
 * Add to this class any other app related information
 * that needs to be cached.
 */
public class SharedPreferenceEntry {

    // Name of the user.
    private boolean mIsLoggedIn = false;

    // Username of the user.
    private final String mUsername;

    // Add others here..

    public SharedPreferenceEntry(boolean loggedIn, String username) {
        mIsLoggedIn = loggedIn;
        mUsername = username;
//        Log.i("SPEntry", username);
    }

    /**
     * Method to determine whether user is logged in.
     * @return
     */
    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    /**
     * Getter to get username.
     * @return
     */
    public String getUsername() {
        return mUsername;
    }
}
