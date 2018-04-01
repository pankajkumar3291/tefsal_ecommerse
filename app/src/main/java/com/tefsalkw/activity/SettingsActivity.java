package com.tefsalkw.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.utils.FontChangeCrawler;
import com.tefsalkw.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_edit_profile)
    TextView text_edit_profile;



    @BindView(R.id.text_language_spinner)
    Spinner text_language_spinner;

    @BindView(R.id.text_send_feedback)
    TextView text_send_feedback;

    @BindView(R.id.text_terms)
    TextView text_terms;

    @BindView(R.id.text_about)
    TextView text_about;

    @BindView(R.id.text_logout)
    TextView text_logout;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.text_change_password)
    TextView text_change_password;



    @BindView(R.id.view1)
            View view1;
    @BindView(R.id.view2)
            View view2;
    @BindView(R.id.view7)
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
               // startActivity(new Intent(SettingsActivity.this, FeedBackActivity.class));

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"info@tefsalkw.com"});
                // intent.putExtra(Intent.EXTRA_CC, new String[] {"andy@whatever.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Tefsal app feedback");
                intent.putExtra(Intent.EXTRA_TEXT, "Tell us your positive or negative experience:\n");
                startActivity(Intent.createChooser(intent, "Tefsal app feedback"));

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
