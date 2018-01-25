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

public class NowPlayingMoviesDataSource extends PageKeyedDataSource<Integer, Movie> {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesDataSource";

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

    NowPlayingMoviesDataSource() {
        dataState = new MutableLiveData<>();
        nowPlayingMoviesUseCase = new GetNowPlayingMoviesInteractor(nowPlayingMoviesResponse -> {
            dataState.postValue(new DataState(EDataState.Loaded, -1, ""));
            final int totalPages = nowPlayingMoviesResponse.totalPages;
            final int currentPage = nowPlayingMoviesResponse.page;
            log("[MOVIES_NOW_PLAYING] :: totalPages  %s", totalPages);
            log("[MOVIES_NOW_PLAYING] :: currentPage %s", currentPage);
            canLoadMore = this.currentPage <= totalPages;
            if (currentPage == 1) {
                if (loadInitialCallback != null) {
                    loadInitialCallback.onResult(nowPlayingMoviesResponse.movies, currentPage, totalPages);
                }
            } else {
                if (loadAfterCallback != null) {
                    loadAfterCallback.onResult(nowPlayingMoviesResponse.movies, canLoadMore ? (currentPage + 1) : null);
                }
            }
        }, s -> {
            dataState.postValue(new DataState(EDataState.Error, -1, s));
            log("[MOVIES_NOW_PLAYING] :: onError %s", s);
        }, integer -> {
            dataState.postValue(new DataState(EDataState.Error, integer, ""));
            log("[MOVIES_NOW_PLAYING] :: onError");
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
            log("[MOVIES_NOW_PLAYING] :: cannot load more items, page %s", currentPage);
    }

    @NonNull
    public MutableLiveData<DataState> getDataState() {
        return dataState;
    }

    private void getMovies(int page) {
        log("[MOVIES_NOW_PLAYING] :: start loading page %s", currentPage);
        dataState.postValue(new DataState(EDataState.Loading, -1, ""));
        nowPlayingMoviesUseCase.getNowPlayingMovies("", "", page);
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, String.format(msg, args));
        }
    }
}
