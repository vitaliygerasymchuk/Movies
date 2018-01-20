package gerasymchuk.v.themovies.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import gerasymchuk.v.themovies.R;


public abstract class AbsActivity extends AppCompatActivity {

    @LayoutRes
    protected abstract int onRequestLayout();

    protected abstract void beforeAddingLayout();

    protected abstract void onCreateActivity();

    @Nullable
    protected abstract BasePresenter initBasePresenter();

    @Nullable
    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeAddingLayout();
        setContentView(onRequestLayout());
        onCreateActivity();

        basePresenter = initBasePresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (basePresenter != null) {
            basePresenter.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (basePresenter != null) {
            basePresenter.onDestroy();
        }
    }

    @ColorInt
    protected int getCompatColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    @NonNull
    protected <T extends View> T find(@IdRes int id) {
        return findViewById(id);
    }

    protected void showTryAgainSnackBar(@NonNull View view, @NonNull String msg, View.OnClickListener listener) {
        showSnackBar(view, msg, R.string.error_try_again, Snackbar.LENGTH_LONG, listener);
    }

    protected void showTryAgainSnackBar(@NonNull View view, @StringRes int msg, View.OnClickListener listener) {
        showSnackBar(view, msg, R.string.error_try_again, Snackbar.LENGTH_LONG, listener);
    }

    protected void showTryAgainSnackBarIndefinite(@NonNull View view, @NonNull String msg, View.OnClickListener listener) {
        showSnackBar(view, msg, R.string.error_try_again, Snackbar.LENGTH_INDEFINITE, listener);
    }

    protected void showTryAgainSnackBarIndefinite(@NonNull View view, @StringRes int msg, View.OnClickListener listener) {
        showSnackBar(view, msg, R.string.error_try_again, Snackbar.LENGTH_INDEFINITE, listener);
    }

    protected void showSnackBar(@NonNull View view, @NonNull String message, @StringRes int actionText, int duration, View.OnClickListener listener) {
        if (isDestroyed() || !isFinishing()) {
            Snackbar snack = Snackbar.make(view, message, duration);
            snack.setAction(actionText, listener);
            snack.setActionTextColor(getCompatColor(R.color.colorAccent));
            snack.show();
        }
    }

    protected void showSnackBar(@NonNull View view, @StringRes int message, int actionText, int duration, View.OnClickListener listener) {
        if (!isDestroyed() || !isFinishing()) {
            Snackbar snack = Snackbar.make(view, message, duration);
            snack.setAction(actionText, listener);
            snack.setActionTextColor(getCompatColor(R.color.colorAccent));
            snack.show();
        }
    }

    protected void hideKeyboard() {
        if (getCurrentFocus() != null) {
            IBinder token = getCurrentFocus().getWindowToken();

            if (token != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(token, 0);
                }
            }
        }
    }
}
