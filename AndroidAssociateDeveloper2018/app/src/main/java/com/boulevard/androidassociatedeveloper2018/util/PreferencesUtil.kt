package com.boulevard.androidassociatedeveloper2018.util

import android.content.Context
import android.preference.PreferenceManager

object PreferencesUtil {

    /* Shared Preferences Keys */
    const val KEY_INCREMENT_PREF : String = "incrementPrefKey"
    const val KEY_COUNTER : String = "counter-key"

    fun setDecrementVisibility(context : Context, isNotVisible : Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(KEY_INCREMENT_PREF, isNotVisible)
        editor.apply()
    }

    fun getDecrementVisibility(context: Context) : Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(KEY_INCREMENT_PREF, false)
    }

    fun getCounterCount(context : Context) : Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(KEY_COUNTER, 0)
    }

    fun incrementCounter(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var currentCount = prefs.getInt(KEY_COUNTER, 0)

        val editor = prefs.edit()
        editor.putInt(KEY_COUNTER, ++currentCount)
        editor.apply()
    }

    fun decrementCounter(context: Context) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var currentCount = prefs.getInt(KEY_COUNTER, 0)

        val editor = prefs.edit()
        editor.putInt(KEY_COUNTER, --currentCount)
        editor.apply()

    }
}