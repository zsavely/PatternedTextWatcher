package com.szagurskii.patternedtextwatcher;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

public class CustomRobolectricTestRunner extends RobolectricTestRunner {
  public CustomRobolectricTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
    //System.setProperty("user.home", "C:\\Repository");
  }
}
