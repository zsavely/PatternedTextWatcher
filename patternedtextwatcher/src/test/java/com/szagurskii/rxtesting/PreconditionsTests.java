package com.szagurskii.rxtesting;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Savelii Zagurskii
 */
public class PreconditionsTests {

    @Before
    public void setup() {
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowIfNull() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher(null);
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
    public void shouldThrowIfNullInBuilder() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIfEmptyInBuilder() {
        PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("")
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
}
