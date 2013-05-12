You can run `ant dependencies` to download the required libraries into `libs/`.

This project has the following dependencies:

* [cucumber-core-x.x.x.jar](http://cukes.info/install-cucumber-jvm.html)
* [cucumber-java-x.x.x.jar](http://cukes.info/install-cucumber-jvm.html)
* [junit-4.x.jar](https://oss.sonatype.org/content/repositories/releases/junit/junit)

#### Using an IDE

1. Create an Android library-project from these sources.
2. Make sure that the required libraries from `libs/` are included.

#### Using ant

1. Put the required libraries into `libs/`.
2. Make sure everything is set up to [build and run from the command line](http://developer.android.com/tools/building/building-cmdline.html).
3. You can run `ant release` to build `classes.jar`, which can be renamed to cucumber-android.jar and be included elsewhere.
