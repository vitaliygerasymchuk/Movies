package gerasymchuk.v.themovies.view.tab.now_playing.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.App;
import gerasymchuk.v.themovies.data.RoomDB;
import gerasymchuk.v.themovies.data.model.response.NowPlayingMoviesResponse;
import gerasymchuk.v.themovies.enums.MovieType;
import gerasymchuk.v.themovies.network.AbsInteractor;
import gerasymchuk.v.themovies.shared.Logger;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;
import static gerasymchuk.v.themovies.shared.Const.DEFAULT_LANGUAGE;
import static gerasymchuk.v.themovies.shared.Const.DEFAULT_PAGE;
import static gerasymchuk.v.themovies.shared.Const.DEFAULT_REGION;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class GetNowPlayingMoviesInteractor
        extends AbsInteractor<NowPlayingMoviesResponse>
        implements GetNowPlayingMoviesUseCase {

    @SuppressWarnings("unused")
    private static final String TAG = "GetNowPlayingMoviesInteractor";

    @NonNull
    private RoomDB db;

    @Nullable
    private String language;

    @Nullable
    private String region;

    private int page = -1;

    public GetNowPlayingMoviesInteractor(@Nullable OnSuccess<NowPlayingMoviesResponse> onSuccess,
                                         @Nullable OnError<String> errorString,
                                         @Nullable OnError<Integer> errorInt) {
        super(onSuccess, errorString, errorInt);
        db = App.getInstance().getDB();
    }

    @Override
    public void getNowPlayingMovies(@Nullable String language, @Nullable String region, int page) {
        this.language = language;
        this.region = region;
        this.page = page;
        this.execute();
    }

    @Override
    protected void doInBackground() {
        getMovies();
    }

    private void getMovies() {
        try {
            log("getMoviesForType :: start");
            final Response<NowPlayingMoviesResponse> response = App.getInstance()
                    .getApi()
                    .getNowPlayingMovies(getLanguage(), getPage(), getRegion())
                    .execute();

            log("getMoviesForType :: received response %s", +response.code());

            final NowPlayingMoviesResponse responseBody = response.body();

            if (responseBody != null) {
                db.moviesDao().insertMovies(responseBody.movies);
                notifySuccess(responseBody);
            } else {
                handleErrorResponse(response);
                logE("getMoviesForType :: NowPlayingMoviesResponse is null ");
            }
        } catch (Exception e) {
            logE("getMoviesForType :: exception " + e.toString());
            notifyError(EXCEPTION_MSG);
        }
    }

    @NonNull
    private String getLanguage() {
        if (isEmpty(language)) return DEFAULT_LANGUAGE;
        return language;
    }

    @NonNull
    private String getRegion() {
        if (isEmpty(region)) return DEFAULT_REGION;
        return region;
    }

    private int getPage() {
        if (page == -1) return DEFAULT_PAGE;
        return page;
    }

    private void log(@NonNull String msg, @NonNull Object... args) {
        Logger.d(TAG, "[MOVIES_NOW_PLAYING] " + msg, args);
    }

    private void logE(@NonNull String msg) {
        Logger.e(TAG, "[MOVIES_NOW_PLAYING] " + msg);
    }
}
