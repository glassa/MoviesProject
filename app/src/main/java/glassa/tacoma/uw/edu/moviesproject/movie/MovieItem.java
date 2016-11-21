package glassa.tacoma.uw.edu.moviesproject.movie;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tony on 11/21/2016.
 */

public class MovieItem implements Serializable {

    public static final String USER_NAME = "Username", MOVIE_RATING_NUM = "Rating", MOVIE_NAME = "Name", MOVIE_ID = "MovieID";

    String mTargetUser;
    String mMovieName;
    int mMovieRatingNum;
    int mMovieID;

    public MovieItem(String mTargetUser, int mMovieID, String mMovieName, int mMovieRatingNum) {
        this.mTargetUser = mTargetUser;
        this.mMovieName = mMovieName;
        this.mMovieRatingNum = mMovieRatingNum;
        this.mMovieID = mMovieID;
    }

    public int getmMovieID() {
        return mMovieID;
    }

    public void setmMovieID(int mMovieID) {
        this.mMovieID = mMovieID;
    }

    public String getmTargetUser() {
        return mTargetUser;
    }

    public void setmTargetUser(String mTargetUser) {
        this.mTargetUser = mTargetUser;
    }

    public String getmMovieName() {
        return mMovieName;
    }

    public void setmMovieName(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    public int getmMovieRatingNum() {
        return mMovieRatingNum;
    }

    public void setmMovieRatingNum(int mMovieRatingNum) {
        this.mMovieRatingNum = mMovieRatingNum;
    }

    public String getMovieRatingString() {
        if (mMovieRatingNum == 1) {
            return "Disliked";
        } else if (mMovieRatingNum == 2) {
            return "Haven't Seen";
        } else if (mMovieRatingNum == 3) {
            return "Liked";
        } else {
            return "";
        }
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns userA and userB as two FollowItems in an array if success.
     * @param movieListJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String movieListJSON, List<MovieItem> mItemList) {
        String reason = null;
        if (movieListJSON != null) {
            try {
                JSONArray arr = new JSONArray(movieListJSON);

                Log.i("MovieItem", "JSON Array Length: " + arr.length());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    MovieItem mItem = new MovieItem(
                            obj.getString(MovieItem.USER_NAME),
                            obj.getInt(MovieItem.MOVIE_ID),
                            obj.getString(MovieItem.MOVIE_NAME),
                            obj.getInt(MovieItem.MOVIE_RATING_NUM));

                    mItemList.add(mItem);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

}
