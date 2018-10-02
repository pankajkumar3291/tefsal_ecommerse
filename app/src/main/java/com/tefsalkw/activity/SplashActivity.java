package com.tefsalkw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.tefsalkw.R;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.PreferencesUtil;
import com.tefsalkw.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.provider.Settings.Secure;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends BaseActivity {

    SessionManager session;

    int SPLASH_DISPLAY_LENGTH = 3000;

    String password, email;

    //SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());
        session = new SessionManager(this);

        //password = session.getKeyPass();
        // email = session.getKeyEmail();



        if (Contents.isBlank(session.getCustomerId())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // This block of code is used to internal login

                    Intent mainIntent = new Intent(SplashActivity.this, SelectLanguage.class);
                    startActivity(mainIntent);
                    finish();

                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                  //  WebCallServiceSetToken();

                    String keyLang = session.getKeyLang();
                    String lang = keyLang.equalsIgnoreCase("English") ? "en" : "ar";

                    Locale locale = new Locale(lang);
                    Locale.setDefault(locale);

                    Resources resources = getResources();
                    Configuration configuration = resources.getConfiguration();
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    configuration.setLocale(locale);
                    resources.updateConfiguration(configuration, displayMetrics);

                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();


                }
            }, SPLASH_DISPLAY_LENGTH);
        }


       // throw new RuntimeException("Test Crash");
    }



}
