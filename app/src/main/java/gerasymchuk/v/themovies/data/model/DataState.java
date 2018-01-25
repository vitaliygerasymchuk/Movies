package gerasymchuk.v.themovies.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import gerasymchuk.v.themovies.network.EDataState;

/**
 * Created by vitaliygerasymchuk on 1/25/18
 */

public class DataState {

    @StringRes
    private int errorIntRes = -1;

    @Nullable
    private String errorString = "";

    @NonNull
    private EDataState eDataState = EDataState.Na;

    public DataState(@NonNull EDataState eDataState, @StringRes int errorIntRes, @Nullable String errorString) {
        this.errorIntRes = errorIntRes;
        this.errorString = errorString;
        this.eDataState = eDataState;
    }
}
