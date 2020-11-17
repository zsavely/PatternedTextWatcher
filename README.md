# Deprecated
Releated projects:
1. https://github.com/RedMadRobot/input-mask-android
2. https://github.com/TinkoffCreditSystems/decoro

PatternedTextWatcher
========
[![Build Status](https://travis-ci.org/zsavely/PatternedTextWatcher.svg?branch=master)](https://travis-ci.org/zsavely/PatternedTextWatcher)
[![Download](https://api.bintray.com/packages/zsavely/maven/patternedtextwatcher/images/download.svg) ](https://bintray.com/zsavely/maven/patternedtextwatcher/_latestVersion)
[![codecov](https://codecov.io/gh/zsavely/PatternedTextWatcher/branch/master/graph/badge.svg)](https://codecov.io/gh/zsavely/PatternedTextWatcher)
<a href="http://www.methodscount.com/?lib=com.szagurskii%3Apatternedtextwatcher%3A0.5.0"><img src="https://img.shields.io/badge/Methods and size-127 | 13 KB-e91e63.svg"></img></a>

Customizable TextWatcher for implementing different input types very quickly.

### Dependency
```groovy
compile 'com.szagurskii:patternedtextwatcher:0.5.0'
```

### How to use

1. Basic setup

    ```java
     // Format input string as in the pattern.
     EditText editText = (EditText) findViewById(R.id.edittext);
     editText.addTextChangedListener(new PatternedTextWatcher("(###) ###-##-##"));
     
     // Format four digits to a mask of a credit card number. 
     TextView textView = (TextView) findViewById(R.id.textview);
     textView.addTextChangedListener(new PatternedTextWatcher("**** **** **** ####"));
    ```

2. Advanced setup
    ```java
     EditText editText = (EditText) findViewById(R.id.edittext);
     PatternedTextWatcher patternedTextWatcher = new PatternedTextWatcher.Builder("###-###")
            .fillExtraCharactersAutomatically(true)
            .deleteExtraCharactersAutomatically(true)
            .specialChar("#")
            .respectPatternLength(true)
            .saveAllInput(false)
            .build();
     editText.addTextChangedListener(patternedTextWatcher);
    ```
    
### Snapshot
```groovy
repositories {
  maven {
    url "https://oss.sonatype.org/content/repositories/snapshots"
  }
}
```

```groovy
compile 'com.szagurskii:patternedtextwatcher:0.6.0-SNAPSHOT'
```
    
### License

    The MIT License (MIT)

    Copyright (c) 2016 Savelii Zagurskii

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
