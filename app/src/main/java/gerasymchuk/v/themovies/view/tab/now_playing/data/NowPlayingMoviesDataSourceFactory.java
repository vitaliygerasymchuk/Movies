package gerasymchuk.v.themovies.view.tab.now_playing.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/25/18
 */

public class NowPlayingMoviesDataSourceFactory implements DataSource.Factory<Integer, Movie> {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesDataSourceFactory";

    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;

    @NonNull
    private MutableLiveData<NowPlayingMoviesDataSource> liveData;

    @NonNull
    private NowPlayingMoviesDataSource dataSource;

    public NowPlayingMoviesDataSourceFactory() {
        log("factory constructed");
        this.liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Movie> create() {
        log("factory create called");
        dataSource = new NowPlayingMoviesDataSource();
        liveData.postValue(dataSource);
        return dataSource;
    }

    @NonNull
    public MutableLiveData<NowPlayingMoviesDataSource> getLiveData() {
        return liveData;
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, String.format(msg, args));
        }
    }
}
