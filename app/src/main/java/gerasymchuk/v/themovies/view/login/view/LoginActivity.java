package gerasymchuk.v.themovies.view.login.view;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import gerasymchuk.v.themovies.view.main.view.MainActivity;
import gerasymchuk.v.themovies.R;
import gerasymchuk.v.themovies.view.AbsActivity;
import gerasymchuk.v.themovies.view.BasePresenter;
import gerasymchuk.v.themovies.view.login.LoginContract;
import gerasymchuk.v.themovies.view.login.presenter.LoginPresenter;

/**
 * Created by vitaliygerasymchuk on 11/17/17
 */

public class LoginActivity extends AbsActivity implements LoginContract.View {

    @Nullable
    private LoginContract.Presenter presenter;

    @NonNull
    private View rootView;

    @NonNull
    private TextInputEditText tedtUserName;

    @NonNull
    private TextInputEditText tedtPassword;

    @NonNull
    private TextInputLayout tilUserName;

    @NonNull
    private TextInputLayout tilPassword;

    @NonNull
    private AppCompatButton btnLogin;

    @NonNull
    private ImageView ivLogo;

    @NonNull
    private ProgressBar pbLogin;

    @Override
    protected int onRequestLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void beforeAddingLayout() {
        //no-op
    }

    @Override
    protected void onCreateActivity() {
        rootView = find(R.id.root);

        tedtUserName = find(R.id.tedt_username);
        tedtPassword = find(R.id.tedt_password);

        tilUserName = find(R.id.til_user_name);
        tilPassword = find(R.id.til_password);

        ivLogo = find(R.id.iv_logo);
        btnLogin = find(R.id.btn_login);
        pbLogin = find(R.id.pb_login);

        handleActionDoneEvent();
        handleLoginEvent();
    }

    /**
     * Initializes the presenter of current view
     *
     * @return LoginPresenter
     */
    @Nullable
    @Override
    protected BasePresenter initBasePresenter() {
        if (presenter == null) {
            presenter = new LoginPresenter(this);
        }
        return presenter;
    }

    /**
     * Gets the username from {@link TextInputLayout}
     *
     * @return String username
     */
    @NonNull
    @Override
    public String getUserName() {
        return tedtUserName.getText().toString();
    }

    /**
     * Gets the password from {@link TextInputLayout}
     *
     * @return String password
     */
    @NonNull
    @Override
    public String getPassword() {
        return tedtPassword.getText().toString();
    }

    /**
     * Shows username error on {@link TextInputLayout}
     *
     * @param error StringRes error
     */
    @Override
    public void showUserNameError(@StringRes int error) {
        tilUserName.setError(getString(error));
    }

    /**
     * Hides the username error
     */
    @Override
    public void hideUserNameError() {
        tilUserName.setError("");
    }

    /**
     * Shows password error on {@link TextInputLayout}
     *
     * @param error StringRes error
     */
    @Override
    public void showPasswordError(@StringRes int error) {
        tilPassword.setError(getString(error));
    }

    /**
     * Hides the password error
     */
    @Override
    public void hidePasswordError() {
        tilPassword.setError("");
    }

    /**
     * Shows the login progress
     */
    @Override
    public void showLoginProgress() {
        setUiEnabled(false);
        pbLogin.setVisibility(View.VISIBLE);
    }

    /**
     * Hides the login progress
     */
    @Override
    public void hideLoginProgress() {
        setUiEnabled(true);
        pbLogin.setVisibility(View.GONE);

    }

    /**
     * Shows {@link android.support.design.widget.Snackbar}
     *
     * @param msg StringRes msg
     */
    @Override
    public void showSnack(@StringRes int msg) {
        showTryAgainSnackBarIndefinite(rootView, msg, v -> presenterLogin());
    }

    /**
     * Shows {@link android.support.design.widget.Snackbar}
     *
     * @param msg String msg
     */
    @Override
    public void showSnack(@NonNull String msg) {
        showTryAgainSnackBarIndefinite(rootView, msg, v -> presenterLogin());
    }

    @Override
    public void startMainActivity() {
        MainActivity.launch(this);
        finish();
    }

    /**
     * Handles the IME action done event of the keyboard
     */
    private void handleActionDoneEvent() {
        tedtPassword.setOnEditorActionListener((v, actionId, event) -> {
            presenterLogin();
            return false;
        });
    }

    /**
     * Handles the login click event
     */
    private void handleLoginEvent() {
        btnLogin.setOnClickListener(v -> presenterLogin());
    }

    /**
     * Forces the presenter to start the login process
     */
    private void presenterLogin() {
        if (presenter != null) {
            hideKeyboard();
            presenter.login();
        }
    }

    /**
     * Enables or disables the controls
     *
     * @param enabled true if enabled
     */
    private void setUiEnabled(boolean enabled) {
        tilUserName.setEnabled(enabled);
        tilPassword.setEnabled(enabled);
        btnLogin.setEnabled(enabled);
        btnLogin.setText(enabled ? R.string.login_btn_title_inactive : R.string.login_btn_title_active);
        ivLogo.getDrawable().setColorFilter(enabled ? getCompatColor(R.color.colorAccent) : getCompatColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
    }
}
