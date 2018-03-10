package com.tefal.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tefal.Models.MailModel;
import com.tefal.Models.SentMailModel;
import com.tefal.R;
import com.tefal.app.TefalApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.txt_mail_heading)
    TextView txt_mail_heading;

    @BindView(R.id.txt_mail_details)
    TextView txt_mail_details;


    //Hold the data of sent mail;
    SentMailModel sentMailModel = new SentMailModel();

    // Hold the data of inbox mail;
    MailModel mailModel = new MailModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("MAIL");
        //toolbar.setTitle("Terms & Conditions");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (TefalApp.getInstance().getWhereFromInMail().equals("from sent")) {
            sentMailModel = (SentMailModel) getIntent().getSerializableExtra("sentMailModel");

            txt_mail_heading.setText("Subject: " + sentMailModel.getSubject());
            txt_mail_details.setText(sentMailModel.getMessage());
            System.out.println("I AM FROM SENT====" + sentMailModel.toString());
        }
        if (TefalApp.getInstance().getWhereFromInMail().equals("from inbox")) {
            mailModel = (MailModel) getIntent().getSerializableExtra("mailModel");

            txt_mail_heading.setText("Subject: " + mailModel.getSubject());
            txt_mail_details.setText(mailModel.getMessage());
            System.out.println("I AM FROM INBOX====" + mailModel.toString());
        }

    }
}
