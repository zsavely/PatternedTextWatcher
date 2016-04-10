package com.szagurskii.patternedtextwatcher.deletion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.patternedtextwatcher.base.BaseTests;

import org.junit.Test;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseDeletionTests extends BaseTests {
    // 9 chars
    static final String INSERT = "123456789";
    // 6 chars
    static final String PATTERN_1 = "######";
    // 8 chars
    static final String PATTERN_2 = "(######)";
    // 12 chars
    static final String PATTERN_3 = "(######)))))";
    // 15 chars
    static final String PATTERN_4 = "(###)))###)))))";

    @Test
    public abstract void multipleAddingAndDeletion1();

    void appendClearOneSymbolAndCheck(String appended, String expected, String pattern) {
        PatternedTextWatcher patternedTextWatcher = init(editText, pattern);
        addText(editText, appended);
        backspace(appended, expected, pattern);
        clearTextChangeListener(editText, patternedTextWatcher);
    }

    void backspace(String appended, String expected, String pattern) {
        clearTextAndAssert(editText, expected, appended, pattern, editText.length() - 1, editText.length());
    }

    /**
     * Clear or backspace text from EditText and assert the expected result.
     *
     * @param editText current EdiText to watch.
     * @param expected expected string.
     * @param typed    string that was inserted before deleting.
     * @param pattern  the pattern which was used.
     * @param start    start index of the string which is being removed.
     * @param end      end index of the string which is being removed.
     */
    private void clearTextAndAssert(EditText editText, String expected, String typed, String pattern, int start, int end) {
        editText.getText().delete(start, end);
        assertText(editText, expected, typed, pattern);
    }

    @Override
    protected PatternedTextWatcher init(EditText editText, String pattern) {
        return addTextChangedListener(editText, pattern);
    }

    /**
     * Add text to the EditText via {@link EditText#setText(char[], int, int)} or {@link EditText#append(CharSequence)}.
     *
     * @param editText EditText to which the text will be appended.
     * @param value    the value th append.
     */
    abstract void addText(EditText editText, String value);
}
