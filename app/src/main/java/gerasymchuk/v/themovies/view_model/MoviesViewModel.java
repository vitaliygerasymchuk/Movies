package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.RoomDB;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.enums.MovieType;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesViewModel extends AbsViewModel {

    @NonNull
    private RoomDB db;

    @NonNull
    public final LiveData<PagedList<Movie>> moviesList;

    public MoviesViewModel() {
        db = App.getInstance().getDB();

        final PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();

        moviesList = new LivePagedListBuilder<>(
                db.moviesDao().getMoviesForType(MovieType.NowPlaying.name()), pagedListConfig)
                .build();
    }
}
