Features must be placed in `assets/features/`. Subdirectories are not supported at this time.

This project has the following dependencies:

* cucumber-android
* [cucumber-java-x.x.x.jar](http://cukes.info/install-cucumber-jvm.html)

#### Using an IDE

1. Create an Android test-project from these sources.
2. Create a run/debug configuration with `at.mfellner.cucumber.android.api.CucumberInstrumentation`
3. Include cucumber-android as a library project **or** put cucumber-android-x.x.x.jar into `libs/` (and include it).
4. Put cucumber-java-x.x.x.jar into `libs/` (and include it).

If you're using **ant,** first set up **cucumber-android** as described in its readme. Then do this:

#### Using ant

1. By default, `project.properties` includes cucumber-android as a library project. Remove the appropriate line, if you want to put cucumber-java-x.x.x.jar into `libs/` instead.
2. Put `cucumber-java-x.x.x.jar` into `libs/`.
3. Make sure everything is set up to [build and run from the command line](http://developer.android.com/tools/building/building-cmdline.html).
4. Use `run_test.sh` to [run the tests with adb](http://developer.android.com/tools/testing/testing_otheride.html#RunTestsCommand).
