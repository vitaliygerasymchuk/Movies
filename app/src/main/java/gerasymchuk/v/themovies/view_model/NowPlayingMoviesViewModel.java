package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.data.model.response.MoviesResponse;
import gerasymchuk.v.themovies.data.shared_data.GetNowPlayingMoviesInteractor;
import gerasymchuk.v.themovies.data.shared_data.GetNowPlayingMoviesUseCase;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingMoviesViewModel extends AbsViewModel {

    @Nullable
    private MutableLiveData<List<Movie>> liveData;

}
