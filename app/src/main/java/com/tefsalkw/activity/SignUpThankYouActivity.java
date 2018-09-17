package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tefsalkw.R;

public class SignUpThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_thank_you);


        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpThankYouActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });


        String email = "";
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra("email");

        }
        TextView txtStatus = findViewById(R.id.txtStatus);
        txtStatus.setText(getString(R.string.thank_you1) + email + getString(R.string.thank_you2));


    }
}
