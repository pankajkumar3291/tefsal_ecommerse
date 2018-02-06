package com.tefal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tefal.R;
import com.tefal.utils.FontChangeCrawler;

public class StartActivity extends BaseActivity {

    Button btn_signin, btn_signup;

    TextView guestUserText;

    TextView tncText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        tncText=(TextView)findViewById(R.id.tncText);
        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartActivity.this,SigninActivity.class));
               // finish();
            }
        });
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,SignupActivity.class));
                //finish();
            }
        });

        guestUserText = (TextView) findViewById(R.id.guestUserText);
        guestUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        });

        tncText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,TC_Actitivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
