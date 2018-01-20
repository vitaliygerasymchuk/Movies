package gerasymchuk.v.themovies.view.main.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.view.main.MainContract;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MainPresenter implements MainContract.Presenter {

    @SuppressWarnings("unused")
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Nullable
    private MainContract.View view;

    public MainPresenter(@NonNull MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void onResume() {

    }
}
