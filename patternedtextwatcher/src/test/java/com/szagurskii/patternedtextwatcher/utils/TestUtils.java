package com.szagurskii.patternedtextwatcher.utils;

import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class TestUtils {
  /**
   * Verifies that a utility class is well defined.
   * <br>
   * Author: <a href="https://stackoverflow.com/users/242042/archimedes-trajano">Archimedes Trajano</a>
   *
   * @param clazz utility class to verify.
   * @see <a href="http://stackoverflow.com/a/10872497/4271064">http://stackoverflow.com/a/10872497/4271064</a>
   */
  public static void assertUtilityClassWellDefined(Class<?> clazz)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Assert.assertTrue("Class must be final.", Modifier.isFinal(clazz.getModifiers()));
    Assert.assertEquals("There must be only one constructor.", 1, clazz.getDeclaredConstructors().length);

    Constructor<?> constructor = clazz.getDeclaredConstructor();
    if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
      Assert.fail("Constructor is not private.");
    }
    constructor.setAccessible(true);
    constructor.newInstance();
    constructor.setAccessible(false);

    for (Method method : clazz.getMethods()) {
      if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
        Assert.fail("There exists a non-static method:" + method);
      }
    }
  }
}
