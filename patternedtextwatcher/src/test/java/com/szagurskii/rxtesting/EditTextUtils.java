package com.szagurskii.rxtesting;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

/**
 * @author Savelii Zagurskii
 */
public class EditTextUtils {
    private EditTextUtils() {
    }

    static PatternedTextWatcher addTextChangedListener(EditText editText, String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(pattern);
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }

    static void clearTextChangeListener(EditText editText, PatternedTextWatcher patternedTextWatcher, boolean clearText) {
        editText.removeTextChangedListener(patternedTextWatcher);
        if (clearText) {
            editText.getText().clear();
        } else {
            editText.setSelection(editText.length());
        }
    }
}
