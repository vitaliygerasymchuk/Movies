package gerasymchuk.v.themovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;


import gerasymchuk.v.themovies.data.db.MovieCertificationsDao;
import gerasymchuk.v.themovies.data.db.NowPlayingMoviesDao;
import gerasymchuk.v.themovies.data.model.Certification;

import static gerasymchuk.v.themovies.shared.Const.DB_VERSION;

@Database(entities = {Certification.class}, version = DB_VERSION)
public abstract class RoomDB extends RoomDatabase {
    @NonNull
    public abstract MovieCertificationsDao certificationsDao();

    @NonNull
    public abstract NowPlayingMoviesDao upcomingMoviesDao();
}