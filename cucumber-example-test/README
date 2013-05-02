Features must be placed in /assets/features. Subdirectories are not supported at this time.

This project has the following build-dependencies:
cucumber-android (library project)
cucumber-java-1.1.3.jar

If you're using an IDE, first import this directory as a test project. Then there are two ways to get going:

A) Include cucumber-android as a library project. cucumber-android's dependencies should be exported (i.e., included in this project).
B) Include cucumber-android.jar and all its dependencies (i.e., the necessary .jars).

If you're using ant, first set up cucumber-android as described in its readme. Then do this:

1. Copy cucumber-java-x.x.x.jar into the /libs subdirectory. This is necessary, because the Android ant-build-system sucks, and you can't reference the .jars from somewhere else.
2. Make sure to set `$ANDROID_HOME` or create local.properties with `sdk.dir=/path/to/sdk`.
3. Run `run_test.sh`, because the ant-job 'test' doesn't respect the custom Instrumentation.
