package com.szagurskii.patternedtextwatcher.insertion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.CustomRobolectricTestRunner;

import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(CustomRobolectricTestRunner.class)
public class InsertingTestsCustom extends BaseCustomizedAdditionTests {
  @Override
  public void multipleAddition() {
    addTextAndAssert(editText, "(2", STRING_TO_BE_TYPED_LENGTH_TWO, PATTERN_1);
    addTextAndAssert(editText, "(23", STRING_TO_BE_TYPED_LENGTH_THREE, PATTERN_1);
    addTextAndAssert(editText, "(234-678", STRING_TO_BE_TYPED_LENGTH_EIGHT, PATTERN_1);
  }

  @Override void addTextAndAssert(EditText editText, String expected, String typed,
      String pattern) {
    editText.setText(typed);
    assertText(editText, expected, typed, pattern);
  }
}
