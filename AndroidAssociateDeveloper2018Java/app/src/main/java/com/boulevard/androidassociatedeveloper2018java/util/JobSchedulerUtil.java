package com.boulevard.androidassociatedeveloper2018java.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.boulevard.androidassociatedeveloper2018java.IncrementFirebaseJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class JobSchedulerUtil {

    private static final String REMINDER_JOB_TAG = "increment_job_tag";

    private static Boolean sInitialized = false;

    synchronized public static void scheduleIncrementJob(@NonNull Context context) {
        // If the job has already been initialized, return
        if (sInitialized) return;

        // Create a new GooglePlayDriver
        GooglePlayDriver driver = new GooglePlayDriver(context);

        // Create a new FirebaseJobDispatcher with the driver
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job incrementCounterJob = dispatcher.newJobBuilder()
                /* The Service that will be used to write to preferences */
                .setService(IncrementFirebaseJobService.class)
                /*
                 * Set the UNIQUE tag used to identify this Job.
                 */
                .setTag(REMINDER_JOB_TAG)
                /*
                 * Network constraints on which this Job should run. In this app, we're using the
                 * device charging constraint so that the job only executes if the device is
                 * charging.
                 *
                 * In a normal app, it might be a good idea to include a preference for this,
                 * as different users may have different preferences on when you should be
                 * syncing your application's data.
                 */
                .setConstraints(Constraint.DEVICE_CHARGING)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want these reminders to continuously happen, so we tell this Job to recur.
                 */
                .setRecurring(true)
                /*
                 * We want the reminders to happen every 15 minutes or so. The first argument for
                 * Trigger class's static executionWindow method is the start of the time frame
                 * when the
                 * job should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                .setTrigger(Trigger.executionWindow(30, 60))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        // COMPLETED (21) Use dispatcher's schedule method to schedule the job
        /* Schedule the Job with the dispatcher */
        dispatcher.schedule(incrementCounterJob);

        // COMPLETED (22) Set sInitialized to true to mark that we're done setting up the job
        /* The job has been initialized */
        sInitialized = true;
    }
}