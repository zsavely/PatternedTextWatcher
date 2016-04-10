package com.szagurskii.patternedtextwatcher.utils;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

/**
 * @author Savelii Zagurskii
 */
public class EditTextUtils {
    private EditTextUtils() {
    }

    public static PatternedTextWatcher addTextChangedListener(EditText editText, String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(pattern);
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }

    public static void clearTextChangeListener(EditText editText, PatternedTextWatcher patternedTextWatcher) {
        editText.removeTextChangedListener(patternedTextWatcher);
    }

    public static void clearTextChangeListener(EditText editText, PatternedTextWatcher patternedTextWatcher, boolean clearText) {
        editText.removeTextChangedListener(patternedTextWatcher);
        if (clearText) {
            editText.getText().clear();
        } else {
            editText.setSelection(editText.length());
        }
    }
}
