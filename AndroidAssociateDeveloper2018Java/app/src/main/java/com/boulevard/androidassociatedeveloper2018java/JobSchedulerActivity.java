package com.boulevard.androidassociatedeveloper2018java;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.boulevard.androidassociatedeveloper2018java.util.JobSchedulerUtil;
import com.boulevard.androidassociatedeveloper2018java.util.PreferenceUtil;

public class JobSchedulerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    ImageButton scheduleIncrementCounterButton;
    TextView counterTextView;
    Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

        // Setup view elements for reference, after view is created
        scheduleIncrementCounterButton = findViewById(R.id.schedule_increment_counter_button);
        counterTextView = findViewById(R.id.counter_textview);
        mainToolbar = findViewById(R.id.main_toolbar);

        // Retrieve saved out state, if it exists
        if (savedInstanceState != null) {
            int count = savedInstanceState.getInt("currentCount");
            counterTextView.setText(count + "");
        } else {
            counterTextView.setText(0 + "");
        }

        /**
         * Setup custom toolbar (used for launching navigation drawer)
         */
        setSupportActionBar(mainToolbar);

        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        actionbar.setTitle("Job Scheduler");

        /** Setup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Tied to onClick within the Activity layout.
     */
    public void scheduleIncrementCounter(View view) {
        JobSchedulerUtil.scheduleIncrementJob(this);
    }

    /**
     * Triggered every time SharedPreferences is changed.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PreferenceUtil.KEY_COUNTER)) {
            updateCounter();
        }
    }

    /**
     * Called when the user is leaving the activity (sometime before onStop())
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save out state (if complete object, just use parcelable)
        outState.putInt("currentCount", Integer.parseInt(counterTextView.getText().toString()));
    }

    /* Private helper methods */
    private void updateCounter() {
        int counterCount = PreferenceUtil.getCounterCount(this);
        counterTextView.setText(counterCount + "");
    }
}
