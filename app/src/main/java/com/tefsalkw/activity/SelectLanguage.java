package com.tefsalkw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.tefsalkw.R;
import com.tefsalkw.utils.SessionManager;

import java.util.Locale;

public class SelectLanguage extends BaseActivity {

    Button btn_arabic, btn_english;


    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        sessionManager = new SessionManager(this);

        btn_arabic = (Button) findViewById(R.id.btn_arabic);
        btn_english = (Button) findViewById(R.id.btn_english);




    }

    @Override
    protected void onResume() {
        super.onResume();


        btn_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                sessionManager.setKeyLang("Arabic");

                Locale locale = new Locale("en");
                Locale.setDefault(locale);

                Resources resources = getBaseContext().getResources();
                Configuration configuration = getBaseContext().getResources().getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.setLocale(locale);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    getApplicationContext().createConfigurationContext(configuration);
                } else {
                    resources.updateConfiguration(configuration, displayMetrics);
                }

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                Runtime.getRuntime().exit(0);


            }
        });

        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManager.setKeyLang("English");

                Locale locale = new Locale("en");
                Locale.setDefault(locale);

                Resources resources = getBaseContext().getResources();
                Configuration configuration = getBaseContext().getResources().getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.setLocale(locale);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    getApplicationContext().createConfigurationContext(configuration);
                } else {
                    resources.updateConfiguration(configuration, displayMetrics);
                }

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                Runtime.getRuntime().exit(0);

            }
        });


    }


}
