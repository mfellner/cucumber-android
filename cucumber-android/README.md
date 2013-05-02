Run `ant dependencies` to download the dependencies into /libs.

This project has the following dependencies:
cucumber-core-1.1.3.jar
cucumber-java-1.1.3.jar
cucumber-junit-1.1.3.jar
hamcrest-core-1.3.jar
junit-4.11.jar

If you're using Eclipse, the .jars in /libs should be automatically included. In IDEA you need to include them manually.

If you're using ant, you should be able to just run `ant debug` or `ant release`.

When building with ant, also make sure to set `$ANDROID_HOME or create local.properties with `sdk.dir=/path/to/sdk`.
