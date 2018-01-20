package gerasymchuk.v.themovies.data.shared_data;

import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Certification;
import gerasymchuk.v.themovies.view.BaseCallback;

/**
 * Created by vitaliygerasymchuk on 11/17/17.
 */

public interface GetMoviesCertificationsUseCase {
    void getAllCertifications(@Nullable Callback callback);

    interface Callback extends BaseCallback {
        void onSuccess(List<Certification> moviesCertificationsList);
    }
}
