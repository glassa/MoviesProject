package glassa.tacoma.uw.edu.moviesproject.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.io.Serializable;

/**
 * Created by Tony on 11/7/2016.
 */
public class Movie implements Serializable {
    public static final String MOVIE_ID = "MovieID", MOVIE_YEAR = "Year",
            IMDB_URL = "URL", MOVIE_NAME = "Name";

    String mMovieID, mYear, mURL, mName;

    /**
     * Constructor for the Movie object.
     *
     * @param mMovieID The movie ID
     * @param mYear The movie year
     * @param mURL The IMDB.com url to that movie
     * @param mName The movie name
     */
    public Movie(String mMovieID, String mYear, String mURL, String mName) {
        this.mMovieID = mMovieID;
        this.mYear = mYear;
        this.mURL = mURL;
        this.mName = mName;
    }

    /**
     * Getter for movie ID.
     * @return
     */
    public String getmMovieID() {
        return mMovieID;
    }

    /**
     * Setter for movie id.
     * @param mMovieID
     */
    public void setmMovieID(String mMovieID) {
        this.mMovieID = mMovieID;
    }

    /**
     * Getter for movie Year.
     * @return
     */
    public String getmYear() {
        return mYear;
    }

    /**
     * Setter for movie year.
     * @param mYear
     */
    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    /**
     * Getter for movie URL.
     * @return
     */
    public String getmURL() {
        return mURL;
    }

    /**
     * Setter for movie URL.
     * @param mURL
     */
    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    /**
     * Getter for movie name.
     * @return
     */
    public String getmName() {
        return mName;
    }

    /**
     * Setter for movie name.
     * @param mName
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param movieJSON
     * @return reason or null if successful.
     */
    public static String parseMovieJSON(String movieJSON, List<Movie> movieList) {
        String reason = null;
        if (movieJSON != null) {
            try {
                JSONArray arr = new JSONArray(movieJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Movie movie = new Movie(obj.getString(Movie.MOVIE_ID), obj.getString(Movie.MOVIE_YEAR)
                            , obj.getString(Movie.IMDB_URL), obj.getString(Movie.MOVIE_NAME));
                    movieList.add(movie);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
