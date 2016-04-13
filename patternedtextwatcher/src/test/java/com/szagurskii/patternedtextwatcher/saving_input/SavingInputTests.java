package com.szagurskii.patternedtextwatcher.saving_input;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.szagurskii.patternedtextwatcher.utils.EditTextUtils.clearTextChangeListener;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class SavingInputTests extends BaseSavingInputTests {

    @Override
    public void shouldSaveOnMultipleAdding() {
        PatternedTextWatcher patternedTextWatcher = init(editText, BASIC_PATTERN);
        addText(ESPRESSO);
        assertText(ESPRESSO, ESPRESSO, patternedTextWatcher.getFullString(), BASIC_PATTERN);
        clearTextChangeListener(editText, patternedTextWatcher);
    }

    @Override
    public void shouldSaveOnAddingAndBackspace() {
        // TODO
    }

    @Override
    public void shouldSaveOnAddingAndClearing() {
        // TODO
    }

    @Override
    public void shouldSaveOnAddingClearingAndBackspace() {
        // TODO
    }

    @Override
    public void shouldSaveOnAddingBackspaceAndClearing() {
        // TODO
    }

    @Override
    void addText(String value) {
        editText.append(value);
    }

    @Override
    protected PatternedTextWatcher init(EditText editText, String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
                .fillExtraCharactersAutomatically(false)
                .saveAllInput(true)
                .build();
        editText.addTextChangedListener(patternedTextWatcher);
        return patternedTextWatcher;
    }
}
