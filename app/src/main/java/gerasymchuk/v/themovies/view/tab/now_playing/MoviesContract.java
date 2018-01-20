package gerasymchuk.v.themovies.view.tab.now_playing;

import android.support.annotation.NonNull;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.BasePresenter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public interface MoviesContract {

    interface Presenter extends BasePresenter {
        void onVisibilityChanged(boolean isVisible);
    }

    interface View {
        void showMovies(@NonNull List<Movie> movieList);
    }
}
