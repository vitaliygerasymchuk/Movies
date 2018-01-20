package gerasymchuk.v.themovies.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface BaseCallback {
    void onError(@StringRes int msg);

    void onError(@NonNull String msg);
}
