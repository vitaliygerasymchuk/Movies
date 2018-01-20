package gerasymchuk.v.themovies.shared;


import android.support.annotation.Nullable;
import android.text.TextUtils;

public class Validator {
    public static String validString(@Nullable String string) {
        return TextUtils.isEmpty(string) ? "" : string;
    }
}
