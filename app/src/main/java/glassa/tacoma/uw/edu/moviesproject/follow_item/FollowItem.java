package glassa.tacoma.uw.edu.moviesproject.follow_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tony on 11/19/2016.
 */

public class FollowItem implements Serializable{
    String mUserA, mUserB, mMatches, mDifferences, mTimeStamp;
    public static final String USER_A = "UserA", USER_B = "UserB";


    public FollowItem(String mUserA, String mUserB) {
        this.mUserA = mUserA;
        this.mUserB = mUserB;
    }

    public String getmUserA() {
        return mUserA;
    }

    public void setmUserA(String mUserA) {
        this.mUserA = mUserA;
    }

    public String getmUserB() {
        return mUserB;
    }

    public void setmUserB(String mUserB) {
        this.mUserB = mUserB;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
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
