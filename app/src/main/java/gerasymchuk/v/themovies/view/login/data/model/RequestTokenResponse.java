package gerasymchuk.v.themovies.view.login.data.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import gerasymchuk.v.themovies.MoviesApiResponse;

import static gerasymchuk.v.themovies.shared.Validator.validString;

public class RequestTokenResponse extends MoviesApiResponse {

    @SerializedName("expires_at")
    @Nullable
    public DateTime expiresAt;

    @SerializedName("request_token")
    @Nullable
    public String requestToken;

    @NonNull
    public String getRequestToken() {
        return validString(requestToken);
    }

    @Override
    public String toString() {
        return "RequestTokenResponse{" +
                "success=" + success +
                ", expiresAt=" + expiresAt +
                ", requestToken='" + requestToken + '\'' +
                '}';
    }
}
