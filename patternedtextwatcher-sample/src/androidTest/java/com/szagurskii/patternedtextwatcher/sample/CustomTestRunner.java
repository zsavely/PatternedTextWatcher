package com.szagurskii.patternedtextwatcher.sample;

import android.support.test.runner.AndroidJUnitRunner;

public final class CustomTestRunner extends AndroidJUnitRunner {
  @Override public void onStart() {
    super.onStart();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }
}
