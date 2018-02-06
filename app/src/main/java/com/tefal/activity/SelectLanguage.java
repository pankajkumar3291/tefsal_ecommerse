package com.tefal.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefal.R;
import com.tefal.utils.Contents;
import com.tefal.utils.FontChangeCrawler;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SelectLanguage extends BaseActivity {

    Button btn_arabic, btn_english;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

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

                Resources resources = getResources();
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

                Resources resources = getResources();
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
