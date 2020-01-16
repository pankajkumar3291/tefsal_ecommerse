package com.tefsalkw.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tefsalkw.R;
import com.tefsalkw.adapter.MailTabAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MailingSystemActivity extends BaseActivity
{

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.btn_back)
    ImageView btn_back;

    @BindView(R.id.btn_write_mail)
    ImageButton btn_write_mail;



    MailTabAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailing_system);

        ButterKnife.bind(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn_write_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MailingSystemActivity.this, SendMailActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));


                // Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });

        loadView();
    }


    private void loadView() {

        tab_layout.addTab(tab_layout.newTab().setText(R.string.btn_inbox));
        tab_layout.addTab(tab_layout.newTab().setText(R.string.btn_sent));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MailTabAdapter(getSupportFragmentManager(), tab_layout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                pager.setCurrentItem(tab.getPosition());
                //Toast.makeText(getContext(),"ITEM=="+tab.getPosition(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
