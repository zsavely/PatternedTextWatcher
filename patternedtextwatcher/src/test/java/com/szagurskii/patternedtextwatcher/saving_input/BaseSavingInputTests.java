package com.szagurskii.patternedtextwatcher.saving_input;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.patternedtextwatcher.base.BaseTests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

public abstract class BaseSavingInputTests extends BaseTests {

  static final String BASIC_PATTERN = "###########";

  static final String ESPRESSO = "Espresso";
  static final String E = "E";
  static final String s = "s";
  static final String p = "p";
  static final String r = "r";
  static final String e = "e";
  static final String o = "o";

  final List<String> PATTERNS = new ArrayList<>();

  @Before
  public void setUp() {
    PATTERNS.add("######");
    PATTERNS.add("(######");
    PATTERNS.add("######)");
    PATTERNS.add("###-###");
    PATTERNS.add("(######)");
    PATTERNS.add("(###-###)");
    PATTERNS.add("(-######)");
    PATTERNS.add("(######-)");
    PATTERNS.add("(-######-)");
    PATTERNS.add("(-#-#-#-#-#-#-)");
    PATTERNS.add("(-#-#-#-#-#-#-)))))))))))))))))))))");
    PATTERNS.add("+# (###) ###-##-##");
    PATTERNS.add("+# (###) ###-##-##");
    PATTERNS.add(")))###(((###");
    PATTERNS.add(")))######(((");
    PATTERNS.add("###)))(((###");
    PATTERNS.add("###)))###(((");
    PATTERNS.add("(((###)))###");
    PATTERNS.add("(((######)))");
    PATTERNS.add("###((()))###");
    PATTERNS.add("###(((###)))");
  }

  @Test
  public void shouldSaveOnSingleAdding() {
    for (String pattern : PATTERNS) {
      PatternedTextWatcher patternedTextWatcher = init(editText, pattern);
      addText(E);
      assertText(E, E, patternedTextWatcher.getFullString(), pattern);
      addText(s);
      assertText(E + s, E + s, patternedTextWatcher.getFullString(), pattern);
      addText(p);
      assertText(E + s + p, E + s + p, patternedTextWatcher.getFullString(), pattern);
      addText(r);
      assertText(E + s + p + r, E + s + p + r, patternedTextWatcher.getFullString(), pattern);
      addText(e);
      assertText(E + s + p + r + e, E + s + p + r + e, patternedTextWatcher.getFullString(),
          pattern);
      addText(s);
      assertText(E + s + p + r + e + s, E + s + p + r + e + s,
          patternedTextWatcher.getFullString(), pattern);
      addText(s);
      assertText(E + s + p + r + e + s + s, E + s + p + r + e + s + s,
          patternedTextWatcher.getFullString(), pattern);
      addText(o);
      assertText(E + s + p + r + e + s + s + o, E + s + p + r + e + s + s + o,
          patternedTextWatcher.getFullString(), pattern);
      editText.getText().clear();
      assertText("", "", patternedTextWatcher.getFullString(), pattern);
      clearTextChangeListener(editText, patternedTextWatcher);
    }
  }

  @Test
  public abstract void shouldSaveOnMultipleAdding();

  @Test
  public abstract void shouldSaveOnAddingAndBackspace();

  @Test
  public abstract void shouldSaveOnAddingAndClearing();

  @Test
  public abstract void shouldSaveOnAddingClearingAndBackspace();

  @Test
  public abstract void shouldSaveOnAddingBackspaceAndClearing();

  abstract void addText(String value);

  protected void assertText(String typed, String expected, String actual, String pattern) {
    // Here we strip the possible unnecessary expected string because we know that we should
    // assert string that is equal to the pattern in length.
    super.assertText(typed,
        pattern.length() < expected.length() ? expected.substring(0, pattern.length()) : expected,
        actual, pattern);
  }
}
