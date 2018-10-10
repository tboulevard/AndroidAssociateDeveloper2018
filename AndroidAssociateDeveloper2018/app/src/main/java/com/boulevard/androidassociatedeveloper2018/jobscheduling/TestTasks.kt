package com.boulevard.androidassociatedeveloper2018.jobscheduling;

import android.content.Context
import com.boulevard.androidassociatedeveloper2018.util.NotificationsUtil
import com.boulevard.androidassociatedeveloper2018.util.PreferencesUtil

object TestTasks {

    val ACTION_INCREMENT_COUNTER = "increment-counter"
    val ACTION_DISMISS_NOTIFICATION = "dismiss-notification"
    val ACTION_TEST_REMINDER = "test-reminder"

    fun executeTask(context: Context, action: String) {
        if (ACTION_INCREMENT_COUNTER == action) {
            incrementCounter(context)
        } else if (ACTION_DISMISS_NOTIFICATION == action) {
            NotificationsUtil.clearAllNotifications(context)
        } else if (ACTION_TEST_REMINDER == action) {
            //issueChargingReminder(context)
        }
    }

    private fun incrementCounter(context: Context) {
        PreferencesUtil.incrementCounter(context)
        NotificationsUtil.clearAllNotifications(context)
    }

    // COMPLETED (2) Create an additional task for issuing a charging reminder notification.
    // This should be done in a similar way to how you have an action for incrementingWaterCount
    // and dismissing notifications. This task should both create a notification AND
    // increment the charging reminder count (hint: there is a method for this in PreferenceUtilities)
    // When finished, you should be able to call executeTask with the correct parameters to execute
    // this task. Don't forget to add the code to executeTask which actually calls your new task!

//    private fun issueChargingReminder(context: Context) {
//        PreferencesUtil.incrementChargingReminderCount(context)
//        NotificationsUtil.remindUserBecauseCharging(context)
//    }
}