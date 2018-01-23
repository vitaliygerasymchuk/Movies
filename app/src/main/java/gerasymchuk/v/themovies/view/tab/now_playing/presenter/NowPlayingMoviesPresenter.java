package gerasymchuk.v.themovies.view.tab.now_playing.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.view.tab.now_playing.MoviesContract;
import gerasymchuk.v.themovies.view.tab.now_playing.data.GetNowPlayingMoviesInteractor;
import gerasymchuk.v.themovies.view.tab.now_playing.data.GetNowPlayingMoviesUseCase;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingMoviesPresenter implements MoviesContract.Presenter {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesPresenter";

    private static final boolean DEBUG = true;

    @Nullable
    private MoviesContract.View view;

    @Nullable
    private GetNowPlayingMoviesUseCase nowPlayingMoviesUseCase;

    private int currentPage = 1;

    private int totalPages = -1;

    public NowPlayingMoviesPresenter(@NonNull MoviesContract.View view) {
        this.view = view;
        this.initNowPlayingMoviesUseCase();
        log("constructor");
    }

    @Override
    public void onDestroy() {
        this.view = null;
        log("onDestroy");
    }

    @Override
    public void onResume() {
        log("onResume");
        getMoreMovies();
    }

    @Override
    public void onVisibilityChanged(boolean isVisible) {
        if (view == null) return;
        log("onVisibilityChanged %s ", String.valueOf(isVisible));
    }

    @Override
    public void getMoreMovies() {
        if (currentPage != totalPages) {
            if (nowPlayingMoviesUseCase != null) {
                log("getMoreMovies :: start, currentPage %s ", currentPage);
                nowPlayingMoviesUseCase.getNowPlayingMovies(null, null, currentPage);
            } else log("getMoreMovies :: GetNowPlayingMoviesUseCase is null");
        } else {
            log("getMoreMovies :: no need to download, last page is %s ", currentPage);
        }
    }

    private void initNowPlayingMoviesUseCase() {
        nowPlayingMoviesUseCase = new GetNowPlayingMoviesInteractor(nowPlayingMoviesResponse -> {
            if (view == null) return;
            view.renderMovies(nowPlayingMoviesResponse.movies);
            totalPages = nowPlayingMoviesResponse.totalPages;
            if (currentPage != totalPages) {
                currentPage++;
                log("getMoreMovies :: done, nextPage %s ", currentPage);
            } else log("getMoreMovies :: done, all pages downloaded");
        }, new OnError<String>() {
            @Override
            public void onError(String s) {

            }
        }, new OnError<Integer>() {
            @Override
            public void onError(Integer integer) {

            }
        });
    }

    private void log(@NonNull String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, "[MOVIES_NOW_PLAYING] " + msg, args);
        }
    }
}
