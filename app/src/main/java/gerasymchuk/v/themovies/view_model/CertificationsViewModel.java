package gerasymchuk.v.themovies.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.data.model.Certification;
import gerasymchuk.v.themovies.threading.BackgroundThread;

/**
 * Created by vitaliygerasymchuk on 12/10/17
 */

public class CertificationsViewModel extends AbsViewModel {

    @Nullable
    private MutableLiveData<Certification> liveData;

    @NonNull
    public LiveData<Certification> getCertificationForKeyValue(@NonNull String key, @NonNull String value) {
        liveData = getLiveData();
        loadCertificationForKeyValueAsync(key, value);
        return liveData;
    }

    private void loadCertificationForKeyValueAsync(@NonNull String key, @NonNull String value) {
        new BackgroundThread<>(()
                -> db.certificationsDao().getCertificationForKeyAndValue(key, value), certification
                -> getLiveData().postValue(certification));
    }

    @NonNull
    private MutableLiveData<Certification> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
}
