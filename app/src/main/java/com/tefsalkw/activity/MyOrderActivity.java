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
import com.tefsalkw.adapter.MyOrderAdapter;
import com.tefsalkw.models.MyOrderResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.textMsg)
    TextView textMsg;

    private SessionManager sessionManager;
    private MyOrderAdapter myOrderAdapter;
    private ArrayList<MyOrderResponse> myOrderModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText(getString(R.string.nav_myorders));

        sessionManager = new SessionManager(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();

                startActivity(new Intent(MyOrderActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


            }
        });

        httpGetMyOrderCall();
    }


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        startActivity(new Intent(MyOrderActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    private void httpGetMyOrderCall() {
        SimpleProgressBar.showProgress(MyOrderActivity.this);
        try {
            final String url = Contents.baseURL + "getOrdersList";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                MyOrderResponse mResponse = g.fromJson(response, MyOrderResponse.class);

                                if (mResponse.getStatus().equals("1")) {

                                    if (mResponse.getRecord() != null && mResponse.getRecord().size() > 0) {
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrderActivity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                                layoutManager.getOrientation());
                                        recyclerView.addItemDecoration(dividerItemDecoration);

                                        myOrderAdapter = new MyOrderAdapter(MyOrderActivity.this, mResponse.getRecord(), sessionManager.getKeyLang().equals("Arabic"));
                                        recyclerView.setAdapter(myOrderAdapter);

                                    } else {
                                        Toast.makeText(MyOrderActivity.this, R.string.no_record_found, Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    Toast.makeText(MyOrderActivity.this, R.string.no_record_found, Toast.LENGTH_LONG).show();
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

                    if (sessionManager.getIsGuestId()) {
                        params.put("unique_id", sessionManager.getCustomerId());
                    } else {
                        params.put("user_id", sessionManager.getCustomerId());
                    }

                    params.put("access_token", sessionManager.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");


                    Log.e("Tefsal tailor == ", url + new Gson().toJson(params));

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
}
