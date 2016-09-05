package com.szagurskii.patternedtextwatcher;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.utils.ViewDirtyIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public abstract class BaseTests {
  static final String STRING_TO_BE_TYPED = "Espresso";

  @Rule
  public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

  ViewDirtyIdlingResource viewDirtyIdler;
  EditText editText;

  @Before
  public void setUp() throws Exception {
    editText = activityRule.getActivity().editText;
    TestActivity activity = activityRule.getActivity();
    viewDirtyIdler = new ViewDirtyIdlingResource(activity);
    Espresso.registerIdlingResources(viewDirtyIdler);
  }

  @After public void tearDown() {
    Espresso.unregisterIdlingResources(viewDirtyIdler);
  }
}
