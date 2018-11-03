package com.boulevard.androidassociatedeveloper2018java.settings;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.boulevard.androidassociatedeveloper2018java.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings);
    }

    // Notice how there's no view inflation logic for PreferenceFragment

}
