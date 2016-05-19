package com.szagurskii.patternedtextwatcher.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.patternedtextwatcher.sample.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Savelii Zagurskii
 */
@RunWith(AndroidJUnit4.class)
public class BasicAdditionTests {
  private static final String STRING_TO_BE_TYPED = "Espresso";

  @Rule
  public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<>(TestActivity.class);

  private EditText editText;

  @Before
  public void setUp() throws Exception {
    editText = (EditText) mActivityRule.getActivity().findViewById(R.id.et_sample);
  }

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


