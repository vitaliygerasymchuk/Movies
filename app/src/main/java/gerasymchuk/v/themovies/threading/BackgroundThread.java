package gerasymchuk.v.themovies.threading;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BackgroundThread<T> implements Runnable {

    public interface Thread<T> {
        T execute();
    }

    public interface Callback<T> {
        void onPostExecute(T t);
    }

    @Override
    public void run() {
        final T t = thread.execute();

        if (callback != null) {
            UIThread.getInstance().post(() -> callback.onPostExecute(t));
        }
    }

    @NonNull
    private final Thread<T> thread;

    @Nullable
    private Callback<T> callback;

    public BackgroundThread(@NonNull Thread<T> thread, @Nullable Callback<T> callback) {
        this.thread = thread;
        this.callback = callback;
        new JobExecutor().execute(this);
    }
}
