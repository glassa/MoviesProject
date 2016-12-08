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
class FollowItem implements Serializable{

    /**
     * The constant USER_A.
     */
    private static final String USER_A = "UserA", /**
     * The User b.
     */
    USER_B = "UserB";

    /**
     * The string usernames of both of the target users.
     */
    private String mUserA, /**
     * The M user b.
     */
    mUserB;

    /**
     * The constructor of the FollowItem.  Takes in both the current user's username string
     * and the target user's username string.
     *
     * @param mUserA current user's username.
     * @param mUserB target user's username.
     */
    private FollowItem(String mUserA, String mUserB) {
        this.mUserA = mUserA;
        this.mUserB = mUserB;
    }

    /**
     * Getter for current username.
     *
     * @return user a
     */
    String getmUserA() {
        return mUserA;
    }

    /**
     * Getter for target username.
     *
     * @return user b
     */
    String getmUserB() {
        return mUserB;
    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns userA and userB as two FollowItems in an array if success.
     *
     * @param followListJSON the follow list json
     * @param fItemList      the f item list
     * @return reason or null if successful.
     */
    static String parseFollowItemJSON(String followListJSON, List<FollowItem> fItemList) {
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
