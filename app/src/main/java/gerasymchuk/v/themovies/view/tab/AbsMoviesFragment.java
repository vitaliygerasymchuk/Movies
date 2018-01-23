package gerasymchuk.v.themovies.view.tab;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.view.AbsFragment;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public abstract class AbsMoviesFragment extends AbsFragment {

    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "AbsMoviesFragment";

    @Nullable
    private MoviesListAdapter adapter;

    @Override
    protected int onRequestLayout() {
        log("onRequestLayout");
        return R.layout.fragment_movies_list;
    }

    @Override
    protected void onFragmentReady() {
        log("onFragmentReady");
        setupRecyclerView();
    }

    protected abstract void onFragmentVisible();

    protected abstract void onFragmentInvisible();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onFragmentVisible();
        } else onFragmentInvisible();
    }

    /**
     * Setups {@link RecyclerView} and {@link MoviesListAdapter}
     */
    private void setupRecyclerView() {
        log("setupRecyclerView :: start");
        if (getContext() != null) {
            RecyclerView recyclerView = find(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MoviesListAdapter();
            recyclerView.setAdapter(adapter);
            log("setupRecyclerView :: done");
        } else log("setupRecyclerView :: context null");
    }

    /**
     * Refreshes {@link RecyclerView}
     *
     * @param movieList List of {@link Movie}
     */
    protected void refreshRecyclerView(@NonNull PagedList<Movie> movieList) {
        log("refreshRecyclerView :: start");
        if (adapter != null) {
            adapter.setList(movieList);
            log("refreshRecyclerView :: done");
        } else log("refreshRecyclerView :: adapter null");
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, msg, args);
        }
    }
}
