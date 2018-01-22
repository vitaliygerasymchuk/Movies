package gerasymchuk.v.themovies.view.tab.now_playing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.view.BasePresenter;
import gerasymchuk.v.themovies.view.tab.AbsMoviesFragment;
import gerasymchuk.v.themovies.view.tab.now_playing.presenter.NowPlayingMoviesPresenter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */
public class NowPlayingMoviesFragment
        extends AbsMoviesFragment
        implements MoviesContract.View {

    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesFragment";

    public static NowPlayingMoviesFragment newInstance() {

        Bundle args = new Bundle();

        NowPlayingMoviesFragment fragment = new NowPlayingMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    private MoviesContract.Presenter presenter;

    @Override
    protected void onFragmentReady() {
        super.onFragmentReady();
        log("onFragmentReady");
    }

    @Override
    protected BasePresenter bindPresenter() {
        presenter = new NowPlayingMoviesPresenter(this);
        log("bindPresenter");
        return presenter;
    }

    @Override
    protected void onFragmentVisible() {
        log("onFragmentVisible");
        if (presenter != null) {
            presenter.onVisibilityChanged(true);
        }
    }

    @Override
    protected void onFragmentInvisible() {
        log("onFragmentInvisible");
        if (presenter != null) {
            presenter.onVisibilityChanged(false);
        }
    }

    @Override
    public void showMovies(@NonNull List<Movie> movieList) {
        log("showMovies");
        refreshRecyclerView(movieList);
    }

    private void log(String msg, Object... args) {
        if (DEBUG){
            Logger.d(TAG,msg,args);
        }
    }
}
