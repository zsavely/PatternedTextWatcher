package com.szagurskii.rxtesting;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.szagurskii.rxtesting.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AppendingTests extends BaseAdditionTests {
    @Override
    public void basicMultipleAddition() {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, PATTERN_1);
        addTextAndAssert(editText, "(12", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
        addTextAndAssert(editText, "(121-2", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
        addTextAndAssert(editText, "(121-212)", STRING_TO_BE_TYPED_LENGTH_EIGHT, PATTERN_1);
        clearTextChangeListener(editText, patternedTextWatcher, true);
    }

    @Override
    void addTextAndAssert(EditText editText, String expected, String typed, String pattern) {
        editText.append(typed);
        assertText(editText, expected, typed, pattern);
    }
}
