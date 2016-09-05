package com.szagurskii.patternedtextwatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;

import static com.szagurskii.patternedtextwatcher.Preconditions.checkInput;
import static com.szagurskii.patternedtextwatcher.Preconditions.checkPatternInInput;
import static com.szagurskii.patternedtextwatcher.Preconditions.checkPatternInput;

/**
 * A customizable text watcher which can be constructed via {@link Builder}.
 *
 * @author Savelii Zagurskii
 */
public class PatternedTextWatcher implements TextWatcher {

  static final String DEFAULT_CHAR = "#";

  private final int maxLength;
  private final char specialCharacter;

  /** Indexes of secondary characters (not inserted by user). */
  private final SparseArray<Character> patternCharactersByIndex;

  /** Indexes of symbols inserted by user. */
  private final SparseArray<Character> normalCharactersByIndex;

  private final boolean fillExtra;
  private final boolean deleteExtra;
  private final boolean saveInput;
  private final boolean respectPatternLength;
  private final boolean debug;

  private int firstIndexToCheck;
  private int lastIndexToCheck;

  private String lastText;
  private boolean enabled;
  private StringBuilder savedText;

  /**
   * Initialize {@link PatternedTextWatcher} with {@code pattern} and default parameters.
   * For advanced tweaking use {@link Builder}.
   *
   * @param pattern the pattern of the text watcher.
   */
  public PatternedTextWatcher(String pattern) {
    this(new Builder(pattern));
  }

  PatternedTextWatcher(Builder builder) {
    checkPatternInput(builder.pattern);
    checkInput(builder.specialChar, builder.saveInput, builder.fillExtraChars);
    checkPatternInInput(builder.pattern, builder.specialChar);

    String pattern = builder.pattern;
    this.fillExtra = builder.fillExtraChars;
    this.deleteExtra = builder.deleteExtraChars;
    this.saveInput = builder.saveInput;
    this.respectPatternLength = builder.respectPatternLength;
    this.debug = builder.debug;
    this.maxLength = pattern.length();
    this.specialCharacter = builder.specialChar.charAt(0);
    this.patternCharactersByIndex = new SparseArray<>();
    this.normalCharactersByIndex = new SparseArray<>();
    this.lastText = "";
    this.enabled = true;
    this.savedText = new StringBuilder();
    this.firstIndexToCheck = -1;
    this.lastIndexToCheck = -1;

    initializeIndexes(pattern);
  }

  private void initializeIndexes(String pattern) {
    for (int i = 0; i < pattern.length(); i++) {
      char c = pattern.charAt(i);

      // If current char is present in special characters that we must replace,
      // save it.
      if (specialCharacter == c) {
        normalCharactersByIndex.put(i, c);
      }
      // If current char is not present in special characters,
      // then we save current index and char that should be placed in the future into the string.
      else {
        if (firstIndexToCheck == -1)
          firstIndexToCheck = i;

        lastIndexToCheck = i;
        patternCharactersByIndex.put(i, c);
      }
    }
    if (firstIndexToCheck == -1) {
      // We don't have any chars to automatically insert.
      firstIndexToCheck = 0;
    }
    if (lastIndexToCheck <= 0) {
      // We don't have any chars to automatically insert.
      lastIndexToCheck = pattern.length();
    }
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    LogUtils.logd("beforeTextChanged", s, debug);
  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    LogUtils.logd("onTextChanged", s, debug);
  }

  @Override public synchronized void afterTextChanged(Editable s) {
    LogUtils.logd("afterTextChanged", s, debug);

    if (isEnabled()) {
      final boolean isValidInLength = isValidInLength(s);
      StringBuilder sb = new StringBuilder(s);

      if (!isValidInLength) {
        if (sb.length() != 0) {
          // Delete all chars that exceed the limit.
          sb.delete(maxLength, sb.length());
        }
      }

      final boolean lastTextIsPresentIsSequence = sb.toString().startsWith(lastText);
      final boolean currentStringIsPresentIsSequence = lastText.startsWith(sb.toString());
      final int difference = sb.length() - lastText.length();
      final boolean batchInsert = difference > 1;
      final boolean batchDeletion = difference < -1;
      // Batch insert.
      if (batchInsert && lastTextIsPresentIsSequence) {
        if (saveInput) {
          // Text can be saved when it is saved from scratch.
          if (lastText.isEmpty()) {
            savedText.append(sb.toString());
          } else {
            // Attempt to append right string.
            savedText.append(StringUtils.difference(lastText, sb.toString()));
          }
        }
        insertCharactersIfNeeded(sb, lastText.length(), sb.length(), true);
      } else {
        if (batchDeletion) {
          if (saveInput) {
            // Delete all text if current string is empty.
            if (sb.length() == 0) {
              savedText.setLength(0);
            } else {
              // Attempt to append right string.
              savedText.delete(savedText.length() - StringUtils.difference(sb.toString(),
                  lastText).length() - 1, savedText.length());
            }
          }
          lastText = "";
          insertCharactersIfNeeded(sb, lastText.length(), sb.length(), true);
        }

        // Determine if a character was added.
        if (s.length() > lastText.length()) {
          onCharacterAdded(sb, isValidInLength);
        } else if (s.length() < lastText.length()) {
          onCharacterDeleted(sb);
        }
      }

      // Check if the pattern is followed.
      if (!validatePattern(sb)) {
        insertCharactersIfNeeded(sb, firstIndexToCheck, lastIndexToCheck, false);
      }

      // Finalize the string by replacing the old object into a new one.
      checkIfReplaceIsNeeded(s, sb);
    }

    LogUtils.logd("Saved text", savedText.toString(), debug);
  }

  /**
   * Character was added.
   *
   * @param sb current string.
   */
  private void onCharacterAdded(StringBuilder sb, boolean isValidInLength) {
    // Check if the pattern has characters to replace in current index.
    Character possibleCharToInsert = patternCharactersByIndex.get(sb.length() - 1);

    // Append a pattern character if current index is not empty.
    if (possibleCharToInsert != null) {
      if (!possibleCharToInsert.equals(sb.charAt(sb.length() - 1))) {
        if (fillExtra) {
          // Insert pattern char.
          sb.insert(sb.length() - 1, possibleCharToInsert);

          // Insert pattern chars until they are over.
          while (patternCharactersByIndex.get(sb.length() - 1) != null) {
            sb.insert(sb.length() - 1, patternCharactersByIndex.get(sb.length() - 1));
          }
        } else {
          if (saveInput && isValidInLength) {
            savedText.append(sb.charAt(sb.length() - 1));
          }
          sb.setCharAt(sb.length() - 1, possibleCharToInsert);
        }
      }
    } else {
      if (saveInput && isValidInLength) {
        savedText.append(sb.charAt(sb.length() - 1));
      }
    }
    if (!areThereCharsLeftToReplace(sb))
      insertCharactersIfNeeded(sb, firstIndexToCheck, lastIndexToCheck, false);
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
        deleteIfNextSymbolsArePatternCharacters(sb);

        // Then we delete the symbol, which was intended to be deleted initially.
        if (sb.length() != 0)
          sb.deleteCharAt(sb.length() - 1);

        deleteIfNextSymbolsArePatternCharacters(sb);
      }
      // If the user deleted a normal character.
      else {
        deleteIfNextSymbolsArePatternCharacters(sb);
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
   * If next symbols are also pattern characters,
   * we need to delete them, too.
   *
   * @param sb current string.
   */
  private void deleteIfNextSymbolsArePatternCharacters(StringBuilder sb) {
    // If next symbols are also pattern characters,
    // we need to delete them, too.
    while (patternCharactersByIndex.get(sb.length() - 1) != null) {
      sb.deleteCharAt(sb.length() - 1);
    }
  }

  /**
   * Determine if there are chars left to replace with user input.
   *
   * @param sb current string.
   * @return {@code true} if there are chars left to replace. {@code false} otherwise.
   */
  private boolean areThereCharsLeftToReplace(StringBuilder sb) {
    if (normalCharactersByIndex.get(sb.length()) != null) {
      return true;
    }
    return false;
  }

  /**
   * Insert all missing character if needed.
   *
   * @param sb current string.
   */
  private void insertCharactersIfNeeded(StringBuilder sb, int firstIndexToCheck,
      int lastIndexToCheck, boolean batch) {
    if (sb.length() >= firstIndexToCheck && sb.length() - 1 <= lastIndexToCheck) {
      int i = firstIndexToCheck;

      while (i <= sb.length()) {
        // First check the length of current string.
        // If we exceed the max limit, we need to delete symbols.
        deleteOneLastCharIfLengthExceeds(sb);

        // Then we insert the next character if there is one.
        insertCharacterIfAvailable(sb, i, batch);

        i++;
      }
    }
  }

  /**
   * Insert the pattern character by the specified index if there is one.
   *
   * @param sb current string.
   * @param i  current index to look.
   */
  private void insertCharacterIfAvailable(StringBuilder sb, int i, boolean batch) {
    Character charToInsert = patternCharactersByIndex.get(i);
    if (charToInsert != null) {
      if (i < sb.length()) {
        if (batch) {
          Character normal = normalCharactersByIndex.get(i);
          if (normal == null) {
            insertDepending(sb, i, charToInsert);
          }
        } else {
          if (!charToInsert.equals(sb.charAt(i))) {
            insertDepending(sb, i, charToInsert);
          } else {
            Character normal = normalCharactersByIndex.get(i);
            if (normal == null) {
              sb.setCharAt(i, charToInsert);
            }
          }
        }
      } else {
        if (fillExtra) {
          sb.insert(i, charToInsert);
        }
      }
    }
  }

  /**
   * Insert the character depending on the {@link PatternedTextWatcher#fillExtra} parameter.
   *
   * @param sb           current string.
   * @param i            current index.
   * @param charToInsert char to insert.
   */
  private void insertDepending(StringBuilder sb, int i, Character charToInsert) {
    if (fillExtra) {
      sb.insert(i, charToInsert);
    } else {
      sb.setCharAt(i, charToInsert);
    }
  }

  /**
   * Check whether the Editable is empty or full.
   *
   * @param s the editable to check.
   * @return {@code false} if the {@code s} is empty or exceeds the maximum limit. {@code true}
   * otherwise.
   */
  private boolean isValidInLength(Editable s) {
    if (respectPatternLength && s.length() > maxLength)
      return false;
    return true;
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
  private void deleteOneLastCharIfLengthExceeds(StringBuilder sb) {
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
    boolean patternValidated = true;
    for (int i = 0; i < patternCharactersByIndex.size(); i++) {
      Character character = patternCharactersByIndex.get(i);
      if (character != null && sb.length() > i && character != sb.charAt(i)) {
        LogUtils.logw("validatePattern",
            String.format("Assertion error! Expected \"%1$s\" in index \'%2$s\'." +
                "\nGot \"%3$s\".", character, i, sb.charAt(i)),
            debug);
        patternValidated = false;
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
   * <p>Get the full string which was accumulated during the user's symbols addition.</p>
   * <p><b>NOTE:</b></p>
   * This works inly if you have set {@link Builder#saveAllInput(boolean)} to {@code true}
   * <b>AND</b>
   * {@link Builder#fillExtraCharactersAutomatically(boolean)} to {@code false}.
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
   * {@link Builder#respectPatternLength(boolean)}
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
    String specialChar;
    String pattern;
    boolean fillExtraChars;
    boolean deleteExtraChars;
    boolean saveInput;
    boolean respectPatternLength;
    boolean debug;

    /**
     * Initialize builder with pattern definition. The default {@code specialChar} is '#'.
     * You can set it via {@link Builder#specialChar(String)}.
     *
     * @param pattern the pattern to follow. Can't be null.
     */
    public Builder(String pattern) {
      this.pattern = pattern;
      this.specialChar = DEFAULT_CHAR;
      this.fillExtraChars = true;
      this.deleteExtraChars = true;
      this.saveInput = false;
      this.respectPatternLength = true;
      this.debug = false;
    }

    /**
     * Set the special char which is to be replaced instead of the user input.
     *
     * @param specialChar special char consisting of one or more
     * @return {@link Builder}
     */
    public Builder specialChar(String specialChar) {
      this.specialChar = specialChar;
      return this;
    }

    /**
     * Set the debug param in order to see the debug log in LogCat. Default is {@code false}.
     *
     * @param debug {@code true} to see debug log messages.
     * @return {@link Builder}
     */
    public Builder debug(boolean debug) {
      this.debug = debug;
      return this;
    }

    /**
     * <p>Set the pattern to follow.</p>
     * <p>Example: <b>{@code (###-###)}</b>.</p>
     *
     * @param pattern pattern to follow.
     * @return {@link Builder}
     */
    public Builder pattern(String pattern) {
      this.pattern = pattern;
      return this;
    }

    /**
     * <p>Fill extra characters automatically. Default is {@code true}.</p>
     * <p>If you have a pattern with some characters that are not a part of the special
     * characters,
     * then this tweak will automatically insert these characters for the user.</p>
     *
     * @param fillExtraChars whether you want the extra characters to be
     *                       inserted automatically.
     * @return {@link Builder}
     * @see Builder#deleteExtraCharactersAutomatically(boolean)
     * @see Builder#saveAllInput(boolean)
     */
    public Builder fillExtraCharactersAutomatically(boolean fillExtraChars) {
      this.fillExtraChars = fillExtraChars;
      return this;
    }

    /**
     * Delete extra characters automatically. Default is {@code true}.
     *
     * @param deleteExtraChars whether you want the extra characters to be
     *                         deleted automatically.
     * @return {@link Builder}
     * @see Builder#fillExtraCharactersAutomatically(boolean)
     * @see Builder#saveAllInput(boolean)
     */
    public Builder deleteExtraCharactersAutomatically(boolean deleteExtraChars) {
      this.deleteExtraChars = deleteExtraChars;
      return this;
    }

    /**
     * This tweak works <b>ONLY</b> if you have set the
     * {@link Builder#fillExtraCharactersAutomatically(boolean)} to {@code false},
     * otherwise it will throw {@link IllegalArgumentException} because values can't be saved
     * when the extra characters are inserted automatically.
     *
     * @param saveInput whether you want to save the input of characters hidden under other
     *                  characters.
     * @return {@link Builder}
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
     * @return {@link Builder}
     */
    public Builder respectPatternLength(boolean respectPatternLength) {
      this.respectPatternLength = respectPatternLength;
      return this;
    }

    /**
     * Build the Patterned TextWatcher.
     *
     * @return a
     * {@link PatternedTextWatcher} which you can use for {@link android.widget.TextView}, {@link android.widget.EditText}, etc.
     */
    public PatternedTextWatcher build() {
      return new PatternedTextWatcher(this);
    }
  }
}
