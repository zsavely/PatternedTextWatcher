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

    // Not yet merged.
    //@Test(expected = IllegalStateException.class)
    //public void shouldThrowIfCharDoesntExistInPattern() {
    //    PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("(NoSpecialChar)")
    //            .build();
    //}
}
