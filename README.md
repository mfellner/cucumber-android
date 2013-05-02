# Cucumber on Android
This project is basically an updated and improved version of [ccady-ubermind](https://github.com/ccady-ubermind/cucumber-android)'s **cucumber-android**.
# Setup
0. Run `ant dependencies` inside the `cucumber-android` subdirectory or manually download the dependencies listed in the README of that directory.
1. Include **cucumber-android** as a library project or [download the jar](http://sourceforge.net/projects/cucumberandroid/files/cucumber-android.jar/download). Don't forget to include the dependencies.
2. Create an empty Android test-project and set the `instrumentation` like so:

```xml
<instrumentation
  android:name="at.mfellner.cucumber.android.api.CucumberInstrumentation"
  android:targetPackage="at.mfellner.android.cucumber.example"/>
```

(Set `targetPackage` for the application you want to test.)
3. Make a new test-configuration for that project using the `CucumberInstrumentation`.
4. Create a new `InstrumentationTest` (e.g., `ActivityInstrumentationTest2`).
# Features and step definitions
1. Put all your `.feature` files inside  the subdirectory `/assets/features` of the test-project.
2. Put your [step-defintions](http://cukes.info/step-definitions.html) inside the `InstrumentationTest` you created. You can have multiple test-classes to better organize your steps.
3. Run the tests like you would any ordinary Android test.
4. Some things don't work yet; this is an early version.