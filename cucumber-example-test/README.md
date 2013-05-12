Features must be placed in `assets/features/`. Subdirectories are not supported at this time.

This project has the following dependencies:

* cucumber-android
* [cucumber-java-x.x.x.jar](http://cukes.info/install-cucumber-jvm.html)

#### Using an IDE

1. Create an Android test-project from these sources and include `cucumber-example/` (as the tested project).
2. Create a run/debug configuration with `at.mfellner.cucumber.android.api.CucumberInstrumentation`
3. Include cucumber-android as a library-project **or** put cucumber-android-x.x.x.jar into `libs/` (and include it).
4. Put cucumber-java-x.x.x.jar into `libs/` (and include it).

*For each Android project, Eclipse and ADT will automatically include all .jars from the libs/ directory.*

*In IDEA you need to manually include dependencies for each module. However, you can share dependencies across modules by exporting them in order to avoid duplicate files.*

#### Using ant

1. By default, `project.properties` includes cucumber-android as a library project. Remove the appropriate line if you want to put cucumber-java-x.x.x.jar into `libs/` instead.
2. `ant.properties` already defines `cucumber-example/` as the tested project dir.
2. Put `cucumber-java-x.x.x.jar` into `libs/`.
3. Make sure everything is set up to [build and run from the command line](http://developer.android.com/tools/building/building-cmdline.html).
4. Use `run_test.sh` to [run the tests with adb](http://developer.android.com/tools/testing/testing_otheride.html#RunTestsCommand).

*For each Android project, ant will automatically include all .jars from the libs/ directory.*
