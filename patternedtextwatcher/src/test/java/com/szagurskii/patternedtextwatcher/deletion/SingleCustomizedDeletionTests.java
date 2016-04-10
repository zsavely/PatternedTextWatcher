package com.szagurskii.patternedtextwatcher.deletion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class SingleCustomizedDeletionTests extends BaseCustomizedDeletionTests {
    @Override
    public void multipleAddingAndDeletion1() {
        PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);
        addText(editText, STRING_TO_BE_TYPED_LENGTH_NINE);
        backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456))))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_EIGHT);
        backspace(STRING_TO_BE_TYPED_LENGTH_EIGHT, "(123)))456))))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_SEVEN);
        backspace(STRING_TO_BE_TYPED_LENGTH_SEVEN, "(123)))456))))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_SIX);
        backspace(STRING_TO_BE_TYPED_LENGTH_SIX, "(123)))456))))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_FIVE);
        backspace(STRING_TO_BE_TYPED_LENGTH_FIVE, "(123)))4", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_FOUR);
        backspace(STRING_TO_BE_TYPED_LENGTH_FOUR, "(123)))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_THREE);
        backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123))", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_TWO);
        backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(1", PATTERN_4);

        addText(editText, STRING_TO_BE_TYPED_LENGTH_ONE);
        backspace(STRING_TO_BE_TYPED_LENGTH_ONE, "(", PATTERN_4);
        backspace(STRING_TO_BE_TYPED_LENGTH_ONE, "", PATTERN_4);

        clearTextChangeListener(editText, patternedTextWatcher);
    }

    @Override
    void addText(EditText editText, String value) {
        editText.setText(value);
    }
}
