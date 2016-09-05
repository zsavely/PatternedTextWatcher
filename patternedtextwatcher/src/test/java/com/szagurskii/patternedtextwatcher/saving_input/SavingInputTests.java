package com.szagurskii.patternedtextwatcher.saving_input;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.CustomRobolectricTestRunner;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(CustomRobolectricTestRunner.class)
public class SavingInputTests extends BaseSavingInputTests {

  @Override
  public void shouldSaveOnMultipleAdding() {
    PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    clearTextChangeListener(editText, patternedTextWatcher, true);
  }

  @Override
  public void shouldSaveOnAddingAndBackspace() {
    PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    backspace(ESPRESSO, ESPRESSO.substring(0, ESPRESSO.length() - 1), BASIC_PATTERN);
    clearTextChangeListener(editText, patternedTextWatcher, true);
  }

  @Override
  public void shouldSaveOnAddingAndClearing() {
    PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    editText.getText().clear();
    assertText(ESPRESSO, "", patternedTextWatcher.getFullString(), BASIC_PATTERN);
    clearTextChangeListener(editText, patternedTextWatcher, true);
  }

  @Override
  public void shouldSaveOnAddingClearingAndBackspace() {
    PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    editText.getText().clear();
    assertText(ESPRESSO, "", patternedTextWatcher.getFullString(), BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    backspace(ESPRESSO, ESPRESSO.substring(0, ESPRESSO.length() - 1), BASIC_PATTERN);
    clearTextChangeListener(editText, patternedTextWatcher, true);
  }

  @Override
  public void shouldSaveOnAddingBackspaceAndClearing() {
    PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    addText(ESPRESSO);
    assertText(ESPRESSO, ESPRESSO + E + s + p, patternedTextWatcher.getFullString(), BASIC_PATTERN);
    backspace(ESPRESSO, (ESPRESSO + E + s + p).substring(0, (ESPRESSO + E + s + p).length() - 1),
        BASIC_PATTERN);
    editText.getText().clear();
    assertText(ESPRESSO, "", patternedTextWatcher.getFullString(), BASIC_PATTERN);
    clearTextChangeListener(editText, patternedTextWatcher, true);
  }

  @Override void addText(String value) {
    editText.append(value);
  }

  @Override
  protected PatternedTextWatcher init(EditText editText, String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .fillExtraCharactersAutomatically(false)
        .saveAllInput(true)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
  }
}
