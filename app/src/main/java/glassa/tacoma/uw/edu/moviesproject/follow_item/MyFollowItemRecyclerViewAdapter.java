package glassa.tacoma.uw.edu.moviesproject.follow_item;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private final List<FollowItem> mValues;
    private Boolean mFollowingButton;
    private Boolean mFollowersButton;
    private final OnListFragmentInteractionListener mListener;

    public MyFollowItemRecyclerViewAdapter(List<FollowItem> items, OnListFragmentInteractionListener listener, Boolean followersButton, Boolean followingButton) {
        mValues = items;
        mListener = listener;
        mFollowersButton = followersButton;
        mFollowingButton = followingButton;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_followitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //This is what builds the string that is displayed as the list item

        if (mFollowingButton) {
            holder.mIdView.setText(mValues.get(position).getmUserB());

        } else if (mFollowersButton) {
            holder.mIdView.setText(mValues.get(position).getmUserA());
        }

//        holder.mIdView.setText(mValues.get(position).getmUserA() + " is following " + mValues.get(position).getmUserB());
//        holder.mContentView.setText(mValues.get(position).getmUserB());       //commented out the second item in list

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public FollowItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            Log.i("RecyclerView", mIdView.getText()+"");
            Log.i("RecyclerView", mContentView.getText() + "");
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
