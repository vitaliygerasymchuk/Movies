package gerasymchuk.v.themovies;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.danlew.android.joda.JodaTimeAndroid;

import gerasymchuk.v.themovies.data.DataManager;
import gerasymchuk.v.themovies.data.RoomDB;
import gerasymchuk.v.themovies.network.MoviesAPI;
import gerasymchuk.v.themovies.network.RetrofitManager;

import static gerasymchuk.v.themovies.shared.Const.DB_NAME;

public class App extends Application {

    @NonNull
    private static App INSTANCE;

    @Nullable
    private MoviesAPI api;

    @Nullable
    private RoomDB roomDB;

    @Nullable
    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        INSTANCE = this;
    }

    @NonNull
    public static App getInstance() {
        return INSTANCE;
    }

    @NonNull
    public MoviesAPI getApi() {
        if (api == null) {
            api = new RetrofitManager().initRetrofit().create(MoviesAPI.class);
        }
        return api;
    }

    @NonNull
    public RoomDB getDB() {
        if (roomDB == null) {
            roomDB = Room.databaseBuilder(this,
                    RoomDB.class, DB_NAME).build();
            dataManager = new DataManager(roomDB);
        }
        return roomDB;
    }

    @NonNull
    public DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager(getDB());
        }
        return dataManager;
    }

    public boolean isNetworkAvailable() {
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
