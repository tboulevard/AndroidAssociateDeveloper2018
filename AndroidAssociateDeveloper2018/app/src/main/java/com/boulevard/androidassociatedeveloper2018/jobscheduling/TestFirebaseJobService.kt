package com.boulevard.androidassociatedeveloper2018.jobscheduling

import android.os.AsyncTask
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class TestFirebaseJobService : JobService() {

    private lateinit var mBackgroundTask: AsyncTask<Any, Any, Unit>

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        // COMPLETED (5) By default, jobs are executed on the main thread, so make an anonymous class extending
        //  AsyncTask called mBackgroundTask.
        // Here's where we make an AsyncTask so that this is no longer on the main thread

        mBackgroundTask = object: AsyncTask<Any, Any, Unit>() {
            override fun doInBackground(vararg p0: Any?) {
                val context = this@TestFirebaseJobService
                TestTasks.executeTask(context, TestTasks.ACTION_INCREMENT_COUNTER)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                jobFinished(jobParameters, true)
            }

        }

        mBackgroundTask.execute(Unit)

        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        // COMPLETED (12) If mBackgroundTask is valid, cancel it
        // COMPLETED (13) Return true to signify the job should be retried
        if (mBackgroundTask != null) (mBackgroundTask as AsyncTask<Any, Any, Unit>).cancel(true)
        return true
    }
}