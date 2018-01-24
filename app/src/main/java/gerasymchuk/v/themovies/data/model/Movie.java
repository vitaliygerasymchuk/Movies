package gerasymchuk.v.themovies.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import gerasymchuk.v.themovies.enums.MovieType;

import static gerasymchuk.v.themovies.shared.Const.TABLE_MOVIES;
import static gerasymchuk.v.themovies.shared.Validator.validString;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

@Entity(tableName = TABLE_MOVIES,
        indices = {@Index(value = {"id", "type"})},
        primaryKeys = {"id", "type"})
public class Movie implements Parcelable {

    @NonNull
    public String type = MovieType.Na.name();

    @SerializedName("vote_count")
    public int voteCount;

    @SerializedName("id")
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

    @NonNull
    public MovieType getType() {
        return MovieType.valueOf(type);
    }

    public void setType(@NonNull MovieType type) {
        this.type = type.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (voteCount != movie.voteCount) return false;
        if (id != movie.id) return false;
        if (video != movie.video) return false;
        if (adult != movie.adult) return false;
        if (Double.compare(movie.voteAverage, voteAverage) != 0) return false;
        if (Double.compare(movie.popularity, popularity) != 0) return false;
        if (!type.equals(movie.type)) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (originalLanguage != null ? !originalLanguage.equals(movie.originalLanguage) : movie.originalLanguage != null)
            return false;
        if (originalTitle != null ? !originalTitle.equals(movie.originalTitle) : movie.originalTitle != null)
            return false;
        if (backdropPath != null ? !backdropPath.equals(movie.backdropPath) : movie.backdropPath != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        return genreIds.equals(movie.genreIds);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type.hashCode();
        result = 31 * result + voteCount;
        result = 31 * result + id;
        result = 31 * result + (video ? 1 : 0);
        result = 31 * result + (adult ? 1 : 0);
        temp = Double.doubleToLongBits(voteAverage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (originalLanguage != null ? originalLanguage.hashCode() : 0);
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + genreIds.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeInt(this.voteCount);
        dest.writeInt(this.id);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.voteAverage);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeList(this.genreIds);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.type = in.readString();
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.video = in.readByte() != 0;
        this.adult = in.readByte() != 0;
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
                ", type=" + getType() +
                '}';
    }
}
