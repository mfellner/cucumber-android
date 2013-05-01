package at.mfellner.android.cucumber.example;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HelloActivitySteps extends ActivityInstrumentationTestCase2<HelloActivity> {
    public static final String TAG = "cucumber-android-example";

    public HelloActivitySteps() {
        super(HelloActivity.class);
    }

    @Given("^I have a hello app with \"([^\"]*)\"$")
    public void I_have_a_hello_app_with(String greeting) {
//        assertEquals(1, 2);
    }

    @When("^I ask it to say hi$")
    public void I_ask_it_to_say_hi() {
    }

    @Then("^it should answer with \"([^\"]*)\"$")
    public void it_should_answer_with(String expectedHi) {
        Log.d(TAG, expectedHi);
    }
}
