package com.szagurskii.rxtesting;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Test;

import static com.szagurskii.rxtesting.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseDeletionTests extends BaseTests {
    // 9 chars
    static final String INSERT = "123456789";
    // 6 chars
    static final String PATTERN_1 = "######";

    @Test
    public void basicDeletion() {
        appendClearOneSymbolAndCheck(INSERT, "12345", PATTERN_1);
    }

    private void appendClearOneSymbolAndCheck(String appended, String expected, String pattern) {
        PatternedTextWatcher patternedTextWatcher = addTextChangedListener(editText, pattern);
        addText(editText, appended);
        clearTextAndAssert(editText, expected, appended, pattern, editText.length() - 1, editText.length());
        clearTextChangeListener(editText, patternedTextWatcher);
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

    /**
     * Add text to the EditText via {@link EditText#setText(char[], int, int)} or {@link EditText#append(CharSequence)}.
     *
     * @param editText EditText to which the text will be appended.
     * @param value    the value th append.
     */
    abstract void addText(EditText editText, String value);
}
