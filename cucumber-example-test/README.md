## Cukeulator Example Test
This is the example test-project for the Cukeulator app.


### Setup
Features must be placed in `assets/features/`. Subdirectories are allowed.

This project has the following dependencies:

* cucumber-android
* cucumber-java
...
**TODO**

#### Using ant
1. Please read ["Building and Running from the Command Line"](https://developer.android.com/tools/building/building-cmdline.html).
2. `ant.properties` already defines `cucumber-example/` as the tested project dir.
2. Put `cucumber-java-x.x.x.jar` into `libs/`.
3. Make sure everything is set up to [build and run from the command line](http://developer.android.com/tools/building/building-cmdline.html).
4. Use `run_test.sh` to [run the tests with adb](http://developer.android.com/tools/testing/testing_otheride.html#RunTestsCommand).

*Note: For each Android project, ant will automatically include all .jars from the libs/ directory.*

#### Using an IDE
1. Please read ["Building and Running from Eclipse with ADT"](https://developer.android.com/tools/building/building-eclipse.html).
2. Create an Android test-project from these sources with `cucumber-example/` as the tested project.
3. Create a run configuration with `cucumber.android.api.CucumberInstrumentation` as the instrumentation.
4. Put the required jar dependencies into `libs/`.

*Note for IDEA users: You need to manually include jars from /libs for each module. However, you can share dependencies across modules by exporting them in order to avoid duplicate files.*
