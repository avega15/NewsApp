package cs4540.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class ScheduleUtilities {
    private static final int SCHEDULE_INTERVAL_MINUTES = 10;
    private static final int SYNC_FLEXTIME_SECONDS = 10;
    private static final String NEWS_JOB_SERVICE_TAG = "news_job_service_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleRefresh(@NonNull final Context context) {
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsReminderJobService.class)
                .setTag(NEWS_JOB_SERVICE_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;
    }
}
