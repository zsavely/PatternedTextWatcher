package com.szagurskii.patternedtextwatcher.sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle(R.string.activity_name);

    if (!isInstrumentedRun()) {
      EditText editText = (EditText) findViewById(R.id.et_sample);
      editText.addTextChangedListener(new PatternedTextWatcher.Builder("###)))(((###")
          .fillExtraCharactersAutomatically(true)
          .deleteExtraCharactersAutomatically(true)
          .debug(true)
          .build());
    }
  }

  private boolean isInstrumentedRun() {
    boolean result;
    try {
      getApplication()
          .getClassLoader()
          .loadClass("com.szagurskii.patternedtextwatcher.sample.BasicAdditionTests");
      result = true;
    } catch (Throwable throwable) {
      result = false;
    }
    return result;
  }
}
