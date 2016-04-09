package com.szagurskii.rxtesting.insertion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.rxtesting.base.BaseTests;
import com.szagurskii.rxtesting.models.PatternCheck;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseAdditionTests extends BaseTests {
    // 9 chars
    static final String PATTERN_1 = "(###-###)";

    final List<PatternCheck> PATTERN_CHECKS = new ArrayList<>();

    @Test
    @Override
    public void validateAddingAndRemovingTextWatcher() {
        appendAndCheck("", "");
    }

    @Test
    public void multipleAdditionPatternCheck() {
        fillPatternChecks();
        for (PatternCheck patternCheck : PATTERN_CHECKS) {
            appendAndCheck(patternCheck.getInput(),
                    patternCheck.getExpected(),
                    patternCheck.getExpectedFormattedString(),
                    patternCheck.getExpectedCleanString(),
                    patternCheck.getExpectedFullString(),
                    patternCheck.getPattern(),
                    true);
        }
    }

    abstract void fillPatternChecks();

    @After
    public void tearDown() {
        PATTERN_CHECKS.clear();
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended the string to append or to set.
     * @param expected the expected result after setting, appending.
     */
    void appendAndCheck(String appended, String expected) {
        appendAndCheck(appended, expected, null, null, null, PATTERN_1, true);
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended  the string to append or to set.
     * @param expected  the expected result after setting, appending.
     * @param clearText {@code true} to clear the text after asserting.
     */
    void appendAndCheck(String appended, String expected, boolean clearText) {
        appendAndCheck(appended, expected, null, null, null, PATTERN_1, clearText);
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended  the string to append or to set.
     * @param expected  the expected result after setting, appending.
     * @param pattern   the pattern used in the TextWatcher. Needed to logging.
     * @param clearText {@code true} to clear the text after asserting.
     */
    void appendAndCheck(String appended, String expected, String pattern, boolean clearText) {
        appendAndCheck(appended, expected, null, null, null, pattern, clearText);
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended  the string to append or to set.
     * @param expected  the expected result after setting, appending.
     * @param pattern   the pattern used in the TextWatcher. Needed to logging.
     * @param clearText {@code true} to clear the text after asserting.
     */
    void appendAndCheck(String appended, String expected,
                        String expectedFormattedString,
                        String expectedCleanString,
                        String expectedFullString,
                        String pattern, boolean clearText) {
        PatternedTextWatcher patternedTextWatcher = init(editText, pattern);
        addTextAndAssert(editText, expected, appended, pattern);
        if (expectedFormattedString != null) {
            assertText(appended, expectedFormattedString, patternedTextWatcher.getFormattedString(), pattern);
        }
        if (expectedCleanString != null) {
            assertText(appended, expectedCleanString, patternedTextWatcher.getCleanString(), pattern);
        }
        if (expectedFullString != null) {
            assertText(appended, expectedFullString, patternedTextWatcher.getFullString(), pattern);

        }
        clearTextChangeListener(editText, patternedTextWatcher, clearText);
    }

    /**
     * Add or set text to EditText and assert the expected result.
     *
     * @param editText current EdiText to watch.
     * @param expected expected string.
     * @param typed    what was typed.
     * @param pattern  the pattern which was used.
     */
    abstract void addTextAndAssert(EditText editText, String expected, String typed, String pattern);
}
