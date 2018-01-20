package gerasymchuk.v.themovies.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Set;

import gerasymchuk.v.themovies.App;


public class Pref {

    private static final String TAG = "Pref";

    private static final String PREF_NAME = "movies_prefs";

    private static final String ARG_SESSION_ID = "session_id";

    public static void saveSessionId(@NonNull String sessionId) {
        setString(ARG_SESSION_ID, sessionId);
    }

    @NonNull
    public static String getSessionId() {
        return getString(ARG_SESSION_ID);
    }

    @NonNull
    private static final SharedPreferences
            sPref = App.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

    @NonNull
    private static SharedPreferences.Editor edit = sPref.edit();

    public static void clear() {
        edit.clear().commit();
    }

    //PREFERENCES WRITE/READ START REGION

    public static void setStringSet(String key, Set<String> value) {
        Logger.i(TAG, "setString " + key + "=" + value);

        edit.putStringSet(key, value);
        edit.commit();
    }

    public static void setString(String key, String value) {
        Logger.i(TAG, "setString " + key + "=" + value);
        edit.putString(key, value);
        edit.commit();
    }

    public static void setLong(String key, long value) {
        Logger.i(TAG, "setLong " + key + "=" + value);
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setBoolean(String key, boolean value) {
        Logger.i(TAG, "setBoolean " + key + "=" + value);
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void setInt(String key, int value) {
        Logger.i(TAG, "setInt " + key + "=" + value);
        edit.putInt(key, value);
        edit.commit();
    }

    public static void setPrefFloat(String key, float value) {
        Logger.i(TAG, "setPrefFloat " + key + "=" + value);
        edit.putFloat(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        return sPref.getString(key, "");
    }

    public static String getString(String key, String def) {
        return sPref.getString(key, def);
    }

    public static Set<String> getStringSet(String key) {
        return sPref.getStringSet(key, null);
    }

    public static boolean getBoolean(String key) {
        return sPref.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sPref.getBoolean(key, defValue);
    }

    public static long getLong(String key) {
        return sPref.getLong(key, -1);
    }

    public static int getInt(String key) {
        return sPref.getInt(key, -1);
    }

    public static int getInt(String key, int def) {
        return sPref.getInt(key, def);
    }

    public static float getFloat(String key) {
        return sPref.getFloat(key, -1);
    }

    //PREFERENCES WRITE/READ END REGION
}
