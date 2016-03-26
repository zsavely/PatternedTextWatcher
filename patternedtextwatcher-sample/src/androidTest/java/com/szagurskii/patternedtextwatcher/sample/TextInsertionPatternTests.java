package com.szagurskii.patternedtextwatcher.sample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;
import com.szagurskii.patternedtextwatcher.sample.utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Savelii Zagurskii
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TextInsertionPatternTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private EditText editText;

    @Before
    public void setUp() throws Exception {
        editText = (EditText) mActivityRule.getActivity().findViewById(R.id.et_sample);
    }

    @Test
    public void simpleTextChanged() {
        addTextChangedListener("(##-##)");
        Utils.insertTextAtOnceAndAssert("Espr", "(Es-pr)");
    }

    @Test
    public void basicTest() {
        addTextChangedListener("(###)");
        Utils.insertTextAtOnceAndAssert("Espresso", "(Esp)");
    }

    @Test
    public void basicTest2() {
        addTextChangedListener("(### ###)");
        Utils.insertTextAtOnceAndAssert("Espresso", "(Esp res)");
    }

    @Test
    public void basicTestWithMobilePhone() {
        addTextChangedListener("+# (###) ###-##-##");
        Utils.insertTextAtOnceAndAssert("71234567890", "+7 (123) 456-78-90");
    }

    @Test
    public void basicTest3() {
        addTextChangedListener("########");
        Utils.insertTextAtOnceAndAssert("Espresso", "Espresso");
    }

    @Test
    public void basicTest4() {
        addTextChangedListener("(### ### ##)");
        Utils.insertTextAtOnceAndAssert("Espresso", "(Esp res so)");
    }

    @Test
    public void basicTest5() {
        addTextChangedListener("### ### ##)");
        Utils.insertTextAtOnceAndAssert("Espresso", "Esp res so)");
    }

    private void addTextChangedListener(String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
                .build();
        editText.addTextChangedListener(patternedTextWatcher);
    }
}
