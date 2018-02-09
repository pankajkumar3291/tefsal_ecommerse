package com.tefal.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tefal.R;
import com.tefal.adapter.PagerStoresAdapter;
import com.tefal.adapter.PagerTabAdapter;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentAllStores;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaraAbayaActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.qr_code_btn)
    ImageView qr_code_btn;

    @BindView(R.id.view_cart_btn)
    ImageView view_cart_btn;
    public static String flag="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dara_abaya);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
       // qr_code_btn.setVisibility(View.VISIBLE);
        view_cart_btn.setVisibility(View.VISIBLE);

        toolbar_title.setText( TefalApp.getInstance().getToolbar_title());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        flag = getIntent().getStringExtra("flag");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        /*tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_view_all));*/
        tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_textiles));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_tailors));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        final PagerStoresAdapter adapter = new PagerStoresAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),flag);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        setUpFragment();
    }


    public  void gotoCart(View v)
    {
        try {
            startActivity(new Intent(DaraAbayaActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
//    private void setUpFragment()
//    {
//        FragmentAllStores fragmentAllStores = new FragmentAllStores();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.fragmentContainer, fragmentAllStores);
//        ft.commit();
//    }


}
