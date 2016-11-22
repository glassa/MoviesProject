package glassa.tacoma.uw.edu.moviesproject.movie;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents an individual movie item and its corresponding rating with its user.
 * It holds the movie name, movie ID (as it is in the database), its corresponding user's username,
 * and the corresponding rating that the user entered for this particular movie.
 */
class MovieItem implements Serializable {
    /**
     * The constant names of the columns as they are in the SQL database.
     */
    private static final String USER_NAME = "Username", /**
     * The Movie rating num.
     */
    MOVIE_RATING_NUM = "Rating", /**
     * The Movie name.
     */
    MOVIE_NAME = "Name", /**
     * The Movie id.
     */
    MOVIE_ID = "MovieID";

    /**
     * The list of rated movies belongs to the user with this username string.
     */
    private String mTargetUser;

    /**
     * The string for the movie title.
     */
    private String mMovieName;
    /**
     * The int value of the rating given.
     * 1 = dislike
     * 2 = haven't seen
     * 3 = like
     */
    private int mMovieRatingNum;
    /**
     * The movie ID of the movie as is in database.
     */
    private int mMovieID;

    /**
     * The constructor of the movie item.  Initializes the required fields of the movie item.
     *
     * @param mTargetUser     the m target user
     * @param mMovieID        the m movie id
     * @param mMovieName      the m movie name
     * @param mMovieRatingNum the m movie rating num
     */
    private MovieItem(String mTargetUser, int mMovieID, String mMovieName, int mMovieRatingNum) {
        this.mTargetUser = mTargetUser;
        this.mMovieName = mMovieName;
        this.mMovieRatingNum = mMovieRatingNum;
        this.mMovieID = mMovieID;
    }

    /**
     * Gets movie id.
     *
     * @return the movie id
     */
    int getmMovieID() {
        return mMovieID;
    }


    /**
     * Gets target user.
     *
     * @return the target user
     */
    public String getmTargetUser() {
        return mTargetUser;
    }

    /**
     * Sets target user.
     *
     * @param mTargetUser the m target user
     */
    public void setmTargetUser(String mTargetUser) {
        this.mTargetUser = mTargetUser;
    }

    /**
     * Gets movie name.
     *
     * @return the movie name
     */
    public String getmMovieName() {
        return mMovieName;
    }

    /**
     * Sets movie name.
     *
     * @param mMovieName the m movie name
     */
    public void setmMovieName(String mMovieName) {
        this.mMovieName = mMovieName;
    }

    /**
     * Gets movie rating num.
     *
     * @return the movie rating num
     */
    public int getmMovieRatingNum() {
        return mMovieRatingNum;
    }

    /**
     * Sets movie rating num.
     *
     * @param mMovieRatingNum the m movie rating num
     */
    public void setmMovieRatingNum(int mMovieRatingNum) {
        this.mMovieRatingNum = mMovieRatingNum;
    }

    /**
     * Gets movie rating string.
     *
     * @return the movie rating string
     */
    String getMovieRatingString() {
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
     *
     * @param movieListJSON the movie list json
     * @param mItemList     the m item list
     * @return reason or null if successful.
     */
    static String parseCourseJSON(String movieListJSON, List<MovieItem> mItemList) {
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
