package gerasymchuk.v.themovies.view.tab.now_playing.data;

import android.support.annotation.Nullable;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public interface GetNowPlayingMoviesUseCase {
    void getNowPlayingMovies(@Nullable String language, @Nullable String region, int page);
}
