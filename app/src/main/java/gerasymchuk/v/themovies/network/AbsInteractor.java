package gerasymchuk.v.themovies.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import gerasymchuk.v.themovies.MoviesApiResponse;
import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.data.model.response.ErrorResponse;
import gerasymchuk.v.themovies.shared.callback.OnError;
import gerasymchuk.v.themovies.shared.callback.OnSuccess;
import gerasymchuk.v.themovies.threading.JobExecutor;
import gerasymchuk.v.themovies.threading.PostExecutionThread;
import gerasymchuk.v.themovies.threading.ThreadExecutor;
import gerasymchuk.v.themovies.threading.UIThread;
import gerasymchuk.v.themovies.view.BaseCallback;
import okhttp3.ResponseBody;
import retrofit2.Response;

public abstract class AbsInteractor<T> implements Runnable {

    @SuppressWarnings("unused")
    private static final String TAG = AbsInteractor.class.getSimpleName();

    protected int RESPONSE_OK = HttpURLConnection.HTTP_OK;

    @StringRes
    protected int EXCEPTION_MSG = R.string.error_request_exception_msg;

    @StringRes
    protected int RESPONSE_NULL_MSG = R.string.error_request_response_null_msg;

    @StringRes
    protected int REQUEST_FAILED = R.string.error_request_failed;

    @StringRes
    protected int NOT_FOUND = R.string.error_not_found;

    @StringRes
    protected int INTERNAL_SERVER_ERROR = R.string.error_internal_server;

    @NonNull
    protected PostExecutionThread postExecution = new UIThread();

    @NonNull
    private ThreadExecutor executor = new JobExecutor();
    @Nullable
    private OnSuccess<T> onSuccess;

    @Nullable
    private OnError<String> errorString;

    @Nullable
    private OnError<Integer> errorInt;

    private boolean isRunning;

    public AbsInteractor(@Nullable OnSuccess<T> onSuccess, @Nullable OnError<String> errorString, @Nullable OnError<Integer> errorInt) {
        this.onSuccess = onSuccess;
        this.errorString = errorString;
        this.errorInt = errorInt;
    }

    protected void notifyError(@StringRes final int msg) {
        if (errorInt == null) return;

        postExecution.post(() -> errorInt.onError(msg));
    }

    protected void notifyError(@NonNull final String msg) {
        if (errorString == null) return;

        postExecution.post(() -> errorString.onError(msg));
        isRunning = false;
    }

    protected void notifySuccess(@NonNull T t) {
        if (onSuccess == null) return;

        postExecution.post(() -> onSuccess.onSuccess(t));
        isRunning = false;
    }

    protected void execute() {
        if (!isRunning) {
            isRunning = true;
            executor.execute(this);
        }
    }

    protected abstract void doInBackground();

    @Override
    public void run() {
        doInBackground();
        isRunning = false;
    }

    protected void handleErrorResponse(@Nullable Response<T> response) throws IOException {
        if (response != null) {

            if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                notifyError(NOT_FOUND);
                return;
            } else if (response.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                notifyError(INTERNAL_SERVER_ERROR);
                return;
            }

            final ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {

                final String errorString = errorBody.string();

                if (!TextUtils.isEmpty(errorString)) {
                    final ErrorResponse errorResponse = new Gson().fromJson(errorString, ErrorResponse.class);
                    if (errorResponse != null) {
                        notifyError(errorResponse.getStatusMessage());
                    } else {
                        notifyError(REQUEST_FAILED);
                    }
                } else {
                    notifyError(REQUEST_FAILED);
                }

            } else {
                notifyError(REQUEST_FAILED);
            }
        } else {
            notifyError(REQUEST_FAILED);
        }
    }
}
