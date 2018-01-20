package gerasymchuk.v.themovies.shared.callback;

import android.support.annotation.NonNull;

/**
 * Created by vitaliygerasymchuk on 1/11/18
 */

public interface OnSuccess<T> {
    void onSuccess(@NonNull T t);
}
