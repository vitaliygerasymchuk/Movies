package gerasymchuk.v.themovies.data.shared_data;

import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;

/**
 * Created by vitaliygerasymchuk on 11/19/17.
 */

public class GetTvCertificationsInteractor extends AbsInteractor implements GetTvCertificationsUseCase {

    @Nullable
    private Callback callback;

    public GetTvCertificationsInteractor(@Nullable OnSuccess onSuccess, @Nullable OnError errorString, @Nullable OnError errorInt) {
        super(onSuccess, errorString, errorInt);
    }

    @Override
    public void getTvCertifications(@Nullable Callback callback) {
        this.callback = callback;
        this.execute();
    }

    @Override
    protected void doInBackground() {

    }

    @Override
    public void run() {

    }

    private void getTvCertificationsInternal() {
        try {

        } catch (Exception e) {

        }
    }
}
