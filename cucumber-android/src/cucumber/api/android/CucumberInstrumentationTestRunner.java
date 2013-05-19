package cucumber.api.android;

import android.content.Context;
import android.os.Bundle;
import android.test.InstrumentationTestRunner;
import android.util.Log;
import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.android.AndroidBackend;
import cucumber.runtime.android.AndroidFormatter;
import cucumber.runtime.android.AndroidResourceLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.*;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static cucumber.runtime.Runtime.isPending;

/*
 * This class will run Cucumber features in the default assets/features/ directory,
 * only if no target class is defined in the meta-data or command line argument parameters.
 * Otherwise it acts like a normal InstrumentationTestRunner.
 */
public class CucumberInstrumentationTestRunner extends InstrumentationTestRunner {
    public static final String TAG = "cucumber-android";
    private RuntimeOptions mRuntimeOptions;
    private ResourceLoader mResourceLoader;
    private AndroidFormatter mFormatter;
    private cucumber.runtime.Runtime mRuntime;

    @Override
    public void onCreate(Bundle arguments) {
        Context context = getContext();
        Properties properties = new Properties();
        String glue = context.getPackageName();
        String features = "features";

        properties.setProperty("cucumber.options", String.format("-g %s %s", glue, features));
        mRuntimeOptions = new RuntimeOptions(properties);

        mResourceLoader = new AndroidResourceLoader(context);
        List<Backend> backends = new ArrayList<Backend>();
        backends.add(new AndroidBackend(this));
        mRuntime = new Runtime(mResourceLoader, getLoader(), backends, mRuntimeOptions);

        mFormatter = new AndroidFormatter(TAG);
        mRuntimeOptions.formatters.clear();
        mRuntimeOptions.formatters.add(mFormatter);

        super.onCreate(arguments);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public TestSuite getAllTests() {
        TestSuite testSuite = new TestSuite();
        List<CucumberFeature> cucumberFeatures = mRuntimeOptions.cucumberFeatures(mResourceLoader);

        for (CucumberFeature feature : cucumberFeatures) {
            for (CucumberTagStatement statement : feature.getFeatureElements()) {
                if (statement instanceof CucumberScenario) {
                    CucumberScenario scenario = (CucumberScenario) statement;
                    testSuite.addTest(new ScenarioTest(scenario, mFormatter, mRuntime));
                } else if (statement instanceof CucumberScenarioOutline) {
                    for (CucumberExamples examples : ((CucumberScenarioOutline) statement).getCucumberExamplesList()) {
                        for (CucumberScenario scenario : examples.createExampleScenarios()) {
                            testSuite.addTest(new ScenarioTest(scenario, mFormatter, mRuntime));
                        }
                    }
                }
            }
        }
        return testSuite;
    }

    @Override
    public ClassLoader getLoader() {
        return getContext().getClassLoader();
    }

    private class ScenarioTest extends TestCase implements Reporter {
        private final CucumberScenario mScenario;
        private final Formatter mFormatter;
        private final Runtime mRuntime;
        private TestResult mTestResult;

        public ScenarioTest(CucumberScenario scenario, Formatter formatter, Runtime runtime) {
            mScenario = scenario;
            mFormatter = formatter;
            mRuntime = runtime;
            super.setName(scenario.getVisualName());
        }

        @Override
        public TestResult run() {
            mTestResult = new TestResult();
            run(mTestResult);
            return mTestResult;
        }

        @Override
        public void run(TestResult result) {
            mTestResult = result;
            super.run(mTestResult);
        }

        @Override
        protected void runTest() {
            mScenario.run(mFormatter, this, mRuntime);
        }

        @Override
        public void result(Result result) {
            Throwable error = result.getError();
            if (Result.SKIPPED == result) {
                Log.w(TAG, "Skipped!");
            } else if (isPendingOrUndefined(result)) {
                mTestResult.addError(this, error);
            } else if (error != null) {
                mTestResult.addError(this, error);
            }
        }

        private boolean isPendingOrUndefined(Result result) {
            Throwable error = result.getError();
            return Result.UNDEFINED == result || isPending(error);
        }

        @Override
        public void before(Match match, Result result) {
        }

        @Override
        public void after(Match match, Result result) {
        }

        @Override
        public void match(Match match) {
        }

        @Override
        public void embedding(String s, byte[] bytes) {
        }

        @Override
        public void write(String s) {
        }
    }
}
