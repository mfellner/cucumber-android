package at.mfellner.android.cucumber.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class HelloActivity extends Activity {
    private TextView mTxtHello;
    private String mGreeting;
    private String[] mGreetings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGreetings = getResources().getStringArray(R.array.greetings);
        mTxtHello = (TextView) findViewById(R.id.txt_hello);
    }

    public void onBtnSayHelloClick(View v) {
        if (mGreeting == null) mGreeting = randomGreeting();
        mTxtHello.setText(String.format("%s, World!", mGreeting));
    }

    public void onBtnResetClick(View v) {
        mGreeting = null;
        mTxtHello.setText(null);
    }

    public void setNextGreeting(String greeting) {
        mGreeting = greeting;
    }

    private String randomGreeting() {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());
        return mGreetings[r.nextInt(mGreetings.length - 1)];
    }
}
