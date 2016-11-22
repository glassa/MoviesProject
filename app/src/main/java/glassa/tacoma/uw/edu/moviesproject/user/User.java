package glassa.tacoma.uw.edu.moviesproject.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aglas on 11/7/2016.
 */
public class User implements Serializable{
    /**
     * The M user id.
     */
    String mUserID, /**
     * The M user name.
     */
    mUserName, /**
     * The M user pass.
     */
    mUserPass;
    /**
     * The constant ID.
     */
    public static final String ID = "id", /**
     * The User name.
     */
    USER_NAME = "name", /**
     * The User pass.
     */
    USER_PASS = "pass";
    /**
     * The Is logged in.
     */
    boolean isLoggedIn;

    /**
     * Instantiates a new User.
     *
     * @param mUserID   the m user id
     * @param mUserName the m user name
     * @param mUserPass the m user pass
     */
    public User(String mUserID, String mUserName, String mUserPass) {
        this.mUserID = mUserID;
        this.mUserName = mUserName;
        this.mUserPass = mUserPass;
    }

    /**
     * Is logged in boolean.
     *
     * @return the boolean
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Sets logged in.
     *
     * @param loggedIn the logged in
     */
    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getmUserID() {
        return mUserID;
    }

    /**
     * Sets user id.
     *
     * @param mUserID the m user id
     */
    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getmUserName() {
        return mUserName;
    }

    /**
     * Sets user name.
     *
     * @param mUserName the m user name
     */
    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    /**
     * Gets user pass.
     *
     * @return the user pass
     */
    public String getmUserPass() {
        return mUserPass;
    }

    /**
     * Sets user pass.
     *
     * @param mUserPass the m user pass
     */
    public void setmUserPass(String mUserPass) {
        this.mUserPass = mUserPass;
    }

    /**
     * Parse user json string.
     *
     * @param userJSON the user json
     * @param userList the user list
     * @return the string
     */
    public static String parseUserJSON(String userJSON, List<User> userList){
        String reason = null;
        if (userJSON != null) {
            try {
                JSONArray arr = new JSONArray(userJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    User user = new User(obj.getString(User.ID), obj.getString(User.USER_NAME), obj.getString(User.USER_PASS));
                    userList.add(user);
                }
            } catch (JSONException e) {
                reason = "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
