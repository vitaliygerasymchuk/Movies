package gerasymchuk.v.themovies.view.tab.now_playing.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.view.tab.now_playing.MoviesContract;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingPresenter implements MoviesContract.Presenter {

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingPresenter";

    private static final boolean DEBUG = true;

    @Nullable
    private MoviesContract.View view;

    public NowPlayingPresenter(@NonNull MoviesContract.View view) {
        this.view = view;
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
    }

    @Override
    public void onVisibilityChanged(boolean isVisible) {
        if (view == null) return;
        log("onVisibilityChanged %s ", String.valueOf(isVisible));
    }

    private void log(@NonNull String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, "[MOVIES_NOW_PLAYING] " + msg, args);
        }
    }
}
