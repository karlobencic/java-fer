package hr.fer.android.hw0036493805.hw17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This activity represents the result screen after the calculation has occurred with the result of the operation or an error.
 *
 * @author Karlo Bencic
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * The Constant EMAIL.
     */
    private static final String EMAIL = "ana@baotic.org";

    /**
     * The result text.
     */
    @BindView(R.id.resultText)
    TextView resultText;

    /**
     * The result.
     */
    private String result;

    /**
     * Starting point of this activity.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(CalculusActivity.RESULT_KEY)) {
            result = extras.getString(CalculusActivity.RESULT_KEY);
            resultText.setText(result);
        }
    }

    /**
     * Triggered when the ok button is clicked. Goes back to the previous activity.
     */
    @OnClick(R.id.okBtn)
    void onOkClick() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * Triggered when the send report button is clicked. It sends an email with the given result shown on the screen.
     */
    @OnClick(R.id.sendReportBtn)
    void onSendReportClick() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL});
        i.putExtra(Intent.EXTRA_SUBJECT, "0036493805: dz report");
        i.putExtra(Intent.EXTRA_TEXT, result);

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
