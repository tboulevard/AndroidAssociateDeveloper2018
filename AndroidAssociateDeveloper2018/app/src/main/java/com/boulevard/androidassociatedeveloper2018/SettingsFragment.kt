package com.boulevard.androidassociatedeveloper2018

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.support.v7.preference.CheckBoxPreference

class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        // Figure out which preference was changed
        val preference = findPreference(key)
        if (null != preference) {
            // Updates the summary for the preference
            if (preference !is CheckBoxPreference) {
                val value = sharedPreferences?.getString(preference.key, "")
                //setPreferenceSummary(preference, value)
            }
        }
    }


    override fun onCreatePreferences(p0: Bundle?, p1: String?) {

        // Add visualizer preferences, defined in the XML file in res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_settings)

        val sharedPreferences = preferenceScreen.sharedPreferences
        val prefScreen = preferenceScreen
        val count = prefScreen.preferenceCount
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