package com.szagurskii.patternedtextwatcher;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;

/**
 * @author Savelii Zagurskii
 */
public class CustomRobolectricTestRunner extends RobolectricGradleTestRunner {
  public CustomRobolectricTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
    System.setProperty("user.home", "C:\\Repository");
  }
}
