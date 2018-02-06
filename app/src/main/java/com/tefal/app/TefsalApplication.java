package com.tefal.app;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tefal.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Rituparna Khadka on 11/30/2017.
 */

public class TefsalApplication extends Application
{
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;


    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);

        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Context getContext()
    {
        return getContext();
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
