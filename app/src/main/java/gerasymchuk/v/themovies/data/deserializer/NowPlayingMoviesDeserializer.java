package gerasymchuk.v.themovies.data.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import gerasymchuk.v.themovies.data.model.Movie;
import gerasymchuk.v.themovies.data.model.response.NowPlayingMoviesResponse;
import gerasymchuk.v.themovies.enums.MovieType;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/22/18
 * <p>
 * This class is responsible for deserialization of Now Playing Movies response and applying type
 * to each {@link Movie} for local db queries
 */
public class NowPlayingMoviesDeserializer implements JsonDeserializer<NowPlayingMoviesResponse> {

    private static final boolean DEBUG = true;

    @SuppressWarnings("unused")
    private static final String TAG = "NowPlayingMoviesDeserializer";

    @Override
    public NowPlayingMoviesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final NowPlayingMoviesResponse response = new Gson().fromJson(json, typeOfT);
        if (response != null) {
            for (int i = 0; i < response.movies.size(); i++) {
                final Movie movie = response.movies.get(i);
                if (movie != null) {
                    movie.setType(MovieType.NowPlaying);
                }
            }
            log("deserialize :: de-serialized NowPlayingMoviesResponse %s ", response.toString());
        } else {
            log("deserialize :: NowPlayingMoviesResponse is null !!!");
        }
        return response;
    }

    private void log(String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, "[MOVIES_NOW_PLAYING] " + msg, args);
        }
    }
}
