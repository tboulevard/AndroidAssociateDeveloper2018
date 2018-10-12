package com.boulevard.androidassociatedeveloper2018java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.boulevard.androidassociatedeveloper2018java.util.JobSchedulerUtil;
import com.boulevard.androidassociatedeveloper2018java.util.PreferenceUtil;

/**
 * General activity lifecycle notes:
 *
 * Sequence of lifecycle events after device rotation:
 * onPause, onStop, onDestroy, onCreate, onStart, onResume
 *
 */
public class JobSchedulerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /* View holder member variables */
    ImageButton scheduleIncrementCounterButton;
    TextView counterTextView;
    Toolbar mainToolbar;
    ImageView chargingImageView;

    IntentFilter chargingIntentFilter;
    /* Important note: If the BroadbastReceiver was defined in the AndroidManifest (statically), registered
       Intents would trigger it whether the app is running or not. By registering the BroadcastReceiver
       in onResume and unregistering it in onPause (dynamically), we only allow the broadcast receiver
       to trigger when the app is open. */
    ChargingBroadcastReceiver chargingBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

        // Setup view elements for reference, after view is created
        scheduleIncrementCounterButton = findViewById(R.id.schedule_increment_counter_button);
        counterTextView = findViewById(R.id.counter_textview);
        mainToolbar = findViewById(R.id.main_toolbar);
        chargingImageView = findViewById(R.id.charging_imageview);

        // Retrieve saved out state, if it exists
        if (savedInstanceState != null) {
            int count = savedInstanceState.getInt("currentCount");
            counterTextView.setText(count + "");
        } else {
            counterTextView.setText(0 + "");
        }

        /*
         * Setup custom toolbar (used for launching navigation drawer)
         */
        setSupportActionBar(mainToolbar);

        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);
        actionbar.setTitle("Job Scheduler");

        /* Setup the shared preference listener */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        /* Intent filter for ChargingBroadcastReceiver */
        chargingIntentFilter = new IntentFilter();
        chargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        chargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        /* Registering the receiver */
        chargingBroadcastReceiver = new ChargingBroadcastReceiver();
    }

    /**
     * The activity has become visible (it is now "resumed").
     */
    @Override
    protected void onResume() {
        super.onResume();

        /** Determine the current charging state (because our dynamic BroadcastReceiver doesn't listen
         * for changes when the app is closed) **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            showCharging(batteryManager.isCharging());
        } else {

            // Create a new intent filter with the action ACTION_BATTERY_CHANGED. This is a
            // sticky broadcast that contains a lot of information about the battery state.
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            // Set a new Intent object equal to what is returned by registerReceiver, passing in null
            // for the receiver. Pass in your intent filter as well. Passing in null means that you're
            // getting the current state of a sticky broadcast - the intent returned will contain the
            // battery information you need.
            Intent currentBatteryStatusIntent = registerReceiver(null, ifilter);
            // Get the integer extra BatteryManager.EXTRA_STATUS. Check if it matches
            // BatteryManager.BATTERY_STATUS_CHARGING or BatteryManager.BATTERY_STATUS_FULL. This means
            // the battery is currently charging.
            int batteryStatus = currentBatteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ||
                    batteryStatus == BatteryManager.BATTERY_STATUS_FULL;
            // COMPLETED (8) Update the UI using your showCharging method
            showCharging(isCharging);
        }

        /* Register the broadcast receiver that listens to changes in charging state */
        registerReceiver(chargingBroadcastReceiver, chargingIntentFilter);
    }

    /**
     * Another activity is taking focus (this activity is about to be "paused").
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(chargingBroadcastReceiver);
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

    /**
     * Broadcast receiver stuff
     */
    private class ChargingBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Boolean isCharging = action.equals(Intent.ACTION_POWER_CONNECTED);
            showCharging(isCharging);
        }
    }

    /* Private helper methods */
    private void updateCounter() {
        int counterCount = PreferenceUtil.getCounterCount(this);
        counterTextView.setText(counterCount + "");
    }

    private void showCharging(Boolean isCharging) {
        if (isCharging) {
            chargingImageView.setVisibility(View.VISIBLE);
        } else {
            chargingImageView.setVisibility(View.INVISIBLE);
        }
    }
}
