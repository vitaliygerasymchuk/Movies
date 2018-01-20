package gerasymchuk.v.themovies.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class MoviesResponse {

    @SerializedName("results")
    public List<Movie> movies = new ArrayList<>();

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "movies=" + movies.size() +
                '}';
    }
}
