package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingMoviesViewModel extends AbsViewModel {

    @Nullable
    private MutableLiveData<List<Movie>> liveData;

}
