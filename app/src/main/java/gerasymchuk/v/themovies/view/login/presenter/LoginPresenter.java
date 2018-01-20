package gerasymchuk.v.themovies.view.login.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.data.model.response.MoviesResponse;
import gerasymchuk.v.themovies.data.shared_data.GetNowPlayingMoviesInteractor;
import gerasymchuk.v.themovies.view.login.data.model.RequestTokenResponse;
import gerasymchuk.v.themovies.data.model.response.SessionResponse;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.view.login.LoginContract;
import gerasymchuk.v.themovies.view.login.data.CreateSessionTokenUseCase;
import gerasymchuk.v.themovies.view.login.data.GetRequestTokenUseCase;
import gerasymchuk.v.themovies.view.login.data.LoginUseCase;
import gerasymchuk.v.themovies.view.login.data.interactor.CreateSessionTokenInteractor;
import gerasymchuk.v.themovies.view.login.data.interactor.GetRequestTokenInteractor;
import gerasymchuk.v.themovies.view.login.data.interactor.LoginInteractor;
import gerasymchuk.v.themovies.view.login.data.model.LoginResponse;

import static android.text.TextUtils.isEmpty;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public class LoginPresenter implements LoginContract.Presenter {

    @SuppressWarnings("unused")
    private static final String TAG = LoginPresenter.class.getSimpleName();

    @Nullable
    private LoginContract.View view;

    @Nullable
    private RequestTokenResponse requestTokenResponse;

    @NonNull
    private LoginUseCase loginUseCase;

    @NonNull
    private GetRequestTokenUseCase requestTokenUseCase;

    @NonNull
    private CreateSessionTokenUseCase createSessionTokenUseCase;

    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
        this.initLoginInteractor();
        this.initRequestTokenInteractor();
        this.initCreateSessionInteractor();
        log("presenter created");
    }

    @Override
    public void onDestroy() {
        log("onDestroy :: presenter destroyed");
        view = null;
    }

    @Override
    public void onResume() {
        log("onResume :: presenter resumed");
    }

    @Override
    public void login() {
        loginInternal();
    }


    //**********************************************************************************************
    //Initialization of interactors Start REGION

    /**
     * Initializes {@link GetRequestTokenInteractor}
     */
    private void initRequestTokenInteractor() {
        this.requestTokenUseCase = new GetRequestTokenInteractor(
                requestTokenResponse -> {
                    LoginPresenter.this.requestTokenResponse = requestTokenResponse;
                    loginWithNet(requestTokenResponse);
                },
                errorResponseStringReasonListener, errorResponseIntReasonListener);
    }

    /**
     * Initializes {@link LoginInteractor}
     */
    private void initLoginInteractor() {
        this.loginUseCase = new LoginInteractor(loginResponse -> {
            if (view == null) return;
            createSessionTokenWithNet(loginResponse);
        }, errorResponseStringReasonListener, errorResponseIntReasonListener);
    }

    /**
     * Initializes {@link CreateSessionTokenUseCase}
     */
    private void initCreateSessionInteractor() {
        createSessionTokenUseCase = new CreateSessionTokenInteractor(sessionResponse -> {
            requestTokenResponse = null;
            if (view == null) return;
            view.hideLoginProgress();
            view.startMainActivity();
        }, errorResponseStringReasonListener, errorResponseIntReasonListener);
    }

    //Initialization of interactors End REGION
    //**********************************************************************************************

    /**
     * Starts the login process by checking the user input
     * than generating the request token and performing the login request
     */
    private void loginInternal() {
        if (view == null) return;

        view.showLoginProgress();

        final String userName = view.getUserName();
        final String password = view.getPassword();

        log("loginInternal :: username %s, password %s ", userName, password);

        view.hideUserNameError();
        view.hidePasswordError();

        if (isInputValid(userName, password)) {
            doWithNetwork(this::generateRequestToken);
        }
    }

    /**
     * Checks if user input is valid
     *
     * @param userName String username
     * @param password String password
     * @return true if input is not empty
     */
    private boolean isInputValid(@Nullable String userName, @Nullable String password) {
        if (view == null) return false;

        if (isEmpty(userName)) {
            log("isInputValid :: empty username");
            view.showUserNameError(R.string.login_empty_username_error);
            return false;
        } else if (isEmpty(password)) {
            log("isInputValid :: empty password");
            view.showPasswordError(R.string.login_empty_password_error);
            return false;
        } else {
            log("isInputValid :: credentials OK");
            return true;
        }
    }

    /**
     * Generates the request token
     */
    private void generateRequestToken() {
        if (view == null) return;
        if (isRequestTokenValid(requestTokenResponse)) {
            log("generateRequestToken :: token is valid, %s", requestTokenResponse);
            loginWithNet(requestTokenResponse);
        } else {
            requestTokenUseCase.getRequestToken();
        }
    }

    /**
     * Checks whether the request token was already generated and if it is valid
     *
     * @param requestTokenResponse {@link RequestTokenResponse}
     * @return true if not null and valid
     */
    private boolean isRequestTokenValid(@Nullable RequestTokenResponse requestTokenResponse) {
        return requestTokenResponse != null && !DateTime.now(DateTimeZone.UTC).isAfter(requestTokenResponse.expiresAt);
    }

    /**
     * Logs in if the Internet is available
     *
     * @param requestTokenResponse {@link RequestTokenResponse}
     */
    private void loginWithNet(@NonNull RequestTokenResponse requestTokenResponse) {
        if (view == null) return;
        doWithNetwork(() -> loginUseCase.login(requestTokenResponse.getRequestToken(), view.getUserName(), view.getPassword()));
    }

    /**
     * Creates session token after login if the Internet is available
     *
     * @param loginResponse {@link LoginResponse}
     */
    private void createSessionTokenWithNet(@NonNull LoginResponse loginResponse) {
        doWithNetwork(() -> createSessionTokenUseCase.createSessionToken(loginResponse.getRequestToken()));
    }

    /**
     * Starts the operation if the Internet is available, else shows No Internet connection msg
     *
     * @param target {@link Runnable}
     */
    private void doWithNetwork(@NonNull Runnable target) {
        if (App.getInstance().isNetworkAvailable()) {
            target.run();
        } else {
            if (view == null) return;
            view.hideLoginProgress();
            view.showSnack(R.string.error_connection_lost);
        }
    }

    /**
     * Listener of the error server response which returns string reason
     */
    @NonNull
    private OnError<String> errorResponseStringReasonListener = s -> {
        if (view == null) return;
        view.hideLoginProgress();
        view.showSnack(s);
    };

    /**
     * Listener of the error server response which returns string resource reason
     */
    @NonNull
    private OnError<Integer> errorResponseIntReasonListener = integer -> {
        if (view == null) return;
        view.hideLoginProgress();
        view.showSnack(integer);
    };

    /**
     * Log
     */
    private void log(@NonNull String msg, @NonNull Object... args) {
        Logger.d(TAG, "[LOGIN] " + msg, args);
    }
}
