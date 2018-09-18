package com.tefsalkw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.tefsalkw.R;
import com.tefsalkw.app.TefsalApplication;
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

                Locale locale = new Locale("ar");
                Locale.setDefault(locale);

                sessionManager.setKeyLang("Arabic");

                Resources resources = TefsalApplication.getContext().getResources();
                Configuration configuration = resources.getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration, displayMetrics);


                startActivity(new Intent(SelectLanguage.this, StartActivity.class));
                finish();
            }
        });

        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Locale locale = new Locale("eg");
                Locale.setDefault(locale);
                sessionManager.setKeyLang("English");
                Resources resources = TefsalApplication.getContext().getResources();
                Configuration configuration = resources.getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.setLocale(locale);
                resources.updateConfiguration(configuration, displayMetrics);

                startActivity(new Intent(SelectLanguage.this, StartActivity.class));
                finish();
            }
        });


    }


}
