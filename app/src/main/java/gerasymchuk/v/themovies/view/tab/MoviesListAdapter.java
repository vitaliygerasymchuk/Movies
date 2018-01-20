package gerasymchuk.v.themovies.view.tab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.AbsAdapter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesListAdapter extends AbsAdapter<Movie, MoviesListAdapter.MovieItemHolder> {

    @SuppressWarnings("unused")
    private static final String TAG = MoviesListAdapter.class.getSimpleName();

    @Override
    protected MovieItemHolder createHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieItemHolder(inflate(parent, R.layout.item_movie));
    }

    @Override
    protected void bind(@NonNull MovieItemHolder holder, @NonNull Movie item) {
        holder.bind(item);
    }

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
}
