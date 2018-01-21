package gerasymchuk.v.themovies.view.tab.now_playing.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.data.model.response.MoviesResponse;
import gerasymchuk.v.themovies.data.shared_data.GetNowPlayingMoviesInteractor;
import gerasymchuk.v.themovies.data.shared_data.GetNowPlayingMoviesUseCase;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.tab.now_playing.MoviesContract;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingMoviesPresenter implements MoviesContract.Presenter {

    @Nullable
    private MoviesContract.View view;

    @NonNull
    private GetNowPlayingMoviesUseCase nowPlayingMoviesUseCase;

    public NowPlayingMoviesPresenter(@NonNull MoviesContract.View view) {
        this.view = view;
        this.initNowPlayingMoviesUseCase();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onVisibilityChanged(boolean isVisible) {
        if (view == null) return;
        if (isVisible) {

        }
    }

    private void initNowPlayingMoviesUseCase() {
        nowPlayingMoviesUseCase = new GetNowPlayingMoviesInteractor(new OnSuccess<MoviesResponse>() {
            @Override
            public void onSuccess(@NonNull MoviesResponse moviesResponse) {

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
}
