package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.models.PromoCodesResponseModel;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AC 101 on 23-10-2017.
 */

public class PaymentSelectActivity extends BaseActivity {

    private static final String TAG = "PaymentSelectActivity";
    private static Tracker mTracker;

    @BindView(R.id.header_txt)
    TextView header_txt;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;

    @BindView(R.id.proceed_payment_method)
    Button proceed_payment_method;


    @BindView(R.id.LL_visa_masterPaymentTCContainer)
    LinearLayout LL_visa_masterPaymentTCContainer;

    @BindView(R.id.LL_knetPaymentTCContainer)
    LinearLayout LL_knetPaymentTCContainer;

    @BindView(R.id.knetPaymentCheck)
    CheckBox knetPaymentCheck;

    @BindView(R.id.visa_masterPaymentCheck)
    CheckBox visa_masterPaymentCheck;

    @BindView(R.id.visa_masterPaymentTCCheck)
    CheckBox visa_masterPaymentTCCheck;

    @BindView(R.id.knetPaymentTCCheck)
    CheckBox knetPaymentTCCheck;

    @BindView(R.id.codPaymentCheck)
    CheckBox codPaymentCheck;


    @BindView(R.id.btnAppyPromo)
    Button btnAppyPromo;

    @BindView(R.id.etPromoCode)
    EditText etPromoCode;

    GetCartResponse mResponse;
    SessionManager session;

    @BindView(R.id.txtPreviousTotal)
    TextView txtPreviousTotal;


    @BindView(R.id.discount)
    TextView discount;

    @BindView(R.id.txtDiscount)
    TextView txtDiscount;

    @BindView(R.id.txtTotal)
    TextView txtTotal;

    String previousAmount = "0";

    Double discountAmount = 0.0;

    @BindView(R.id.llPromoSection)
    LinearLayout llPromoSection;

    public String defaultAddressId = "";
    public String promoId = "";
    public HashMap<String, Object> guest = null;

    GetCartResponse cartResponse = null;

    @BindView(R.id.txtDeliveryCharge)
    TextView txtDeliveryCharge;

    @BindView(R.id.txtSubTotal)
    TextView txtSubTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_select);

        ButterKnife.bind(this);
        session = new SessionManager(this);

        //Init Additional
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();


        defaultAddressId = getIntent().getStringExtra("defaultAddressId");
        guest = (HashMap<String, Object>) getIntent().getSerializableExtra("guest");
        header_txt.setText(getIntent().getStringExtra("header"));

        cartResponse = (GetCartResponse) getIntent().getSerializableExtra("CartResponse");

        if (cartResponse != null) {

            previousAmount = cartResponse.getTotal_amount_cart();

            String deliveryCharge = cartResponse.getDelivery_charge();

            Double deliveryAmount = 0.000;

            double grandTotal = Double.parseDouble(previousAmount);

            if (deliveryCharge != null && !deliveryCharge.equalsIgnoreCase("free")) {

                deliveryAmount = Double.parseDouble(deliveryCharge);

                deliveryCharge = String.format("%.3f", deliveryAmount) + " KWD";

                grandTotal = grandTotal + deliveryAmount;

            }


            txtDeliveryCharge.setText(deliveryCharge);

            txtSubTotal.setText(previousAmount + " KWD");


            amount.setText("TOTAL : " + String.format("%.3f", grandTotal) + " KWD");
        }


        bindEvents();

    }


    public void bindEvents() {

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        knetPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    LL_knetPaymentTCContainer.setVisibility(View.VISIBLE);
                    visa_masterPaymentCheck.setChecked(false);
                    visa_masterPaymentTCCheck.setChecked(false);
                    codPaymentCheck.setChecked(false);
                    TefalApp.getInstance().setPayment_method("KNET");

                } else {
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);
                    TefalApp.getInstance().setPayment_method("");
                    TefalApp.getInstance().setPayment_method_tc("");
                }

            }
        });

        visa_masterPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.VISIBLE);
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);
                    knetPaymentCheck.setChecked(false);
                    knetPaymentTCCheck.setChecked(false);
                    codPaymentCheck.setChecked(false);

                    TefalApp.getInstance().setPayment_method("CARD");


                } else {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    TefalApp.getInstance().setPayment_method("");
                    TefalApp.getInstance().setPayment_method_tc("");


                }

            }
        });

        knetPaymentTCCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TefalApp.getInstance().setPayment_method_tc("KNET_AGREE");
                } else {
                    TefalApp.getInstance().setPayment_method_tc("");
                }
            }
        });

        visa_masterPaymentTCCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TefalApp.getInstance().setPayment_method_tc("VIMA_AGREE");
                } else {
                    TefalApp.getInstance().setPayment_method_tc("");
                }
            }
        });
        codPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);

                    knetPaymentCheck.setChecked(false);
                    knetPaymentTCCheck.setChecked(false);
                    visa_masterPaymentCheck.setChecked(false);
                    visa_masterPaymentTCCheck.setChecked(false);

                    TefalApp.getInstance().setPayment_method_tc("COD_AGREE");
                    TefalApp.getInstance().setPayment_method("COD");


                } else {
                    TefalApp.getInstance().setPayment_method_tc("");
                    TefalApp.getInstance().setPayment_method("");
                }
            }
        });
        proceed_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("OUTPUT==== PAYMENT METHOD====" + TefalApp.getInstance().getPayment_method());
                System.out.println("OUTPUT==== PAYMENT TC====" + TefalApp.getInstance().getPayment_method_tc());

                WebCallServiceOrder();

            }
        });

        btnAppyPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnAppyPromo.getTag() != null && btnAppyPromo.getTag().equals("1")) {

                    llPromoSection.setVisibility(View.GONE);
                    btnAppyPromo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    amount.setText("TOTAL : " + previousAmount + " KWD");
                    btnAppyPromo.setText("APPLY");
                    etPromoCode.setEnabled(true);
                    etPromoCode.setText("");
                    btnAppyPromo.setTag("");
                    //  discount.setVisibility(View.GONE);
                } else {
                    WebCallServicePromo();
                }

            }
        });

    }

    public void WebCallServicePromo() {

        Log.i(TAG, "WebCallServicePromo");


        SimpleProgressBar.showProgress(this);
        try {
            final String url = Contents.baseURL + "checkPromotions";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e(CartActivity.class.getSimpleName(), response);
                                Gson g = new Gson();

                                PromoCodesResponseModel promoCodesResponseModel = g.fromJson(response, PromoCodesResponseModel.class);

                                if (promoCodesResponseModel.getStatus() == 1) {

                                    Double previousTotal = Double.parseDouble(previousAmount + "");
                                    discountAmount = Double.parseDouble(promoCodesResponseModel.getRecord().getVoucher_amount());

                                    Double newTotal = (previousTotal - discountAmount);

                                    txtPreviousTotal.setText(previousTotal + " KWD");
                                    txtDiscount.setText(discountAmount + " KWD");
                                    txtTotal.setText(newTotal + " KWD");
                                    amount.setText("TOTAL : " + newTotal + " KWD");
                                    etPromoCode.setEnabled(false);
                                    btnAppyPromo.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                    btnAppyPromo.setText("REMOVE");
                                    btnAppyPromo.setTag("1");

                                    llPromoSection.setVisibility(View.VISIBLE);
                                    //  discount.setVisibility(View.VISIBLE);

                                    promoId = promoCodesResponseModel.getRecord().getPromo_id();

                                } else {
                                    Toast.makeText(PaymentSelectActivity.this, promoCodesResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("user_id", session.getCustomerId());
                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("promo_code", etPromoCode.getText().toString());
                    Log.e("Tefsal store == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(PaymentSelectActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    public void WebCallServiceOrder() {


        final String url = Contents.baseURL + "checkout";


        // Log.i(TAG, "Setting screen name: " + "ZaaraDaraaActivity");
        // mTracker.setScreenName("Image~" + "ZaaraDaraaActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(this);


        Map<String, Object> params = new HashMap<String, Object>();

        try {

            if (session.getIsGuestId()) {

                params.put("unique_id", session.getCustomerId());
                params.put("guest", guest);

            } else {
                params.put("user_id", session.getCustomerId());
                params.put("access_token", session.getToken());
                params.put("address_id", defaultAddressId);
            }


            params.put("appUser", "tefsal");
            params.put("appSecret", "tefsal@123");
            params.put("appVersion", "1.1");

            //Api specific
            params.put("cart_id", session.getKeyCartId());
            params.put("payment_method", "COD");
            params.put("promo_id", promoId);
            params.put("delivery_charge", cartResponse.getDelivery_charge());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Tefsal tailor == ", url + new JSONObject(params));


        BaseHttpClient baseHttpClient = new BaseHttpClient();
        baseHttpClient.doPost(url, new JSONObject(params), new BaseHttpClient.TaskCompleteListener<String>() {
            @Override
            public void onFailure() {
                SimpleProgressBar.closeProgress();
            }

            @Override
            public void onSuccess(String object) {

                try {
                    SimpleProgressBar.closeProgress();
                    Log.e("JSONObject", String.valueOf(object));

                    Log.e("stores response", object);


                    System.out.println("ADD CART RESPONSE====" + object);

                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(object);

                        if (jsonObject.getInt("status") == 1) {
                            Toast.makeText(PaymentSelectActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            startActivity(new Intent(PaymentSelectActivity.this, MyOrderActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                    SimpleProgressBar.closeProgress();
                }

            }
        });


    }

}