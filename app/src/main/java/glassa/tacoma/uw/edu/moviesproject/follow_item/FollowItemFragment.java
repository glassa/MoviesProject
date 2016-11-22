package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class FollowItemFragment extends Fragment {
    /**
     * The base url of our command to interact with our database via php
     */
    private static final String FOLLOW_ITEM_URL = "http://cssgate.insttech.washington.edu/~_450team2/list.php?cmd=followlist";

    /**
     * Current user.
     */
    private String mCurrentUser;
    /**
     * Was the Following button clicked?
     */
    private Boolean mFollowingButton;
    /**
     * Was the Followers button clicked?
     */
    private Boolean mFollowersButton;
    /**
     * Recycler view to view the list of followers or following.
     */
    private RecyclerView mRecyclerView;
    /**
     * The listener for if a list object is clicked.
     */
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FollowItemFragment() {
    }

    /**
     * This method builds the URL from the base URL constant.  Also, based on the mFollowing
     * and mFollowers fields, it will decide which one to filter the list for. If we are looking
     * at Followers, the list would come out as [User A is following User B] where current user is
     * [User B], but since this is a list of followers, we only want to see the list of those
     * following us, so we make it a list of only [User A].  And if we are looking at who we are
     * following, we only want to see a list of [User B].
     * @param v the View
     * @return the URL String.
     */
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(FOLLOW_ITEM_URL);

        try {
            if(mFollowingButton) {
                sb.append("A&UserA=");
                sb.append(URLEncoder.encode(mCurrentUser, "UTF-8"));

                Log.i("FollowItemFragment", "URL=" + sb.toString());
            } else if (mFollowersButton) {
                sb.append("B&UserB=");
                sb.append(URLEncoder.encode(mCurrentUser, "UTF-8"));

                Log.i("FollowItemFragment", "URL=" + sb.toString());
            }
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * AsyncTask to get the filtered list of followers or followed users.
     */
    private class GetFollowListTask extends AsyncTask<String, Void, String> {
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
                    String s;
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
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            Log.i("FIF OnPostExecute", result);
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<FollowItem> followItemList = new ArrayList<>();
            result = FollowItem.parseCourseJSON(result, followItemList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!followItemList.isEmpty()) {
                mRecyclerView.setAdapter(new MyFollowItemRecyclerViewAdapter(followItemList, mListener, mFollowersButton, mFollowingButton));
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            FollowItemActivity fiActivity = (FollowItemActivity)getActivity();


            mCurrentUser = fiActivity.getmCurrentUser();
            mFollowingButton = fiActivity.getmFollowingButton();
            mFollowersButton = fiActivity.getmFollowersButton();

            if (mFollowingButton) {
                getActivity().setTitle(mCurrentUser + " is Following");

            } else {
                getActivity().setTitle(mCurrentUser + "'s Followers");

            }

            Log.i("FollowItemFragment", "current user: " + mCurrentUser);

            mRecyclerView = (RecyclerView) view;

            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            GetFollowListTask task = new GetFollowListTask();

            task.execute(buildUserURL(getView()));
        }
        return view;
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
        /**
         * On list fragment interaction.
         *
         * @param item the item
         */
        void onListFragmentInteraction(FollowItem item);
    }


}
