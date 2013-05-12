package at.mfellner.android.cucumber.example.test;

import at.mfellner.cucumber.android.api.RunWithCucumber;
import junit.framework.TestCase;

/**
 * The instrumentation runner will look for any class that is annotated with @RunWithCucumber.
 * The annotation can be used to set specific parameters for cucumber, like glue and features.
 * You could, for example, create multiple run-configurations in your IDE using differently annotated classes.
 * <p/>
 * You can also run all your features without this class. In that case default values will be used.
 */
@RunWithCucumber(glue = "at.mfellner.android.cucumber.example.test.steps", features = "features")
public class RunCukes extends TestCase {
}
