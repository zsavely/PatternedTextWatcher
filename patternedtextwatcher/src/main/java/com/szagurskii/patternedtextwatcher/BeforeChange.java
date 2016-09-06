package com.szagurskii.patternedtextwatcher;

/**
 * This method is called to notify you that, within <code>s</code>,
 * the <code>count</code> characters beginning at <code>start</code>
 * are about to be replaced by new text with length <code>after</code>.
 * It is an error to attempt to make changes to <code>s</code> from
 * this callback.
 */
final class BeforeChange {
  /** Resulting char sequence. */
  private final CharSequence s;

  /** The index of starting the change. */
  private final int start;

  /** How many symbols were deleted. */
  private final int count;

  /** The length of the new text that is to be inserted/replaced. */
  private final int after;

  static BeforeChange createBeforeChange(CharSequence charSequence, int start, int count, int after) {
    return new BeforeChange(charSequence, start, count, after);
  }

  private BeforeChange(CharSequence s, int start, int count, int after) {
    this.s = s;
    this.start = start;
    this.count = count;
    this.after = after;
  }

  /** Resulting char sequence. */
  public CharSequence s() {
    return s;
  }

  /** The index of starting the change. */
  public int start() {
    return start;
  }

  /** How many symbols were deleted. */
  public int count() {
    return count;
  }

  /** The length of the new text that is to be inserted/replaced. */
  public int after() {
    return after;
  }

  @Override public String toString() {
    return "BeforeChange{" +
        "s=" + s +
        ", start=" + start +
        ", count=" + count +
        ", after=" + after +
        '}';
  }
}
