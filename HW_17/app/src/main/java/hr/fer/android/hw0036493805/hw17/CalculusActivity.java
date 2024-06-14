package hr.fer.android.hw0036493805.hw17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This activity represents a simple calculator.
 *
 * @author Karlo Bencic
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * The Constant ADDITION.
     */
    private static final String ADDITION = "zbrajanje";

    /**
     * The Constant SUBTRACTION.
     */
    private static final String SUBTRACTION = "oduzimanje";

    /**
     * The Constant MULTIPLICATION.
     */
    private static final String MULTIPLICATION = "množenje";

    /**
     * The Constant DIVISION.
     */
    private static final String DIVISION = "dijeljenje";

    /**
     * The Constant RESULT_KEY.
     */
    public static final String RESULT_KEY = "result";

    /**
     * The Constant DISPLAY_REQUEST_CODE.
     */
    public static final int DISPLAY_REQUEST_CODE = 100;

    /**
     * The radio button group.
     */
    @BindView(R.id.btnGroup)
    RadioGroup btnGroup;

    /**
     * The addition radio button.
     */
    @BindView(R.id.addBtn)
    RadioButton addBtn;

    /**
     * The subtraction radio button.
     */
    @BindView(R.id.subBtn)
    RadioButton subBtn;

    /**
     * The multiplication radio button.
     */
    @BindView(R.id.mulBtn)
    RadioButton mulBtn;

    /**
     * The division radio button.
     */
    @BindView(R.id.divBtn)
    RadioButton divBtn;

    /**
     * The first input.
     */
    @BindView(R.id.firstInput)
    EditText firstInput;

    /**
     * The second input.
     */
    @BindView(R.id.secondInput)
    EditText secondInput;

    /**
     * The calculate button.
     */
    @BindView(R.id.calcBtn)
    Button calcBtn;

    /**
     * Starting point of this activity.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        ButterKnife.bind(this);
    }

    /**
     * Triggered when the calculate button is clicked.
     */
    @OnClick(R.id.calcBtn)
    void onCalculateClick() {
        int selectedId = btnGroup.getCheckedRadioButtonId();

        int firstNum, secondNum;
        try {
            firstNum = Integer.parseInt(firstInput.getText().toString());
            secondNum = Integer.parseInt(secondInput.getText().toString());
        } catch (NumberFormatException e) {
            String operation = ((RadioButton) findViewById(selectedId)).getText().toString().toLowerCase();
            onError(operation, firstInput.getText().toString(), secondInput.getText().toString(), "ulaz nije valjan");
            return;
        }

        if (selectedId == addBtn.getId()) {
            add(firstNum, secondNum);
        } else if (selectedId == subBtn.getId()) {
            sub(firstNum, secondNum);
        } else if (selectedId == mulBtn.getId()) {
            mul(firstNum, secondNum);
        } else if (selectedId == divBtn.getId()) {
            div(firstNum, secondNum);
        }
    }

    /**
     * Adds two numbers.
     *
     * @param a the first operand
     * @param b the second operand
     */
    private void add(int a, int b) {
        onCalculate(ADDITION, a + b);
    }

    /**
     * Subtracts two numbers.
     *
     * @param a the first operand
     * @param b the second operand
     */
    private void sub(int a, int b) {
        onCalculate(SUBTRACTION, a - b);
    }

    /**
     * Multiplies two numbers.
     *
     * @param a the first operand
     * @param b the second operand
     */
    private void mul(int a, int b) {
        onCalculate(MULTIPLICATION, a * b);
    }

    /**
     * Divides two numbers.
     *
     * @param a the first operand
     * @param b the second operand
     */
    private void div(int a, int b) {
        try {
            onCalculate(DIVISION, a / b);
        } catch (ArithmeticException e) {
            onError(DIVISION, String.valueOf(a), String.valueOf(b), "dijeljenje sa nulom");
        }
    }

    /**
     * Called after the operation has been executed with the result.
     *
     * @param operation the operation
     * @param result    the result
     */
    private void onCalculate(String operation, int result) {
        String info = String.format(Locale.getDefault(), "Rezultat operacije %s je %d", operation, result);

        showResult(info);
    }

    /**
     * Called when an error occurs during the calculation.
     *
     * @param operation the operation
     * @param first     the first operand
     * @param second    the second operand
     * @param error     the error message
     */
    private void onError(String operation, String first, String second, String error) {
        String info = String.format("Prilikom obavljanja operacije %s nad unosima %s i %s došlo je do sljedeće greške: %s",
                operation, first, second, error);

        showResult(info);
    }

    /**
     * Starts a new activity and passes the result to display it.
     *
     * @param info the info
     */
    private void showResult(String info) {
        Intent i = new Intent(this, DisplayActivity.class);

        Bundle extras = new Bundle();
        extras.putString(RESULT_KEY, info);
        i.putExtras(extras);

        startActivityForResult(i, DISPLAY_REQUEST_CODE);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DISPLAY_REQUEST_CODE && resultCode == RESULT_OK) {
            firstInput.getText().clear();
            secondInput.getText().clear();
        }
    }
}

