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
    // 1 char
    static final String STRING_TO_BE_TYPED_LENGTH_ONE = "1";
    // 2 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TWO = "12";
    // 3 chars
    static final String STRING_TO_BE_TYPED_LENGTH_THREE = "123";
    // 4 chars
    static final String STRING_TO_BE_TYPED_LENGTH_FOUR = "1234";
    // 5 chars
    static final String STRING_TO_BE_TYPED_LENGTH_FIVE = "12345";
    // 6 chars
    static final String STRING_TO_BE_TYPED_LENGTH_SIX = "123456";
    // 7 chars
    static final String STRING_TO_BE_TYPED_LENGTH_SEVEN = "1234567";
    // 8 chars
    static final String STRING_TO_BE_TYPED_LENGTH_EIGHT = "12345678";
    // 9 chars
    static final String STRING_TO_BE_TYPED_LENGTH_NINE = "123456789";
    // 10 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TEN = "1234567890";
    // 11 chars
    static final String STRING_TO_BE_TYPED_LENGTH_ELEVEN = "12345678901";
    // 12 chars
    static final String STRING_TO_BE_TYPED_LENGTH_TWELVE = "123456789012";

    // 9 chars
    static final String PATTERN_1 = "(###-###)";

    static final List<PatternCheck> PATTERN_CHECKS = new ArrayList<>();

    @Test
    @Override
    public void validateAddingAndRemovingTextWatcher() {
        appendAndCheck("", "");
    }

    @Test
    public void multipleAdditionPatternCheck() {
        for (PatternCheck patternCheck : PATTERN_CHECKS)
            appendAndCheck(patternCheck.getInput(), patternCheck.getExpected(), patternCheck.getPattern(), true);
    }

    @After
    public void tearDown() throws Exception {
        PATTERN_CHECKS.clear();
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended the string to append or to set.
     * @param expected the expected result after setting, appending.
     */
    void appendAndCheck(String appended, String expected) {
        appendAndCheck(appended, expected, PATTERN_1, true);
    }

    /**
     * Append the string and assert the expected result.
     *
     * @param appended  the string to append or to set.
     * @param expected  the expected result after setting, appending.
     * @param clearText {@code true} to clear the text after asserting.
     */
    void appendAndCheck(String appended, String expected, boolean clearText) {
        appendAndCheck(appended, expected, PATTERN_1, clearText);
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
        PatternedTextWatcher patternedTextWatcher = init(editText, pattern);
        addTextAndAssert(editText, expected, appended, pattern);
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

    /**
     * Initialize EditText with a special {@link PatternedTextWatcher}.
     *
     * @param editText EditText to add a TextWatcher.
     * @param pattern  Pattern to supply to TextWatcher.
     * @return initialized {@link PatternedTextWatcher}.
     */
    abstract PatternedTextWatcher init(EditText editText, String pattern);
}
