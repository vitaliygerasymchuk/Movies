package gerasymchuk.v.themovies.view.tab.now_playing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.BasePresenter;
import gerasymchuk.v.themovies.view.tab.AbsMoviesFragment;
import gerasymchuk.v.themovies.view.tab.now_playing.presenter.UpcomingMoviesPresenter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */
public class NowPlayingMoviesFragment
        extends AbsMoviesFragment
        implements MoviesContract.View {

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
    }

    @Override
    protected BasePresenter bindPresenter() {
        presenter = new UpcomingMoviesPresenter(this);
        return presenter;
    }

    @Override
    protected void onFragmentVisible() {
        if (presenter != null) {
            presenter.onVisibilityChanged(true);
        }
    }

    @Override
    protected void onFragmentInvisible() {
        if (presenter != null) {
            presenter.onVisibilityChanged(false);
        }
    }

    @Override
    public void showMovies(@NonNull List<Movie> movieList) {
        refreshRecyclerView(movieList);
    }
}
