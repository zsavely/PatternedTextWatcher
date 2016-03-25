package com.szagurskii.rxtesting;

import android.app.Activity;
import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AdditionTests {

    // 11 chars
    private static final String STRING_TO_BE_TYPED_MORE_THAN_PATTERN = "12345678912";

    // 8 chars
    private static final String STRING_TO_BE_TYPED_LESS_THAN_PATTERN = "12345678";

    // 1 char
    private static final String STRING_TO_BE_TYPED_LENGTH_ONE = "1";

    // 9 chars.
    private static final String PATTERN = "(###-###)";

    private static final String EDITTEXT_ERROR_STRING = "EditText contains incorrect text.";

    private Activity activity;
    private EditText editText;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        editText = new EditText(activity);
    }

    @Test
    public void validateAddingAndRemovingTextWatcher() {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener();
        clearTextChangeListener(patternedTextWatcher);
    }

    @Test
    public void basicSingleAddition() {
        basicTest(STRING_TO_BE_TYPED_LENGTH_ONE, "(1");
    }

    @Test
    public void basicAdditionMoreThanPattern() {
        basicTest(STRING_TO_BE_TYPED_MORE_THAN_PATTERN, "(123-456)");
    }

    @Test
    public void basicAdditionLessThanPattern() {
        basicTest(STRING_TO_BE_TYPED_LESS_THAN_PATTERN, "(123-456)");
    }

    private void basicTest(String appended, String expected) {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener();
        editText.setText(appended);
        assertTrue(EDITTEXT_ERROR_STRING, editText.getText().toString().equals(expected));
        clearTextChangeListener(patternedTextWatcher);
    }

    private PatternedTextWatcher addTextChangedListener() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(PATTERN);
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }

    private void clearTextChangeListener(PatternedTextWatcher patternedTextWatcher) {
        editText.removeTextChangedListener(patternedTextWatcher);
    }
}
