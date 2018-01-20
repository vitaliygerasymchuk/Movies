package gerasymchuk.v.themovies.view.main.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gerasymchuk.v.themovies.shared.AbsPagerAdapter;

/**
 * Created by vitaliygerasymchuk on 1/12/18
 */

public class ViewPagerAdapter extends AbsPagerAdapter {

    @NonNull
    private List<Fragment> fragments = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragments.size()) {
            return fragments.get(position);
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragments(@NonNull Fragment... fragment) {
        fragments.addAll(Arrays.asList(fragment));
        notifyDataSetChanged();
    }
}
