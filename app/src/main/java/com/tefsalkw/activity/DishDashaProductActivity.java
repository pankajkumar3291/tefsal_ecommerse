package com.tefsalkw.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.fragment.TailorTextileChooseFragment;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishDashaProductActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.qr_code_btn)
    ImageView qr_code_btn;

    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;


    @BindView(R.id.subText)
    TextView subText;


    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;

    SessionManager session;
    GetCartResponse mResponse;
    List<GetCartRecord> getCartRecordList;

    //This list is used to stored the filter data of Tailor product having itemtype DTA from GetCart Response
    List<GetCartRecord> getCartRecordList2;


    String store_id, flag, fromWhere, color, sub_color, season, country;
    //  public static TabLayout tabLayout;
    //public static ViewPager viewPager;


    /*
     * This dialog is used to show the image which can zoom in zoom out from view pager
     * */
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_dasha_product);

        ButterKnife.bind(this);


        store_id = TefalApp.getInstance().getStoreId();
        flag = TefalApp.getInstance().getFlage();
        fromWhere = TefalApp.getInstance().getWhereFrom();
        color = TefalApp.getInstance().getColor();
        season = TefalApp.getInstance().getSeason();
        country = TefalApp.getInstance().getCountry();
        sub_color = TefalApp.getInstance().getSubColor();
        session = new SessionManager(this);

        System.out.println("OUTPUT========STORE ID :: " + store_id);
        System.out.println("OUTPUT========FLAG :: " + flag);
        System.out.println("OUTPUT========FROMWHERE :: " + fromWhere);
        System.out.println("OUTPUT========COLOR :: " + color);
        System.out.println("OUTPUT========SEASON :: " + season);
        System.out.println("OUTPUT========COUNTRY :: " + country);
        System.out.println("OUTPUT========SUB COLOR :: " + sub_color);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getAction() != null && intent.getAction().equals("FromPushNotification")) {
                store_id = intent.getStringExtra("store_id");
                flag = intent.getStringExtra("flag");
                fromWhere = intent.getStringExtra("fromWhere");

            }
        }


        /*TefalApp.getInstance().setFlage(flag);
        TefalApp.getInstance().setStoreId(store_id);*/


        setSupportActionBar(toolbar);
        //toolbar_title.setText(getIntent().getStringExtra("title"));

        qr_code_btn.setVisibility(View.GONE);
        view_cart_btn.setVisibility(View.VISIBLE);
        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(DishDashaProductActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        toolbar_title.setText(TefalApp.getInstance().getStoreName());


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fragmentInflate();


    }


    @Override
    public void onBackPressed() {


        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        for (Fragment f : fragmentList) {
            if (f instanceof TailorTextileChooseFragment) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }


                builder.setTitle("Cancel Textile Selection")
                        .setMessage("Are you sure you want to cancel?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete


                                finish();

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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


            } else {

                Intent mainIntent = new Intent(DishDashaProductActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                //finish();

            }
        }


    }

    public void gotoCart(View v) {
        try {
            startActivity(new Intent(DishDashaProductActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.e(DishDashaProductActivity.class.getSimpleName(), "onResume");

        if (toolbar_title != null)
            toolbar_title.setText(TefalApp.getInstance().getStoreName());
        httpGetBadgesCall();

    }


    private void httpGetBadgesCall() {
        // SimpleProgressBar.showProgress(SendMailActivity.this);
        try {
            final String url = Contents.baseURL + "getBadges";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());


                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    if (status.equals("1")) {

                                        String record = jsonObject.getString("record");
                                        Gson g = new Gson();
                                        BadgeRecordModel badgeRecordModel = g.fromJson(record, BadgeRecordModel.class);
                                        total_badge_txt.setText(badgeRecordModel.getCart_badge());


                                    }
                                } catch (Exception ex) {

                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error==" + error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (session.getIsGuestId()) {
                        params.put("unique_id", session.getCustomerId());
                    } else {
                        params.put("user_id", session.getCustomerId());
                    }
                    params.put("appUser", "tefsal");
                    params.put("appVersion", "1.1");
                    params.put("appSecret", "tefsal@123");

                    Log.e("Tefsal tailor == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private void fragmentInflate() {


        if (fromWhere != null) {

            if (fromWhere.equals("textile")) {
                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("flag", "dish");

                subText.setText("Textiles");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTextileProducts fragmentTextileProducts = new FragmentTextileProducts();
                fragmentTextileProducts.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, fragmentTextileProducts);
                fragmentTransaction.commit();

            }
            if (fromWhere.equals("tailor")) {


                subText.setText("Tailors");

                Bundle bundle = new Bundle();
                bundle.putString("store_id", store_id);
                bundle.putString("flag", "dish");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TailorTextileChooseFragment tailorTextileChooseFragment = new TailorTextileChooseFragment();
                tailorTextileChooseFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, tailorTextileChooseFragment);

                fragmentTransaction.commit();


            }

        }


    }


}
