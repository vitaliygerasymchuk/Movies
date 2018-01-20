package gerasymchuk.v.themovies.view.login.data.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.view.login.data.model.RequestTokenResponse;
import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.login.data.GetRequestTokenUseCase;
import retrofit2.Response;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public class GetRequestTokenInteractor
        extends AbsInteractor<RequestTokenResponse>
        implements GetRequestTokenUseCase {

    @SuppressWarnings("unused")
    private static final String TAG = GetRequestTokenInteractor.class.getSimpleName();

    public GetRequestTokenInteractor(@Nullable OnSuccess<RequestTokenResponse> onSuccess,
                                     @Nullable OnError<String> errorString,
                                     @Nullable OnError<Integer> errorInt) {
        super(onSuccess, errorString, errorInt);
    }

    @Override
    public void getRequestToken() {
        this.execute();
    }

    @Override
    protected void doInBackground() {
        getRequestTokenInternal();
    }

    private void getRequestTokenInternal() {
        try {

            final Response<RequestTokenResponse> response = App.getInstance()
                    .getApi()
                    .getRequestToken()
                    .execute();

            final RequestTokenResponse responseBody = response.body();

            if (responseBody != null) {
                log("getRequestTokenInternal :: received response %s ", responseBody);
                if (responseBody.success) {
                    notifySuccess(responseBody);
                } else {
                    notifyError(REQUEST_FAILED);
                }
            } else {
                logE("getRequestTokenInternal :: RequestTokenResponse == null");
                handleErrorResponse(response);
            }

        } catch (Exception e) {
            logE("getRequestTokenInternal :: exception " + e.toString());
            e.printStackTrace();
            notifyError(EXCEPTION_MSG);
        }
    }

    private void log(@NonNull String msg, @NonNull Object... args) {
        Logger.d(TAG, "[LOGIN] " + msg, args);
    }

    private void logE(@NonNull String msg) {
        Logger.e(TAG, "[LOGIN] " + msg);
    }
}
