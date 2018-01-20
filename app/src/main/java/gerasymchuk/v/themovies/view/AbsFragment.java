package gerasymchuk.v.themovies.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public abstract class AbsFragment extends Fragment {

    @NonNull
    private View view;

    @Nullable
    private BasePresenter presenter;

    protected abstract int onRequestLayout();

    protected abstract void onFragmentReady();

    protected abstract BasePresenter bindPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(onRequestLayout(), container, false);
        onFragmentReady();
        presenter = bindPresenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @NonNull
    protected <T extends View> T find(@IdRes int id) {
        return view.findViewById(id);
    }
}
