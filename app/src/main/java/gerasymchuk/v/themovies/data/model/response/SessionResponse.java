package gerasymchuk.v.themovies.data.model.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;


import gerasymchuk.v.themovies.MoviesApiResponse;

import static gerasymchuk.v.themovies.shared.Validator.validString;


public class SessionResponse extends MoviesApiResponse {

    @SerializedName("session_id")
    @Nullable
    private String sessionId;

    @NonNull
    public String getSessionId() {
        return validString(sessionId);
    }

    @Override
    public String toString() {
        return "SessionResponse{" +
                "success=" + success +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}
