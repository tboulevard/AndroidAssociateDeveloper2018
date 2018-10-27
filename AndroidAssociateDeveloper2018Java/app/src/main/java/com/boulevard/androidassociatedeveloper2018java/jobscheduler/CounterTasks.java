package com.boulevard.androidassociatedeveloper2018java.jobscheduler;

import android.content.Context;

import com.boulevard.androidassociatedeveloper2018java.common.util.PreferenceUtil;

public class CounterTasks {

    public static final String ACTION_INCREMENT_COUNTER = "increment-counter";

    public static void executeTask(Context context, String action) {
        if (ACTION_INCREMENT_COUNTER.equals(action)) {
            incrementCounter(context);
        }
    }

    private static void incrementCounter(Context context) {
        PreferenceUtil.incrementCounter(context);
        //NotificationUtils.clearAllNotifications(context);
    }
}
