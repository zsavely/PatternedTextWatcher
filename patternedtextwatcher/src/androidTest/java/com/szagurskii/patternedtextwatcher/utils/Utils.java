package com.szagurskii.patternedtextwatcher.utils;

import android.support.test.espresso.action.ViewActions;
import android.view.KeyEvent;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class Utils {
  /**
   * Type inserted strings by character, like in real life.
   *
   * @param input          input string which needs to be typed.
   * @param expectedOutput expected output from this input which will be checked.
   */
  public static void typeTextAndAssert(String input, String expectedOutput) {
    // Type text.
    onView(withId(android.R.id.primary))
        .perform(typeText(input), closeSoftKeyboard());

    // Check that the text was changed.
    assertExpectedOutput(expectedOutput);
  }

  /**
   * Insert a text into a view.
   *
   * @param input          input string which needs to be typed.
   * @param expectedOutput expected output from this input which will be checked.
   */
  public static void insertTextAtOnceAndAssert(String input, String expectedOutput) {
    // Type input.
    onView(withId(android.R.id.primary))
        .perform(ViewActions.replaceText(input));
    assertExpectedOutput(expectedOutput);

  }

  /**
   * Assert expected output.
   *
   * @param expectedOutput the string to assert.
   */
  public static void assertExpectedOutput(String expectedOutput) {
    // Check that the input was changed.
    onView(withId(android.R.id.primary))
        .check(matches(withText(expectedOutput)));
  }

  /**
   * Delete one character from the edit text.
   */
  public static void deleteOneCharacter() {
    onView(withId(android.R.id.primary)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_DEL));
  }
}
