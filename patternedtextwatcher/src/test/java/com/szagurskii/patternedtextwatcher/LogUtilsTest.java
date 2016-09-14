package com.szagurskii.patternedtextwatcher;

import com.szagurskii.patternedtextwatcher.utils.TestUtils;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class LogUtilsTest {
  @Test public void constructorShouldBePrivate()
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    TestUtils.assertUtilityClassWellDefined(LogUtils.class);
  }
}
