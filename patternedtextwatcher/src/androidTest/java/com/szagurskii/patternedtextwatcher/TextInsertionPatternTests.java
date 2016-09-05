package com.szagurskii.patternedtextwatcher;

import android.support.test.runner.AndroidJUnit4;

import com.szagurskii.patternedtextwatcher.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TextInsertionPatternTests extends BaseTests {
  @Test
  public void simpleTextChanged() {
    addTextChangedListener("(##-##)");
    Utils.insertTextAtOnceAndAssert("Espr", "(Es-pr)");
  }

  @Test
  public void basicTest() {
    addTextChangedListener("(###)");
    Utils.insertTextAtOnceAndAssert("Espresso", "(Esp)");
  }

  @Test
  public void basicTest2() {
    addTextChangedListener("(### ###)");
    Utils.insertTextAtOnceAndAssert("Espresso", "(Esp res)");
  }

  @Test
  public void basicTestWithMobilePhone() {
    addTextChangedListener("+# (###) ###-##-##");
    Utils.insertTextAtOnceAndAssert("71234567890", "+7 (123) 456-78-90");
  }

  @Test
  public void basicTest3() {
    addTextChangedListener("########");
    Utils.insertTextAtOnceAndAssert("Espresso", "Espresso");
  }

  @Test
  public void basicTest4() {
    addTextChangedListener("(### ### ##)");
    Utils.insertTextAtOnceAndAssert("Espresso", "(Esp res so)");
  }

  @Test
  public void basicTest5() {
    addTextChangedListener("### ### ##)");
    Utils.insertTextAtOnceAndAssert("Espresso", "Esp res so)");
  }

  private void addTextChangedListener(String pattern) {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
        .build();
    editText.addTextChangedListener(patternedTextWatcher);
  }
}
