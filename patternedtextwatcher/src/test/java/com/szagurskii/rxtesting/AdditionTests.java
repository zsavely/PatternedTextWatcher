package com.szagurskii.rxtesting;

import android.app.Activity;
import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.rxtesting.models.PatternCheck;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AdditionTests {

    // 1 char
    private static final String STRING_TO_BE_TYPED_LENGTH_ONE = "1";
    // 2 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_TWO = "12";
    // 3 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_THREE = "123";
    // 4 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_FOUR = "1234";
    // 5 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_FIVE = "12345";
    // 6 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_SIX = "123456";
    // 7 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_SEVEN = "1234567";
    // 8 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_EIGHT = "12345678";
    // 9 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_NINE = "123456789";
    // 10 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_TEN = "1234567890";
    // 11 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_ELEVEN = "12345678901";
    // 12 chars
    private static final String STRING_TO_BE_TYPED_LENGTH_TWELVE = "123456789012";

    // 9 chars
    private static final String PATTERN_1 = "(###-###)";

    private static final List<PatternCheck> PATTERN_CHECKS = new ArrayList<>();

    static {
        // Pattern, Input, Expected.
        PATTERN_CHECKS.add(new PatternCheck("######", STRING_TO_BE_TYPED_LENGTH_SIX, "123456"));
        PATTERN_CHECKS.add(new PatternCheck("(######", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456"));
        PATTERN_CHECKS.add(new PatternCheck("######)", STRING_TO_BE_TYPED_LENGTH_SIX, "123456)"));
        PATTERN_CHECKS.add(new PatternCheck("###-###", STRING_TO_BE_TYPED_LENGTH_SIX, "123-456"));
        PATTERN_CHECKS.add(new PatternCheck("(######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456)"));
        PATTERN_CHECKS.add(new PatternCheck("(###-###)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123-456)"));
        PATTERN_CHECKS.add(new PatternCheck("(-######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-123456)"));
        PATTERN_CHECKS.add(new PatternCheck("(######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(123456-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-123456-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-1-2-3-4-5-6-)"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)))))))))))))))))))))", STRING_TO_BE_TYPED_LENGTH_SIX, "(-1-2-3-4-5-6-)))))))))))))))))))))"));

        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_ELEVEN, "+1 (234) 567-89-01"));
        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_TWELVE, "+1 (234) 567-89-01"));

        //PATTERN_CHECKS.add(new PatternCheck(")))###(((###", "((()))", ")))(((((()))"));
        //PATTERN_CHECKS.add(new PatternCheck(")))######(((", "((()))", ")))((()))((("));
        //PATTERN_CHECKS.add(new PatternCheck("###)))(((###", "((()))", "((()))((()))"));
        //PATTERN_CHECKS.add(new PatternCheck("###)))###(((", "((()))", "((())))))((("));
        //
        //PATTERN_CHECKS.add(new PatternCheck("(((###)))###", "((()))", "(((((())))))"));
        //PATTERN_CHECKS.add(new PatternCheck("(((######)))", "((()))", "(((((())))))"));
        //PATTERN_CHECKS.add(new PatternCheck("###((()))###", "((()))", "(((((())))))"));
        //PATTERN_CHECKS.add(new PatternCheck("###(((###)))", "((()))", "(((((())))))"));
    }

    private static final String EDITTEXT_ERROR_STRING = "EditText contains incorrect text.";

    private Activity activity;
    private EditText editText;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        editText = new EditText(activity);
    }

    @Test
    public void validateAddingAndRemovingTextWatcher() {
        appendAndCheck("", "");
    }

    @Test
    public void basicSingleAddition() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ONE, "(1");
    }

    @Test
    public void basicMultipleAddition() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_TWO, "(12", false);
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_TWO, "(121-2", false);
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_EIGHT, "(121-212)");
    }

    @Test
    public void basicAdditionExactPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "(123-456)");
    }

    @Test
    public void basicAdditionMoreThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ELEVEN, "(123-456)");
    }

    @Test
    public void basicAdditionLessThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SEVEN, "(123-456)");
    }

    @Test
    public void basicAdditionExactPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SIX, "(123-456)");
    }

    @Test
    public void basicAdditionMoreThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_TEN, "(123-456)");
    }

    @Test
    public void basicAdditionLessThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_FOUR, "(123-4");
    }

    @Test
    public void multipleAdditionPatternCheck() {
        for (PatternCheck patternCheck : PATTERN_CHECKS)
            appendAndCheck(patternCheck.getInput(), patternCheck.getExpected(), patternCheck.getPattern(), true);
    }

    private void appendAndCheck(String appended, String expected) {
        appendAndCheck(appended, expected, PATTERN_1, true);
    }

    private void appendAndCheck(String appended, String expected, boolean clearText) {
        appendAndCheck(appended, expected, PATTERN_1, clearText);
    }

    private void appendAndCheck(String appended, String expected, String pattern, boolean clearText) {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, pattern);
        editText.append(appended);
        assertTrue(EDITTEXT_ERROR_STRING, editText.getText().toString().equals(expected));
        clearTextChangeListener(editText, patternedTextWatcher, clearText);
    }

    private static PatternedTextWatcher addTextChangedListener(EditText editText, String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(pattern);
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }

    private static void clearTextChangeListener(EditText editText, PatternedTextWatcher patternedTextWatcher, boolean clearText) {
        editText.removeTextChangedListener(patternedTextWatcher);
        if (clearText)
            editText.getText().clear();
    }
}
