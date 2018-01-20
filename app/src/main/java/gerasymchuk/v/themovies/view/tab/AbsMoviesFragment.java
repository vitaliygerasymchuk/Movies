package gerasymchuk.v.themovies.view.tab;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.AbsFragment;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public abstract class AbsMoviesFragment extends AbsFragment {

    @NonNull
    private RecyclerView recyclerView;

    @Nullable
    private MoviesListAdapter adapter;

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_movies_list;
    }

    @Override
    protected void onFragmentReady() {
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();

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
        if (getContext() != null) {
            recyclerView = find(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new MoviesListAdapter();
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * Refreshes {@link RecyclerView}
     *
     * @param movieList List of {@link Movie}
     */
    protected void refreshRecyclerView(@NonNull List<Movie> movieList) {
        if (adapter != null) {
            adapter.refresh(movieList);
        }
    }
}
