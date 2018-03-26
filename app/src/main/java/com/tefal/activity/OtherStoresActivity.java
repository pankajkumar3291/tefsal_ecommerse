package com.tefal.activity;

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
import com.tefal.Models.BadgeRecordModel;
import com.tefal.Models.DishdashaStoreModel;
import com.tefal.R;
import com.tefal.adapter.OtherStoresAdapter;
import com.tefal.app.TefalApp;
import com.tefal.fragment.HomeFragment;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherStoresActivity extends BaseActivity {

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
        setContentView(R.layout.activity_other_stores);

        ButterKnife.bind(this);
        session = new SessionManager(this);

        setSupportActionBar(toolbar);

        view_cart_btn.setVisibility(View.VISIBLE);


        toolbar_title.setText(TefalApp.getInstance().getToolbar_title());

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        flag = getIntent().getStringExtra("flag");


        if (flag.equals("Accessories"))
        {
            sub_cat_id = getIntent().getStringExtra("sub_cat");
            System.out.println("SUB CATEGORY==="+sub_cat_id);

        }


        WebCallServiceStores();
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




    public  void gotoCart(View v)
    {
        try {
            startActivity(new Intent(OtherStoresActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(OtherStoresActivity.this);
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
                                    JSONObject object=new JSONObject(response);
                                    String status=object.getString("status");
                                    String msg=object.getString("message");

                                    if(status.equals("1"))
                                    {
                                        Gson g = new Gson();
                                        DishdashaStoreModel mResponse = g.fromJson(response, DishdashaStoreModel.class);

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(OtherStoresActivity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                        recycler.setLayoutManager(layoutManager);
                                        recycler.setItemAnimator(new DefaultItemAnimator());
                                        adapter = new OtherStoresAdapter(OtherStoresActivity.this, mResponse.getRecord(), flag);
                                        recycler.setAdapter(adapter);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(OtherStoresActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }
}
