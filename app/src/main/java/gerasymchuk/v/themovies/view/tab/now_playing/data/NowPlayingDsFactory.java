package gerasymchuk.v.themovies.view.tab.now_playing.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/25/18
 */

public class NowPlayingDsFactory implements DataSource.Factory<Integer, Movie> {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingDsFactory";

    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;

    @NonNull
    private MutableLiveData<NowPlayingMoviesDS> liveData;

    @NonNull
    private NowPlayingMoviesDS dataSource;

    public NowPlayingDsFactory() {
        log("factory constructed");
        this.liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Movie> create() {
        log("factory create called");
        dataSource = new NowPlayingMoviesDS();
        liveData.postValue(dataSource);
        return dataSource;
    }

    @NonNull
    public MutableLiveData<NowPlayingMoviesDS> getLiveData() {
        return liveData;
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, String.format(msg, args));
        }
    }
}
