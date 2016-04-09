package com.szagurskii.rxtesting.insertion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.rxtesting.models.PatternCheck;

import org.junit.Test;

import static com.szagurskii.rxtesting.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseDefaultAdditionTests extends BaseAdditionTests {
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

        PATTERN_CHECKS.add(new PatternCheck(")))###(((###", "((()))", ")))(((((()))"));
        PATTERN_CHECKS.add(new PatternCheck(")))######(((", "((()))", ")))((()))((("));
        PATTERN_CHECKS.add(new PatternCheck("###)))(((###", "((()))", "((()))((()))"));
        PATTERN_CHECKS.add(new PatternCheck("###)))###(((", "((()))", "((())))))((("));

        PATTERN_CHECKS.add(new PatternCheck("(((###)))###", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("(((######)))", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("###((()))###", "((()))", "(((((())))))"));
        PATTERN_CHECKS.add(new PatternCheck("###(((###)))", "((()))", "(((((())))))"));
    }

    @Test
    public void basicSingleAddition() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ONE, "(1");
    }

    @Test
    public void basicMultipleAddition() {
        PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_1);
        multipleAddition();
        clearTextChangeListener(editText, patternedTextWatcher, true);
    }

    public abstract void multipleAddition();

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

    @Override
    protected PatternedTextWatcher init(EditText editText, String pattern) {
        return addTextChangedListener(editText, pattern);
    }
}
