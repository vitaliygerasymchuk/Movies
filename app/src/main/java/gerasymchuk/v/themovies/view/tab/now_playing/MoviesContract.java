package gerasymchuk.v.themovies.view.tab.now_playing;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.BasePresenter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public interface MoviesContract {

    interface Presenter extends BasePresenter {
        void onVisibilityChanged(boolean isVisible);

        void getMoreMovies();
    }

    interface View {
        void renderMovies(@NonNull PagedList<Movie> movieList);

        void launchMovieDetails(@NonNull Movie movie);
    }
}
