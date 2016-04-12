package com.szagurskii.patternedtextwatcher.saving_input;

import com.szagurskii.patternedtextwatcher.base.BaseTests;

import org.junit.Test;

/**
 * @author Savelii Zagurskii
 */
public abstract class BaseSavingInputTests extends BaseTests {
    @Test
    public abstract void shouldSaveOnAdding();

    @Test
    public abstract void shouldSaveOnAddingAndBackspace();

    @Test
    public abstract void shouldSaveOnAddingAndClearing();

    @Test
    public abstract void shouldSaveOnAddingClearingAndBackspace();

    @Test
    public abstract void shouldSaveOnAddingBackspaceAndClearing();

    abstract void addText(String value);
}
