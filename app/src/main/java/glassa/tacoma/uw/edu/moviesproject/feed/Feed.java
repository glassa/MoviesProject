package glassa.tacoma.uw.edu.moviesproject.feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tony on 11/8/2016.
 */

public class Feed implements Serializable {
    public static final String HOST_ID = "HostID", FOLLOWING_ID = "FollowingID",
            MOVIE_ID= "MovieID";

    String mHostID, mFollowingID, mMovieID;

    public Feed(String mHostID, String mFollowingID, String mMovieID) {
        this.mHostID = mHostID;
        this.mFollowingID = mFollowingID;
        this.mMovieID = mMovieID;
    }

    public String getmHostID() {
        return mHostID;
    }

    public void setmHostID(String mHostID) {
        this.mHostID = mHostID;
    }

    public String getmFollowingID() {
        return mFollowingID;
    }

    public void setmFollowingID(String mFollowingID) {
        this.mFollowingID = mFollowingID;
    }

    public String getmMovieID() {
        return mMovieID;
    }

    public void setmMovieID(String mMovieID) {
        this.mMovieID = mMovieID;
    }




    public static String parseFeedJSON(String feedJSON, List<Feed> feedList) {
        String reason = null;
        if (feedJSON != null) {
            try {
                JSONArray arr = new JSONArray(feedJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Feed feed = new Feed(obj.getString(Feed.HOST_ID), obj.getString(Feed.FOLLOWING_ID)
                            , obj.getString(Feed.MOVIE_ID));
                    feedList.add(feed);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}