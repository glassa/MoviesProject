package glassa.tacoma.uw.edu.moviesproject.util;

/**
 * Created by aglass on 11/14/2016.
 */

import android.content.SharedPreferences;

/**
 *  Helper class to manage access to {@link SharedPreferences}.
 */
public class SharedPreferencesHelper {

    // Keys for saving values in SharedPreferences.
    public static final String KEY_LOGGED_IN = "key_logged_in";
    public static final String KEY_USERNAME = "key_username";

    // The injected SharedPreferences implementation to use for persistence.
    private final SharedPreferences mSharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The {@link SharedPreferences}
     *                          that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * Saves the given {@link SharedPreferenceEntry}
     * that contains the user's settings to
     * {@link SharedPreferences}.
     *
     * @param sharedPreferenceEntry contains data to save to {@link SharedPreferences}.
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     *         otherwise.
     */
    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry){
        // Start a SharedPreferences transaction.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, sharedPreferenceEntry.isLoggedIn());
        editor.putString(KEY_USERNAME, sharedPreferenceEntry.getUsername());

        // Commit changes to SharedPreferences.
        return editor.commit();
    }
/*
    public boolean clearPersonalInfo(SharedPreferenceEntry sharedPreferenceEntry){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear().apply();
        return true;
    }
*/
    /**
     * Retrieves the {@link SharedPreferenceEntry} containing the user's personal information from
     * {@link SharedPreferences}.
     *
     * @return the Retrieved {@link SharedPreferenceEntry}.
     */
    public SharedPreferenceEntry getLoginInfo() {
        // Get data from the SharedPreferences.
        boolean isLoggedIn = mSharedPreferences.getBoolean(KEY_LOGGED_IN, false);
        String username = mSharedPreferences.getString(KEY_USERNAME, "");

        // Create and fill a SharedPreferenceEntry model object.
        return new SharedPreferenceEntry(isLoggedIn, username);
    }
}
