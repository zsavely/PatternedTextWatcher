package com.szagurskii.patternedtextwatcher;

import android.text.Editable;
import android.text.TextWatcher;

import com.szagurskii.patternedtextwatcher.utils.ConversionUtils;
import com.szagurskii.patternedtextwatcher.utils.LogUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A customizable text watcher which can be constructed via {@link com.szagurskii.patternedtextwatcher.PatternedTextWatcher.Builder}.
 *
 * @author Savelii Zagurskii
 */
public class PatternedTextWatcher implements TextWatcher {

    private static final String DEFAULT_CHAR = "#";

    private final String pattern;
    private final int maxLength;
    private final String specialChar;
    private final Set<Character> specialCharacters;
    private final Map<Integer, Character> patternCharactersByIndex;
    private final Map<Integer, Character> normalCharactersByIndex;
    private final int firstIndexToCheck;
    private final boolean fillExtra;
    private final boolean deleteExtra;
    private final boolean saveInput;
    private final boolean respectPatternLength;
    private final boolean debug;

    private String lastText;
    private boolean enabled;
    private StringBuilder savedText;

    /**
     * Initialize {@link PatternedTextWatcher}.
     *
     * @param pattern     the pattern of the text watcher.
     * @param specialChar special character(-s) to be replaced.
     */
    private PatternedTextWatcher(String pattern,
                                 String specialChar,
                                 boolean fillExtra,
                                 boolean deleteExtra,
                                 boolean saveInput,
                                 boolean respectPatternLength,
                                 boolean debug) {
        this.pattern = pattern;
        this.fillExtra = fillExtra;
        this.deleteExtra = deleteExtra;
        this.saveInput = saveInput;
        this.respectPatternLength = respectPatternLength;
        this.debug = debug;
        this.maxLength = pattern.length();
        this.specialChar = specialChar;
        this.specialCharacters = new HashSet<>(ConversionUtils.asList(specialChar));
        this.patternCharactersByIndex = new HashMap<>();
        this.normalCharactersByIndex = new HashMap<>();

        int firstIndexToCheck = -1;
        firstIndexToCheck = initializeIndexes(pattern, firstIndexToCheck);
        // TODO Think this problem over. Why didn't the 'firstIndexToCheck' initialize?
        this.firstIndexToCheck = firstIndexToCheck == -1 ? 0 : firstIndexToCheck;

        this.lastText = "";
        this.enabled = true;
        this.savedText = new StringBuilder();
    }

    private int initializeIndexes(String pattern, int firstIndexToCheck) {
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);

            // If current char is present in special characters that we must replace,
            // we just save the first index of the special character to replace.
            if (specialCharacters.contains(c)) {
                normalCharactersByIndex.put(i, c);
            }
            // If current char is not present in special characters,
            // then we save current index and char that should be placed in the future into the string.
            else {
                if (firstIndexToCheck == -1)
                    firstIndexToCheck = i;

                patternCharactersByIndex.put(i, c);
            }
        }
        return firstIndexToCheck;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        LogUtils.logd("beforeTextChanged", s, debug);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogUtils.logd("onTextChanged", s, debug);
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {
        LogUtils.logd("afterTextChanged", s, debug);

        if (isEnabled()) {
            StringBuilder sb = new StringBuilder(s);

            // If current text is valid in length, proceed.
            if (isValidInLength(s)) {
                // Determine if a character was added.
                if (s.length() > lastText.length()) {
                    onCharacterAdded(sb);
                } else if (s.length() < lastText.length()) {
                    onCharacterDeleted(sb);
                }
            }
            // If the string is over the limit,
            // we just delete the last symbol.
            else {
                if (sb.length() != 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
            // Finalize the string by replacing the old object into a new one.
            checkIfReplaceIsNeeded(s, sb);
            // Check if the pattern is followed.
            validatePattern(sb);
        }
        LogUtils.logd("Saved text", savedText.toString(), debug);
    }

    /**
     * Character was added.
     *
     * @param sb current string.
     */
    private void onCharacterAdded(StringBuilder sb) {
        // Check if the pattern has characters to replace in current index.
        Character possibleCharToInsert = patternCharactersByIndex.get(sb.length() - 1);

        // Append a pattern character if current index is not empty.
        if (possibleCharToInsert != null) {
            if (!possibleCharToInsert.equals(sb.charAt(sb.length() - 1))) {
                if (fillExtra) {
                    // Insert pattern char.
                    sb.insert(sb.length() - 1, possibleCharToInsert);

                    // Insert pattern chars until they are over.
                    while (patternCharactersByIndex.get(sb.length() - 1) != null)
                        sb.insert(sb.length() - 1, patternCharactersByIndex.get(sb.length() - 1));
                } else {
                    if (saveInput) {
                        savedText.append(sb.charAt(sb.length() - 1));
                    }
                    sb.replace(sb.length() - 1, sb.length(), String.valueOf(possibleCharToInsert));
                }
            }
        } else {
            if (saveInput) {
                savedText.append(sb.charAt(sb.length() - 1));
            }

            if (sb.length() >= firstIndexToCheck) {
                int i = firstIndexToCheck;

                while (i <= sb.length()) {
                    // First check the length of current string.
                    // If we exceed the max limit, we need to delete symbols.
                    deleteIfLengthExceeds(sb);

                    // Then we insert the next character if there is a one.
                    if (fillExtra) {
                        insertCharacterIfAvailable(sb, i);
                    }

                    i++;
                }
            }
        }
    }

    /**
     * Insert the pattern character by the specified index if there is one.
     *
     * @param sb current string.
     * @param i  current index to look.
     */
    private void insertCharacterIfAvailable(StringBuilder sb, int i) {
        Character charToInsert = patternCharactersByIndex.get(i);
        if (charToInsert != null) {
            if (i < sb.length()) {
                if (!charToInsert.equals(sb.charAt(i))) {
                    sb.insert(i, charToInsert);
                }
            } else {
                sb.insert(i, charToInsert);
            }
        }
    }

    /**
     * Character deleted.
     *
     * @param sb current string.
     */
    private void onCharacterDeleted(StringBuilder sb) {
        int currentLength = sb.length();
        if (saveInput) {
            int charactersDeletedCount = lastText.length() - sb.length();
            // Delete as many symbols as were deleted.
            for (int i = 0; i < charactersDeletedCount; i++) {
                savedText.deleteCharAt(savedText.length() - 1);
            }
        }
        if (deleteExtra) {
            // If the user deleted a pattern character.
            if (patternCharactersByIndex.get(lastText.length() - 1) != null) {
                // If next symbols are also pattern characters,
                // we need to delete them, too.
                while (patternCharactersByIndex.get(sb.length() - 1) != null) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                // Then we delete the symbol, which was intended to be deleted initially.
                if (sb.length() != 0)
                    sb.deleteCharAt(sb.length() - 1);
            }
            // If the user deleted a normal character.
            else {
                // If next symbols are pattern characters,
                // we need to delete them, too.
                while (patternCharactersByIndex.get(sb.length() - 1) != null) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        if (saveInput) {
            int charactersDeletedCount = currentLength - sb.length();
            // Delete as many symbols as were deleted.
            for (int i = 0; i < charactersDeletedCount; i++) {
                savedText.deleteCharAt(savedText.length() - 1);
            }
        }
    }

    /**
     * Check whether the Editable is empty or full.
     *
     * @param s the editable to check.
     * @return {@code false} if the {@code s} is empty or exceeds the maximum limit. {@code true} otherwise.
     */
    private boolean isValidInLength(Editable s) {
        return (!respectPatternLength || s.length() <= maxLength);
    }

    /**
     * Check if current string was changed, so that editable should be also changed.
     *
     * @param s  the editable to change.
     * @param sb current string.
     */
    private void checkIfReplaceIsNeeded(Editable s, StringBuilder sb) {
        lastText = sb.toString();
        if (!s.toString().equals(lastText)) {
            s.replace(0, s.length(), sb);
        }
    }

    /**
     * Delete the last character if the length exceeds the maximum limit.
     *
     * @param sb current string.
     */
    private void deleteIfLengthExceeds(StringBuilder sb) {
        if (sb.length() > maxLength && respectPatternLength) {
            sb.delete(sb.length() - 1, sb.length());
        }
    }

    /**
     * Validate the pattern. Check so that all the patternCharactersByIndex were on their places.
     *
     * @param sb current string builder.
     * @return {@code true} if the pattern is followed correctly. {@code false} otherwise.
     */
    private boolean validatePattern(StringBuilder sb) {
        // TODO Think what to do if something is not on their places.
        boolean patternValidated = true;
        for (Map.Entry<Integer, Character> entry : patternCharactersByIndex.entrySet()) {
            if (entry.getValue() != null) {
                if (sb.length() > entry.getKey()) {
                    if (!entry.getValue().equals(sb.charAt(entry.getKey()))) {
                        LogUtils.logw("validatePattern",
                                      String.format("Assertion error! Expected \"%1$s\" in index \'%2$s\'." +
                                                            "\nGot \"%3$s\".",
                                                    entry.getValue(),
                                                    entry.getKey(),
                                                    sb.charAt(entry.getKey())),
                                      debug);
                        patternValidated = false;
                    }
                }
            }
        }
        return patternValidated;
    }

    /**
     * Get the string as it is shown.
     *
     * @return string that is shown to the user.
     */
    public String getFormattedString() {
        return lastText;
    }

    /**
     * <br>Get the full string which was accumulated during the user's symbols addition.</br>
     * <b><br>NOTE:</br></b>
     * <br>This works inly if you have set
     * {@link com.szagurskii.patternedtextwatcher.PatternedTextWatcher.Builder#saveAllInput(boolean)} to {@code true}
     * <b>AND</b></br>
     * {@link com.szagurskii.patternedtextwatcher.PatternedTextWatcher.Builder#fillExtraCharactersAutomatically(boolean)} to {@code false}.
     *
     * @return the full string if the above criteria was met; {@link #getFormattedString()} otherwise.
     */
    public String getFullString() {
        if (saveInput)
            return savedText.toString();
        return getFormattedString();
    }

    /**
     * Get string only with characters which were replaced.
     *
     * @return clean string with inserted characters if you have set the
     * {@link com.szagurskii.patternedtextwatcher.PatternedTextWatcher.Builder#respectPatternLength(boolean)}
     * to {@code true} (default).
     * {@link #getFormattedString()} otherwise.
     */
    public String getCleanString() {
        if (respectPatternLength) {
            StringBuilder cleanString = new StringBuilder();
            for (int i = 0; i < lastText.length(); i++) {
                if (normalCharactersByIndex.get(i) != null) {
                    cleanString.append(lastText.charAt(i));
                }
            }
            return cleanString.toString();
        }
        return getFormattedString();
    }

    /**
     * Change the state of current TextWatcher.
     *
     * @param enabled {@code true} to enable text watching. {@code false} otherwise.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Determine if the TextWatcher is enabled and watches the text.
     *
     * @return {@code true} if enabled, {@code false} otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Determine if the TextWatcher is filling pattern characters automatically.
     *
     * @return {@code true} if fills automatically. {@code false} otherwise.
     */
    public boolean isFillingExtra() {
        return fillExtra;
    }

    /**
     * Determine if the TextWatcher is deleting extra characters automatically.
     *
     * @return {@code true} if deletes automatically. {@code false} otherwise.
     */
    public boolean isDeletingExtra() {
        return deleteExtra;
    }

    /**
     * Determine if the TextWatcher is saving input behind pattern characters.
     * This can be only done if automatic filling is turned off.
     *
     * @return {@code true} if saving input. {@code false} otherwise.
     */
    public boolean isSavingInput() {
        return saveInput;
    }

    /**
     * Determine if the TextWatcher is respecting pattern length.
     *
     * @return {@code true} if respecting. {@code false} otherwise.
     */
    public boolean isRespectingPatternLength() {
        return respectPatternLength;
    }

    /**
     * Determine if the debug logs are seen in logcat.
     *
     * @return {@code true} if debug logs can be seen in logcat. {@code false} otherwise.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Use {@link PatternedTextWatcher} builder to construct a TextWatcher
     * that will monitor your class that extends {@link android.widget.TextView}.
     */
    public static class Builder {
        private String specialChar;
        private String pattern;
        private boolean fillExtraCharactersAutomatically;
        private boolean deleteExtraCharactersAutomatically;
        private boolean saveInput;
        private boolean respectPatternLength;
        private boolean debug;

        /**
         * Initialize builder with pattern definition. The default {@code specialChar} is '#'.
         * You can set it via {@link com.szagurskii.patternedtextwatcher.PatternedTextWatcher.Builder#specialChar(String)}.
         *
         * @param pattern the pattern to follow. Can't be null.
         */
        public Builder(String pattern) {
            this.pattern = pattern;
            this.specialChar = DEFAULT_CHAR;
            this.fillExtraCharactersAutomatically = true;
            this.deleteExtraCharactersAutomatically = true;
            this.saveInput = false;
            this.respectPatternLength = true;
            this.debug = false;
        }

        /**
         * Set the special char which is to be replaced instead of the user input.
         *
         * @param specialChar special char consisting of one or more
         * @return
         */
        public Builder specialChar(String specialChar) {
            this.specialChar = specialChar;
            return this;
        }

        /**
         * Set the debug param in order to see the debug log in LogCat. Default is {@code false}.
         *
         * @param debug {@code true} to see debug log messages.
         * @return
         */
        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * <br>Set the pattern to follow.</br>
         * <br>Example: <b>{@code (###-###)}</b>.</br>
         *
         * @param pattern
         * @return
         */
        public Builder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        /**
         * <br>Fill extra characters automatically. Default is {@code true}.</br>
         * <br>If you have a pattern with some characters that are not a part of the special characters,
         * then this tweak will automatically insert these characters for the user.</br>
         *
         * @param fillExtraCharactersAutomatically whether you want the extra characters to be inserted automatically.
         * @return
         * @see Builder#deleteExtraCharactersAutomatically(boolean)
         * @see Builder#saveAllInput(boolean)
         */
        public Builder fillExtraCharactersAutomatically(boolean fillExtraCharactersAutomatically) {
            this.fillExtraCharactersAutomatically = fillExtraCharactersAutomatically;
            return this;
        }

        /**
         * Delete extra characters automatically. Default is {@code true}.
         *
         * @param deleteExtraCharactersAutomatically whether you want the extra characters to be deleted automatically.
         * @return
         * @see Builder#fillExtraCharactersAutomatically(boolean)
         * @see Builder#saveAllInput(boolean)
         */
        public Builder deleteExtraCharactersAutomatically(boolean deleteExtraCharactersAutomatically) {
            this.deleteExtraCharactersAutomatically = deleteExtraCharactersAutomatically;
            return this;
        }

        /**
         * This tweak works <b>ONLY</b> if you have set the
         * {@link Builder#fillExtraCharactersAutomatically(boolean)} to {@code false},
         * otherwise it will throw {@link IllegalArgumentException} because values can't be saved
         * when the extra characters are inserted automatically.
         *
         * @param saveInput whether you want to save the input of characters hidden under other characters.
         * @return
         * @see Builder#fillExtraCharactersAutomatically(boolean)
         * @see Builder#deleteExtraCharactersAutomatically(boolean)
         */
        public Builder saveAllInput(boolean saveInput) {
            this.saveInput = saveInput;
            return this;
        }

        /**
         * Get the maximum string length from the pattern. Default is {@code true}.
         *
         * @param respectPatternLength whether you want to respect pattern length.
         * @return
         */
        public Builder respectPatternLength(boolean respectPatternLength) {
            this.respectPatternLength = respectPatternLength;
            return this;
        }

        /**
         * Build the Patterned TextWatcher.
         *
         * @return a {@link PatternedTextWatcher} which you can use for {@link android.widget.TextView}, {@link android.widget.EditText}, etc.
         */
        public PatternedTextWatcher build() {
            if (pattern == null) {
                throw new NullPointerException("Pattern can't be null.");
            }
            if (pattern.isEmpty()) {
                throw new IllegalArgumentException("Pattern can't be empty.");
            }
            if (specialChar == null) {
                throw new NullPointerException("Special char can't be null.");
            }
            if (specialChar.isEmpty()) {
                throw new IllegalArgumentException("Special char can't be empty.");
            }
            if (specialChar.length() > 1) {
                throw new IllegalArgumentException("Special char must be length = 1.");
            }
            if (saveInput && fillExtraCharactersAutomatically) {
                throw new IllegalArgumentException("It is impossible to save input when the characters are filled automatically instead of characters.");
            }
            return new PatternedTextWatcher(pattern,
                                            specialChar,
                                            fillExtraCharactersAutomatically,
                                            deleteExtraCharactersAutomatically,
                                            saveInput,
                                            respectPatternLength,
                                            debug);
        }
    }
}
