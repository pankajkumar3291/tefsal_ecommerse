package com.tefsalkw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.adapter.PagerTabAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentDishdasha;
import com.tefsalkw.models.DishdashaStylesRecord;
import com.tefsalkw.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabbarActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.newStyleBtn)
    ImageView newStyleBtn;

    private DishdashaStylesRecord mDishdashaStylesRecord;
    private SessionManager session;
    private ViewPager viewPager;
    private TefalApp mTefalApp;


    EditText input_style_name;
    Button dialog_ok_btn;
    Button dialog_cancel_btn;
    TextInputLayout input_layout_style_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_styles);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        newStyleBtn.setVisibility(View.VISIBLE);
        toolbar_title.setText("MY STYLES");


        session = new SessionManager(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        newStyleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNamePrompt();

            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_dishdasha));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_daraa));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.btn_abaya));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        final PagerTabAdapter adapter = new PagerTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    TefalApp.getInstance().setmCategory("1");
                    TefalApp.getInstance().setmAction("create");
                }
                if (tab.getPosition() == 1) {

                    TefalApp.getInstance().setmCategory("2");
                    TefalApp.getInstance().setmAction("create");
                }
                if (tab.getPosition() == 2) {

                    TefalApp.getInstance().setmCategory("3");
                    TefalApp.getInstance().setmAction("create");
                }


                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void showNamePrompt() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_name_dialog, null);

        input_layout_style_name = (TextInputLayout) dialogView.findViewById(R.id.input_layout_style_name);
        input_style_name = (EditText) dialogView.findViewById(R.id.input_style_name);
        dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateStyleName() && isUniqueName()) {
                    measurementActivityGo();
                    alertDialog.dismiss();
                }
            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private boolean validateStyleName() {
        if (input_style_name.getText().toString().trim().equals("")) {
            input_layout_style_name.setError("Style name should not be empty");
            requestFocus(input_style_name);
            return false;
        }

        return true;
    }


    private boolean isUniqueName() {
        boolean isNameUnique = true;


        if (FragmentDishdasha.mResponse != null && FragmentDishdasha.mResponse.getRecord() != null) {
            if (FragmentDishdasha.mResponse.getRecord().size() > 0) {


                String styleName = input_style_name.getText().toString().toLowerCase().trim();
                for (DishdashaStylesRecord dishdashaStylesRecord : FragmentDishdasha.mResponse.getRecord()) {

                    if (dishdashaStylesRecord.getName().toLowerCase().trim().equalsIgnoreCase(styleName)) {
                        isNameUnique = false;
                    }
                }
            }


            if (!isNameUnique) {
                input_layout_style_name.setError("Duplicate style name");
                requestFocus(input_style_name);
            }
        }


        return isNameUnique;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void measurementActivityGo() {
        Bundle bundle = new Bundle();
        mDishdashaStylesRecord = new DishdashaStylesRecord();


        mDishdashaStylesRecord.setNeck("0.0");
        mDishdashaStylesRecord.setChest("0.0");
        mDishdashaStylesRecord.setWrist("0.0");
        mDishdashaStylesRecord.setWaist("0.0");
        mDishdashaStylesRecord.setArm("0.0");
        mDishdashaStylesRecord.setFront_height("0.0");
        mDishdashaStylesRecord.setBack_height("0.0");
        mDishdashaStylesRecord.setShoulder("0.0");


        mDishdashaStylesRecord.setButtons("1");
        mDishdashaStylesRecord.setCollar_button_visibility("no");

        mDishdashaStylesRecord.setPen_pocket("no");
        mDishdashaStylesRecord.setMobile_pocket("no");
        mDishdashaStylesRecord.setKey_pocket("no");
        mDishdashaStylesRecord.setWide("0");

        mDishdashaStylesRecord.setCollar_buttons("3");
        mDishdashaStylesRecord.setShirt_button_visibility("no");

        mDishdashaStylesRecord.setCollar_buttons_push("no");

        mDishdashaStylesRecord.setCufflink("no");
        mDishdashaStylesRecord.setId("");


        mDishdashaStylesRecord.setCategory("0");
        mDishdashaStylesRecord.setNarrow("0");

        mDishdashaStylesRecord.setUpdated_at("");
        mDishdashaStylesRecord.setCreated_at("");

        mDishdashaStylesRecord.setName(input_style_name.getText().toString());

        mDishdashaStylesRecord.setUser_id(session.getCustomerId());


        //Yur work here----


        bundle.putSerializable("STYLE_DATA", mDishdashaStylesRecord);
        bundle.putString("flow", "TabbarActivity");
        // mTefalApp=TefalApp.getInstance();
        if (viewPager.getCurrentItem() == 0) {
            TefalApp.getInstance().setmCategory("1");
            TefalApp.getInstance().setmAction("create");
        }
        Intent i = new Intent(getApplicationContext(), MeasermentActivity.class);
        i.putExtras(bundle);

        startActivity(i);
    }
}
