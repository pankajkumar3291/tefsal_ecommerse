package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.SessionManager;

public class StartActivity extends BaseActivity {

    Button btn_signin, btn_signup;

    TextView guestUserText;

    TextView tncText;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        session = new SessionManager(this);
        tncText = (TextView) findViewById(R.id.tncText);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartActivity.this, SigninActivity.class));
                // finish();
            }
        });
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, SignupActivity.class));
                //finish();
            }
        });

        guestUserText = (TextView) findViewById(R.id.guestUserText);
        guestUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                session.setCustomerId(androidDeviceId);
                session.setIsGuestUser(true);

                TefalApp.getInstance().setMin_meters("3");

                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        tncText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StartActivity.this, TermsConditionActitivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
