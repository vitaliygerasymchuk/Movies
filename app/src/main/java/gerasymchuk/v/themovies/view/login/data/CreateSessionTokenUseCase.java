package gerasymchuk.v.themovies.view.login.data;

import android.support.annotation.NonNull;

import gerasymchuk.v.themovies.data.model.response.SessionResponse;
import gerasymchuk.v.themovies.view.BaseCallback;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public interface CreateSessionTokenUseCase {
    void createSessionToken(@NonNull String requestToken);
}
