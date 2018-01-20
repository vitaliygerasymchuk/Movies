package gerasymchuk.v.themovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import gerasymchuk.v.themovies.data.model.Certification;

public class DataManager {

    @NonNull
    private RoomDB db;

    public DataManager(@NonNull RoomDB db) {
        this.db = db;
    }

    @Nullable
    @WorkerThread
    public Certification getCertificationForKeyAndValueFromDB(@NonNull String countryKey,
                                                              @NonNull String certification) {
        return db.certificationsDao().getCertificationForKeyAndValue(countryKey, certification);
    }
}
