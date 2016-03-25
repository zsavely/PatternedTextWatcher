package com.szagurskii.rxtesting;

/**
 * @author Savelii Zagurskii
 */
public class PatternCheck {
    private String pattern;
    private String input;
    private String expected;

    public PatternCheck(String pattern, String input, String expected) {
        this.pattern = pattern;
        this.input = input;
        this.expected = expected;
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
}
