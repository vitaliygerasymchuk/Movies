package gerasymchuk.v.themovies.view.login.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import gerasymchuk.v.themovies.MoviesApiResponse;

import static gerasymchuk.v.themovies.shared.Validator.validString;

/**
 * Created by vitaliygerasymchuk on 1/11/18
 */

public class LoginResponse extends MoviesApiResponse {

    @SerializedName("request_token")
    @Nullable
    private String requestToken;

    @NonNull
    public String getRequestToken() {
        return validString(requestToken);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", requestToken='" + requestToken + '\'' +
                '}';
    }
}
