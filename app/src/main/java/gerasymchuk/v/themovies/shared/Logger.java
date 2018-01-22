package gerasymchuk.v.themovies.shared;


import android.support.annotation.NonNull;
import android.util.Log;

import gerasymchuk.v.themovies.BuildConfig;


public class Logger {

    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void i(String text, String message) {
        if (DEBUG) {
            Log.i(text, message);
        }
    }

    public static void w(String text, String message) {
        if (DEBUG) {
            Log.w(text, message);
        }
    }

    public static void e(String text, String message) {
        e(text, message, null);
    }

    public static <EXC extends Throwable> void e(String text, String message, EXC e) {
        if (DEBUG) {
            if (e == null) Log.e(text, message);
            else Log.e(text, message, e);
        }
    }

    public static void d(String text, String message) {
        if (DEBUG) {
            Log.d(text, message);
        }
    }

    public static void d(@NonNull String tag, String text, Object... args) {
        if (DEBUG) {
            Log.d(tag, "[ " + Thread.currentThread().getId() + " ] " + String.format(text, args));
        }
    }

    public static void i(@NonNull String tag, String text, Object... args) {
        if (DEBUG) {
            Log.i(tag, "[ " + Thread.currentThread().getId() + " ] " + String.format(text, args));
        }
    }
}
