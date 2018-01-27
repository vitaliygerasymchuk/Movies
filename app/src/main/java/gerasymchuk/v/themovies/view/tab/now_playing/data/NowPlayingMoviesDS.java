package gerasymchuk.v.themovies.view.tab.now_playing.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.data.model.DataState;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.network.EDataState;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/25/18
 */

public class NowPlayingMoviesDS extends PageKeyedDataSource<Integer, Movie> {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesDS";

    private static final boolean DEBUG = true;

    @NonNull
    private MutableLiveData<DataState> dataState;

    @NonNull
    private GetNowPlayingMoviesUseCase nowPlayingMoviesUseCase;

    @Nullable
    private LoadInitialCallback<Integer, Movie> loadInitialCallback;

    @Nullable
    private LoadCallback<Integer, Movie> loadAfterCallback;

    private int currentPage = 1;

    private boolean canLoadMore = true;

    NowPlayingMoviesDS() {
        dataState = new MutableLiveData<>();
        nowPlayingMoviesUseCase = new GetNowPlayingInteractor(nowPlayingMoviesResponse -> {
            dataState.postValue(new DataState(EDataState.Loaded, -1, ""));
            final int totalPages = nowPlayingMoviesResponse.totalPages;
            final int downloadedPage = nowPlayingMoviesResponse.page;
            log("totalPages  %s", totalPages);
            log("downloadedPage %s", downloadedPage);
            canLoadMore = this.currentPage < totalPages - 1;
            if (downloadedPage == 1) {
                if (loadInitialCallback != null) {
                    loadInitialCallback.onResult(nowPlayingMoviesResponse.movies, downloadedPage, totalPages);
                }
            } else {
                if (loadAfterCallback != null) {
                    loadAfterCallback.onResult(nowPlayingMoviesResponse.movies, canLoadMore ? (downloadedPage + 1) : null);
                }
            }
        }, s -> {
            dataState.postValue(new DataState(EDataState.Error, -1, s));
            log("onError %s", s);
        }, integer -> {
            dataState.postValue(new DataState(EDataState.Error, integer, ""));
            log("onError");
        });
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        log("loadInitial :: params placeholdersEnabled -> %s, requestedLoadSize %s ", params.placeholdersEnabled, params.requestedLoadSize);
        this.loadInitialCallback = callback;
        getMovies(currentPage);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        //no-op
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        this.loadAfterCallback = callback;
        if (canLoadMore) {
            getMovies(currentPage += 1);
        } else
            log("cannot load more items, page %s", currentPage);
    }

    /**
     * Returns {@link DataState} which represents current state of datat
     *
     * @return DataState
     */
    @NonNull
    public MutableLiveData<DataState> getDataState() {
        return dataState;
    }

    /**
     * Invokes interactor to get more data
     *
     * @param page page to load
     */
    private void getMovies(int page) {
        log("start loading page %s", currentPage);
        dataState.postValue(new DataState(EDataState.Loading, -1, ""));
        nowPlayingMoviesUseCase.getNowPlayingMovies("", "", page);
    }

    /**
     * Log
     */
    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG,"[MOVIES_NOW_PLAYING] " +  msg, args);
        }
    }
}
