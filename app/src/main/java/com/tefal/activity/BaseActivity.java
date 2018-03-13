package com.tefal.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tefal.R;
import com.tefal.utils.FontChangeCrawler;

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

        Log.e("Activity",this.getClass().getSimpleName());


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
