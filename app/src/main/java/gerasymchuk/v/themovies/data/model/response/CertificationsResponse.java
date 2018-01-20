package gerasymchuk.v.themovies.data.model.response;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import gerasymchuk.v.themovies.MoviesApiResponse;

/**
 * Created by vitaliygerasymchuk on 11/18/17
 */

public class CertificationsResponse extends MoviesApiResponse {

    @SerializedName("certifications")
    @Nullable
    public Certifications certifications;

    @Override
    public String toString() {
        return "CertificationsResponse{" +
                "certifications=" + certifications +
                '}';
    }
}
