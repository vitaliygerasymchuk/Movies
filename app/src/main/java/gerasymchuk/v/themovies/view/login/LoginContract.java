package gerasymchuk.v.themovies.view.login;


import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import gerasymchuk.v.themovies.view.BasePresenter;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public interface LoginContract {
    interface View {
        @NonNull
        String getUserName();

        @NonNull
        String getPassword();

        void showUserNameError(@StringRes int error);

        void hideUserNameError();

        void showPasswordError(@StringRes int error);

        void hidePasswordError();

        void showLoginProgress();

        void hideLoginProgress();

        void showSnack(@StringRes int msg);

        void showSnack(@NonNull String msg);

        void startMainActivity();
    }

    interface Presenter extends BasePresenter {
        void login();
    }
}
