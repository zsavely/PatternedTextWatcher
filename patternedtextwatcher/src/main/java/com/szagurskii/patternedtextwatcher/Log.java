package com.szagurskii.patternedtextwatcher;

final class Log {
  private static final String TAG = "PatternedTextWatcher";

  static boolean debug = false;

  static void d(Object methodName, Object value) {
    if (debug) {
      android.util.Log.d(TAG, String.format("%1$s = \"%2$s\"", methodName, value));
    }
  }

  static void w(Object methodName, Object value) {
    if (debug) {
      android.util.Log.w(TAG, String.format("%1$s:\n%2$s", methodName, value));
    }
  }

  static void setDebug(boolean debug) {
    Log.debug = debug;
  }

  private Log() {
  }
}
