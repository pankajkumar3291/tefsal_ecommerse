package com.tefsalkw.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.models.MyAddressesModel;
import com.tefsalkw.R;
import com.tefsalkw.adapter.MyAddressAdapter;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AC 101 on 23-10-2017.
 */

public class AddressPageActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;

    MyAddressAdapter adapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_page);
        ButterKnife.bind(this);

        session = new SessionManager(this);

//
//        Button btn_deliver=(Button)findViewById(R.id.btn_deliver);
//        btn_deliver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(AddressPageActivity.this,PaymentSelectActivity.class);
//                startActivity(i);
//            }
//        });
//        Button btn_deliver_diwaniya=(Button)findViewById(R.id.btn_deliver_diwaniya);
//        btn_deliver_diwaniya.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(AddressPageActivity.this,PaymentSelectActivity.class);
//                startActivity(i);
//            }
//        });
        WebCallServiceAddresses();

    }
    public void WebCallServiceAddresses() {
        loading.setVisibility(View.VISIBLE);
        try {
            final String url = Contents.baseURL + "getCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            loading.setVisibility(View.GONE);

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                MyAddressesModel mResponse = g.fromJson(response, MyAddressesModel.class);

                                if (mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(AddressPageActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    adapter = new MyAddressAdapter(AddressPageActivity.this, mResponse.getRecord());
                                    recycler.setAdapter(adapter);
                                }
                                else {
                                    Toast.makeText(AddressPageActivity.this,mResponse.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.setVisibility(View.GONE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("access_token", session.getToken());
                    params.put("customer_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    Log.e("Tefsal store == ", url + params);

                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AddressPageActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            //SimpleProgressBar.closeProgress();
        }
    }
}
