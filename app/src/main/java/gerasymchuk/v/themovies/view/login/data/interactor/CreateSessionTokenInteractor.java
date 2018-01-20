package gerasymchuk.v.themovies.view.login.data.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.model.response.SessionResponse;
import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.login.data.CreateSessionTokenUseCase;
import retrofit2.Response;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public class CreateSessionTokenInteractor
        extends AbsInteractor<SessionResponse>
        implements CreateSessionTokenUseCase {

    @SuppressWarnings("unused")
    private static final String TAG = CreateSessionTokenInteractor.class.getSimpleName();

    @NonNull
    private String requestToken = "";

    public CreateSessionTokenInteractor(@Nullable OnSuccess<SessionResponse> onSuccess, @Nullable OnError<String> errorString, @Nullable OnError<Integer> errorInt) {
        super(onSuccess, errorString, errorInt);
    }

    @Override
    public void createSessionToken(@NonNull String requestToken) {
        this.requestToken = requestToken;
        this.execute();
    }

    @Override
    protected void doInBackground() {
        createSessionTokenInternal();
    }

    private void createSessionTokenInternal() {
        try {

            final Response<SessionResponse> response = App.getInstance()
                    .getApi()
                    .createSession(requestToken)
                    .execute();

            final SessionResponse responseBody = response.body();

            if (responseBody != null) {
                log("createSessionTokenInternal :: response :: " + responseBody);
                if (responseBody.success) {
                    notifySuccess(responseBody);
                } else {
                    handleErrorResponse(response);
                }
            } else {
                logE("createSessionTokenInternal :: SessionResponse == null");
                notifyError(RESPONSE_NULL_MSG);
            }

        } catch (Exception e) {
            logE("createSessionTokenInternal :: exception " + e.toString());
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
