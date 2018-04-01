package com.tefsalkw.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.Models.BadgeRecordModel;
import com.tefsalkw.Models.GetCartRecord;
import com.tefsalkw.Models.TailoringRecord;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTailorProducts;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TailorProductActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
    ArrayList<TailoringRecord> tailoringRecordArrayListOfChecked = new ArrayList<TailoringRecord>();


    List<GetCartRecord> getCartRecordListOfChecked = new ArrayList<GetCartRecord>();
    List<GetCartRecord> getCartRecordListOfCheckedTrue = new ArrayList<GetCartRecord>();

    private String ownTextileString;

    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_product);

        ButterKnife.bind(this);
        session = new SessionManager(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar_title.setText(TefalApp.getInstance().getStoreName());
        //subText.setText(TefalApp.getInstance().getWhereFrom());
        subText.setText("Tailors");


        Bundle bundle = getIntent().getExtras();
        tailoringRecordArrayListOfChecked = (ArrayList<TailoringRecord>) bundle.getSerializable("tailoringRecordArrayListOfChecked");

        if (tailoringRecordArrayListOfChecked == null) {
            ownTextileString = bundle.getString("ownTextileString");
            System.out.println("OUTPUT==========STORE ID===" + ownTextileString);
        }

        System.out.println("OUTPUT==========STORE ID===" + TefalApp.getInstance().getStoreId());
        System.out.println("OUTPUT==========STORE ID===" + getCartRecordListOfChecked);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        fragmentInflation();
    }

    public void gotoCart(View view) {
        startActivity(new Intent(TailorProductActivity.this, CartActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.e(DaraAbayaActivity.class.getSimpleName(),"onResume");

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


                            // SimpleProgressBar.closeProgress();

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
                    params.put("user_id", session.getCustomerId());
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

    private void fragmentInflation() {
        Bundle bundle = new Bundle();

        bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
        bundle.putSerializable("ownTextileString", ownTextileString);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTailorProducts fragmentTailorProducts = new FragmentTailorProducts();
        fragmentTailorProducts.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, fragmentTailorProducts);
        fragmentTransaction.commit();

    }
}
