package com.szagurskii.rxtesting.deletion;

import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Test;

import static com.szagurskii.rxtesting.utils.EditTextUtils.addTextChangedListener;
import static com.szagurskii.rxtesting.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseDefaultDeletionTests extends BaseDeletionTests {
    @Test
    public void deletion1() {
        appendClearOneSymbolAndCheck(INSERT, "12345", PATTERN_1);
    }

    @Test
    public void deletion2() {
        appendClearOneSymbolAndCheck(INSERT, "(12345", PATTERN_2);
    }

    @Test
    public void deletion3() {
        appendClearOneSymbolAndCheck(INSERT, "(12345", PATTERN_3);
    }

    @Test
    public void multipleDeletion1() {
        PatternedTextWatcher patternedTextWatcher = init(editText, PATTERN_4);
        addText(editText, INSERT);
        backspace(INSERT, "(123)))45", PATTERN_4);
        backspace(INSERT, "(123)))4", PATTERN_4);
        backspace(INSERT, "(123", PATTERN_4);
        backspace(INSERT, "(12", PATTERN_4);
        backspace(INSERT, "(1", PATTERN_4);
        backspace(INSERT, "", PATTERN_4);
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
