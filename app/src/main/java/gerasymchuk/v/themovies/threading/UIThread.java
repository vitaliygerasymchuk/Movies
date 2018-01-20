package gerasymchuk.v.themovies.threading;

import android.os.Handler;
import android.os.Looper;

public class UIThread implements PostExecutionThread {

    private final Handler handler;

    public UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    private static class LazyHolder {
        private static final UIThread INSTANCE = new UIThread();
    }
}

