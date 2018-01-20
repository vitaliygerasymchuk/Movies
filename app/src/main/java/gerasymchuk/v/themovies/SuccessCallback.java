package gerasymchuk.v.themovies;

import android.support.annotation.Nullable;

/**
 * Created by vitaliygerasymchuk on 1/6/18
 */

public interface SuccessCallback<T> {
    void onSuccess(@Nullable T t);
}
