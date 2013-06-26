## Cucumber on Android

This project provides an Android-backend to [cucumber-jvm](https://github.com/cucumber/cucumber-jvm) and is basically an updated and improved version of [ccady-ubermind](https://github.com/ccady-ubermind/cucumber-android)'s *cucumber-android.*

**ATTENTION!** This repository is no longer actively maintained. Cucumber-android has become an official module of [cucumber-jvm](https://github.com/cucumber/cucumber-jvm).

## Getting started

Read the `README` from `cucumber-android/` to either

* use cucumber-android as an Android library-project or
* build a cucumber-android .jar to include elsewhere.

You can also **[download cucumber-android.jar here.](https://sourceforge.net/projects/cucumberandroid/files/)**

For an example, please look at `cucumber-example-test/`.

### Using cucumber-android in your own project

You can use cucumber-android as an Android library-project or use a pre-built .jar.

1. Create a new empty [Android test-project](http://developer.android.com/tools/testing/index.html).
2. Change the [instrumentation](http://developer.android.com/tools/testing/testing_android.html#InstrumentationTestRunner) in `AndroidManifest.xml` from `InstrumentationTestRunner` to `CucumberInstrumentation`:

```xml
<instrumentation
  android:name="cucumber.api.android.CucumberInstrumentation"
  android:targetPackage="package.of.your.own.application"/>
```

3. Include cucumber-android and [cucumber-java](http://cukes.info/install-cucumber-jvm.html).
4. Create a new test-configuration that uses the `CucumberInstrumentation` instrumentation runner.

### Features and step definitions

1. Put all your `.feature` files inside  the subdirectory `assets/features/` of the test-project.
2. For your [step-defintions](http://cukes.info/step-definitions.html), you must use an `InstrumentationTest` (e.g. `ActivityInstrumentationTestCase2`).
3. You can have multiple test-classes for your step-definitions, refer to `cucumber-example-test/` for an example.
4. Start the test-project and the `CucumberInstrumentation` will run all your features.

*You can use @RunWithCucumber to pass parameters to Cucumber.*
