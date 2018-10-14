package com.boulevard.androidassociatedeveloper2018java.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.util.JobSchedulerUtil;
import com.boulevard.androidassociatedeveloper2018java.util.PreferenceUtil;

import static android.content.Context.BATTERY_SERVICE;

/**
 * General activity lifecycle notes:
 *
 * Sequence of lifecycle events after device rotation:
 * onPause, onStop, onDestroy, onCreate, onStart, onResume
 *
 */
public class JobSchedulerFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    /* View holder member variables */
    ImageButton scheduleIncrementCounterButton;
    TextView counterTextView;
    ImageView chargingImageView;


    IntentFilter chargingIntentFilter;
    /* Important note: If the BroadbastReceiver was defined in the AndroidManifest (statically), registered
       Intents would trigger it whether the app is running or not. By registering the BroadcastReceiver
       in onResume and unregistering it in onPause (dynamically), we only allow the broadcast receiver
       to trigger when the app is open. */
    ChargingBroadcastReceiver chargingBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup the shared preference listener */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        /* Intent filter for ChargingBroadcastReceiver */
        chargingIntentFilter = new IntentFilter();
        chargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        chargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        /* Registering the receiver */
        chargingBroadcastReceiver = new ChargingBroadcastReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_scheduler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup view elements for reference, after view is created
        scheduleIncrementCounterButton = getView().findViewById(R.id.schedule_increment_counter_button);
        counterTextView = getView().findViewById(R.id.counter_textview);
        chargingImageView = getView().findViewById(R.id.charging_imageview);


        // Retrieve saved out state, if it exists
        if (savedInstanceState != null) {
            int count = savedInstanceState.getInt("currentCount");
            counterTextView.setText(count + "");
        } else {
            counterTextView.setText(0 + "");
        }

        updateCounter();

        /* Setup onClick handlers */
        scheduleIncrementCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleIncrementCounter();
            }
        });
    }

    /**
     * The activity has become visible (it is now "resumed").
     */
    @Override
    public void onResume() {
        super.onResume();

        /** Determine the current charging state (because our dynamic BroadcastReceiver doesn't listen
         * for changes when the app is closed) **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BatteryManager batteryManager = (BatteryManager) getContext().getSystemService(BATTERY_SERVICE);
            showCharging(batteryManager.isCharging());
        } else {

            // Create a new intent filter with the action ACTION_BATTERY_CHANGED. This is a
            // sticky broadcast that contains a lot of information about the battery state.
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            // Set a new Intent object equal to what is returned by registerReceiver, passing in null
            // for the receiver. Pass in your intent filter as well. Passing in null means that you're
            // getting the current state of a sticky broadcast - the intent returned will contain the
            // battery information you need.
            Intent currentBatteryStatusIntent = getContext().registerReceiver(null, ifilter);
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
        this.getContext().registerReceiver(chargingBroadcastReceiver, chargingIntentFilter);
    }

    /**
     * Another activity is taking focus (this activity is about to be "paused").
     */
    @Override
    public void onPause() {
        super.onPause();
        this.getContext().unregisterReceiver(chargingBroadcastReceiver);
    }

    /**
     * Tied to onClick within the Activity layout.
     */
    public void scheduleIncrementCounter() {
        JobSchedulerUtil.scheduleIncrementJob(this.getContext());
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
    public void onSaveInstanceState(Bundle outState) {
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
        int counterCount = PreferenceUtil.getCounterCount(this.getActivity());
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
