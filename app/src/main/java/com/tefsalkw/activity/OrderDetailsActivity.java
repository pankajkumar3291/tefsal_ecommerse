package com.tefsalkw.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.tefsalkw.models.MyOrderResponse;
import com.tefsalkw.models.OrderDetailsResponse;
import com.tefsalkw.models.OrderItems;
import com.tefsalkw.models.OrderRecord;
import com.tefsalkw.models.OrderRecordCustom;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.DateTimeHelper;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsActivity extends AppCompatActivity {


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


        orderRecord = (OrderRecord) getIntent().getSerializableExtra("OrderRecord");

        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            if (orderRecord != null && orderRecord.getOrder_id() != null) {
                toolbar_title.setText("ORDER #" + orderRecord.getOrder_id());
            }

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
                                MyOrderResponse mResponse = g.fromJson(response, MyOrderResponse.class);

                                if (mResponse.getStatus().equals("1")) {

                                    if (mResponse.getRecord() != null && mResponse.getRecord().size() > 0) {

                                        //Set address details

                                        OrderRecord orderRecord = mResponse.getRecord().get(0);

                                        txtTxnDate.setText(DateTimeHelper.getFormattedDate(orderRecord.getCreated_at()));

                                        String fullAddress = orderRecord.getHouse() + " " + orderRecord.getAddress_name() + " " + orderRecord.getAvenue() + " " + orderRecord.getBlock() + " " + orderRecord.getStreet() + " " + orderRecord.getFloor() + " " + orderRecord.getFlat_number();
                                        txtCustomerAddress.setText(fullAddress);
                                        txtPaymentType.setText(orderRecord.getPayment_method());

                                        //fill box

                                        //Log.e("orderRecordCustomList", new Gson().toJson(mResponse.getRecord()));

                                        HashMap<String, OrderRecordCustom> listHashMap = new HashMap<String, OrderRecordCustom>();


                                        for (OrderItems orderItems : orderRecord.getItems()) {

                                            if (orderItems.getItem_type().equalsIgnoreCase("DTA") || orderItems.getItem_type().equalsIgnoreCase("DTE")) {

                                                if (listHashMap.containsKey(orderItems.getStore_id() + "DAAE")) {


                                                    if (orderItems.getItem_type().equalsIgnoreCase("DTE")) {

                                                        List<OrderItems> textileItems = listHashMap.get(orderItems.getStore_id() + "DAAE").getTextileItems();

                                                        if (textileItems != null) {

                                                            listHashMap.get(orderItems.getStore_id() + "DAAE").getTextileItems().add(orderItems);
                                                        } else {

                                                            List<OrderItems> orderItemsList = new ArrayList<>();
                                                            orderItemsList.add(orderItems);
                                                            listHashMap.get(orderItems.getStore_id() + "DAAE").setTextileItems(orderItemsList);
                                                        }


                                                    }

                                                    if (orderItems.getItem_type().equalsIgnoreCase("DTA")) {

                                                        List<OrderItems> textileItems = listHashMap.get(orderItems.getStore_id() + "DAAE").getTailorItems();

                                                        if (textileItems != null) {

                                                            listHashMap.get(orderItems.getStore_id() + "DAAE").getTailorItems().add(orderItems);
                                                        } else {

                                                            List<OrderItems> orderItemsList = new ArrayList<>();
                                                            orderItemsList.add(orderItems);
                                                            listHashMap.get(orderItems.getStore_id() + "DAAE").setTailorItems(orderItemsList);
                                                        }


                                                    }

                                                    Float totalNew = listHashMap.get(orderItems.getStore_id() + "DAAE").getTotalAmount();
                                                    totalNew = totalNew + Float.parseFloat(orderItems.getPrice());
                                                    listHashMap.get(orderItems.getStore_id() + "DAAE").setTotalAmount(totalNew);

                                                } else {

                                                    OrderRecordCustom orderRecordCustom = new OrderRecordCustom();

                                                    if (orderItems.getItem_type().equalsIgnoreCase("DTE")) {
                                                        List<OrderItems> orderItemsList = new ArrayList<>();
                                                        orderItemsList.add(orderItems);

                                                        orderRecordCustom.setTextileItems(orderItemsList);
                                                        orderRecordCustom.setItemType(orderItems.getItem_type());
                                                        orderRecordCustom.setStoreId(orderItems.getStore_id());
                                                        orderRecordCustom.setStoreName(orderItems.getStore_name());
                                                        orderRecordCustom.setStyleName(orderItems.getStyle().getName());
                                                        orderRecordCustom.setTotalAmount(Float.parseFloat(orderItems.getPrice()));
                                                        orderRecordCustom.setStoreImage(orderItems.getStore_img());
                                                        orderRecordCustom.setExpected_delivery_date(DateTimeHelper.getDeliveryDate(orderRecord.getCreated_at(),orderItems.getMax_delivery_days()));
                                                        orderRecordCustom.setDelivery_status(orderRecord.getDelivery_status());
                                                        listHashMap.put(orderItems.getStore_id() + "DAAE", orderRecordCustom);
                                                    }

                                                    if (orderItems.getItem_type().equalsIgnoreCase("DTA")) {
                                                        List<OrderItems> orderItemsList = new ArrayList<>();
                                                        orderItemsList.add(orderItems);

                                                        orderRecordCustom.setTailorItems(orderItemsList);

                                                        orderRecordCustom.setItemType(orderItems.getItem_type());
                                                        orderRecordCustom.setStoreId(orderItems.getStore_id());
                                                        orderRecordCustom.setStoreName(orderItems.getStore_name());
                                                        orderRecordCustom.setStyleName(orderItems.getStyle().getName());
                                                        orderRecordCustom.setTotalAmount(Float.parseFloat(orderItems.getPrice()));
                                                        orderRecordCustom.setStoreImage(orderItems.getStore_img());
                                                        orderRecordCustom.setExpected_delivery_date(DateTimeHelper.getDeliveryDate(orderRecord.getCreated_at(),orderItems.getMax_delivery_days()));
                                                        orderRecordCustom.setDelivery_status(orderRecord.getDelivery_status());
                                                        listHashMap.put(orderItems.getStore_id() + "DAAE", orderRecordCustom);
                                                    }

                                                }


                                            } else {


                                                if (listHashMap.containsKey(orderItems.getStore_id() + orderItems.getItem_type())) {


                                                    List<OrderItems> textileItems = listHashMap.get(orderItems.getStore_id() + orderItems.getItem_type()).getOtherItems();

                                                    if (textileItems != null) {

                                                        listHashMap.get(orderItems.getStore_id() + orderItems.getItem_type()).getOtherItems().add(orderItems);
                                                    } else {

                                                        List<OrderItems> orderItemsList = new ArrayList<>();
                                                        orderItemsList.add(orderItems);

                                                        listHashMap.get(orderItems.getStore_id() + orderItems.getItem_type()).setOtherItems(orderItemsList);
                                                    }

                                                    Float totalNew = listHashMap.get(orderItems.getStore_id() + orderItems.getItem_type()).getTotalAmount();
                                                    totalNew = totalNew + Float.parseFloat(orderItems.getPrice());
                                                    listHashMap.get(orderItems.getStore_id() + orderItems.getItem_type()).setTotalAmount(totalNew);


                                                } else {

                                                    OrderRecordCustom orderRecordCustom = new OrderRecordCustom();

                                                    List<OrderItems> orderItemsList = new ArrayList<>();
                                                    orderItemsList.add(orderItems);

                                                    orderRecordCustom.setOtherItems(orderItemsList);
                                                    orderRecordCustom.setItemType(orderItems.getItem_type());
                                                    orderRecordCustom.setStoreId(orderItems.getStore_id());
                                                    orderRecordCustom.setStoreName(orderItems.getStore_name());
                                                    orderRecordCustom.setTotalAmount(Float.parseFloat(orderItems.getPrice()));
                                                    orderRecordCustom.setStoreImage(orderItems.getStore_img());
                                                    orderRecordCustom.setExpected_delivery_date(DateTimeHelper.getDeliveryDate(orderRecord.getCreated_at(),orderItems.getMax_delivery_days()));
                                                    orderRecordCustom.setDelivery_status(orderRecord.getDelivery_status());
                                                    listHashMap.put(orderItems.getStore_id() + orderItems.getItem_type(), orderRecordCustom);
                                                }


                                            }

                                        }

                                        // Log.e("listHashMap", new Gson().toJson(listHashMap));

                                        List<OrderRecordCustom> orderRecordCustomList = new ArrayList<>();

                                        for (Map.Entry<String, OrderRecordCustom> entry : listHashMap.entrySet()) {

                                            OrderRecordCustom value = entry.getValue();
                                            orderRecordCustomList.add(value);

                                        }
                                        Log.e("orderRecordCustomList", new Gson().toJson(orderRecordCustomList));

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                        recyclerView.setLayoutManager(layoutManager);
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this, orderRecordCustomList);
                                        recyclerView.setAdapter(orderDetailsAdapter);


                                    } else {
                                        Toast.makeText(OrderDetailsActivity.this, "No record found", Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    Toast.makeText(OrderDetailsActivity.this, mResponse.getMessage(), Toast.LENGTH_LONG).show();
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
                    params.put("user_id", sessionManager.getCustomerId());
                    params.put("order_id", orderRecord.getOrder_id());
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
        finish();
    }


}
