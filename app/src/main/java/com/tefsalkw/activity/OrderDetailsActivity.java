package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
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
import com.tefsalkw.adapter.OrderDetailsAdapter;
import com.tefsalkw.models.Customer_address;
import com.tefsalkw.models.OrderDetails;
import com.tefsalkw.models.OrderDetailsResponse;
import com.tefsalkw.models.OrderRecord;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.DateTimeHelper;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends BaseActivity {


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

    @BindView(R.id.txtTxnDate)
    TextView txtTxnDate;

    @BindView(R.id.txtCustomerAddress)
    TextView txtCustomerAddress;

    @BindView(R.id.txtPaymentType)
    TextView txtPaymentType;


    private SessionManager sessionManager;
    private OrderDetailsAdapter orderDetailsAdapter;
    private ArrayList<OrderDetailsResponse> orderDetailsModels;

    OrderRecord orderRecord = null;

    @BindView(R.id.txtDeliveryCharge)
    TextView txtDeliveryCharge;

    String orderId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ButterKnife.bind(this);


        sessionManager = new SessionManager(this);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();

                onBackPressed();

            }
        });


        Intent intent = getIntent();


        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase("FromPushNotification")) {


            orderId = intent.getStringExtra("order_id") + "";
        } else {
            orderRecord = (OrderRecord) getIntent().getSerializableExtra("OrderRecord");
            if (orderRecord != null && orderRecord.getOrder_id() != null) {
                orderId = orderRecord.getOrder_id();
            }
        }


        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText("ORDER #" + orderId);


        } catch (Exception exc) {

        }

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        httpGetMyOrderDetailsCall();


    }


    private void httpGetMyOrderDetailsCall() {
        SimpleProgressBar.showProgress(OrderDetailsActivity.this);
        try {
            final String url = Contents.baseURL + "getOrderDetails";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();


                                OrderDetailsResponse orderDetailsResponse = g.fromJson(response, OrderDetailsResponse.class);


                                if (orderDetailsResponse != null && orderDetailsResponse.getStatus() == 1) {

                                    OrderDetails mResponse = orderDetailsResponse.getRecord();

                                    //Set address details

                                    txtTxnDate.setText(" " + DateTimeHelper.getFormattedDate(mResponse.getCreated_at()));

                                    String deliveryCharge = mResponse.getDelivery_charge();


                                    if (deliveryCharge != null && !deliveryCharge.equalsIgnoreCase("free")) {


                                        deliveryCharge = String.format(new Locale("en"), "%.3f", Double.parseDouble(deliveryCharge)) + " KWD";


                                    }


                                    txtDeliveryCharge.setText(deliveryCharge);
                                    Customer_address customer_address = mResponse.getCustomer_address();

                                    String fullAddress = "";

                                    if (customer_address != null) {
                                        String name = customer_address.getAddress_name();
                                        String house = customer_address.getHouse();
                                        String street = customer_address.getStreet();
                                        String block = customer_address.getBlock();
                                        String area = customer_address.getArea();
                                        String city = customer_address.getProvince();

                                        name = name != null ? name : "-";
                                        house = house != null ? house : "-";
                                        street = street != null ? street : "-";
                                        block = block != null ? block : "-";
                                        area = area != null ? area : "-";
                                        city = city != null ? city : "-";

                                        fullAddress = name.toUpperCase() + "\n"
                                                + "House / Building: " + house + " , "
                                                + "Street: " + street + " , "
                                                + "Block: " + block + " , "
                                                + "Area: " + area + " , "
                                                + "City: " + city + " ";
                                    }


                                    // String fullAddress = customer_address.getHouse() + " " + customer_address.getAddress_name() + " " + customer_address.getProvince() + " " + customer_address.getArea()+ " " + customer_address.getBlock() + " " + customer_address.getStreet() + " " + customer_address.getFloor() + " " + customer_address.getFlat_number()+ " " + customer_address.getHouse();
                                    txtCustomerAddress.setText(fullAddress);
                                    txtPaymentType.setText(mResponse.getPayment_method());


                                    LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, mResponse.getOrder_items());
                                    recyclerView.setAdapter(orderDetailsAdapter);


                                } else {
                                    Toast.makeText(OrderDetailsActivity.this, "No record found", Toast.LENGTH_LONG).show();
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
                    params.put("order_id", orderId);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = getIntent();

        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase("FromPushNotification")) {

            startActivity(new Intent(OrderDetailsActivity.this, MyOrderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        } else {
            finish();
        }


    }


}
