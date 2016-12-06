package glassa.tacoma.uw.edu.moviesproject.feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * This class will hold each feed item.
 */
public class Feed implements Serializable {
    /**
     * The constant HOST_ID.
     */
    public static final String HOST_ID = "HostID", /**
     * The Following id.
     */
    FOLLOWING_ID = "FollowingID",
    /**
     * The Movie id.
     */
    MOVIE_ID= "MovieID";

    /**
     * The M host id.
     */
    String mHostID, /**
     * The M following id.
     */
    mFollowingID, /**
     * The M movie id.
     */
    mMovieID;

    /**
     * This is the feed constructor.
     *
     * @param mHostID      the host id in the database.
     * @param mFollowingID the id of the user being followed.
     * @param mMovieID     the movie id.
     */
    public Feed(String mHostID, String mFollowingID, String mMovieID) {
        this.mHostID = mHostID;
        this.mFollowingID = mFollowingID;
        this.mMovieID = mMovieID;
    }

    /**
     * Getter for host id.
     *
     * @return return host id.
     */
    public String getmHostID() {
        return mHostID;
    }

    /**
     * Setter for host id.
     *
     * @param mHostID the host id.
     */
    public void setmHostID(String mHostID) {
        this.mHostID = mHostID;
    }

    /**
     * Getter for the user beging followed.
     *
     * @return the user id.
     */
    public String getmFollowingID() {
        return mFollowingID;
    }

    /**
     * Setter for user followed.
     *
     * @param mFollowingID the user id.
     */
    public void setmFollowingID(String mFollowingID) {
        this.mFollowingID = mFollowingID;
    }

    /**
     * Getter for movie id.
     *
     * @return the movie id.
     */
    public String getmMovieID() {
        return mMovieID;
    }

    /**
     * Setter for movie ID.
     *
     * @param mMovieID the movie id.
     */
    public void setmMovieID(String mMovieID) {
        this.mMovieID = mMovieID;
    }


    /**
     * The JSON parser from database to code.  This is the translator. It adds the JSON list
     * to the feedList array.
     *
     * @param feedJSON the JSON string
     * @param feedList The array of feed items.
     * @return return string
     */
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