package com.tefsalkw.activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
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

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.GetPromoAdapter;
import com.tefsalkw.adapter.GetPromoAddedItemsAdapter;
import com.tefsalkw.adapter.MyCartAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.models.BaseModel;
import com.tefsalkw.models.DisplayPromoItems;
import com.tefsalkw.models.GetAddPromoProductsRequest;
import com.tefsalkw.models.GetCartPromo;
import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.models.GetPromoProductDetail;
import com.tefsalkw.models.ProductDetails;
import com.tefsalkw.models.PromoRestponseModel;
import com.tefsalkw.rest_client.RestClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
/**
 * Created by AC 101 on 11-10-2017.
 */
public class CartActivity extends BaseActivity implements MyCartAdapter.OnCartItemDeletedListener {
    @BindView(R.id.header_txt)
    TextView header_txt;
    @BindView(R.id.recgetpromo)
    RecyclerView recgetpromo;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.edit_btn)
    TextView edit_btn;
    @BindView(R.id.constraintLayout)
    ConstraintLayout addedPromoLayout;
    @BindView(R.id.rec_added_promo_items)
    RecyclerView rec_added_promo_items;
    @BindView(R.id.btn_purchase)
    Button btn_purchase;
    boolean isfirst = false;
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
    List<GetPromoProductDetail> promoDiscountList = new ArrayList<>();
    List<GetCartPromo> promoFreeList = new ArrayList<>();
    MyCartAdapter.OnCartItemDeletedListener onCartItemDeletedListener;
    List<Integer> addpromo = new ArrayList();
    private float totalPrice = 0.0f;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
        // ViewCompat.setNestedScrollingEnabled(recycler_view, false);
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
        isfirst = true;
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
                AdjustEvent event = new AdjustEvent("cf5uaw");
//                event.addPartnerParameter("Order Number", cartResponse.getCart_id());
                event.addPartnerParameter("Total Amount", amount.getText().toString());
                Adjust.trackEvent(event);
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
    }
    public void WebCallServiceCart(){
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
                                GetCartResponse cartResponse = g.fromJson(response, GetCartResponse.class);
                                if (cartResponse != null && cartResponse.getStatus() == 1) {
                                    if (cartResponse.getCart_id() != null) {
                                        session.setKeyCartId(cartResponse.getCart_id());
                                    }
                                    if (cartResponse.getRecord().size() == 1) {
                                        header_txt.setText(getString(R.string.cart_header_txt_text));
                                    } else {
                                        header_txt.setText(String.format(new Locale("en"), getString(R.string.cart_header_txt_texts), cartResponse.getCart_count()));
                                    }
                                    if (cartResponse.getPromo().size() != 0) {
                                        List<Integer> promolist = cartResponse.getPromo();
                                        recgetpromo.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                                        recgetpromo.setAdapter(new GetPromoAdapter(CartActivity.this, promolist));
                                    } else {
                                        recgetpromo.setVisibility(View.GONE);
                                    }
                                    List<BaseModel> productDetailsList = new ArrayList<BaseModel>();
                                    if (cartResponse.getSelectedPromo().size() > 0) {
                                        GetAddPromoProductsRequest getAddPromoProductsRequest = new GetAddPromoProductsRequest();
                                        getAddPromoProductsRequest.setAppSecret("tefsal@123");
                                        getAddPromoProductsRequest.setAppUser("tefsal");
                                        getAddPromoProductsRequest.setAppVersion("1.1");
                                        getAddPromoProductsRequest.setCartId(session.getKeyCartId());
                                        getAddPromoProductsRequest.setUserId(session.getCustomerId());
                                        compositeDisposable.add(Observable.fromIterable(cartResponse.getSelectedPromo()).flatMap(new Function<Integer, ObservableSource<DisplayPromoItems>>() {
                                            @Override
                                            public ObservableSource<DisplayPromoItems> apply(Integer integer) throws Exception {
                                                RestClient.APIInterface apiInterface = RestClient.getClient();
                                                getAddPromoProductsRequest.setPromoId(integer + "");
                                                return apiInterface.getPromo(getAddPromoProductsRequest);
                                            }
                                        }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<DisplayPromoItems>>() {
                                            @Override
                                            public void accept(List<DisplayPromoItems> baseModels) throws Exception {
                                                String discount = "";

                                                Log.d(TAG, "accept:" + baseModels.size());
                                                float totalDisCount = 0.0f;
                                                float totalDisCountOther = 0.0f;
                                                for (DisplayPromoItems baseModel : baseModels) {

                                                    if (baseModel.getStatus() == 1) {
                                                        if (baseModel.getPayload().get(0).getProductDetails() != null)

                                                            discount=baseModel.getPayload().get(0).getDiscount();

                                                            for (BaseModel model : baseModel.getPayload().get(0).getProductDetails().getDishdashaTextile()) {
                                                                totalDisCount = totalDisCount + model.getDisCountedAmount();
                                                                productDetailsList.add(model);
                                                            }
                                                        if (baseModel.getPayload().get(0).getProductDetails() != null)
                                                            for (BaseModel model : baseModel.getPayload().get(0).getProductDetails().getDaraaAndAbaya()) {
                                                                totalDisCount = totalDisCount + model.getDisCountedAmount();
                                                                productDetailsList.add(model);
                                                            }
                                                        if (baseModel.getPayload().get(0).getProductDetails() != null)
                                                            for (BaseModel model : baseModel.getPayload().get(0).getProductDetails().getAccessories()) {
                                                                totalDisCount = totalDisCount + model.getDisCountedAmount();
                                                                productDetailsList.add(model);
                                                            }
                                                    }
                                                    totalPrice = totalDisCount;

                                                }
                                                for (GetCartRecord getCartRecord : cartResponse.getRecord()) {
                                                    totalDisCountOther = totalDisCountOther + getCartRecord.getDisCountedAmount();
                                                }
                                                totalPrice = totalPrice + totalDisCountOther;
                                                amount.setText(String.format(new Locale("en"), "%.3f", Float.parseFloat(totalPrice + "")));
                                                rec_added_promo_items.setAdapter(new GetPromoAddedItemsAdapter(CartActivity.this, productDetailsList,discount));
                                                addedPromoLayout.setVisibility(View.VISIBLE);
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                throwable.printStackTrace();
                                            }
                                        }));
                                    } else {
                                        addedPromoLayout.setVisibility(View.GONE);
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
                                    if (session.getKeyLang().equals("Arabic")) {
                                        Toast.makeText(getApplicationContext(), cartResponse.getMessage_ar(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), cartResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null){
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                            SimpleProgressBar.closeProgress();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (session.getIsGuestId()) {
                        params.put("unique_id", session.getCustomerId());
                    } else {
                        params.put("user_id", session.getCustomerId());
                        params.put("access_token", session.getToken());
                    }
                    //todo  Cart id= 676221554461661
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("cart_id", session.getKeyCartId());
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
        } catch (Exception surError){
            surError.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            WebCallServiceCart();
            //=====For getting crash Analytics==================================
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            if (!compositeDisposable.isDisposed()) {
                compositeDisposable.dispose();
            }
        }
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
