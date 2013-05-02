package at.mfellner.cucumber.android.api;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;
import at.mfellner.cucumber.android.runtime.AndroidBackend;
import at.mfellner.cucumber.android.runtime.AndroidFormatter;
import at.mfellner.cucumber.android.runtime.AndroidResourceLoader;
import cucumber.runtime.Backend;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.junit.JUnitReporter;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.snippets.SummaryPrinter;
import ext.com.android.internal.os.LoggingPrintStream;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class CucumberTestRunner extends ParentRunner<FeatureRunner> {
    private Runtime mRuntime;
    private final JUnitReporter mJUnitReporter;
    private final List<FeatureRunner> mChildren = new ArrayList<FeatureRunner>();

    public CucumberTestRunner(Instrumentation instrumentation) throws InitializationError {
        super(null);

        Context context = instrumentation.getContext();
        ClassLoader classLoader = context.getClassLoader();
        // this is the package name as defined in AndroidManifest.xml
        String glue = context.getPackageName();

        Properties properties = new Properties();
        // 'features' must be a subdirectory of the test-projects 'assets' directory
        String features = "features";
        properties.setProperty("cucumber.options", String.format("-g %s %s", glue, features));
        RuntimeOptions runtimeOptions = new RuntimeOptions(properties);

        AndroidFormatter formatter = new AndroidFormatter(CucumberInstrumentation.TAG);
        runtimeOptions.formatters.add(formatter);

        ResourceLoader resourceLoader = new AndroidResourceLoader(context);
        List<Backend> backends = new ArrayList<Backend>();
        backends.add(new AndroidBackend(instrumentation));
        mRuntime = new Runtime(resourceLoader, classLoader, backends, runtimeOptions);

        mJUnitReporter = new JUnitReporter(formatter, formatter, /* strict */ true);

        for (CucumberFeature cucumberFeature : runtimeOptions.cucumberFeatures(resourceLoader)) {
            mChildren.add(new FeatureRunner(cucumberFeature, mRuntime, mJUnitReporter));
        }
    }

    @Override
    public List<FeatureRunner> getChildren() {
        return mChildren;
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(FeatureRunner child, RunNotifier notifier) {
        child.run(notifier);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        mJUnitReporter.done();
        new SummaryPrinter(loggingPrintStream()).print(mRuntime);
        mJUnitReporter.close();
    }

    private LoggingPrintStream loggingPrintStream() {
        return new LoggingPrintStream() {
            @Override
            protected void log(String line) {
                Log.w(CucumberInstrumentation.TAG, line);
            }
        };
    }
}
