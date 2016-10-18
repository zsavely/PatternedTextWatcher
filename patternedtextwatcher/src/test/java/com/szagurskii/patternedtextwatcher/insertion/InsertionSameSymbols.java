package com.szagurskii.patternedtextwatcher.insertion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.CustomRobolectricTestRunner;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(CustomRobolectricTestRunner.class)
public class InsertionSameSymbols extends BaseCustomizedAdditionTests {
  // https://github.com/zsavely/PatternedTextWatcher/issues/22
  @Test public void multipleSameSymbolsAddition() {
    final String pattern = "##.##.####";

    init(editText, pattern);

    appendTextAndAssert(editText, "1", "1", pattern);
    appendTextAndAssert(editText, "11.", "1", pattern);
    appendTextAndAssert(editText, "11.1", "1", pattern);
    appendTextAndAssert(editText, "11.12.", "2", pattern);
    appendTextAndAssert(editText, "11.12.1", "1", pattern);
    appendTextAndAssert(editText, "11.12.12", "2", pattern);
    appendTextAndAssert(editText, "11.12.123", "3", pattern);
    appendTextAndAssert(editText, "11.12.1234", "4", pattern);

    setTextAndAssert(editText, "11.12.12", "11.12.12", pattern);
  }

  @Override protected PatternedTextWatcher init(EditText editText, String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(pattern);
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
  }

  private void appendTextAndAssert(EditText editText, String expected, String typed, String pattern) {
    editText.append(typed);
    assertText(editText, expected, typed, pattern);
  }

  private void setTextAndAssert(EditText editText, String expected, String typed, String pattern) {
    editText.setText(typed);
    assertText(editText, expected, typed, pattern);
  }

  @Override void addTextAndAssert(EditText editText, String expected, String typed, String pattern) {
  }

  @Override public void basicSingleAddition() {
  }

  @Override public void basicMultipleAddition() {
  }

  @Override public void basicAdditionExactPattern() {
  }

  @Override public void basicAdditionMoreThanPattern() {
  }

  @Override public void basicAdditionLessThanPattern() {
  }

  @Override public void basicAdditionExactPatternSpecialCharacters() {
  }

  @Override public void basicAdditionMoreThanPatternSpecialCharacters() {
  }

  @Override public void basicAdditionLessThanPatternSpecialCharacters() {
  }

  @Override void fillPatternChecks() {
  }

  @Override public void multipleAddition() {
  }
}
