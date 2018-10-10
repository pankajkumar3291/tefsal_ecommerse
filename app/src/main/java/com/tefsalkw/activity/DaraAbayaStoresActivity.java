package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.tefsalkw.adapter.OtherStoresAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.HomeFragment;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.DishdashaStoreModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaraAbayaStoresActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    OtherStoresAdapter adapter;
    SessionManager session;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;

    String flag;
    public static String sub_cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dara_abaya_stores);

        ButterKnife.bind(this);
        session = new SessionManager(this);

        setSupportActionBar(toolbar);

        view_cart_btn.setVisibility(View.VISIBLE);
        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(DaraAbayaStoresActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        toolbar_title.setText(TefalApp.getInstance().getToolbar_title());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        flag = getIntent().getStringExtra("flag");


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getAction() != null && intent.getAction().equals("FromPushNotification")) {

                flag = intent.getStringExtra("flag");

                if (flag.equals("Accessories")) {

                    sub_cat_id = getIntent().getStringExtra("sub_cat");

                }

            }
        }


        WebCallServiceStores();
    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.e(DaraAbayaStoresActivity.class.getSimpleName(), "onResume");

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

                                        if (total_badge_txt != null) {
                                            String record = jsonObject.getString("record");
                                            Gson g = new Gson();
                                            BadgeRecordModel badgeRecordModel = g.fromJson(record, BadgeRecordModel.class);
                                            total_badge_txt.setText(badgeRecordModel.getCart_badge());
                                        }


                                    }
                                } catch (Exception ex) {

                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
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


    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(DaraAbayaStoresActivity.this);
        try {
            final String url = Contents.baseURL + "getStores";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                System.out.println("Response=====" + response);
                                Log.e("stores response", response);


                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");
                                    String msg = object.getString("message");

                                    if (status.equals("1")) {
                                        Gson g = new Gson();
                                        DishdashaStoreModel mResponse = g.fromJson(response, DishdashaStoreModel.class);

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(DaraAbayaStoresActivity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                        recycler.setLayoutManager(layoutManager);
                                        recycler.setItemAnimator(new DefaultItemAnimator());
                                        adapter = new OtherStoresAdapter(DaraAbayaStoresActivity.this, mResponse.getRecord(), flag);
                                        recycler.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();


                    params.put("category", HomeFragment.productFlag);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    if (flag.equals("Daraa")) {
                        params.put("sub_category", "");
                        params.put("category", "3");
                    } else if (flag.equals("Abaya")) {
                        params.put("sub_category", "");
                        params.put("category", "2");
                    } else if (flag.equals("Accessories")) {
                        params.put("category", "4");
                        params.put("sub_category", getIntent().getStringExtra("sub_cat"));
                    }

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(DaraAbayaStoresActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }
}
