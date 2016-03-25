package com.szagurskii.rxtesting;

import android.app.Activity;
import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class AdditionTests {
    private Activity activity;
    private EditText editText;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(Activity.class);
        editText = new EditText(activity);
    }

    @Test
    public void validateAddingAndRemovingTextWatcher() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher("(###-###)");
        editText.addTextChangedListener(patternedTextWatcher);
        editText.removeTextChangedListener(patternedTextWatcher);
    }

    @Test
    public void basicAddition() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher("(###-###)");
        editText.addTextChangedListener(patternedTextWatcher);
        editText.append("3");
        assertTrue("EditText contains incorrect text.", "(3".equals(editText.getText().toString()));
        editText.removeTextChangedListener(patternedTextWatcher);
    }
}
