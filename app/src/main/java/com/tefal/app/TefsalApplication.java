package com.tefal.app;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tefal.R;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Rituparna Khadka on 11/30/2017.
 */

public class TefsalApplication extends Application {
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    private static TefsalApplication mInstance;
    private static OkHttpClient okHttpClient;
    int YOUR_CUSTOM_TIMEOUT = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);
        mInstance = this;
        super.onCreate();

        initOkHttpObject();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Context getContext() {
        return getContext();
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void initOkHttpObject() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);


        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(YOUR_CUSTOM_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(logging);


        okHttpClient = builder.build();


    }
}
