package gerasymchuk.v.themovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;

import static gerasymchuk.v.themovies.shared.Const.TABLE_MOVIES;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

@Dao
public interface NowPlayingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(@NonNull Movie... movies);

    @Query("SELECT * FROM " + TABLE_MOVIES)
    List<Movie> getMovies();
}
