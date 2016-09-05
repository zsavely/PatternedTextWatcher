package com.szagurskii.patternedtextwatcher.utils;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class EditTextUtils {
  private EditTextUtils() {
  }

  public static PatternedTextWatcher addTextChangedListener(EditText editText, String pattern) {
    PatternedTextWatcher watcher = new PatternedTextWatcher(pattern);
    editText.addTextChangedListener(watcher);
    return watcher;
  }

  public static void clearTextChangeListener(EditText editText, PatternedTextWatcher watcher) {
    editText.removeTextChangedListener(watcher);
  }

  public static void clearTextChangeListener(EditText editText, PatternedTextWatcher watcher,
      boolean clearText) {
    editText.removeTextChangedListener(watcher);
    if (clearText) {
      editText.getText().clear();
    } else {
      editText.setSelection(editText.length());
    }
  }
}
