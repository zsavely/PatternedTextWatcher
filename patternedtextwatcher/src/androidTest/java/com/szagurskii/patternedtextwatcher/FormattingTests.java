package com.szagurskii.patternedtextwatcher;

import android.support.test.runner.AndroidJUnit4;

import com.szagurskii.patternedtextwatcher.utils.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FormattingTests extends BaseTests {
  @Test
  public void aSimpleTextChanged() {
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, STRING_TO_BE_TYPED);
  }

  @Test
  public void basicTest() {
    PatternedTextWatcher patternedTextWatcher = addTextChangedListener("(##-##))))#)))#");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr))))e)))s");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(Es-pr))))e)))s");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "(Es-pr))))e)))s");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "Espres");
  }

  @Test
  public void whiteSpacesTest() {
    PatternedTextWatcher patternedTextWatcher = addTextChangedListener("(##-##)          #");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr)          e");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(Es-pr)          e");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "(Es-pr)          e");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "Espre");
  }

  @Test
  public void multipleCharacterPatternTest() {
    PatternedTextWatcher patternedTextWatcher = addTextChangedListener("((#(#)-(#)#)))) !!!");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "((E(s)-(p)r)))) !!!");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "((E(s)-(p)r)))) !!!");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "((E(s)-(p)r)))) !!!");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "Espr");
  }

  @Test
  public void additionalParametersTest() {
    PatternedTextWatcher patternedTextWatcher = addTextChangedListenerWithSavingCleanString("" +
        "(##-##)");

    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(sp-es)");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(sp-es)");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Espress");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "spes");
  }

  @Test
  public void additionalParametersWithAutomaticDeletionTest() {
    PatternedTextWatcher patternedTextWatcher = addTextChangedListenerWithSavingCleanString("" +
        "(##-##)");

    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(sp-es)");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(sp-es)");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Espress");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "spes");

    Utils.deleteOneCharacter();

    Utils.assertExpectedOutput("(sp-e");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(sp-e");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Espre");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "spe");

    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();

    Utils.assertExpectedOutput("");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "");

    Utils.typeTextAndAssert("Is this right?", "(s -hi)");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(s -hi)");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Is this");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "s hi");

    Utils.deleteOneCharacter();

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(s -h");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Is th");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "s h");

  }

  @Test
  public void additionalParametersWithoutAutomaticDeletionTest() {
    PatternedTextWatcher patternedTextWatcher =
        addTextChangedListenerWithSavingCleanStringWithoutAutoDelete("(##-##)");

    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(sp-es)");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(sp-es)");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Espress");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "spes");

    Utils.deleteOneCharacter();

    Utils.assertExpectedOutput("(sp-es");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(sp-es");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Espres");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "spes");

    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();

    Utils.assertExpectedOutput("");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "");

    Utils.typeTextAndAssert("Is this right?", "(s -hi)");

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(s -hi)");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Is this");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "s hi");

    Utils.deleteOneCharacter();

    Assert.assertEquals(patternedTextWatcher.getFormattedString(), "(s -hi");
    Assert.assertEquals(patternedTextWatcher.getFullString(), "Is thi");
    Assert.assertEquals(patternedTextWatcher.getCleanString(), "s hi");

  }

  private PatternedTextWatcher addTextChangedListener(String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
  }

  private PatternedTextWatcher addTextChangedListenerWithSavingCleanString(String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .fillExtraCharactersAutomatically(false)
        .saveAllInput(true)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
  }

  private PatternedTextWatcher addTextChangedListenerWithSavingCleanStringWithoutAutoDelete(
      String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .fillExtraCharactersAutomatically(false)
        .deleteExtraCharactersAutomatically(false)
        .saveAllInput(true)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
    return patternedTextWatcher;
  }
}


