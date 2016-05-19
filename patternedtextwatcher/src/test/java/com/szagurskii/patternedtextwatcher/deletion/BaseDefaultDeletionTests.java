package com.szagurskii.patternedtextwatcher.deletion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Test;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseDefaultDeletionTests extends BaseDeletionTests {
  @Test
  public void deletion1() {
    appendClearOneSymbolAndCheck(INSERT, "12345", PATTERN_1);
  }

  @Test
  public void deletion2() {
    appendClearOneSymbolAndCheck(INSERT, "(12345", PATTERN_2);
  }

  @Test
  public void deletion3() {
    appendClearOneSymbolAndCheck(INSERT, "(12345", PATTERN_3);
  }

  @Test
  public void multipleDeletion1() {
    PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);
    addText(editText, INSERT);
    backspace(INSERT, "(123)))45", PATTERN_4);
    backspace(INSERT, "(123)))4", PATTERN_4);
    backspace(INSERT, "(123", PATTERN_4);
    backspace(INSERT, "(12", PATTERN_4);
    backspace(INSERT, "(1", PATTERN_4);
    backspace(INSERT, "", PATTERN_4);
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
