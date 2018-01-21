package gerasymchuk.v.themovies.data.model.response;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import gerasymchuk.v.themovies.data.model.Certification;

/**
 * Created by vitaliygerasymchuk on 11/18/17
 */

public class Certifications {

    @NonNull
    public List<Certification> certifications = new ArrayList<>();

    @Override
    public String toString() {
        return "Certifications{" +
                "certifications=" + certifications +
                '}';
    }
}

