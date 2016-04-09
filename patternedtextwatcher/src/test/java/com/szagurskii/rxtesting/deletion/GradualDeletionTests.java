package com.szagurskii.rxtesting.deletion;

import android.os.Build;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.BuildConfig;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Savelii Zagurskii
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class GradualDeletionTests extends BaseDeletionTests {
    @Override
    void addText(EditText editText, String value) {
        editText.append(value);
    }
}
