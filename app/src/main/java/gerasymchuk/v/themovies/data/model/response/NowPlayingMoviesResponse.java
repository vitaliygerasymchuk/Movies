package gerasymchuk.v.themovies.data.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import gerasymchuk.v.themovies.data.model.Movie;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class NowPlayingMoviesResponse {

    @SerializedName("results")
    public List<Movie> movies = new ArrayList<>();

    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int totalItems;

    @SerializedName("total_pages")
    public int totalPages;

    @Override
    public String toString() {
        return "NowPlayingMoviesResponse{" +
                "movies=" + movies.size() + ", " +
                "page=" + page + ", " +
                "totalItems=" + totalItems + ", " +
                "totalPages=" + totalPages + ", " +
                '}';
    }
}
