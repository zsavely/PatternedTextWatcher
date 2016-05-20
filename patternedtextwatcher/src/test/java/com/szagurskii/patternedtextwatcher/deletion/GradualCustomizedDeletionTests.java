package com.szagurskii.patternedtextwatcher.deletion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.CustomRobolectricTestRunner;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(CustomRobolectricTestRunner.class)
public class GradualCustomizedDeletionTests extends BaseCustomizedDeletionTests {
  @Override
  public void multipleAddingAndDeletion1() {
    PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);
    addText(editText, STRING_TO_BE_TYPED_LENGTH_FIVE);
    backspace(STRING_TO_BE_TYPED_LENGTH_FIVE, "(123)))4", PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_TWO);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))412))))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))412)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))412))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))412)", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))412", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))41", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))4", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123))", PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_THREE);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))123))))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))123)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))123))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))123)", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))123", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))12", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))1", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123)", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(123", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(12", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(1", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "(", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_THREE, "", PATTERN_4);

    clearTextChangeListener(editText, patternedTextWatcher);
  }

  @Override void addText(EditText editText, String value) {
    editText.append(value);
  }
}
