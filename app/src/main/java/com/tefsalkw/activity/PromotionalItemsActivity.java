package com.tefsalkw.activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.PromoDaraaAndAbayaAdapter;
import com.tefsalkw.adapter.PromoDishdashaTextileAdapter;
import com.tefsalkw.adapter.PromotionalAdapter;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.BaseModel;
import com.tefsalkw.models.GetPromoModelClass;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.PromoRestponseModel;
import com.tefsalkw.models.SendItemPromo;
import com.tefsalkw.models.SendPromoModel;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.rest_client.RestClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
public class PromotionalItemsActivity extends BaseActivity {
    private RecyclerView recPromotionalItems, rec_promotional_items1, rec_promotional_items2;
    private TextView tvAcce, tvdaraaAndAbaya, tvdishdashaTextile;
    private ImageView imgBackArrow;
    private String viewtype;
    private List<AccessoriesRecord> selectedProductList = new ArrayList<>();
    private List<TextileProductModel> DishdishaSelectedProduct = new ArrayList<>();
    private TextView tvmessage;
    private List<AccessoriesRecord> ProductList = new ArrayList<>();
    private List<ProductRecord> darraproductList = new ArrayList<>();
    private List<ProductRecord> selectedDarraproductList = new ArrayList<>();
    private List<TextileProductModel> dishdashaTextile = new ArrayList<>();
    private List<BaseModel> baseModels = new ArrayList<>();
    private PromotionalAdapter promoDaraaAndAbayaAdapter;
    private SessionManager session;
    GetPromoModelClass promolist;
    private Button btnAddToPromo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotional_items);
        session = new SessionManager(this);
        findidhere();
        clickevents();
        callapi();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void callapi() {
        //================================================================================
        SimpleProgressBar.showProgress(PromotionalItemsActivity.this);
        try {
            final String url = Contents.baseURL + "getPromoProduct";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SimpleProgressBar.closeProgress();
                            if (response != null) {
                                Log.e(CartActivity.class.getSimpleName(), response);
                                Gson g = new Gson();
                                promolist = (g.fromJson(response, GetPromoModelClass.class));
                                if (promolist.getStatus() == 1) {
                                    if (promolist.getPayload().get(0).getBundleType().equalsIgnoreCase("Seperate")) {
                                        tvmessage.setVisibility(View.VISIBLE);
                                        btnAddToPromo.setVisibility(View.GONE);
                                    }
                                    if (promolist.getPayload().size() > 0) {
                                        viewtype = promolist.getPayload().get(0).getBundleType();
                                        for (int i = 0; i < promolist.getPayload().size(); i++) {
                                            if (promolist.getPayload().get(i).getProductDetails().getAccessories().size() > 0) {
                                                ProductList.addAll(promolist.getPayload().get(i).getProductDetails().getAccessories());
                                            }
                                            if (promolist.getPayload().get(i).getProductDetails().getDaraaAndAbaya().size() > 0) {
                                                darraproductList.addAll(promolist.getPayload().get(i).getProductDetails().getDaraaAndAbaya());
                                            }
                                            if (promolist.getPayload().get(i).getProductDetails().getDishdashaTextile().size() > 0) {
                                                dishdashaTextile.addAll(promolist.getPayload().get(i).getProductDetails().getDishdashaTextile());
                                            }
                                        }
                                        displayitems(promolist);
                                    }
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
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("promo_id", getIntent().getStringExtra("promoid"));
                    Log.e("Tefsal store == ", url + new JSONObject(params));
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(PromotionalItemsActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);
        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }
    private void displayitems(GetPromoModelClass promolist) {
        if (ProductList.size() > 0) {
            tvAcce.setVisibility(View.VISIBLE);
            recPromotionalItems.setVisibility(View.VISIBLE);
//            recPromotionalItems.setHasFixedSize(true);
            promoDaraaAndAbayaAdapter = new PromotionalAdapter(promolist.getPayload(), ProductList, PromotionalItemsActivity.this, selectedProductList, viewtype);
            recPromotionalItems.setLayoutManager(new LinearLayoutManager(PromotionalItemsActivity.this));
            recPromotionalItems.setAdapter(promoDaraaAndAbayaAdapter);
        }
        if (darraproductList.size() > 0) {
            rec_promotional_items1.setVisibility(View.VISIBLE);
            tvdaraaAndAbaya.setVisibility(View.VISIBLE);
//            recPromotionalItems.setHasFixedSize(true);
            rec_promotional_items1.setLayoutManager(new LinearLayoutManager(PromotionalItemsActivity.this));
            rec_promotional_items1.setAdapter(new PromoDaraaAndAbayaAdapter(promolist.getPayload(), darraproductList, PromotionalItemsActivity.this, selectedDarraproductList, viewtype));
        }
        if (dishdashaTextile.size() > 0) {
            rec_promotional_items2.setVisibility(View.VISIBLE);
            tvdishdashaTextile.setVisibility(View.VISIBLE);
//            recPromotionalItems.setHasFixedSize(true);
            rec_promotional_items2.setLayoutManager(new LinearLayoutManager(PromotionalItemsActivity.this));
            rec_promotional_items2.setAdapter(new PromoDishdashaTextileAdapter(promolist.getPayload(), dishdashaTextile, PromotionalItemsActivity.this, DishdishaSelectedProduct, viewtype));
        }
        checkAllList();
    }
    private void findidhere() {
        tvmessage = findViewById(R.id.textView26);
        tvAcce = findViewById(R.id.textView21);
        recPromotionalItems = findViewById(R.id.rec_promotional_items);
        imgBackArrow = findViewById(R.id.imageView9);
        rec_promotional_items1 = findViewById(R.id.rec_promotional_items1);
        rec_promotional_items2 = findViewById(R.id.rec_promotional_items2);
        tvdaraaAndAbaya = findViewById(R.id.textView24);
        tvdishdashaTextile = findViewById(R.id.textView28);
        btnAddToPromo = findViewById(R.id.button5);
    }
    private void clickevents() {
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 12) {
                selectedProductList.add((AccessoriesRecord) data.getSerializableExtra("AccessoryObject"));
                ProductList.clear();
                darraproductList.clear();
                dishdashaTextile.clear();
                callapi();
            }
            if (requestCode == 13) {
                DishdishaSelectedProduct.add((TextileProductModel) data.getSerializableExtra("DishdashaObject"));
                ProductList.clear();
                darraproductList.clear();
                dishdashaTextile.clear();
                callapi();
            }
            if (requestCode == 14) {
                selectedDarraproductList.add((ProductRecord) data.getSerializableExtra("DaraObject"));
                ProductList.clear();
                darraproductList.clear();
                dishdashaTextile.clear();
                callapi();
            }
        }
    }
    public void checkAllList() {
        if ((selectedProductList.size() == ProductList.size()) && (DishdishaSelectedProduct.size() == dishdashaTextile.size()) && (selectedDarraproductList.size() == darraproductList.size())) {
            btnAddToPromo.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            SendItemPromo sendItemPromo = new SendItemPromo();
            List<SendItemPromo> PromoProductlist = new ArrayList<>();
            SendPromoModel sendPromoModel = new SendPromoModel();
            sendPromoModel.setUserId(session.getCustomerId());
            sendPromoModel.setCartId(session.getKeyCartId());
            sendPromoModel.setAppSecret("tefsal@123");
            sendPromoModel.setAppUser("tefsal");
            sendPromoModel.setAppVersion("1.1");
            for (AccessoriesRecord accessoriesRecord : selectedProductList) {
                sendItemPromo.setCategoryId(promolist.getPayload().get(0).getCategory());
                sendItemPromo.setSubcategoryId(promolist.getPayload().get(0).getSubCategory());
                sendItemPromo.setPromoType(promolist.getPayload().get(0).getBundleType());
                sendItemPromo.setItemQuantity("1");
                sendItemPromo.setPromoGift(promolist.getPayload().get(0).getPromoGift());
                sendItemPromo.setPromoDiscount(promolist.getPayload().get(0).getDiscount()==null?"0":promolist.getPayload().get(0).getDiscount());
                sendItemPromo.setPromoId(String.valueOf(promolist.getPayload().get(0).getPromoId()));
                sendItemPromo.setProductId(accessoriesRecord.getTefsal_product_id());
                sendItemPromo.setItemId(String.valueOf(accessoriesRecord.getItem_id()));
                sendItemPromo.setItemType(accessoriesRecord.getItem_type());
                sendItemPromo.setTotalAmount(accessoriesRecord.getDefault_price());
                PromoProductlist.add(sendItemPromo);
                sendPromoModel.setItems(PromoProductlist);
            }
            for (TextileProductModel textileProductModel : DishdishaSelectedProduct) {
                sendItemPromo.setCategoryId(promolist.getPayload().get(0).getCategory());
                sendItemPromo.setSubcategoryId(promolist.getPayload().get(0).getSubCategory());
                sendItemPromo.setPromoType(promolist.getPayload().get(0).getBundleType());
                sendItemPromo.setItemQuantity("1");
                sendItemPromo.setPromoGift(promolist.getPayload().get(0).getPromoGift());
                sendItemPromo.setPromoDiscount(promolist.getPayload().get(0).getDiscount()==null?"0":promolist.getPayload().get(0).getDiscount());
                sendItemPromo.setPromoId(String.valueOf(promolist.getPayload().get(0).getPromoId()));
                sendItemPromo.setProductId(textileProductModel.getTefsal_product_id());
                sendItemPromo.setItemId(String.valueOf(textileProductModel.getItem_id()));
                sendItemPromo.setItemType(textileProductModel.getItem_type());
                sendItemPromo.setTotalAmount(textileProductModel.getPrice());
                PromoProductlist.add(sendItemPromo);
                sendPromoModel.setItems(PromoProductlist);
            }
            for (ProductRecord productRecord : selectedDarraproductList) {
                sendItemPromo.setCategoryId(promolist.getPayload().get(0).getCategory());
                sendItemPromo.setSubcategoryId(promolist.getPayload().get(0).getSubCategory());
                sendItemPromo.setPromoType(promolist.getPayload().get(0).getBundleType());
                sendItemPromo.setItemQuantity("1");
                sendItemPromo.setPromoGift(promolist.getPayload().get(0).getPromoGift());
                sendItemPromo.setPromoDiscount(promolist.getPayload().get(0).getDiscount()==null?"0":promolist.getPayload().get(0).getDiscount());
                sendItemPromo.setPromoId(String.valueOf(promolist.getPayload().get(0).getPromoId()));
                sendItemPromo.setProductId(productRecord.getTefsal_product_id());
                sendItemPromo.setItemId(String.valueOf(productRecord.getItem_id()));
                sendItemPromo.setItemType(productRecord.getItem_type());
                sendItemPromo.setTotalAmount(productRecord.getDefault_price());
                PromoProductlist.add(sendItemPromo);
                sendPromoModel.setItems(PromoProductlist);
            }
            btnAddToPromo.setEnabled(true);
            btnAddToPromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RestClient.APIInterface apiInterface = RestClient.getClient();
                    try {
                        apiInterface.AddPromo(sendPromoModel).enqueue(new Callback<PromoRestponseModel>() {
                            @Override
                            public void onResponse(Call<PromoRestponseModel> call, retrofit2.Response<PromoRestponseModel> response) {
                                if (response != null) {
                                    if (response.isSuccessful()) {
//                                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
//                                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<PromoRestponseModel> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(getApplicationContext(),""+sendPromoModel,Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnAddToPromo.setBackgroundColor(getResources().getColor(R.color.disablePromobuttonColor));
            btnAddToPromo.setEnabled(false);
        }
    }
}
