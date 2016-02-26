package com.szagurskii.patternedtextwatcher.utils;

import java.util.AbstractList;
import java.util.List;

/**
 * @author Savelii Zagurskii
 */
public class ConversionUtils {
    /**
     * Convert {@link String} to a {@link List} of {@link Character}s.
     *
     * @param string input string.
     * @return List of Characters that were supplied in string.
     */
    public static List<Character> asList(final String string) {
        if (string == null) {
            throw new NullPointerException("String can't be null.");
        }
        return new AbstractList<Character>() {
            public int size() {
                return string.length();
            }

            public Character get(int index) {
                return string.charAt(index);
            }
        };
    }
}
