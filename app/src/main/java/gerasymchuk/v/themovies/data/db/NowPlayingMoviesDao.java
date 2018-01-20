package gerasymchuk.v.themovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.support.annotation.NonNull;

import gerasymchuk.v.themovies.data.model.Movie;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

@Dao
public interface NowPlayingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(@NonNull Movie... movies);
}
