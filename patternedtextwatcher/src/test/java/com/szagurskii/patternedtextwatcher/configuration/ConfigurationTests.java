package com.szagurskii.patternedtextwatcher.configuration;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class ConfigurationTests {
  private static final String MESSAGE = "Condition was incorrect.";

  @Before
  public void setup() {
  }

  @Test
  public void defaultConfiguration() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher("#");

    assertTrue(MESSAGE, patternedTextWatcher.isDeletingExtra());
    assertTrue(MESSAGE, patternedTextWatcher.isEnabled());
    assertTrue(MESSAGE, patternedTextWatcher.isFillingExtra());
    assertTrue(MESSAGE, patternedTextWatcher.isRespectingPatternLength());
    assertTrue(MESSAGE, !patternedTextWatcher.isSavingInput());
    assertTrue(MESSAGE, !patternedTextWatcher.isDebug());
  }

  @Test
  public void customConfiguration() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("#")
        .fillExtraCharactersAutomatically(false)
        .deleteExtraCharactersAutomatically(false)
        .debug(true)
        .respectPatternLength(false)
        .saveAllInput(true)
        .build();

    assertTrue(MESSAGE, !patternedTextWatcher.isDeletingExtra());
    assertTrue(MESSAGE, patternedTextWatcher.isEnabled());
    assertTrue(MESSAGE, !patternedTextWatcher.isFillingExtra());
    assertTrue(MESSAGE, !patternedTextWatcher.isRespectingPatternLength());
    assertTrue(MESSAGE, patternedTextWatcher.isSavingInput());
    assertTrue(MESSAGE, patternedTextWatcher.isDebug());
  }

  @Test
  public void customConfiguration2() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("#")
        .build();
    patternedTextWatcher.setEnabled(false);

    assertTrue(MESSAGE, patternedTextWatcher.isDeletingExtra());
    assertTrue(MESSAGE, !patternedTextWatcher.isEnabled());
    assertTrue(MESSAGE, patternedTextWatcher.isFillingExtra());
    assertTrue(MESSAGE, patternedTextWatcher.isRespectingPatternLength());
    assertTrue(MESSAGE, !patternedTextWatcher.isSavingInput());
    assertTrue(MESSAGE, !patternedTextWatcher.isDebug());
  }
}
