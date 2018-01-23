package gerasymchuk.v.themovies.view.tab;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesListAdapter extends PagedListAdapter<Movie, MoviesListAdapter.MovieItemHolder> {

    @SuppressWarnings("unused")
    public static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "MoviesListAdapter";

    MoviesListAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public MovieItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieItemHolder holder, int position) {
        final Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    @Override
    public void onBindViewHolder(MovieItemHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    private static final DiffCallback<Movie> DIFF_CALLBACK = new DiffCallback<Movie>() {

        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            log("areItemsTheSame oldMovie %s newMovie %s ", oldMovie.getOriginalTitle(), newMovie.getOriginalTitle());
            return oldMovie.id == newMovie.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            log("areContentsTheSame oldMovie %s newMovie %s ", oldMovie.getOriginalTitle(), newMovie.getOriginalTitle());
            return oldMovie.equals(newMovie);
        }

        private void log(String msg, Object... args) {
            if (DEBUG) {
                Logger.d("DiffCallback", msg, args);
            }
        }
    };

    static class MovieItemHolder extends RecyclerView.ViewHolder {

        TextView tv;

        MovieItemHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }

        void bind(@NonNull Movie movie) {
            tv.setText(movie.getOriginalTitle());
        }
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, msg, args);
        }
    }
}
