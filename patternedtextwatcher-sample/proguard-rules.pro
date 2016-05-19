# Jacoco
# Keep everything for the emma classes
-keep class com.vladium.** {
  *;
}
# Keep everything for the jacoco classes
-keep class org.jacoco.** {
  *;
}

# Don't warn.
-dontwarn org.jacoco.**
