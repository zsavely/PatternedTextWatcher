package com.szagurskii.rxtesting;

import android.app.Activity;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static com.szagurskii.rxtesting.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;
import static junit.framework.Assert.assertTrue;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseTests {

    static final String EDITTEXT_ERROR_STRING = "EditText contains incorrect text. " +
            "{Appended: \'%1$s\'; Expected: \'%2$s\'; Actual: \'%3$s\'; Pattern: \'%4$s\'.}";

    Activity activity;
    EditText editText;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        editText = new EditText(activity);
    }

    @Test
    public void validateAddingAndRemovingTextWatcher() {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, "(#)");
        clearTextChangeListener(editText, patternedTextWatcher);
    }

    static void assertText(EditText editText, String expected, String typed, String pattern) {
        assertTrue(String.format(EDITTEXT_ERROR_STRING, typed, expected,
                        editText.getText().toString(), pattern),
                editText.getText().toString().equals(expected));
    }
}
