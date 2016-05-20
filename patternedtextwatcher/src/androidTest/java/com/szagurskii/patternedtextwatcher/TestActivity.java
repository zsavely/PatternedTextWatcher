package com.szagurskii.patternedtextwatcher;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public final class TestActivity extends Activity {
  EditText editText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    editText = new EditText(this);
    editText.setId(android.R.id.primary);
    setContentView(editText);
  }
}
