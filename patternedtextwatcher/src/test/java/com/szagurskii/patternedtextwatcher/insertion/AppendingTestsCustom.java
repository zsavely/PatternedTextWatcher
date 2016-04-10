package com.szagurskii.patternedtextwatcher.insertion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AppendingTestsCustom extends BaseCustomizedAdditionTests {
    @Override
    public void multipleAddition() {
        addTextAndAssert(editText, "(2", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
        addTextAndAssert(editText, "(212", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
        addTextAndAssert(editText, "(212-234)", STRING_TO_BE_TYPED_LENGTH_EIGHT, PATTERN_1);
    }

    @Override
    void addTextAndAssert(EditText editText, String expected, String typed, String pattern) {
        editText.append(typed);
        assertText(editText, expected, typed, pattern);
    }
}
