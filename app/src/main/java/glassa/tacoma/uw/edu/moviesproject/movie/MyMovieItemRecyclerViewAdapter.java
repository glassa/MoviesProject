package glassa.tacoma.uw.edu.moviesproject.movie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.movie.MovieItemFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMovieItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieItemRecyclerViewAdapter.ViewHolder> {

    private final List<MovieItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    /**
     * Instantiates a new My movie item recycler view adapter.
     *
     * @param items    the items
     * @param listener the listener
     */
    public MyMovieItemRecyclerViewAdapter(List<MovieItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movieitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mMovieTitleView.setText(mValues.get(position).getmMovieName());
        holder.mRatingStringView.setText(mValues.get(position).getMovieRatingString());

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

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The M view.
         */
        public final View mView;
        /**
         * The M movie title view.
         */
        public final TextView mMovieTitleView;
        /**
         * The M rating string view.
         */
        public final TextView mRatingStringView;
        /**
         * The M item.
         */
        public MovieItem mItem;

        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMovieTitleView = (TextView) view.findViewById(R.id.movie_title);
            mRatingStringView = (TextView) view.findViewById(R.id.rating_string);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mRatingStringView.getText() + "'";
        }
    }
}
