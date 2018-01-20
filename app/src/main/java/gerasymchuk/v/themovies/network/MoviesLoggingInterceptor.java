package gerasymchuk.v.themovies.network;


import com.orhanobut.logger.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * Created by vitaliygerasymchuk on 12/10/17
 * Utech(c)
 */

public class MoviesLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        /**
         * A {@link HttpLoggingInterceptor.Logger} defaults output appropriate for the current platform.
         */
        MoviesLoggingInterceptor.Logger DEFAULT = message -> Platform.get().log(INFO, message, null);
    }

    public MoviesLoggingInterceptor() {
        this(MoviesLoggingInterceptor.Logger.DEFAULT);
    }

    public MoviesLoggingInterceptor(MoviesLoggingInterceptor.Logger logger) {
        this.logger = logger;
    }

    private final MoviesLoggingInterceptor.Logger logger;

    private volatile MoviesLoggingInterceptor.Level level = MoviesLoggingInterceptor.Level.NONE;

    /**
     * Change the level at which this interceptor logs.
     */
    public MoviesLoggingInterceptor setLevel(MoviesLoggingInterceptor.Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        this.level = level;
        return this;
    }

    public MoviesLoggingInterceptor.Level getLevel() {
        return level;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        MoviesLoggingInterceptor.Level level = this.level;
        String log = "";
        String responseStr = "";

        Request request = chain.request();
        if (level == MoviesLoggingInterceptor.Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = level == MoviesLoggingInterceptor.Level.BODY;
        boolean logHeaders = logBody || level == MoviesLoggingInterceptor.Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        log = log + "\n" + requestStartMessage;

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    log = log  + "\nContent-Type: " + requestBody.contentType();
                }
                if (requestBody.contentLength() != -1) {
                    log = log + "\nContent-Length: " + requestBody.contentLength();
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    log = log + "\n" + name + ": " + headers.value(i);
                }
            }

            if (!logBody || !hasRequestBody) {
                log = log + "\n--> END " + request.method();
            } else if (bodyEncoded(request.headers())) {
                log = log + "\n--> END " + request.method() + " (encoded body omitted)";
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                log = "";
                if (isPlaintext(buffer)) {
                    log = log + "\n" + buffer.readString(charset);
                    log = log + "\n--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)";
                    com.orhanobut.logger.Logger.t("MOVIES").d(log);
                } else {
                    log = log + "\n--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)";
                    com.orhanobut.logger.Logger.t("MOVIES").d(log);
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log = log + "\n<-- HTTP FAILED: " + e;
            com.orhanobut.logger.Logger.t("MOVIES").d(log);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        log = log + "\n<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')';

        if (logHeaders) {
            Headers headers = response.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                log = log + "\n" + headers.name(i) + ": " + headers.value(i);
            }

            if (!logBody || !HttpHeaders.hasBody(response)) {
                log = log + "\n<-- END HTTP";
            } else if (bodyEncoded(response.headers())) {
                log = log + "\n<-- END HTTP (encoded body omitted)";
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        log = log + "\n";
                        log = log + "\nCouldn't decode the response body; charset is likely malformed.";
                        log = log + "\n<-- END HTTP";
                        com.orhanobut.logger.Logger.t("MOVIES").d(log);
                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    log = log + "\n";
                    log = log + "\n<-- END HTTP (binary " + buffer.size() + "-byte body omitted)";
                    com.orhanobut.logger.Logger.t("MOVIES").d(log);
                    return response;
                }

                if (contentLength != 0) {

                    responseStr = buffer.clone().readString(charset);
                }

                log = log + "\n<-- END HTTP (" + buffer.size() + "-byte body)";
            }
            com.orhanobut.logger.Logger.t("MOVIES").d(log);
            com.orhanobut.logger.Logger.t("MOVIES").json(responseStr);
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
