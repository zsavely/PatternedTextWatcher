package com.szagurskii.patternedtextwatcher.deletion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.patternedtextwatcher.base.BaseTests;

import org.junit.Test;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

public abstract class BaseDeletionTests extends BaseTests {
  // 9 chars
  static final String INSERT = "123456789";
  // 6 chars
  static final String PATTERN_1 = "######";
  // 8 chars
  static final String PATTERN_2 = "(######)";
  // 12 chars
  static final String PATTERN_3 = "(######)))))";
  // 15 chars
  static final String PATTERN_4 = "(###)))###)))))";

  @Test
  public abstract void multipleAddingAndDeletion1();

  void appendClearOneSymbolAndCheck(String appended, String expected, String pattern) {
    PatternedTextWatcher patternedTextWatcher = init(editText, pattern);
    addText(editText, appended);
    backspace(appended, expected, pattern);
    clearTextChangeListener(editText, patternedTextWatcher);
  }

  @Override
  protected PatternedTextWatcher init(EditText editText, String pattern) {
    return addTextChangedListener(editText, pattern);
  }

  /**
   * Add text to the EditText via
   * {@link EditText#setText(char[], int, int)} or {@link EditText#append(CharSequence)}.
   *
   * @param editText EditText to which the text will be appended.
   * @param value    the value th append.
   */
  abstract void addText(EditText editText, String value);
}
