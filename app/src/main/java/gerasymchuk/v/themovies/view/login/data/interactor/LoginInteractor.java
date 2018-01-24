package gerasymchuk.v.themovies.view.login.data.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.login.data.LoginUseCase;
import gerasymchuk.v.themovies.view.login.data.model.LoginResponse;
import retrofit2.Response;

/**
 * Created by vitaliygerasymchuk on 1/11/18
 */

public class LoginInteractor
        extends AbsInteractor<LoginResponse>
        implements LoginUseCase {

    @SuppressWarnings("unused")
    private static final String TAG = LoginInteractor.class.getSimpleName();

    @SuppressWarnings("unused")
    public static final boolean DEBUG = true;

    @NonNull
    private String requestToken = "";

    @NonNull
    private String userName = "";

    @NonNull
    private String password = "";

    public LoginInteractor(@Nullable OnSuccess<LoginResponse> onSuccess,
                           @Nullable OnError<String> errorString,
                           @Nullable OnError<Integer> errorInt) {
        super(onSuccess, errorString, errorInt);
    }


    @Override
    public void login(@NonNull String requestToken, @NonNull String userName, @NonNull String password) {
        this.requestToken = requestToken;
        this.userName = userName;
        this.password = password;
        this.execute();
    }

    @Override
    protected void doInBackground() {
        loginSync();
    }

    private void loginSync() {
        try {
            final Response<LoginResponse> response = App.getInstance()
                    .getApi()
                    .login(userName, password, requestToken)
                    .execute();

            final LoginResponse responseBody = response.body();

            if (responseBody != null) {
                log("loginSync :: received response %s ", responseBody);
                if (responseBody.success) {
                    notifySuccess(responseBody);
                } else {
                    notifyError(REQUEST_FAILED);
                }
            } else {
                logE("loginSync :: LoginResponse == null");
                handleErrorResponse(response);
            }

        } catch (Exception e) {
            logE("loginSync :: exception " + e.toString());
            notifyError(EXCEPTION_MSG);
        }
    }

    private void log(@NonNull String msg, @NonNull Object... args) {
        if (DEBUG) {
            Logger.d(TAG, "[LOGIN] " + msg, args);
        }
    }

    private void logE(@NonNull String msg) {
        if (DEBUG) {
            Logger.e(TAG, "[LOGIN] " + msg);
        }
    }
}
