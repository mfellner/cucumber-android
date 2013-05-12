package at.mfellner.android.cucumber.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
    private static enum Operation {ADD, SUB, MULT, DIV, NONE}

    private TextView mTxtCalcDisplay;
    private TextView mTxtCalcOperator;
    private Operation mOperation;
    private boolean mDecimals;
    private boolean mResetDisplay;
    private boolean mPerformOperation;
    private double mValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        mTxtCalcDisplay = (TextView) findViewById(R.id.txt_calc_display);
        mTxtCalcOperator = (TextView) findViewById(R.id.txt_calc_operator);
        mOperation = Operation.NONE;
    }

    public void onDigitPressed(View v) {
        if (mResetDisplay) {
            mTxtCalcDisplay.setText(null);
            mResetDisplay = false;
        }
        mTxtCalcOperator.setText(null);

        if (mDecimals || !only0IsDisplayed()) mTxtCalcDisplay.append(((Button) v).getText());

        if (mOperation != Operation.NONE) mPerformOperation = true;
    }

    public void onOperatorPressed(View v) {
        if (mPerformOperation) {
            performOperation();
            mPerformOperation = false;
        }
        switch (v.getId()) {
            case R.id.btn_op_divide:
                mOperation = Operation.DIV;
                mTxtCalcOperator.setText("/");
                break;
            case R.id.btn_op_multiply:
                mOperation = Operation.MULT;
                mTxtCalcOperator.setText("x");
                break;
            case R.id.btn_op_subtract:
                mOperation = Operation.SUB;
                mTxtCalcOperator.setText("–");
                break;
            case R.id.btn_op_add:
                mOperation = Operation.ADD;
                mTxtCalcOperator.setText("+");
                break;
            case R.id.btn_op_equals:
                break;
            default:
                throw new RuntimeException("Unsupported operation.");
        }
        mResetDisplay = true;
        mValue = getDisplayValue();
    }

    public void onSpecialPressed(View v) {
        Button btn = (Button) v;
        switch (v.getId()) {
            case R.id.btn_spec_sqroot: {
                double value = getDisplayValue();
                double sqrt = Math.sqrt(value);
                mTxtCalcDisplay.setText(Double.toString(sqrt));
                break;
            }
            case R.id.btn_spec_pi: {
                mResetDisplay = false;
                mTxtCalcOperator.setText(null);
                mTxtCalcDisplay.setText(Double.toString(Math.PI));
                if (mOperation != Operation.NONE) mPerformOperation = true;
                return;
            }
            case R.id.btn_spec_percent: {
                double value = getDisplayValue();
                double percent = value / 100.0F;
                mTxtCalcDisplay.setText(Double.toString(percent));
                break;
            }
            case R.id.btn_spec_comma: {
                if (!mDecimals) {
                    String text = displayIsEmpty() ? "0." : ".";
                    mTxtCalcDisplay.append(text);
                    mDecimals = true;
                }
                break;
            }
            case R.id.btn_spec_clear: {
                mValue = 0;
                mDecimals = false;
                mOperation = Operation.NONE;
                mTxtCalcDisplay.setText(null);
                mTxtCalcOperator.setText(null);
                break;
            }
        }
        mResetDisplay = false;
        mPerformOperation = false;
    }

    private void performOperation() {
        double display = getDisplayValue();

        switch (mOperation) {
            case DIV:
                mValue = mValue / display;
                break;
            case MULT:
                mValue = mValue * display;
                break;
            case SUB:
                mValue = mValue - display;
                break;
            case ADD:
                mValue = mValue + display;
                break;
            case NONE:
                return;
            default:
                throw new RuntimeException("Unsupported operation.");
        }
        mTxtCalcOperator.setText(null);
        mTxtCalcDisplay.setText(Double.toString(mValue));
    }

    private boolean only0IsDisplayed() {
        CharSequence text = mTxtCalcDisplay.getText();
        return text.length() == 1 && text.charAt(0) == '0';
    }

    private boolean displayIsEmpty() {
        return mTxtCalcDisplay.getText().length() == 0;
    }

    private double getDisplayValue() {
        String display = mTxtCalcDisplay.getText().toString();
        return display == null || display.isEmpty() ? 0.0F : Double.parseDouble(display);
    }
}
