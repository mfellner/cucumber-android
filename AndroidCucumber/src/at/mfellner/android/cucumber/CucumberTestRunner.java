package at.mfellner.android.cucumber;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.test.AndroidTestRunner;
import android.test.TestSuiteProvider;
import android.util.Log;
import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import dalvik.system.DexFile;
import ext.com.android.internal.os.LoggingPrintStream;
import gherkin.formatter.Formatter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CucumberTestRunner extends Instrumentation implements TestSuiteProvider {

    //    public static final String ARGUMENT_TEST_CLASS = "class";
//    public static final String ARGUMENT_TEST_PACKAGE = "package";
//    public static final String ARGUMENT_TEST_SIZE_PREDICATE = "size";
//    public static final String ARGUMENT_DELAY_MSEC = "delay_msec";
//    private static final String SMALL_SUITE = "small";
//    private static final String MEDIUM_SUITE = "medium";
//    private static final String LARGE_SUITE = "large";
//    private static final String ARGUMENT_LOG_ONLY = "log";
//    static final String ARGUMENT_ANNOTATION = "annotation";
//    static final String ARGUMENT_NOT_ANNOTATION = "notAnnotation";
//    private static final float SMALL_SUITE_MAX_RUNTIME = 100;
//    private static final float MEDIUM_SUITE_MAX_RUNTIME = 1000;
//    public static final String REPORT_VALUE_ID = "InstrumentationTestRunner";
//    public static final String REPORT_KEY_NUM_TOTAL = "numtests";
//    public static final String REPORT_KEY_NUM_CURRENT = "current";
//    public static final String REPORT_KEY_NAME_CLASS = "class";
//    public static final String REPORT_KEY_NAME_TEST = "test";
//    private static final String REPORT_KEY_RUN_TIME = "runtime";
//    private static final String REPORT_KEY_NUM_ITERATIONS = "numiterations";
//    private static final String REPORT_KEY_SUITE_ASSIGNMENT = "suiteassignment";
//    private static final String REPORT_KEY_COVERAGE_PATH = "coverageFilePath";
//    public static final int REPORT_VALUE_RESULT_START = 1;
//    public static final int REPORT_VALUE_RESULT_OK = 0;
//    public static final int REPORT_VALUE_RESULT_ERROR = -1;
//    public static final int REPORT_VALUE_RESULT_FAILURE = -2;
//    public static final String REPORT_KEY_STACK = "stack";
//    // Default file name for code coverage
//    private static final String DEFAULT_COVERAGE_FILE_NAME = "coverage.ec";
//    private static final String LOG_TAG = "InstrumentationTestRunner";
//    private final Bundle mResults = new Bundle();
//    private Bundle mArguments;
    private AndroidTestRunner mTestRunner;
    //    private boolean mDebug;
//    private boolean mJustCount;
//    private boolean mSuiteAssignmentMode;
//    private int mTestCount;
//    private String mPackageOfTests;
//    private boolean mCoverage;
//    private String mCoverageFilePath;
//    private int mDelayMsec;
    public static final String TAG = "cucumber-android";
    private Runtime mRuntime;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        Context context = getContext();
        ClassLoader classLoader = context.getClassLoader();
        String packageName = context.getPackageName();

        Properties properties = new Properties();
        // hack: the package name of the test-project should have '.test' at the end
        String glue = packageName.substring(0, packageName.lastIndexOf(".test"));
        // this must be a subdirectory of the apk's assets directory
        String features = "features";
        properties.setProperty("cucumber.options", String.format("-g %s %s", glue, features));
        RuntimeOptions runtimeOptions = new RuntimeOptions(properties);

        Formatter formatter = new AndroidFormatter(TAG);
        runtimeOptions.formatters.add(formatter);

        ResourceLoader resourceLoader = new AndroidResourceLoader(context);
        List<Backend> backends = new ArrayList<Backend>();
        backends.add(new AndroidBackend(context));
        mRuntime = new Runtime(resourceLoader, classLoader, backends, runtimeOptions);

//        mTestRunner = new AndroidTestRunner();
//        mTestRunner.setContext(getTargetContext());
//        mTestRunner.setInstrumentation(this);
//        mTestRunner.setTest(getTestSuite());


//        mArguments = arguments;
//
//        // Apk paths used to search for test classes when using TestSuiteBuilders.
//        String[] apkPaths =
//                {getTargetContext().getPackageCodePath(), getContext().getPackageCodePath()};
//        ClassPathPackageInfoSource.setApkPaths(apkPaths);
//
//        Predicate<TestMethod> testSizePredicate = null;
//        Predicate<TestMethod> testAnnotationPredicate = null;
//        Predicate<TestMethod> testNotAnnotationPredicate = null;
//        String testClassesArg = null;
//        boolean logOnly = false;
//
//        if (arguments != null) {
//            // Test class name passed as an argument should override any meta-data declaration.
//            testClassesArg = arguments.getString(ARGUMENT_TEST_CLASS);
//            mDebug = getBooleanArgument(arguments, "debug");
//            mJustCount = getBooleanArgument(arguments, "count");
//            mSuiteAssignmentMode = getBooleanArgument(arguments, "suiteAssignment");
//            mPackageOfTests = arguments.getString(ARGUMENT_TEST_PACKAGE);
//            testSizePredicate = getSizePredicateFromArg(
//                    arguments.getString(ARGUMENT_TEST_SIZE_PREDICATE));
//            testAnnotationPredicate = getAnnotationPredicate(
//                    arguments.getString(ARGUMENT_ANNOTATION));
//            testNotAnnotationPredicate = getNotAnnotationPredicate(
//                    arguments.getString(ARGUMENT_NOT_ANNOTATION));
//
//            logOnly = getBooleanArgument(arguments, ARGUMENT_LOG_ONLY);
//            mCoverage = getBooleanArgument(arguments, "coverage");
//            mCoverageFilePath = arguments.getString("coverageFile");
//
//            try {
//                Object delay = arguments.get(ARGUMENT_DELAY_MSEC);  // Accept either string or int
//                if (delay != null) mDelayMsec = Integer.parseInt(delay.toString());
//            } catch (NumberFormatException e) {
//                Log.e(LOG_TAG, "Invalid delay_msec parameter", e);
//            }
//        }
//
//        TestSuiteBuilder testSuiteBuilder = new TestSuiteBuilder(getClass().getName(),
//                getTargetContext().getClassLoader());
//
//        if (testSizePredicate != null) {
//            testSuiteBuilder.addRequirements(testSizePredicate);
//        }
//        if (testAnnotationPredicate != null) {
//            testSuiteBuilder.addRequirements(testAnnotationPredicate);
//        }
//        if (testNotAnnotationPredicate != null) {
//            testSuiteBuilder.addRequirements(testNotAnnotationPredicate);
//        }
//
//        if (testClassesArg == null) {
//            if (mPackageOfTests != null) {
//                testSuiteBuilder.includePackages(mPackageOfTests);
//            } else {
//                TestSuite testSuite = getTestSuite();
//                if (testSuite != null) {
//                    testSuiteBuilder.addTestSuite(testSuite);
//                } else {
//                    // no package or class bundle arguments were supplied, and no test suite
//                    // provided so add all tests in application
//                    testSuiteBuilder.includePackages("");
//                }
//            }
//        } else {
//            parseTestClasses(testClassesArg, testSuiteBuilder);
//        }
//
//        testSuiteBuilder.addRequirements(getBuilderRequirements());
//
//        mTestRunner = getAndroidTestRunner();
//        mTestRunner.setContext(getTargetContext());
//        mTestRunner.setInstrumentation(this);
//        mTestRunner.setSkipExecution(logOnly);
//        mTestRunner.setTest(testSuiteBuilder.build());
//        mTestCount = mTestRunner.getTestCases().size();
//        if (mSuiteAssignmentMode) {
//            mTestRunner.addTestListener(new SuiteAssignmentPrinter());
//        } else {
//            WatcherResultPrinter resultPrinter = new WatcherResultPrinter(mTestCount);
//            mTestRunner.addTestListener(new TestPrinter("TestRunner", false));
//            mTestRunner.addTestListener(resultPrinter);
//            mTestRunner.setPerformanceResultsWriter(resultPrinter);
//        }
        start();
    }

//    public Bundle getBundle() {
//        return mArguments;
//    }

//    protected void addTestListener(TestListener listener) {
//        if (mTestRunner != null && listener != null) {
//            mTestRunner.addTestListener(listener);
//        }
//    }

//    List<Predicate<TestMethod>> getBuilderRequirements() {
//        return new ArrayList<Predicate<TestMethod>>();
//    }

//    private void parseTestClasses(String testClassArg, TestSuiteBuilder testSuiteBuilder) {
//        String[] testClasses = testClassArg.split(",");
//        for (String testClass : testClasses) {
//            parseTestClass(testClass, testSuiteBuilder);
//        }
//    }

//    private void parseTestClass(String testClassName, TestSuiteBuilder testSuiteBuilder) {
//        int methodSeparatorIndex = testClassName.indexOf('#');
//        String testMethodName = null;
//
//        if (methodSeparatorIndex > 0) {
//            testMethodName = testClassName.substring(methodSeparatorIndex + 1);
//            testClassName = testClassName.substring(0, methodSeparatorIndex);
//        }
//        testSuiteBuilder.addTestClassByName(testClassName, testMethodName, getTargetContext());
//    }

//    protected AndroidTestRunner getAndroidTestRunner() {
//        return new AndroidTestRunner();
//    }

//    private boolean getBooleanArgument(Bundle arguments, String tag) {
//        String tagString = arguments.getString(tag);
//        return tagString != null && Boolean.parseBoolean(tagString);
//    }

//    private Predicate<TestMethod> getSizePredicateFromArg(String sizeArg) {
//        if (SMALL_SUITE.equals(sizeArg)) {
//            return TestPredicates.SELECT_SMALL;
//        } else if (MEDIUM_SUITE.equals(sizeArg)) {
//            return TestPredicates.SELECT_MEDIUM;
//        } else if (LARGE_SUITE.equals(sizeArg)) {
//            return TestPredicates.SELECT_LARGE;
//        } else {
//            return null;
//        }
//    }

//    private Predicate<TestMethod> getAnnotationPredicate(String annotationClassName) {
//        Class<? extends Annotation> annotationClass = getAnnotationClass(annotationClassName);
//        if (annotationClass != null) {
//            return new HasAnnotation(annotationClass);
//        }
//        return null;
//    }

//    private Predicate<TestMethod> getNotAnnotationPredicate(String annotationClassName) {
//        Class<? extends Annotation> annotationClass = getAnnotationClass(annotationClassName);
//        if (annotationClass != null) {
//            return Predicates.not(new HasAnnotation(annotationClass));
//        }
//        return null;
//    }

//    private Class<? extends Annotation> getAnnotationClass(String annotationClassName) {
//        if (annotationClassName == null) {
//            return null;
//        }
//        try {
//            Class<?> annotationClass = Class.forName(annotationClassName);
//            if (annotationClass.isAnnotation()) {
//                return (Class<? extends Annotation>) annotationClass;
//            } else {
//                Log.e(LOG_TAG, String.format("Provided annotation value %s is not an Annotation",
//                        annotationClassName));
//            }
//        } catch (ClassNotFoundException e) {
//            Log.e(LOG_TAG, String.format("Could not find class for specified annotation %s",
//                    annotationClassName));
//        }
//        return null;
//    }

//    void prepareLooper() {
//        Looper.prepare();
//    }

    @Override
    public void onStart() {
        Looper.prepare();

        LoggingPrintStream loggingPrintStream = new LoggingPrintStream() {
            @Override
            protected void log(String line) {
                Log.d(TAG, line);
            }
        };
        System.setOut(loggingPrintStream);

        try {
            mRuntime.run();
        } catch (Exception e) {
            Log.e(TAG, "Cucumber runtime error:", e);
        } finally {
            getContext().getAssets().close();
        }
//        mTestRunner.runTest();
        finish(Activity.RESULT_OK, new Bundle());

//        prepareLooper();
//
//        if (mJustCount) {
//            mResults.putString(Instrumentation.REPORT_KEY_IDENTIFIER, REPORT_VALUE_ID);
//            mResults.putInt(REPORT_KEY_NUM_TOTAL, mTestCount);
//            finish(Activity.RESULT_OK, mResults);
//        } else {
//            if (mDebug) {
//                Debug.waitForDebugger();
//            }
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            PrintStream writer = new PrintStream(byteArrayOutputStream);
//            try {
//                StringResultPrinter resultPrinter = new StringResultPrinter(writer);
//
//                mTestRunner.addTestListener(resultPrinter);
//
//                long startTime = System.currentTimeMillis();
//                mTestRunner.runTest();
//                long runTime = System.currentTimeMillis() - startTime;
//
//                resultPrinter.print(mTestRunner.getTestResult(), runTime);
//            } catch (Throwable t) {
//                // catch all exceptions so a more verbose error message can be outputted
//                writer.println(String.format("Test run aborted due to unexpected exception: %s",
//                        t.getMessage()));
//                t.printStackTrace(writer);
//            } finally {
//                mResults.putString(Instrumentation.REPORT_KEY_STREAMRESULT,
//                        String.format("\nTest results for %s=%s",
//                                mTestRunner.getTestClassName(),
//                                byteArrayOutputStream.toString()));
//                if (mCoverage) {
//                    generateCoverageReport();
//                }
//                writer.close();
//
//                finish(Activity.RESULT_OK, mResults);
//            }
//        }
    }

    @Override
    public TestSuite getTestSuite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new CucumberTest());
        return suite;
    }

    private final class CucumberTest extends TestCase {
        @Override
        public int countTestCases() {
            return 1;
        }

        @Override
        public void run(TestResult testResult) {
            try {
                mRuntime.run();
            } catch (Exception e) {
                Log.e(TAG, "Cucumber runtime error:", e);
            } finally {
                getContext().getAssets().close();
            }
            testResult.endTest(CucumberTest.this);
        }
    }
}
