package glassa.tacoma.uw.edu.moviesproject.follow_item;

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
public class FollowItemFragment extends Fragment {
    private static final String FOLLOW_ITEM_URL
            = "http://cssgate.insttech.washington.edu/~_450team2/list.php?cmd=followlist";

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mCurrentUser;
    private Boolean mFollowingButton;
    private Boolean mFollowersButton;
    private RecyclerView mRecyclerView;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FollowItemFragment() {
    }

    private class DownloadCoursesTask extends AsyncTask<String, Void, String> {
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
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<FollowItem> followItemList = new ArrayList<FollowItem>();
            result = FollowItem.parseCourseJSON(result, followItemList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!followItemList.isEmpty()) {
                mRecyclerView.setAdapter(new MyFollowItemRecyclerViewAdapter(followItemList, mListener));
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

            mCurrentUser = fiActivity.getmUsername();
            mFollowingButton = fiActivity.getmFollowingButton();
            mFollowersButton = fiActivity.getmFollowersButton();


            Log.i("FollowItemFragment", "current user: " + mCurrentUser);

            mRecyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            DownloadCoursesTask task = new DownloadCoursesTask();

            task.execute(new String[]{buildUserURL(getView())});
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(FollowItem item);
    }

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
}
