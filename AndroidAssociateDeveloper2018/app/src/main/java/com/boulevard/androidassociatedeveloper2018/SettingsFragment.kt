package com.boulevard.androidassociatedeveloper2018

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.support.v7.preference.CheckBoxPreference

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {

    /**
     * Doesn't do anything yet, but this is where you would add functionality for the
     * SettingsFragment when a preference is changed.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // Figure out which preference was changed
        val preference = findPreference(key)
        if (null != preference) {
            // Update the summary for the preference here
            if (preference !is CheckBoxPreference) {
                val value = sharedPreferences?.getString(preference.key, "")
            }
        }
    }


    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        // Add main activity preferences, defined in the XML file in res->xml->pref_settings
        addPreferencesFromResource(R.xml.pref_settings)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
    }
}