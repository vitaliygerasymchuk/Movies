package gerasymchuk.v.themovies.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import static gerasymchuk.v.themovies.shared.Const.TABLE_CERTIFICATIONS;
import static gerasymchuk.v.themovies.shared.Validator.validString;

/**
 * Created by vitaliygerasymchuk on 11/18/17
 */

@Entity(tableName = TABLE_CERTIFICATIONS,
        indices = {@Index(value = {"countryKey", "certification"})}, primaryKeys = {"countryKey", "certification"})
public class Certification {

    @NonNull
    public String countryKey = "";

    @SerializedName("certification")
    @NonNull
    private String certification = "";

    @SerializedName("meaning")
    @Nullable
    private String meaning;

    @SerializedName("order")
    public int order;

    @NonNull
    public String getCertification() {
        return validString(certification);
    }

    @NonNull
    public String getMeaning() {
        return validString(meaning);
    }

    public void setCertification(@NonNull String certification) {
        this.certification = certification;
    }

    public void setMeaning(@Nullable String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "certification='" + certification + '\'' +
                ", meaning='" + meaning + '\'' +
                ", order=" + order +
                ", countryKey='" + countryKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Certification that = (Certification) o;

        if (order != that.order) return false;
        if (!countryKey.equals(that.countryKey)) return false;
        if (!certification.equals(that.certification)) return false;
        return meaning != null ? meaning.equals(that.meaning) : that.meaning == null;
    }

    @Override
    public int hashCode() {
        int result = countryKey.hashCode();
        result = 31 * result + certification.hashCode();
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        result = 31 * result + order;
        return result;
    }
}
