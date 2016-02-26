package com.szagurskii.patternedtextwatcher.utils;

import android.util.Log;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

/**
 * @author Savelii Zagurskii
 */
public class LogUtils {
    public static final String TAG = PatternedTextWatcher.class.getSimpleName();

    public static void logd(Object methodName, Object value, boolean debug) {
        if (debug) {
            Log.d(TAG, String.format("%1$s = \"%2$s\"", methodName, value));
        }
    }

    public static void logw(Object methodName, Object value, boolean debug) {
        if (debug) {
            Log.w(TAG, String.format("%1$s:\n%2$s", methodName, value));
        }
    }
}
