Run `ant dependencies` to download the required libraries into /libs.

This project has the following dependencies:
* cucumber-core-1.1.3.jar
* cucumber-java-1.1.3.jar
* junit-4.11.jar

If you're using Eclipse, the .jars in *libs/* should be automatically included. In IDEA you need to include them manually.

If you're using ant, you should be able to just run `ant debug`. Run `ant release` to create a `classes.jar` which can be included as a standalone library.

When building with **ant,** also make sure to set `$ANDROID_HOME` or create local.properties with `sdk.dir=/path/to/sdk`.
