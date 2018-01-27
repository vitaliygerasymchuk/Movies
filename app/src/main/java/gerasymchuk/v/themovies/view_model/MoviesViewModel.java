package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import gerasymchuk.v.themovies.data.model.DataState;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.view.tab.now_playing.data.NowPlayingMoviesDS;
import gerasymchuk.v.themovies.view.tab.now_playing.data.NowPlayingDsFactory;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesViewModel extends AbsViewModel {

    @NonNull
    public final LiveData<PagedList<Movie>> moviesList;

    @NonNull
    private MutableLiveData<NowPlayingMoviesDS> apiMoviesDataSource;

    @NonNull
    private LiveData<DataState> dataStateLiveData;

    public MoviesViewModel() {

        final NowPlayingDsFactory factory = new NowPlayingDsFactory();
        apiMoviesDataSource = factory.getLiveData();
        dataStateLiveData = Transformations.switchMap(factory.getLiveData(), NowPlayingMoviesDS::getDataState);

        final PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(20)
                .build();

//        moviesList = new LivePagedListBuilder<>(
//                db.moviesDao().getMoviesForType(MovieType.NowPlaying.name()), pagedListConfig)
//                .build();

        moviesList = new LivePagedListBuilder<>(factory, pagedListConfig)
                .build();
    }
}
