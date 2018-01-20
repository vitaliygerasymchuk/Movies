package gerasymchuk.v.themovies.view.login.data;

import android.support.annotation.NonNull;

/**
 * Created by vitaliygerasymchuk on 1/11/18
 */

public interface LoginUseCase {
    void login(
            @NonNull String requestToken,
            @NonNull String userName,
            @NonNull String password
    );
}
