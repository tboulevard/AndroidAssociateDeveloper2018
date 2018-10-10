package com.boulevard.androidassociatedeveloper2018java.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    public static final String KEY_COUNTER = "key-counter";

    synchronized public static void incrementCounter(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int currentCount = prefs.getInt(KEY_COUNTER, 0);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_COUNTER, ++currentCount);
        editor.apply();
    }

    public static int getCounterCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_COUNTER, 0);
    }
}
