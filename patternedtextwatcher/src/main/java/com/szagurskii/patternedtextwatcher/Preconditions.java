package com.szagurskii.patternedtextwatcher;

/**
 * @author Savelii Zagurskii
 */
public class Preconditions {
  static void checkPatternInput(String pattern) {
    if (pattern == null) {
      throw new NullPointerException("Pattern can't be null.");
    }
    if (pattern.isEmpty()) {
      throw new IllegalArgumentException("Pattern can't be empty.");
    }
  }

  static void checkInput(String specialChar, boolean saveInput, boolean fillExtraChars) {
    if (specialChar == null) {
      throw new NullPointerException("Special char can't be null.");
    }
    if (specialChar.isEmpty()) {
      throw new IllegalArgumentException("Special char can't be empty.");
    }
    if (specialChar.length() > 1) {
      throw new IllegalArgumentException("Special char must be length = 1.");
    }
    if (saveInput && fillExtraChars) {
      throw new IllegalArgumentException("It is impossible to save input when the characters are " +
          "filled automatically instead of characters.");
    }
  }

  static void checkPatternInInput(String pattern, String specialChar) {
    if (!pattern.contains(specialChar)) {
      throw new IllegalStateException("There should be at least one special character in the " +
          "pattern string.");
    }
  }
}
