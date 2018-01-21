package gerasymchuk.v.themovies.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import gerasymchuk.v.themovies.data.Converters;

import static gerasymchuk.v.themovies.shared.Const.TABLE_MOVIES;
import static gerasymchuk.v.themovies.shared.Validator.validString;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

@Entity(tableName = TABLE_MOVIES)
public class Movie {

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("id")
    @PrimaryKey
    public int id;

    @SerializedName("video")
    public boolean video;

    @SerializedName("adult")
    @Expose
    public boolean adult;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("popularity")
    public double popularity;

    @SerializedName("title")
    @Nullable
    private String title;

    @SerializedName("poster_path")
    @Nullable
    private String posterPath;

    @SerializedName("original_language")
    @Nullable
    private String originalLanguage;

    @SerializedName("original_title")
    @Nullable
    private String originalTitle;

    @SerializedName("backdrop_path")
    @Nullable
    private String backdropPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Nullable
    private String releaseDate;

    @SerializedName("genre_ids")
    @NonNull
    public ArrayList<Integer> genreIds = new ArrayList<>();

    @NonNull
    public String getTitle() {
        return validString(title);
    }

    @NonNull
    public String getPosterPath() {
        return validString(posterPath);
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
    public String getBackdropPath() {
        return validString(backdropPath);
    }

    @NonNull
    public String getOverview() {
        return validString(overview);
    }

    @NonNull
    public String getReleaseDate() {
        return validString(releaseDate);
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setPosterPath(@Nullable String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(@Nullable String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(@Nullable String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setBackdropPath(@Nullable String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(@Nullable String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "voteCount=" + voteCount +
                ", id=" + id +
                ", video=" + video +
                ", adult=" + adult +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreIds=" + genreIds +
                '}';
    }
}
