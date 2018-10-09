package com.boulevard.androidassociatedeveloper2018.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.boulevard.androidassociatedeveloper2018.MainActivity
import com.boulevard.androidassociatedeveloper2018.R

object NotificationsUtil {

    /*
     * This notification ID can be used to access our notification after we've displayed it. This
     * can be handy when we need to cancel the notification, or perhaps update it. This number is
     * arbitrary and can be set to whatever you like. 1138 is in no way significant.
     */
    private const val TEST_REMINDER_NOTIFICATION_ID = 1111
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private const val TEST_REMINDER_PENDING_INTENT_ID = 2222
    /**
     * This notification channel id is used to link notifications to this channel
     */
    private const val TEST_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel"

    // This method will create a notification for charging. It might be helpful
    // to take a look at this guide to see an example of what the code in this method will look like:
    // https://developer.android.com/training/notify-user/build-notification.html
    fun remindUser(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                    TEST_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(mChannel)
        }
        // COMPLETED (10) In the remindUser method use NotificationCompat.Builder to create a notification
        // that:
        // - has a color of R.colorPrimary - use ContextCompat.getColor to get a compatible color
        // - has ic_drink_notification as the small icon
        // - uses icon returned by the largeIcon helper method as the large icon
        // - sets the title to the charging_reminder_notification_title String resource
        // - sets the text to the charging_reminder_notification_body String resource
        // - sets the style to NotificationCompat.BigTextStyle().bigText(text)
        // - sets the notification defaults to vibrate
        // - uses the content intent returned by the contentIntent helper method for the contentIntent
        // - automatically cancels the notification when the notification is clicked
        val notificationBuilder = NotificationCompat.Builder(context, TEST_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_test_notification)
                //.setLargeIcon(largeIcon(context)) // Doesn't work for some reason =\
                .setContentTitle(context.getString(R.string.test_notification_content_title))
                .setContentText(context.getString(R.string.test_notification_content_text))
                .setStyle(NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.test_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)

        // COMPLETED (11) If the build version is greater than JELLY_BEAN and lower than OREO,
        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        }

        // COMPLETED (12) Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(TEST_REMINDER_NOTIFICATION_ID, notificationBuilder.build())
    }

    // COMPLETED (1) Create a helper method called contentIntent with a single parameter for a Context. It
    // should return a PendingIntent. This method will create the pending intent which will trigger when
    // the notification is pressed. This pending intent should open up the MainActivity.
    private fun contentIntent(context: Context): PendingIntent {
        // COMPLETED (2) Create an intent that opens up the MainActivity
        val startActivityIntent = Intent(context, MainActivity::class.java)
        // COMPLETED (3) Create a PendingIntent using getActivity that:
        // - Take the context passed in as a parameter
        // - Takes an unique integer ID for the pending intent (you can create a constant for
        //   this integer above
        // - Takes the intent to open the MainActivity you just created; this is what is triggered
        //   when the notification is triggered
        // - Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
        // intent but update the data
        return PendingIntent.getActivity(
                context,
                TEST_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun clearAllNotifications(context : Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

    }

    // COMPLETED (4) Create a helper method called largeIcon which takes in a Context as a parameter and
    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
    private fun largeIcon(context: Context): Bitmap {
        // COMPLETED (5) Get a Resources object from the context.
        val res = context.resources
        // COMPLETED (6) Create and return a bitmap using BitmapFactory.decodeResource, passing in the
        return BitmapFactory.decodeResource(res, R.drawable.ic_test_notification)
    }
}