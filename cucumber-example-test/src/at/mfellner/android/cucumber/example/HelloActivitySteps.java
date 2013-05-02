package at.mfellner.android.cucumber.example;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HelloActivitySteps extends ActivityInstrumentationTestCase2<HelloActivity> {
    private HelloActivity mActivity;

    public HelloActivitySteps() {
        super(HelloActivity.class);
    }

    @Given("^I have a hello activity with \"([^\"]*)\"$")
    public void I_have_a_hello_activity_with(String greeting) {
        mActivity = getActivity();
        assertNotNull("activity must not be null", mActivity);
        mActivity.setNextGreeting(greeting);
    }

    @When("^I press \"say hello\"$")
    public void I_press_say_hello() {
        View button = mActivity.findViewById(R.id.btn_say_hello);
        assertNotNull("button must not be null", button);
        clickOnView(button);
    }

    @Then("^it should answer with \"([^\"]*)\"$")
    public void it_should_answer_with(String expected) {
        TextView txt = (TextView) mActivity.findViewById(R.id.txt_hello);
        assertNotNull("text view must not be null", txt);
        String greeting = txt.getText().toString();
        assertEquals(expected, greeting);
    }

    private void clickOnView(final View view) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.callOnClick();
            }
        });
    }
}
