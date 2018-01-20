package gerasymchuk.v.themovies.data.shared_data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.db.MovieCertificationsDao;
import gerasymchuk.v.themovies.data.model.Certification;
import gerasymchuk.v.themovies.data.model.response.CertificationsResponse;
import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import retrofit2.Response;

/**
 * Created by vitaliygerasymchuk on 11/17/17.
 */

public class GetMoviesCertificationsInteractor extends AbsInteractor implements GetMoviesCertificationsUseCase {

    @SuppressWarnings("unused")
    private static final String TAG = GetMoviesCertificationsInteractor.class.getSimpleName();

    @Nullable
    private Callback callback;

    @NonNull
    private final MovieCertificationsDao movieCertificationsDao = App.getInstance().getDB().certificationsDao();

    public GetMoviesCertificationsInteractor(@Nullable OnSuccess onSuccess, @Nullable OnError errorString, @Nullable OnError errorInt) {
        super(onSuccess, errorString, errorInt);
    }

    @Override
    public void getAllCertifications(@Nullable Callback callback) {
        this.callback = callback;
        this.execute();
    }

    @Override
    protected void doInBackground() {

    }

    @Override
    public void run() {
        getMovieCertificationsInternal();
    }

    private void getMovieCertificationsInternal() {
        try {

            if (App.getInstance().isNetworkAvailable()) {
                Logger.d(TAG, "getMovieCertificationsInternal :: requesting server certifications");
                final Response<CertificationsResponse> response = App.getInstance()
                        .getApi()
                        .getMoviesCertifications()
                        .execute();

                if (!isResponseValid(response)) {
                    notifyError(REQUEST_FAILED);
                }

            } else {
                Logger.d(TAG, "getMovieCertificationsInternal :: no internet... checking for db certifications");
                notifySuccess(movieCertificationsDao.selectAllCertifications());
            }

        } catch (Exception e) {
            notifyError(EXCEPTION_MSG);
            Logger.e(TAG, "getMovieCertificationsInternal :: exception " + e.toString());
        }
    }

    private boolean isResponseValid(@NonNull Response<CertificationsResponse> response) throws IOException {

        final CertificationsResponse responseBody = response.body();

        if (responseBody != null) {
            if (responseBody.certifications != null) {
                movieCertificationsDao.insertCertifications(responseBody.certifications.certifications);
            }
        } else {
            Logger.e(TAG, "getMovieCertificationsInternal :: CertificationsResponse == null");
            handleErrorResponse(response);
        }

        return false;
    }

    private void notifySuccess(@NonNull List<Certification> moviesCertificationsList) {
        if (callback == null) return;

        postExecution.post(() -> callback.onSuccess(moviesCertificationsList));
    }
}
