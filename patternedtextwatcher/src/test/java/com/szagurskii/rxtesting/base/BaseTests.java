package com.szagurskii.rxtesting.base;

import android.app.Activity;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;
import static junit.framework.Assert.assertTrue;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseTests {
    // 1 char
    protected static final String STRING_TO_BE_TYPED_LENGTH_ONE = "1";
    // 2 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_TWO = "12";
    // 3 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_THREE = "123";
    // 4 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_FOUR = "1234";
    // 5 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_FIVE = "12345";
    // 6 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_SIX = "123456";
    // 7 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_SEVEN = "1234567";
    // 8 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_EIGHT = "12345678";
    // 9 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_NINE = "123456789";
    // 10 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_TEN = "1234567890";
    // 11 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_ELEVEN = "12345678901";
    // 12 chars
    protected static final String STRING_TO_BE_TYPED_LENGTH_TWELVE = "123456789012";

    static final String EDITTEXT_ERROR_STRING = "EditText contains incorrect text. " +
            "{Appended: \'%1$s\'; Expected: \'%2$s\'; Actual: \'%3$s\'; Pattern: \'%4$s\'.}";

    Activity activity;
    protected EditText editText;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        editText = new EditText(activity);
    }

    @Test
    public void validateAddingAndRemovingTextWatcher() {
        PatternedTextWatcher patternedTextWatcher = init(editText, "(#)");
        clearTextChangeListener(editText, patternedTextWatcher);
    }

    /**
     * Assert the specified EditText with the expected string.
     *
     * @param editText EditText to assert.
     * @param expected expected string to assert.
     * @param typed    what was inserted/appended.
     * @param pattern  the pattern which was user (needed for logging).
     */
    protected static void assertText(EditText editText, String expected, String typed, String pattern) {
        assertTrue(String.format(EDITTEXT_ERROR_STRING, typed, expected,
                editText.getText().toString(), pattern),
                editText.getText().toString().equals(expected));
    }

    /**
     * Assert the specified string with an expected string.
     *
     * @param expected expected string to assert.
     * @param typed    what was inserted/appended.
     * @param pattern  the pattern which was user (needed for logging).
     */
    protected static void assertText(String typed, String expected, String actual, String pattern) {
        assertTrue(String.format(EDITTEXT_ERROR_STRING, typed, expected, actual, pattern),
                expected.equals(actual));
    }

    /**
     * Initialize EditText with a special {@link PatternedTextWatcher}.
     *
     * @param editText EditText to add a TextWatcher.
     * @param pattern  Pattern to supply to TextWatcher.
     * @return initialized {@link PatternedTextWatcher}.
     */
    protected abstract PatternedTextWatcher init(EditText editText, String pattern);
}
