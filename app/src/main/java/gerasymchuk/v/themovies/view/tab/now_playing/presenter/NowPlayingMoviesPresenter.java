package gerasymchuk.v.themovies.view.tab.now_playing.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.data.model.response.NowPlayingMoviesResponse;
import gerasymchuk.v.themovies.view.tab.now_playing.data.GetNowPlayingMoviesInteractor;
import gerasymchuk.v.themovies.view.tab.now_playing.data.GetNowPlayingMoviesUseCase;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.tab.now_playing.MoviesContract;

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
        if (nowPlayingMoviesUseCase != null) {
            nowPlayingMoviesUseCase.getNowPlayingMovies(null, null, -1);
        }
    }

    @Override
    public void onVisibilityChanged(boolean isVisible) {
        if (view == null) return;
        log("onVisibilityChanged %s ", String.valueOf(isVisible));
    }

    private void initNowPlayingMoviesUseCase() {
        nowPlayingMoviesUseCase = new GetNowPlayingMoviesInteractor(new OnSuccess<NowPlayingMoviesResponse>() {
            @Override
            public void onSuccess(@NonNull NowPlayingMoviesResponse nowPlayingMoviesResponse) {

            }
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
