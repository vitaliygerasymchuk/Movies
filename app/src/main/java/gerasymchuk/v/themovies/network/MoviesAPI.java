package gerasymchuk.v.themovies.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.BuildConfig;
import gerasymchuk.v.themovies.data.model.response.CertificationsResponse;
import gerasymchuk.v.themovies.data.model.response.MoviesResponse;
import gerasymchuk.v.themovies.view.login.data.model.RequestTokenResponse;
import gerasymchuk.v.themovies.data.model.response.SessionResponse;
import gerasymchuk.v.themovies.view.login.data.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {

    String REQUEST_TOKEN = "request_token";
    String USERNAME = "username";
    String PASSWORD = "password";
    String LANGUAGE = "language";
    String PAGE = "page";
    String REGION = "region";

    @GET("authentication/token/new?api_key=" + BuildConfig.API_KEY)
    Call<RequestTokenResponse> getRequestToken();

    @GET("authentication/session/new?api_key=" + BuildConfig.API_KEY)
    Call<SessionResponse> createSession(@Query(REQUEST_TOKEN) @NonNull String requestToken);

    @GET("authentication/token/validate_with_login?api_key=" + BuildConfig.API_KEY)
    Call<LoginResponse> login(@Query(USERNAME) @NonNull String userName, @Query(PASSWORD) @NonNull String password, @Query(REQUEST_TOKEN) @NonNull String requestToken);

    @GET("certification/movie/list?api_key=" + BuildConfig.API_KEY)
    Call<CertificationsResponse> getMoviesCertifications();

    @GET("movie/now_playing?api_key=" + BuildConfig.API_KEY)
    Call<MoviesResponse> getNowPlayingMovies(@Query(LANGUAGE) @Nullable String language, @Query(PAGE) int page, @Query(REGION) @Nullable String region);
}
