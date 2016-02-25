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
public class BasicDeletionTests {
    private static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private EditText editText;

    @Before
    public void setUp() throws Exception {
        editText = (EditText) mActivityRule.getActivity().findViewById(R.id.et_sample);
    }

    @Test
    public void simpleTextChanged() {
        Utils.typeTextAndAssert(STRING_TO_BE_TYPED, STRING_TO_BE_TYPED);
    }

    @Test
    public void basicTest() {
        addTextChangedListener("(##-##))))#)))#");
        Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr))))e)))s");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-pr))))e");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-pr");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-p");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(E");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("");
    }

    @Test
    public void multipleInsertsAndDeletionsTest() {
        addTextChangedListener("(##-##))))#)))#");
        Utils.typeTextAndAssert(STRING_TO_BE_TYPED, "(Es-pr))))e)))s");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-pr))))e");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-pr");

        Utils.typeTextAndAssert("e", "(Es-pr))))e");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-pr");

        Utils.typeTextAndAssert("e", "(Es-pr))))e");

        Utils.deleteOneCharacter();
        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es-p");

        Utils.typeTextAndAssert("r", "(Es-pr))))");

        Utils.deleteOneCharacter();
        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(Es");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("(E");

        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("");

        Utils.typeTextAndAssert("E", "(E");
        Utils.deleteOneCharacter();
        Utils.assertExpectedOutput("");
    }

    private void addTextChangedListener(String pattern) {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(pattern)
                .build();
        editText.addTextChangedListener(patternedTextWatcher);
    }
}


