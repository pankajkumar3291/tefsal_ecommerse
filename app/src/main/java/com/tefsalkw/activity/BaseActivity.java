package com.tefsalkw.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tefsalkw.R;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.utils.SessionManager;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       /* FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Bold.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
   */



        Log.e("::::Activity::::", this.getClass().getSimpleName());

        AdjustEvent event = new AdjustEvent("9ieqgx");
        event.addPartnerParameter("Screen Name", this.getClass().getSimpleName());
        Adjust.trackEvent(event);

//        Configuration conf =  getResources().getConfiguration();
//        conf.locale = new Locale("ar");
//        Locale.setDefault(new Locale("ar"));
//        conf.setLayoutDirection(conf.locale);

//        SessionManager session = new SessionManager(this);
//
//        String savedLang = session.getKeyLang();
//
//        String localeString = savedLang.equalsIgnoreCase("Arabic") ? "ar" : "en";
//        Log.e("::::localeString::::", localeString);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        android.content.res.Configuration conf = res.getConfiguration();
//        conf.setLocale(new Locale(localeString));
//        res.updateConfiguration(conf, dm);


    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onResume() {
        super.onResume();

        TefsalApplication application = (TefsalApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(this.getClass().getSimpleName());
        mTracker.enableAutoActivityTracking(false);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        SessionManager sessionManager = new SessionManager(this);

        String keyLang = sessionManager.getKeyLang();
        String lang = keyLang.equalsIgnoreCase("Arabic") ? "ar" : "en";

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);


    }
}
