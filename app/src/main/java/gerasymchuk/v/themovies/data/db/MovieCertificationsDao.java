package gerasymchuk.v.themovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import gerasymchuk.v.themovies.data.model.Certification;

import static gerasymchuk.v.themovies.shared.Const.TABLE_CERTIFICATIONS;

/**
 * Created by vitaliygerasymchuk on 11/18/17
 */

@Dao
public interface MovieCertificationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCertifications(@NonNull List<Certification> certifications);

    @Update
    void updateCertifications(@NonNull List<Certification> certifications);

    @NonNull
    @Query("SELECT * FROM " + TABLE_CERTIFICATIONS)
    List<Certification> selectAllCertifications();

    @Nullable
    @Query("SELECT * FROM " + TABLE_CERTIFICATIONS + " WHERE countryKey = :key AND certification = :certification LIMIT 1")
    Certification getCertificationForKeyAndValue(@NonNull String key, @NonNull String certification);
}
