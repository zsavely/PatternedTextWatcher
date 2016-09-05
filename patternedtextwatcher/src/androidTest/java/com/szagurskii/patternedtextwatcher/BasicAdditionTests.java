package com.szagurskii.patternedtextwatcher;

import android.support.test.runner.AndroidJUnit4;

import com.szagurskii.patternedtextwatcher.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BasicAdditionTests extends BaseTests {

  @Test
  public void simpleTextChanged() {
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, STRING_TO_BE_TYPED);
  }

  @Test
  public void basicTest() {
    addTextChangedListener("(###)");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Esp)");
  }

  @Test
  public void basicTest2() {
    addTextChangedListener("(### ###)");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Esp res)");
  }

  @Test
  public void basicTest3() {
    addTextChangedListener("########");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "Espresso");
  }

  @Test
  public void basicTest4() {
    addTextChangedListener("(### ### ##)");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Esp res so)");
  }

  @Test
  public void basicTest5() {
    addTextChangedListener("### ### ##)");
    Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "Esp res so)");
  }

  private void addTextChangedListener(String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
  }
}


