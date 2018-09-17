package com.tefsalkw.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.utils.FontChangeCrawler;
import com.tefsalkw.utils.SessionManager;

import java.util.Locale;

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

    boolean userIsInteracting = false;

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

        if (session.getIsGuestId()) {

            //  text_logout.setVisibility(GONE);
            text_change_password.setVisibility(GONE);
            text_edit_profile.setVisibility(GONE);
            view1.setVisibility(GONE);
            view2.setVisibility(GONE);
            view7.setVisibility(GONE);

        }


        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(SettingsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(SettingsActivity.this);
                }
                builder.setTitle(R.string.logout)
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                session.user_logout();
                                startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.cancel();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorWhite));
                        dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                });
                dialog.show();


            }
        });

        text_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getCustomerId().equals("")) {
                    startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                } else {
                    startActivity(new Intent(SettingsActivity.this, ChangePasswordActivity.class));
                }

            }
        });

        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, AboutUsActivity.class));
            }
        });


        text_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, TermsConditionActitivity.class));
            }
        });

        text_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(SettingsActivity.this, FeedBackActivity.class));

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@tefsalkw.com"});
                // intent.putExtra(Intent.EXTRA_CC, new String[] {"andy@whatever.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Tefsal app feedback");
                intent.putExtra(Intent.EXTRA_TEXT, "Tell us your positive or negative experience:\n");
                startActivity(Intent.createChooser(intent, "Tefsal app feedback"));

            }
        });
        text_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (session.getCustomerId().equals("")) {
                    startActivity(new Intent(SettingsActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                } else {
                    startActivity(new Intent(SettingsActivity.this, EditProfileActivity.class));
                }
            }
        });


        Log.e("session.getKeyLang()", session.getKeyLang());

        if (session.getKeyLang().equalsIgnoreCase("Arabic")) {
            text_language_spinner.setSelection(1);
        } else {
            text_language_spinner.setSelection(0);
        }


        text_language_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                if (userIsInteracting) {

                    session.setKeyLang(parentView.getSelectedItem().toString());

                    String lang = position == 0 ? "en" : "ar";
                    Locale locale = new Locale(lang);
                    Locale.setDefault(locale);

                    Resources resources = getResources();
                    Configuration configuration = resources.getConfiguration();
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    configuration.setLocale(locale);
                    resources.updateConfiguration(configuration, displayMetrics);


                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here

            }

        });


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        userIsInteracting = true;
    }
}
