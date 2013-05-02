package at.mfellner.cucumber.android.api;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class CucumberInstrumentation extends Instrumentation {
    public static final String REPORT_VALUE_ID = "InstrumentationTestRunner";
    public static final String REPORT_KEY_NUM_TOTAL = "numtests";
    public static final String REPORT_KEY_NUM_CURRENT = "current";
    public static final String REPORT_KEY_NAME_CLASS = "class";
    public static final String REPORT_KEY_NAME_TEST = "test";
    public static final int REPORT_VALUE_RESULT_START = 1;
    public static final int REPORT_VALUE_RESULT_ERROR = -1;
    public static final int REPORT_VALUE_RESULT_FAILURE = -2;
    public static final String REPORT_KEY_STACK = "stack";
    public static final String TAG = "cucumber-android";
    private CucumberTestRunner mTestRunner;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        try {
            mTestRunner = new CucumberTestRunner(this);
        } catch (InitializationError e) {
            Log.e(TAG, "cannot instantiate CucumberTestRunner", e);
            finish(Activity.RESULT_CANCELED, null);
        }
        start();
    }

    @Override
    public void onStart() {
        Looper.prepare();

        int numTests = mTestRunner.getChildren().size();
        CucumberRunListener listener = new CucumberRunListener(numTests);

        RunNotifier notifier = new RunNotifier();
        notifier.addFirstListener(listener);

        mTestRunner.run(notifier);

        finish(Activity.RESULT_OK, new Bundle());
    }

    private class CucumberRunListener extends RunListener {
        private final Bundle mResultTemplate;
        private Bundle mTestResult;
        private int mTestNum;
        private int mTestResultCode;
        private String mTestClass;

        public CucumberRunListener(int numTests) {
            mResultTemplate = new Bundle();
            mResultTemplate.putString(Instrumentation.REPORT_KEY_IDENTIFIER, REPORT_VALUE_ID);
            mResultTemplate.putInt(REPORT_KEY_NUM_TOTAL, numTests);
        }

        @Override
        public void testStarted(Description description) {
            String testClass = description.getClassName();
            String testName = description.getDisplayName();

            mTestResult = new Bundle(mResultTemplate);
            mTestResult.putString(REPORT_KEY_NAME_CLASS, testClass);
            mTestResult.putString(REPORT_KEY_NAME_TEST, testName);
            mTestResult.putInt(REPORT_KEY_NUM_CURRENT, ++mTestNum);
            // pretty printing
            if (testClass != null && !testClass.equals(mTestClass)) {
                mTestResult.putString(Instrumentation.REPORT_KEY_STREAMRESULT, String.format("\n%s:", testClass));
                mTestClass = testClass;
            } else {
                mTestResult.putString(Instrumentation.REPORT_KEY_STREAMRESULT, description.getMethodName());
            }
            sendStatus(REPORT_VALUE_RESULT_START, mTestResult);
            mTestResultCode = 0;
        }

        @Override
        public void testFinished(Description description) {
            if (mTestResultCode == 0) {
                mTestResult.putString(Instrumentation.REPORT_KEY_STREAMRESULT, ".");
            }
            sendStatus(mTestResultCode, mTestResult);
        }

        @Override
        public void testFailure(Failure failure) {
            String trace = failure.getTrace();
            mTestResult.putString(REPORT_KEY_STACK, trace);
            mTestResultCode = REPORT_VALUE_RESULT_ERROR;
            // pretty printing
            mTestResult.putString(Instrumentation.REPORT_KEY_STREAMRESULT,
                    String.format("\nError in %s:\n%s", failure.getTestHeader(), trace));
        }

        @Override
        public void testAssumptionFailure(Failure failure) {
            String trace = failure.getTrace();
            mTestResult.putString(REPORT_KEY_STACK, trace);
            mTestResultCode = REPORT_VALUE_RESULT_FAILURE;
            // pretty printing
            mTestResult.putString(Instrumentation.REPORT_KEY_STREAMRESULT,
                    String.format("\nFailure in %s:\n%s", failure.getTestHeader(), trace));
        }
    }
}
