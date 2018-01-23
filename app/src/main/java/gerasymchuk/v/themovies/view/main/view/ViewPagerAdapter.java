package gerasymchuk.v.themovies.view.main.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gerasymchuk.v.themovies.shared.AbsPagerAdapter;
import gerasymchuk.v.themovies.shared.Logger;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class ViewPagerAdapter extends AbsPagerAdapter {

    @SuppressWarnings("unused")
    private static final String TAG = "ViewPagerAdapter";

    @SuppressWarnings("unused")
    private static final boolean DEBUG = true;

    @NonNull
    private List<Fragment> fragments = new ArrayList<>();

    ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentForPosition(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * Gets fragment for provided position or new Fragment() if position is >= fragments.size()
     *
     * @param position position
     * @return Fragment
     */
    @NonNull
    private Fragment getFragmentForPosition(int position) {
        if (position < fragments.size()) {
            return fragments.get(position);
        }
        return new Fragment();
    }

    /**
     * Adds fragments to {@link ViewPagerAdapter}
     *
     * @param fragment {@link Fragment}
     */
    void addFragments(@NonNull Fragment... fragment) {
        log("addFragments :: %s ", String.valueOf(fragment.length));
        fragments.addAll(Arrays.asList(fragment));
        notifyDataSetChanged();
    }

    /**
     * Log
     */
    private void log(@NonNull String msg, Object... args) {
        if (DEBUG) {
            Logger.d(TAG, msg, args);
        }
    }
}
