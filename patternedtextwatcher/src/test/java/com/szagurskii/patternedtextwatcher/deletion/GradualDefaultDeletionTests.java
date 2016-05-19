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
public class GradualDefaultDeletionTests extends BaseDefaultDeletionTests {
  @Override
  public void multipleAddingAndDeletion1() {
    PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_FIVE);
    backspace(STRING_TO_BE_TYPED_LENGTH_FIVE, "(123)))4", PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_TWO);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))41", PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_TWO);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))41", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123)))4", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(123", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_TWO, "(12", PATTERN_4);

    addText(editText, STRING_TO_BE_TYPED_LENGTH_ONE);
    backspace(STRING_TO_BE_TYPED_LENGTH_ONE, "(12", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_ONE, "(1", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_ONE, "", PATTERN_4);

    clearTextChangeListener(editText, patternedTextWatcher);
  }

  @Override void addText(EditText editText, String value) {
    editText.append(value);
  }
}
