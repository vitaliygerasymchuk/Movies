package gerasymchuk.v.themovies.view.settings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.view.AbsActivity;
import gerasymchuk.v.themovies.view.BasePresenter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class SettingsActivity extends AbsActivity {

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.activity_settings;
    }

    @Override
    protected void beforeAddingLayout() {

    }

    @Override
    protected void onCreateActivity() {

    }

    @Nullable
    @Override
    protected BasePresenter initBasePresenter() {
        return null;
    }
}
