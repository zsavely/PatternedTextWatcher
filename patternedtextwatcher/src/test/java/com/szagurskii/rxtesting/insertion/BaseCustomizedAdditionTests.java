package com.szagurskii.rxtesting.insertion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.rxtesting.models.PatternCheck;

import org.junit.Test;

import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseCustomizedAdditionTests extends BaseAdditionTests {

    static {
        // Pattern, Input, Expected.
        PATTERN_CHECKS.add(new PatternCheck("######", STRING_TO_BE_TYPED_LENGTH_SIX, "123456"));
        PATTERN_CHECKS.add(new PatternCheck("(######", STRING_TO_BE_TYPED_LENGTH_SIX, "(23456"));
        PATTERN_CHECKS.add(new PatternCheck("######)", STRING_TO_BE_TYPED_LENGTH_SIX, "123456"));
        PATTERN_CHECKS.add(new PatternCheck("###-###", STRING_TO_BE_TYPED_LENGTH_SIX, "123-56"));
        PATTERN_CHECKS.add(new PatternCheck("(######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(23456"));
        PATTERN_CHECKS.add(new PatternCheck("(###-###)", STRING_TO_BE_TYPED_LENGTH_SIX, "(234-6"));
        PATTERN_CHECKS.add(new PatternCheck("(-######)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-3456"));
        PATTERN_CHECKS.add(new PatternCheck("(######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(23456"));
        PATTERN_CHECKS.add(new PatternCheck("(-######-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-3456"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)", STRING_TO_BE_TYPED_LENGTH_SIX, "(-3-4-6"));
        PATTERN_CHECKS.add(new PatternCheck("(-#-#-#-#-#-#-)))))))))))))))))))))", STRING_TO_BE_TYPED_LENGTH_SIX, "(-3-4-6"));

        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_ELEVEN, "+2 (56"));
        PATTERN_CHECKS.add(new PatternCheck("+# (###) ###-##-##", STRING_TO_BE_TYPED_LENGTH_TWELVE, "+2 (56"));

        PATTERN_CHECKS.add(new PatternCheck(")))###(((###", "((()))", "))))))"));
        PATTERN_CHECKS.add(new PatternCheck(")))######(((", "((()))", "))))))"));
        PATTERN_CHECKS.add(new PatternCheck("###)))(((###", "((()))", "((()))"));
        PATTERN_CHECKS.add(new PatternCheck("###)))###(((", "((()))", "((()))"));

        PATTERN_CHECKS.add(new PatternCheck("(((###)))###", "((()))", "((()))"));
        PATTERN_CHECKS.add(new PatternCheck("(((######)))", "((()))", "((()))"));
        PATTERN_CHECKS.add(new PatternCheck("###((()))###", "((()))", "(((((("));
        PATTERN_CHECKS.add(new PatternCheck("###(((###)))", "((()))", "(((((("));
    }

    @Test
    public void basicSingleAddition() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ONE, "(");
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
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_NINE, "(234-678)");
    }

    @Test
    public void basicAdditionMoreThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_ELEVEN, "(234-678)");
    }

    @Test
    public void basicAdditionLessThanPattern() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SEVEN, "(234-67");
    }

    @Test
    public void basicAdditionExactPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_SIX, "(234-6");
    }

    @Test
    public void basicAdditionMoreThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_TEN, "(234-678)");
    }

    @Test
    public void basicAdditionLessThanPatternSpecialCharacters() {
        appendAndCheck(STRING_TO_BE_TYPED_LENGTH_FOUR, "(234");
    }

    @Override
    PatternedTextWatcher init(EditText editText, String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
                .fillExtraCharactersAutomatically(false)
                .build();
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }
}
