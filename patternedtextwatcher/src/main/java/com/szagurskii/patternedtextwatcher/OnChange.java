package com.szagurskii.patternedtextwatcher;

/**
 * This method is called to notify you that, within <code>s</code>,
 * the <code>count</code> characters beginning at <code>start</code>
 * have just replaced old text that had length <code>before</code>.
 * It is an error to attempt to make changes to <code>s</code> from
 * this callback.
 */
final class OnChange {
  /** Resulting char sequence. */
  private final CharSequence s;

  /** The index of starting the change. */
  private final int start;

  /** The length of the replaced old text. */
  private final int before;

  /** How many symbols were inserted. */
  private final int count;

  static OnChange createOnChange(CharSequence charSequence, int start, int before, int count) {
    return new OnChange(charSequence, start, before, count);
  }

  private OnChange(CharSequence s, int start, int before, int count) {
    this.s = s;
    this.start = start;
    this.before = before;
    this.count = count;
  }

  /** Resulting char sequence. */
  public CharSequence s() {
    return s;
  }

  /** The length of the replaced old text. */
  public int start() {
    return start;
  }

  /** The length of the replaced old text. */
  public int before() {
    return before;
  }

  /** How many symbols were inserted. */
  public int count() {
    return count;
  }

  @Override public String toString() {
    return "OnChange{" +
        "s=" + s +
        ", start=" + start +
        ", before=" + before +
        ", count=" + count +
        '}';
  }
}
