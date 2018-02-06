package com.tefal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tefal.R;
import com.tefal.utils.FontChangeCrawler;
import com.tefal.utils.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.text_edit_profile)
    TextView text_edit_profile;



    @Bind(R.id.text_language_spinner)
    Spinner text_language_spinner;

    @Bind(R.id.text_send_feedback)
    TextView text_send_feedback;

    @Bind(R.id.text_terms)
    TextView text_terms;

    @Bind(R.id.text_about)
    TextView text_about;

    @Bind(R.id.text_logout)
    TextView text_logout;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.text_change_password)
    TextView text_change_password;



    @Bind(R.id.view1)
            View view1;
    @Bind(R.id.view2)
            View view2;
    @Bind(R.id.view7)
            View view7;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        session = new SessionManager(this);

        setSupportActionBar(toolbar);
        toolbar_title.setText(R.string.title_settings);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // This block of code is used to hide from settings remove edit profile / change password / logout while user enters as guest

        if (session.getCustomerId().equals(""))
        {
            text_logout.setVisibility(GONE);
            text_change_password.setVisibility(GONE);
            text_edit_profile.setVisibility(GONE);
            view1.setVisibility(GONE);
            view2.setVisibility(GONE);
            view7.setVisibility(GONE);

        }


        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.user_logout();


                //startActivity(new Intent(SettingsActivity.this, SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });

        text_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(session.getCustomerId().equals(""))
                {
                    startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                }
                else
                {
                    startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                }

            }
        });

        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class));
            }
        });


        text_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SettingsActivity.this, TC_Actitivity.class));
            }
        });

        text_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(SettingsActivity.this, FeedBackActivity.class));
            }
        });
        text_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(session.getCustomerId().equals(""))
                {
                    startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                }
                else
                {
                    startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
                }
            }
        });


    }
}
