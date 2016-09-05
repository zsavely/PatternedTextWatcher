package com.szagurskii.patternedtextwatcher.models;

public class PatternCheck {
  private String pattern;

  private String input;
  private String expected;

  private String expectedFormattedString;
  private String expectedCleanString;
  private String expectedFullString;

  public PatternCheck(String pattern, String input, String expected) {
    this.pattern = pattern;
    this.input = input;
    this.expected = expected;
  }

  public PatternCheck(String pattern, String expected) {
    this.pattern = pattern;
    this.expected = expected;
  }

  public PatternCheck(String pattern, String input, String expected,
      String expectedFormattedString, String expectedCleanString, String expectedFullString) {
    this.pattern = pattern;
    this.input = input;
    this.expected = expected;
    this.expectedFormattedString = expectedFormattedString;
    this.expectedCleanString = expectedCleanString;
    this.expectedFullString = expectedFullString;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getExpected() {
    return expected;
  }

  public void setExpected(String expected) {
    this.expected = expected;
  }

  public String getExpectedFormattedString() {
    return expectedFormattedString;
  }

  public void setExpectedFormattedString(String expectedFormattedString) {
    this.expectedFormattedString = expectedFormattedString;
  }

  public String getExpectedCleanString() {
    return expectedCleanString;
  }

  public void setExpectedCleanString(String expectedCleanString) {
    this.expectedCleanString = expectedCleanString;
  }

  public String getExpectedFullString() {
    return expectedFullString;
  }

  public void setExpectedFullString(String expectedFullString) {
    this.expectedFullString = expectedFullString;
  }
}
