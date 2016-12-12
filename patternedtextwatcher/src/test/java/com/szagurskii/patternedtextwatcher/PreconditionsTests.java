package com.szagurskii.patternedtextwatcher;

import android.os.Build;

import com.szagurskii.patternedtextwatcher.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.lang.reflect.InvocationTargetException;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(CustomRobolectricTestRunner.class)
public class PreconditionsTests {
  @Before
  public void setup() {
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowIfNull() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher((String) null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowIfBuilderPatternValueIsNull() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder()
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfEmpty() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher("");
  }

  @Test(expected = IllegalStateException.class)
  public void shouldThrowIfPatternHasNoSpecialCharacter() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher("NoSpecialCharacter");
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowIfNullInBuilderViaConstructor() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(null)
        .build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowIfNullInBuilderViaSetter() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("Abc")
        .pattern(null)
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfEmptyInBuilderViaConstructor() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfEmptyInBuilderViaSetter() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("Abc")
        .pattern("")
        .build();
  }

  @Test(expected = IllegalStateException.class)
  public void shouldThrowIfCharDoesntExistInPattern() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(NoSpecialChar)")
        .build();
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowIfNullChar() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(#)")
        .specialChar(null)
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfEmptyChar() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(#)")
        .specialChar("")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfMoreThanOneInLengthChar() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(#)")
        .specialChar("LongSpecialChar")
        .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIfSaveAndFillAreTrue() {
    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(#)")
        .fillExtraCharactersAutomatically(true)
        .saveAllInput(true)
        .build();
  }

  @Test public void constructorShouldBePrivate()
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    TestUtils.assertUtilityClassWellDefined(Preconditions.class);
  }
}
