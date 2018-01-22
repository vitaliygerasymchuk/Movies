package gerasymchuk.v.themovies.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import gerasymchuk.v.themovies.BuildConfig;
import gerasymchuk.v.themovies.data.deserializer.CertificationsDeserializer;
import gerasymchuk.v.themovies.data.deserializer.NowPlayingMoviesDeserializer;
import gerasymchuk.v.themovies.data.model.response.Certifications;
import gerasymchuk.v.themovies.data.model.response.NowPlayingMoviesResponse;
import gerasymchuk.v.themovies.shared.DateSerializer;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * This class is used to manager {@link Retrofit}
 */
public class RetrofitManager {

    private static final long CONNECT_TIMEOUT = 30;

    private static final long WRITE_TIMEOUT = 30;

    private static final long READ_TIMEOUT = 30;

    /**
     * Initializes {@link Retrofit}
     *
     * @return Retrofit
     */
    @NonNull
    public Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create(getGSON()))
                .client(okHttpClient())
                .build();
    }

    /**
     * Initializes {@link OkHttpClient}
     *
     * @return OkHttpClient
     */
    @NonNull
    private OkHttpClient okHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(WRITE_TIMEOUT, SECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT, SECONDS);
        builder.readTimeout(READ_TIMEOUT, SECONDS);

        if (BuildConfig.DEBUG) {
            final MoviesLoggingInterceptor loggingInterceptor = new MoviesLoggingInterceptor();
            loggingInterceptor.setLevel(MoviesLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }

    /**
     * Initializes {@link Gson}
     *
     * @return Gson
     */
    @NonNull
    private static Gson getGSON() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateSerializer())
                .registerTypeAdapter(Certifications.class, new CertificationsDeserializer())
                .registerTypeAdapter(NowPlayingMoviesResponse.class, new NowPlayingMoviesDeserializer())
                .create();
    }
}