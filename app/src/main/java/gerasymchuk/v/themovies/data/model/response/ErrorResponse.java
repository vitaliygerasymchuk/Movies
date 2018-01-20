package gerasymchuk.v.themovies.data.model.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import static gerasymchuk.v.themovies.shared.Validator.validString;

public class ErrorResponse {

    public static final int SC_INVALID_CREDENTIALS = 30;

    @SerializedName("status_code")
    public int statusCode;

    @SerializedName("status_message")
    @Nullable
    private String statusMessage;

    @NonNull
    public String getStatusMessage() {
        return validString(statusMessage);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
