package com.szagurskii.patternedtextwatcher;

import android.support.test.runner.AndroidJUnit4;

import com.szagurskii.patternedtextwatcher.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BasicDeletionTests extends BaseTests {
  @Test
  public void simpleTextChanged() {
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, STRING_TO_BE_TYPED);
  }

  @Test
  public void basicTest() {
    addTextChangedListener("(##-##))))#)))#");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr))))e)))s");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-pr))))e");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-pr");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-p");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(E");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("");
  }

  @Test
  public void multipleInsertsAndDeletionsTest() {
    addTextChangedListener("(##-##))))#)))#");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr))))e)))s");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-pr))))e");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-pr");

    Utils.typeTextAndAssert("e", "(Es-pr))))e)))");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-pr");

    Utils.typeTextAndAssert("e", "(Es-pr))))e)))");

    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es-p");

    Utils.typeTextAndAssert("r", "(Es-pr))))");

    Utils.deleteOneCharacter();
    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(Es");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("(E");

    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("");

    Utils.typeTextAndAssert("E", "(E");
    Utils.deleteOneCharacter();
    Utils.assertExpectedOutput("");
  }

  private void addTextChangedListener(String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
  }
}


