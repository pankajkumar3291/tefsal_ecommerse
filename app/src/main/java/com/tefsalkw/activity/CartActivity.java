package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.MyCartAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AC 101 on 11-10-2017.
 */

public class CartActivity extends BaseActivity implements MyCartAdapter.OnCartItemDeletedListener {

    @BindView(R.id.header_txt)
    TextView header_txt;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.edit_btn)
    TextView edit_btn;

    @BindView(R.id.btn_purchase)
    Button btn_purchase;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;


    MyCartAdapter adapter;
    SessionManager session;

    GetCartResponse cartResponse;
    boolean isDelete = true;

    private static Tracker mTracker;
    private static final String TAG = "CartActivity";

    public int currentItemsCount = 0;

    MyCartAdapter.OnCartItemDeletedListener onCartItemDeletedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        ButterKnife.bind(this);

        session = new SessionManager(this);
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        init();

        onCartItemDeletedListener = this;


    }


    @Override
    public void onBackPressed() {

        try {

            Intent intent = getIntent();

            if (intent != null) {
                boolean fromDialogKart = intent.getBooleanExtra("fromDialogKart", false);

                if (fromDialogKart) {
                    startActivity(new Intent(CartActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                } else {
                    super.onBackPressed();
                }

            } else {
                super.onBackPressed();
            }


        } catch (Exception exc) {

        }

    }

    private void init() {

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentItemsCount == 0) {

                    Toast.makeText(CartActivity.this, R.string.cart_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                // Here you need to flush payment method info....
                TefalApp.getInstance().setPayment_method_tc("");
                TefalApp.getInstance().setPayment_method("");

                startActivity(new Intent(CartActivity.this, CartAddressSelectionActivity.class)
                        .putExtra("price", amount.getText().toString())
                        .putExtra("header", header_txt.getText().toString()));

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDelete) {
                    /*recycler_view=null;
                    upadteGetRecord2();

                    isDelete=false;
                    edit_btn.setText("DONE");*/

                    adapter.activateDeleteOption(true);
                    edit_btn.setText(R.string.toolbar_textile_detail_done_txt_text);
                    adapter.notifyDataSetChanged();
                    isDelete = false;


                } else {
                    adapter.activateDeleteOption(false);
                    edit_btn.setText(R.string.toolbar_cart_edit_btn_text);
                    adapter.notifyDataSetChanged();
                    isDelete = true;

                }

            }


        });

        try {

            WebCallServiceCart();


            //=====For getting crash Analytics==================================


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    public void WebCallServiceCart() {

        Log.i(TAG, "Setting screen name: " + "CartActivity");
        mTracker.setScreenName("Image~" + "CartActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        //================================================================================

        SimpleProgressBar.showProgress(CartActivity.this);
        try {
            final String url = Contents.baseURL + "getCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e(CartActivity.class.getSimpleName(), response);
                                Gson g = new Gson();
                                cartResponse = g.fromJson(response, GetCartResponse.class);


                                if (cartResponse != null && !cartResponse.getStatus().equals("0")) {

                                    if (cartResponse.getCart_id() != null) {
                                        session.setKeyCartId(cartResponse.getCart_id());
                                    }


                                    if (cartResponse.getRecord().size() == 1) {

                                        header_txt.setText(getString(R.string.cart_header_txt_text));


                                    } else {
                                        header_txt.setText(String.format(new Locale("en"), getString(R.string.cart_header_txt_texts), cartResponse.getRecord().size()));

                                    }

                                    amount.setText(String.format(new Locale("en"), "%.3f", Float.parseFloat(cartResponse.getTotal_amount_cart())));

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(CartActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler_view.setLayoutManager(layoutManager);
                                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                                    // upadteGetRecord();
                                    adapter = new MyCartAdapter(CartActivity.this, cartResponse.getRecord());
                                    adapter.setOnCartItemDeletedListener(onCartItemDeletedListener);
                                    recycler_view.setAdapter(adapter);
                                    currentItemsCount = cartResponse.getRecord().size();

                                    if (currentItemsCount > 0) {

                                        edit_btn.setVisibility(View.VISIBLE);
                                    } else {
                                        edit_btn.setVisibility(View.GONE);
                                        finish();
                                    }


                                } else {

                                    edit_btn.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Cart is Empty!", Toast.LENGTH_LONG).show();

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
                        params.put("access_token", session.getToken());
                    }

                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal store == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    public void updateUifromAdapter() {

        WebCallServiceCart();

//        totalPrice = 0;
//        if (mResponse.getRecord().size() <= 1)
//            header_txt.setText(mResponse.getRecord().size() + " item in your cart");
//        else
//            header_txt.setText(mResponse.getRecord().size() + " items in your cart");
//
//
//        amount.setText("TOTAL : " + mResponse.getTotal_amount_cart() + " KWD");
//
//
//        System.out.println("I FROM ACTIVITY====");


    }


    @Override
    public void onCartItemDeleted(int currentCount) {

        currentItemsCount = currentCount;
        edit_btn.setText(R.string.edit);
        if (currentItemsCount == 0) {
            edit_btn.setVisibility(View.GONE);

            finish();

        } else {
            edit_btn.setVisibility(View.VISIBLE);
        }


    }
}
