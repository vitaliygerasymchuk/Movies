package gerasymchuk.v.themovies.data.shared_data;

import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.view.BaseCallback;

/**
 * Created by vitaliygerasymchuk on 11/19/17.
 */

public interface GetTvCertificationsUseCase {
    void getTvCertifications(@Nullable Callback callback);

    interface Callback extends BaseCallback {
        void onSuccess();
    }
}
