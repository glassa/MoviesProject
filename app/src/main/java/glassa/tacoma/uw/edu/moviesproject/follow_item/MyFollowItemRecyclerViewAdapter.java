package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.follow_item.FollowItemFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FollowItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFollowItemRecyclerViewAdapter extends RecyclerView.Adapter<MyFollowItemRecyclerViewAdapter.ViewHolder> {

    /**
     * List of FollowItems that are entered into the list.
     */
    private final List<FollowItem> mValues;
    /**
     * Was the Following button clicked?
     */
    private Boolean mFollowingButton;
    /**
     * Was the Followers button clicked?
     */
    private Boolean mFollowersButton;
    /**
     * The listener for which item is clicked on the list.
     */
    private final OnListFragmentInteractionListener mListener;

    /**
     * The constructor for the View Adapter. It sets the values of all the fields in the class.
     * @param items
     * @param listener
     * @param followersButton
     * @param followingButton
     */
    public MyFollowItemRecyclerViewAdapter(List<FollowItem> items, OnListFragmentInteractionListener listener, Boolean followersButton, Boolean followingButton) {
        mValues = items;
        mListener = listener;
        mFollowersButton = followersButton;
        mFollowingButton = followingButton;
    }

    /**
     * Starts up the view and calls the layout.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_followitem, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Determines what is displayed on the list.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //This is what builds the string that is displayed as the list item

        if (mFollowingButton) {
            holder.mIdView.setText(mValues.get(position).getmUserB());

        } else if (mFollowersButton) {
            holder.mIdView.setText(mValues.get(position).getmUserA());
        }

//        holder.mMovieTitleView.setText(mValues.get(position).getmUserA() + " is following " + mValues.get(position).getmUserB());
//        holder.mRatingStringView.setText(mValues.get(position).getmUserB());       //commented out the second item in list

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    /**
     * Gets the number of items in the list.
     * @return
     */
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * This holds the view for each item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public FollowItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.movie_title);
            mContentView = (TextView) view.findViewById(R.id.rating_string);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
