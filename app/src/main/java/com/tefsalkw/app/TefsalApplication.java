package com.tefsalkw.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tefsalkw.R;
import com.tefsalkw.utils.SessionManager;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Rituparna Khadka on 11/30/2017.
 */

public class TefsalApplication extends MultiDexApplication {
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    private static TefsalApplication mInstance;
    private static OkHttpClient okHttpClient;
    int YOUR_CUSTOM_TIMEOUT = 30;
    public TefalApp mTefalApp = null;

    private static FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        sAnalytics = GoogleAnalytics.getInstance(this);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //sAnalytics.setDryRun(true);

        mInstance = this;
        super.onCreate();

        MultiDex.install(this);

        initOkHttpObject();


        SessionManager sessionManager = new SessionManager(this);

        String keyLang = sessionManager.getKeyLang();

        Log.e("keyLang",keyLang);

        if(keyLang.equals("Arabic"))
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/GESSTwoMedium-Medium.otf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }
        else
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Lato-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }



        mTefalApp = new TefalApp();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkReceiver(), intentFilter);


    }

    public class NetworkReceiver extends BroadcastReceiver {
        final String TAG = NetworkReceiver.class.getName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Received Network Change event.");


            if (!checkInternet()) {
                Toast.makeText(context, "No Internet Connection...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        return false;

    }


    public TefalApp getInstance() {
        return mTefalApp;
    }


    public static TefsalApplication getContext() {
        return mInstance;
    }

    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }

    synchronized public static FirebaseAnalytics getmFirebaseAnalytics() {

        return mFirebaseAnalytics;
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
        builder.retryOnConnectionFailure(false);
        //builder.addInterceptor(logging);


        okHttpClient = builder.build();


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.e("newConfig","newConfig");
        String lang = "en";

        SessionManager sessionManager = new SessionManager(mInstance);

        if (sessionManager.getKeyLang().equalsIgnoreCase("Arabic")) {
            lang = "ar";
            Log.e("onConfigurationChanged","ar");
        }


        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getBaseContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        newConfig.setLocale(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplicationContext().createConfigurationContext(newConfig);
        } else {
            resources.updateConfiguration(newConfig, displayMetrics);
        }

    }
}
