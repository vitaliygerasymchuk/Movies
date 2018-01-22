package gerasymchuk.v.themovies.view.main.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.view.main.MainContract;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MainPresenter implements MainContract.Presenter {

    private static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "MainPresenter";

    @Nullable
    private MainContract.View view;

    public MainPresenter(@NonNull MainContract.View view) {
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

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, msg, args);
        }
    }
}
