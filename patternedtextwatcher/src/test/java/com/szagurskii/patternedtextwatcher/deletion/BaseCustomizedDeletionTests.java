package com.szagurskii.patternedtextwatcher.deletion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Test;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

public abstract class BaseCustomizedDeletionTests extends BaseDeletionTests {
  @Test
  public void deletion1() {
    appendClearOneSymbolAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "12345", PATTERN_1);
  }

  @Test
  public void deletion2() {
    appendClearOneSymbolAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "(123456", PATTERN_2);
  }

  @Test
  public void deletion3() {
    appendClearOneSymbolAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "(123456))))", PATTERN_3);
  }

  @Test
  public void multipleDeletion1() {
    PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);
    addText(editText, STRING_TO_BE_TYPED_LENGTH_NINE);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456))))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456)", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))456", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))45", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))4", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123))", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123)", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(123", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(12", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(1", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "(", PATTERN_4);
    backspace(STRING_TO_BE_TYPED_LENGTH_NINE, "", PATTERN_4);
    clearTextChangeListener(editText, patternedTextWatcher);
  }

  @Override
  protected PatternedTextWatcher init(EditText editText, String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .deleteExtraCharactersAutomatically(false)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
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
