package hr.fer.android.hw0036493805.hw17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This is the main activity of this app and the starting point of this application.
 *
 * @author Karlo Bencic
 */
public class MainActivity extends AppCompatActivity {

    /** The mathematics button. */
    @BindView(R.id.mathBtn)
    Button mathBtn;

    /** The statistics button. */
    @BindView(R.id.statsBtn)
    Button statsBtn;

    /**
     * Starting point of this activity.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    /**
     * Triggered when the math button is clicked.
     */
    @OnClick(R.id.mathBtn)
    void onMathClick() {
        Intent i = new Intent(this, CalculusActivity.class);

        startActivity(i);
    }

    /**
     * Triggered when the stats button is clicked.
     */
    @OnClick(R.id.statsBtn)
    void onStatsClick() {
        Intent i = new Intent(this, FormActivity.class);

        startActivity(i);
    }
}
