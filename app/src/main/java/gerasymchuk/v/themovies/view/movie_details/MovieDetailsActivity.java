package gerasymchuk.v.themovies.view.movie_details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.AbsActivity;
import gerasymchuk.v.themovies.view.BasePresenter;

/**
 * Created by vitaliygerasymchuk on 1/24/18
 */

public class MovieDetailsActivity extends AbsActivity {

    public static void launch(@NonNull Context context, @NonNull Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(ARG_MOVIE, movie);
        context.startActivity(intent);
    }

    private static final String ARG_MOVIE = "arg_movie";

    @Override
    protected int onRequestLayout() {
        return R.layout.activity_movie_details;
    }

    @Override
    protected void beforeAddingLayout() {

    }

    @Override
    protected void onCreateActivity() {

    }

    @Nullable
    @Override
    protected BasePresenter initBasePresenter() {
        return null;
    }
}
