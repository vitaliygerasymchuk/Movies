package gerasymchuk.v.themovies.view.main.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.view.AbsActivity;
import gerasymchuk.v.themovies.view.BasePresenter;
import gerasymchuk.v.themovies.view.account.AccountActivity;
import gerasymchuk.v.themovies.view.main.MainContract;
import gerasymchuk.v.themovies.view.main.presenter.MainPresenter;
import gerasymchuk.v.themovies.view.settings.SettingsActivity;
import gerasymchuk.v.themovies.view.tab.now_playing.NowPlayingMoviesFragment;

import static android.view.Gravity.START;

public class MainActivity
        extends AbsActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Nullable
    private DrawerLayout drawer;

    @Nullable
    private MainContract.Presenter presenter;

    @Nullable
    private ViewPagerAdapter pagerAdapter;

    @NonNull
    private ViewPager viewPager;

    @Override
    protected int onRequestLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeAddingLayout() {
        //no-op
    }

    @Override
    protected void onCreateActivity() {
        setupDrawer();
        setupViewPager();
    }

    @Nullable
    @Override
    protected BasePresenter initBasePresenter() {
        presenter = new MainPresenter(this);
        return presenter;
    }

    @Override
    public void onBackPressed() {
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        final int id = item.getItemId();

        if (id == R.id.nav_account) {
            AccountActivity.launch(this);
        } else if (id == R.id.nav_settings) {
            SettingsActivity.launch(this);
        }

        if (drawer != null) {
            drawer.closeDrawer(START);
        }
        return true;
    }

    /**
     * Setups {@link NavigationView}
     */
    private void setupDrawer() {
        drawer = find(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, setupToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Setups {@link Toolbar}
     *
     * @return Toolbar
     */
    @NonNull
    private Toolbar setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    /**
     * Setups {@link ViewPager} and {@link ViewPagerAdapter}
     */
    private void setupViewPager() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = find(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        pagerAdapter.addFragments(NowPlayingMoviesFragment.newInstance());
    }
}
