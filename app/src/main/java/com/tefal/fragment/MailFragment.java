package com.tefal.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tefal.R;
import com.tefal.activity.BaseActivity;
import com.tefal.activity.DaraAbayaActivity;
import com.tefal.adapter.MailTabAdapter;
import com.tefal.adapter.PagerTabAdapter;
import com.tefal.utils.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class MailFragment extends Fragment {

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.pager)
    ViewPager pager;

    MailTabAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mail, container, false);
        ButterKnife.bind(this, v);

        loadView();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        //loadView();
        //System.out.println("Start====");

// add your code here which executes when the Fragment gets visible.
    }
    private void loadView() {

        tab_layout.addTab(tab_layout.newTab().setText(R.string.btn_inbox));
        tab_layout.addTab(tab_layout.newTab().setText(R.string.btn_sent));
        tab_layout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MailTabAdapter(getActivity().getSupportFragmentManager(), tab_layout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
