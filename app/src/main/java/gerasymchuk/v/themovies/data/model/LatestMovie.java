package gerasymchuk.v.themovies.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static gerasymchuk.v.themovies.shared.Validator.validString;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class LatestMovie {

    @SerializedName("id")
    public int id;

    @SerializedName("vote_average")
    public int voteAverage;

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("revenue")
    public int revenue;

    @SerializedName("budget")
    public long budget;

    @SerializedName("popularity")
    public int popularity;

    @SerializedName("video")
    public boolean video;

    @SerializedName("adult")
    public boolean adult;

    @SerializedName("runtime")
    public long runtime;

    @SerializedName("belongs_to_collection")
    private Object belongsToCollection;

    @SerializedName("poster_path")
    @Nullable
    private String posterPath;

    @SerializedName("backdrop_path")
    @Nullable
    private String backdropPath;

    @SerializedName("release_date")
    @Nullable
    private String releaseDate;

    @SerializedName("status")
    @Nullable
    private String status;

    @SerializedName("tagline")
    @Nullable
    private String tagline;

    @SerializedName("title")
    @Nullable
    private String title;

    @SerializedName("homepage")
    @Nullable
    private String homepage;

    @SerializedName("imdb_id")
    @Nullable
    private String imdbId;

    @SerializedName("original_language")
    @Nullable
    private String originalLanguage;

    @SerializedName("original_title")
    @Nullable
    private String originalTitle;

    @SerializedName("overview")
    @Nullable
    private String overview;

    @SerializedName("genres")
    @NonNull
    public List<Object> genres = new ArrayList<>();

    @SerializedName("production_companies")
    @NonNull
    public List<Object> productionCompanies = new ArrayList<>();

    @SerializedName("production_countries")
    @NonNull
    public List<Object> productionCountries = new ArrayList<>();

    @SerializedName("spoken_languages")
    @NonNull
    public List<Object> spokenLanguages = new ArrayList<>();

    @NonNull
    public String getPosterPath() {
        return validString(posterPath);
    }

    @NonNull
    public String getBackdropPath() {
        return validString(backdropPath);
    }

    @NonNull
    public String getReleaseDate() {
        return validString(releaseDate);
    }

    @NonNull
    public String getStatus() {
        return validString(status);
    }

    @NonNull
    public String getTagline() {
        return validString(tagline);
    }

    @NonNull
    public String getTitle() {
        return validString(title);
    }

    @NonNull
    public String getHomepage() {
        return validString(homepage);
    }

    @NonNull
    public String getImdbId() {
        return validString(imdbId);
    }

    @NonNull
    public String getOriginalLanguage() {
        return validString(originalLanguage);
    }

    @NonNull
    public String getOriginalTitle() {
        return validString(originalTitle);
    }

    @NonNull
    public String getOverview() {
        return validString(overview);
    }
}
