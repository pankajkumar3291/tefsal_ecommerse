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
import android.widget.LinearLayout;
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
import com.tefsalkw.adapter.CartAddressListAdapter;
import com.tefsalkw.adapter.MyCartAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.eventmodels.CartAddressEvent;
import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.models.MyAddressesModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAddressSelectionActivity extends BaseActivity implements MyCartAdapter.OnCartItemDeletedListener {

    @BindView(R.id.header_txt)
    TextView header_txt;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.edit_btn)
    TextView edit_btn;

    @BindView(R.id.btn_purchase)
    Button btn_purchase;


    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;


    MyCartAdapter adapter;
    SessionManager session;

    private ImageView cart_item_delete;

    int totalPrice = 0;

    GetCartResponse mResponse;
    boolean isDelete = true;

    private static Tracker mTracker;
    private static final String TAG = "CartActivity";

    public int currentItemsCount = 0;

    MyCartAdapter.OnCartItemDeletedListener onCartItemDeletedListener;


    @BindView(R.id.recycler)
    RecyclerView recycler;


    CartAddressListAdapter myAddressAdapter;

    @BindView(R.id.empty_view)
    TextView empty_view;

    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;


    public String defaultAddressId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_address_selection);

        ButterKnife.bind(this);

        session = new SessionManager(this);
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        init();

        onCartItemDeletedListener = this;

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartAddressSelectionActivity.this, AddressesActivity.class));
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCartAddressEvent(CartAddressEvent event) {


        WebCallServiceAddresses();
    }


    public void WebCallServiceAddresses() {
        SimpleProgressBar.showProgress(this);
        try {
            final String url = Contents.baseURL + "getCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                MyAddressesModel mResponse = g.fromJson(response, MyAddressesModel.class);

                                if (mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(CartAddressSelectionActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    myAddressAdapter = new CartAddressListAdapter(CartAddressSelectionActivity.this, mResponse.getRecord());
                                    recycler.setAdapter(myAddressAdapter);

                                    if (mResponse.getRecord().size() == 0) {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.GONE);
                                        empty_view.setText("No address found, please add a new address!");

                                    } else {
                                        llEmpty.setVisibility(View.GONE);
                                        recycler.setVisibility(View.VISIBLE);
                                    }

                                } else {

                                    recycler.setVisibility(View.GONE);
                                    llEmpty.setVisibility(View.VISIBLE);
                                    empty_view.setText("No address found, please add a new address!");
                                    // Toast.makeText(getActivity(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
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
                    params.put("access_token", session.getToken());

                    // System.out.println("ACCESS TOCKEN=="+session.getToken());
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
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            //SimpleProgressBar.closeProgress();
            SimpleProgressBar.closeProgress();
        }
    }


    //************************** CART FUNCTIONALITY **********************************//


    private void init() {

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentItemsCount == 0) {

                    Toast.makeText(CartAddressSelectionActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (defaultAddressId == null || defaultAddressId.equalsIgnoreCase("")) {
                    Toast.makeText(CartAddressSelectionActivity.this, "Please select delivery address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Here you need to flush payment method info....
                TefalApp.getInstance().setPayment_method_tc("");
                TefalApp.getInstance().setPayment_method("");

                Log.e("defaultAddressId", defaultAddressId);
                startActivity(new Intent(CartAddressSelectionActivity.this, PaymentSelectActivity.class)
                        .putExtra("price", totalPrice)
                        .putExtra("defaultAddressId", defaultAddressId)
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

                onBackPressed();

            }


        });

        try {

            WebCallServiceCart();
            WebCallServiceAddresses();

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

        // SimpleProgressBar.showProgress(CartAddressSelectionActivity.this);
        try {
            final String url = Contents.baseURL + "getCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e(CartActivity.class.getSimpleName(), response);
                                Gson g = new Gson();
                                mResponse = g.fromJson(response, GetCartResponse.class);
                                if (!mResponse.getStatus().equals("0")) {
                                    if (mResponse.getRecord().size() <= 1)
                                        header_txt.setText(mResponse.getRecord().size() + " item in your cart");
                                    else
                                        header_txt.setText(mResponse.getRecord().size() + " items in your cart");

                                    for (int i = 0; i < mResponse.getRecord().size(); i++) {
                                        GetCartRecord getCartRecord = mResponse.getRecord().get(i);
                                        if (getCartRecord != null) {

                                            if (getCartRecord.getItem_type().equalsIgnoreCase("DTA")) {

                                                totalPrice += Float.parseFloat(getCartRecord.getTotal_amount());
                                                totalPrice += Float.parseFloat(getCartRecord.getTailor_services().getTotal_amount());

                                            } else {

                                                totalPrice += Float.parseFloat(getCartRecord.getTotal_amount());

                                            }
                                        }
                                    }
                                    amount.setText("TOTAL : " + totalPrice + " KWD");
                                    currentItemsCount = mResponse.getRecord().size();

                                } else {
                                    Toast.makeText(getApplicationContext(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
                    params.put("access_token", session.getToken());
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
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void updateUifromAdapter(List<GetCartRecord> storeModels) {
        float totalPrice = 0;
        if (mResponse.getRecord().size() <= 1)
            header_txt.setText(mResponse.getRecord().size() + " item in your cart");
        else
            header_txt.setText(mResponse.getRecord().size() + " items in your cart");

        for (int i = 0; i < mResponse.getRecord().size(); i++) {

            try {
                GetCartRecord getCartRecord = mResponse.getRecord().get(i);
                if (getCartRecord != null) {

                    if (getCartRecord.getItem_type().equalsIgnoreCase("DTA")) {

                        totalPrice += Float.parseFloat(getCartRecord.getTotal_amount());
                        totalPrice += Float.parseFloat(getCartRecord.getTailor_services().getTotal_amount());

                    } else {

                        totalPrice += Float.parseFloat(getCartRecord.getTotal_amount());

                    }
                }
            } catch (Exception ex) {
                System.out.println("Exception=====" + ex);
            }


            // totalPrice -= Double.valueOf(mResponse.getRecord().get(i).getPrice());
        }
        amount.setText("TOTAL : " + totalPrice + "00 KWD");


        System.out.println("I FROM ACTIVITY====");
    }


    @Override
    public void onCartItemDeleted(int currentCount) {

        currentItemsCount = currentCount;

        if (currentItemsCount == 0) {
            edit_btn.setVisibility(View.GONE);
        } else {
            edit_btn.setVisibility(View.VISIBLE);
        }
    }

}
