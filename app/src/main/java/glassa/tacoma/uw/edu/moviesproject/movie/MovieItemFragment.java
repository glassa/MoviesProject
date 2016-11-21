package glassa.tacoma.uw.edu.moviesproject.movie;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MovieItemFragment extends Fragment {

    public static final String RATING_LIST_URL = "http://cssgate.insttech.washington.edu/~_450team2/list.php?cmd=ratingList&Username=";
    /**
     * Current user.
     */
    private String mTargetUser;

    private int mColumnCount = 1;
    private RecyclerView mRecyclerView;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * This method gets the info of current user and which button was clicked from the
     * "putExtra" from the FollowItemActivity and assigns the information to the fields within
     * this class.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movieitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            MovieItemActivity miActivity = (MovieItemActivity)getActivity();

            mTargetUser = miActivity.getmTargetUser();

            Log.i("MovieItemFragment", "current user: " + mTargetUser);

            mRecyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            GetMovieRatingList task = new GetMovieRatingList();

            task.execute(new String[]{buildUserURL(getView())});
        }
        return view;
    }

    /**
     * This method builds the URL from the base URL constant.  Also, based on the mFollowing
     * and mFollowers fields, it will decide which one to filter the list for. If we are looking
     * at Followers, the list would come out as [User A is following User B] where current user is
     * [User B], but since this is a list of followers, we only want to see the list of those
     * following us, so we make it a list of only [User A].  And if we are looking at who we are
     * following, we only want to see a list of [User B].
     * @param v
     * @return
     */
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(RATING_LIST_URL);

        try {

            sb.append(URLEncoder.encode(mTargetUser, "UTF-8"));
            Log.i("MovieItemFragment", "URL: " + sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MovieItem item);
    }

    /**
     * AsyncTask to get the filtered list of followers or followed users.
     */
    private class GetMovieRatingList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of courses, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }

            Log.i("MovieItemFragment", "Response:" + response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            Log.i("MovieItemFragment", "Result:" + result);
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<MovieItem> movieItemList = new ArrayList<MovieItem>();
            result = MovieItem.parseCourseJSON(result, movieItemList);

            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!movieItemList.isEmpty()) {
                mRecyclerView.setAdapter(new MyMovieItemRecyclerViewAdapter(movieItemList, mListener));
            }
        }

    }
}
