package glassa.tacoma.uw.edu.moviesproject.follow_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * This class holds the follow object.  It holds one instance of who is
 * following who.
 */
public class FollowItem implements Serializable{

    public static final String USER_A = "UserA", USER_B = "UserB";

    /**
     * The string usernames of both of the target users.
     */
    String mUserA, mUserB;

    /**
     * The constructor of the FollowItem.  Takes in both the current user's username string
     * and the target user's username string.
     *
     * @param mUserA current user's username.
     * @param mUserB target user's username.
     */
    public FollowItem(String mUserA, String mUserB) {
        this.mUserA = mUserA;
        this.mUserB = mUserB;
    }

    /**
     * Getter for current username.
     * @return
     */
    public String getmUserA() {
        return mUserA;
    }

    /**
     * Setter for current username.
     * @param mUserA
     */
    public void setmUserA(String mUserA) {
        this.mUserA = mUserA;
    }

    /**
     * Getter for target username.
     * @return
     */
    public String getmUserB() {
        return mUserB;
    }

    /**
     * Setter for target username.
     * @param mUserB
     */
    public void setmUserB(String mUserB) {
        this.mUserB = mUserB;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns userA and userB as two FollowItems in an array if success.
     * @param followListJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String followListJSON, List<FollowItem> fItemList) {
        String reason = null;
        if (followListJSON != null) {
            try {
                JSONArray arr = new JSONArray(followListJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    FollowItem fItem = new FollowItem(obj.getString(FollowItem.USER_A), obj.getString(FollowItem.USER_B));

                    fItemList.add(fItem);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}
