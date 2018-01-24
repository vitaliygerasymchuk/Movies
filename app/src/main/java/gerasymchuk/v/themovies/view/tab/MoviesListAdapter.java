package gerasymchuk.v.themovies.view.tab;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesListAdapter extends PagedListAdapter<Movie, MoviesListAdapter.MovieItemHolder> {

    public interface Callback {
        void onMovieClicked(@NonNull Movie movie);
    }

    @SuppressWarnings("unused")
    public static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "MoviesListAdapter";

    @Nullable
    private Callback callback;

    MoviesListAdapter(@NonNull Callback callback) {
        super(DIFF_CALLBACK);
        this.callback = callback;
        log("[MOVIES_ADAPTER] :: created");
    }

    @Override
    public MovieItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false), movie -> {
            if (callback != null) {
                log("[MOVIES_ADAPTER] :: movie %s clicked", movie);
                if (movie != null) {
                    callback.onMovieClicked(movie);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(MovieItemHolder holder, int position) {
        final Movie movie = getItem(position);
        if (movie != null) {
            holder.bind(movie);
        }
    }

    private static final DiffCallback<Movie> DIFF_CALLBACK = new DiffCallback<Movie>() {

        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldMovie.id == newMovie.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldMovie.equals(newMovie);
        }
    };

    /**
     * {@link android.support.v7.widget.RecyclerView.ViewHolder} which represents {@link Movie}
     */
    static class MovieItemHolder extends RecyclerView.ViewHolder {

        interface OnMovieClickedCallback {
            void onMovie(@Nullable Movie movie);
        }

        TextView tv;

        @Nullable
        private Movie movie;

        MovieItemHolder(View itemView, @NonNull OnMovieClickedCallback callback) {
            super(itemView);
            log("[MOVIES_ADAPTER] :: MovieItemHolder -> created");
            tv = itemView.findViewById(R.id.tv);
            itemView.findViewById(R.id.card_view)
                    .setOnClickListener(view -> callback.onMovie(movie));
        }

        void bind(@Nullable Movie movie) {
            this.movie = movie;
            if (movie != null) {
                tv.setText(movie.getOriginalTitle());
            }
        }
    }

    /**
     * Log
     */
    private static void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, msg, args);
        }
    }
}
