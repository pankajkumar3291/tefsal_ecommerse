package com.tefsalkw.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.adapter.ProductColorAdapterHorizontalZaraDara;
import com.tefsalkw.adapter.ProductSizeAdapterHorizontalZaraDara;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.Colors;
import com.tefsalkw.models.DaraAbayaDetailRecord;
import com.tefsalkw.models.Payload;
import com.tefsalkw.models.ProductMeasurement;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.PromoRestponseModel;
import com.tefsalkw.models.SendItemPromo;
import com.tefsalkw.models.SendPromoModel;
import com.tefsalkw.models.ZaraDaraSizeModel;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.rest_client.RestClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
//import android.widget.RelativeLayout.LayoutParams;

public class DaraAbayaProductDetailsActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private int dotsCount;

    private ImageView[] dots;

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.mainViewPager)
    SliderLayout mainViewPager;

    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    @BindView(R.id.add_cart_btn)
    Button add_cart_btn;

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.subtxt_title)
    TextView subtxt_title;

    @BindView(R.id.text_descp)
    TextView text_descp;

    @BindView(R.id.sizeGuide)
    TextView sizeGuide;


    @BindView(R.id.add_btn)
    ImageView add_btn;

    @BindView(R.id.less_btn)
    ImageView less_btn;

    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;

    @BindView(R.id.text_price)
    TextView text_price;

    @BindView(R.id.meter_value)
    TextView meter_value;

    String sizeFlage = "";
    String sizeGuideResponseHtml;

    //This member variable used in ProductSizeAdapterHorizontal adapter
    float amount;
    public static float price;
    public static float meter = 1;

    public static ZaraDaraSizeModel zaraDaraSizesModel = null;


    SessionManager session;
    public ProductRecord productRecord;
    public DaraAbayaDetailRecord daraAbayaDetailRecord;
    public static List<ProductMeasurement> productMeasurementList;

    private static Tracker mTracker;
    private static final String TAG = "DaraAbayaProductDetails";

    ProductSizeAdapterHorizontalZaraDara productSizeAdapterHorizontalZaraDara;
    ProductColorAdapterHorizontalZaraDara productColorAdapterHorizontalZaraDara;

    /*
     * This dialog is used to show the image which can zoom in zoom out from view pager
     * */
    Dialog dialog;


    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;


    @BindView(R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;


    int currentColorPosition = 0;

    DefaultSliderView.OnSliderClickListener onSliderClickListener;


    //List<ZaraDaraSizeModel> zaraDaraSizeModelList = new ArrayList<>();

    // Set<String> uniqueColorSet = new HashSet<>();


    @BindView(R.id.quntity_LL)
    LinearLayout quntity_LL;

    boolean isSizeSelected = false;


    private List<ZaraDaraSizeModel> productSizesList;


    String productId = "";
    String storeId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dara_abaya_product_details);


        //********************  Init Session  ********************/

        initSession();


        //********************  Init Intent  ********************/

        initIntent();

        //********************  Init Bind Views  ********************/

        initBindViews();

        //********************  Init Views Events  ********************/
        initEvents();

        //********************  Init Slider  ********************/
        initSlider();


        //********************  Call Web API  ********************/


        getProductDetails();

    }

    private void initSession() {
        session = new SessionManager(getApplicationContext());

        //Init Additional
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        TefalApp.getInstance().setPosition(-1);
    }

    private void initIntent() {
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();


        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase("FromPushNotification")) {

            storeId = intent.getStringExtra("store_id");
            productId = intent.getStringExtra("product_id");

        } else {
            productRecord = (ProductRecord) bundle.getSerializable("productRecords");

            if (productRecord != null) {
                storeId = productRecord.getStore_id();
                productId = productRecord.getTefsal_product_id();
            }
        }


    }


    private void initBindViews() {


        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(DaraAbayaProductDetailsActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        //Bind Text Views

        if (productRecord != null) {

            text_descp.setText(productRecord.getProduct_desc());
            txt_title.setText(productRecord.getProduct_name());
            subtxt_title.setText(productRecord.getBrand_name());
        }
    }

    private void initEvents() {
        sizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (sizeGuideResponseHtml != null && !sizeGuideResponseHtml.equalsIgnoreCase("")) {
                        Intent intent = new Intent(DaraAbayaProductDetailsActivity.this, SizeGuideActivirty.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("sizeGuideResponseHtml", sizeGuideResponseHtml);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DaraAbayaProductDetailsActivity.this, "Sorry! Size guide not available!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception ex) {
                    System.out.println("Error==" + ex);
                }

            }
        });
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (daraAbayaDetailRecord == null) {

                    Toast.makeText(DaraAbayaProductDetailsActivity.this, "Please select color and size!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (!isSizeSelected) {
//
//                    Toast.makeText(DaraAbayaProductDetailsActivity.this, "Please select size!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (getIntent().hasExtra("view"))
                {
                    if (getIntent().getStringExtra("view").equalsIgnoreCase("fromPromo"))
                    {
                        if (getIntent().getStringExtra("viewType").equalsIgnoreCase("Seperate")&&getIntent().hasExtra("payloads"))
                        {
                            List<Payload>  list= (List<Payload>) getIntent().getSerializableExtra("payloads");
                            //todo For custom color and size
                            SendItemPromo sendItemPromo=new SendItemPromo();
                            List<SendItemPromo> PromoProductlist=new ArrayList<>();
                            SendPromoModel sendPromoModel=new SendPromoModel();
                            sendPromoModel.setUserId(session.getCustomerId());
                            sendPromoModel.setCartId(session.getKeyCartId());
                            sendPromoModel.setAppSecret("tefsal@123");
                            sendPromoModel.setAppUser("tefsal");
                            sendPromoModel.setAppVersion("1.1");

                            sendItemPromo.setCategoryId(list.get(0).getCategory());
                            sendItemPromo.setSubcategoryId(list.get(0).getSubCategory());
                            sendItemPromo.setPromoType(list.get(0).getBundleType());
                            sendItemPromo.setItemQuantity("1");
                            sendItemPromo.setPromoGift(list.get(0).getPromoGift());
                            sendItemPromo.setPromoDiscount(list.get(0).getDiscount()==null?"0":list.get(0).getDiscount());
                            sendItemPromo.setPromoId(String.valueOf(list.get(0).getPromoId()));


                            sendItemPromo.setProductId(productRecord.getTefsal_product_id());
                            sendItemPromo.setItemId(String.valueOf(productRecord.getItem_id()));
                            sendItemPromo.setItemType(productRecord.getItem_type());
                            sendItemPromo.setTotalAmount(productRecord.getDefault_price());
                            PromoProductlist.add(sendItemPromo);
                            sendPromoModel.setItems(PromoProductlist);


                            try {
                                RestClient.APIInterface apiInterface=RestClient.getClient();
                                apiInterface.AddPromo(sendPromoModel).enqueue(new Callback<PromoRestponseModel>() {
                                    @Override
                                    public void onResponse(Call<PromoRestponseModel> call, retrofit2.Response<PromoRestponseModel> response) {

                                        if (response.body().getStatus()==1)
                                        {
                                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<PromoRestponseModel> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Exception:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

//                            Toast.makeText(getApplicationContext(),""+sendPromoModel,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            productRecord.setPosition(getIntent().getIntExtra("position",-1));
                            Intent intent = new Intent();
                            intent.putExtra("DaraObject", productRecord);
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                }
                else {
                    WebCallServiceAddCartNew();
                }

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zaraDaraSizesModel != null) {

                    if (meter > 0 && meter < zaraDaraSizesModel.getQuantity()) {

                        meter++;

                        amount = meter * price;
                        text_price.setText(String.format(new Locale("en"), "%.3f", amount));
                        meter_value.setText("" + meter);


                    }

                } else {
                    Toast.makeText(DaraAbayaProductDetailsActivity.this, "Please select size!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meter > 1) {
                    meter--;
                    amount = meter * price;
                    text_price.setText(String.format(new Locale("en"), "%.3f", amount));
                    meter_value.setText("" + meter);
                }
            }
        });


    }


    private void initSlider() {

        onSliderClickListener = this;


        mainViewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mainViewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainViewPager.setCustomAnimation(new DescriptionAnimation());
        mainViewPager.setDuration(5000);
        mainViewPager.addOnPageChangeListener(this);
    }

    private void initViewsPostCall() {


        //Size Guide Html
        sizeGuideResponseHtml = daraAbayaDetailRecord.getMeasurements();


        productColorAdapterHorizontalZaraDara = new ProductColorAdapterHorizontalZaraDara(daraAbayaDetailRecord.getColors(), DaraAbayaProductDetailsActivity.this);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(DaraAbayaProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
        TefalApp.getInstance().setColorPosition(0);
        colorRecyclerView.setAdapter(productColorAdapterHorizontalZaraDara);

        //Fill Initial Slider
        String[] img = daraAbayaDetailRecord.getColors().get(0).getImages();
        mainViewPager.removeAllSliders();


        if (img != null) {
            if (img.length <= 1) {
                mainViewPager.stopAutoCycle();
            } else {
                mainViewPager.startAutoCycle();
            }
            for (String imgUrl : img) {

                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView.setOnSliderClickListener(onSliderClickListener);
                textSliderView
                        .image(imgUrl)
                        .setScaleType(BaseSliderView.ScaleType.Fit);


                mainViewPager.addSlider(textSliderView);

            }
        }
        zaraDaraSizesModel = daraAbayaDetailRecord.getColors().get(0).getSizes().get(0);

        bindSelectedSizeData();


        // Bind sizes
        productSizeAdapterHorizontalZaraDara = new ProductSizeAdapterHorizontalZaraDara(daraAbayaDetailRecord.getColors().get(0).getSizes(), DaraAbayaProductDetailsActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(DaraAbayaProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        TefalApp.getInstance().setPosition(0);
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontalZaraDara);


        text_price.setText(String.format(new Locale("en"), "%.3f", Float.parseFloat(daraAbayaDetailRecord.getColors().get(0).getSizes().get(0).getPrice())));


    }


    private void getProductDetails() {

        SimpleProgressBar.showProgress(this);
        try {
            final String url = Contents.baseURL + "getProductDetails";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                try {

                                    Log.e("getProductDetails", response);
                                    Gson g = new Gson();
                                    daraAbayaDetailRecord = g.fromJson(response, DaraAbayaDetailRecord.class);

                                    if (daraAbayaDetailRecord != null) {
                                        initViewsPostCall();

                                        if (session.getKeyLang().equals("Arabic")) {
                                            text_descp.setText(daraAbayaDetailRecord.getProduct_desc_arabic());
                                            txt_title.setText(daraAbayaDetailRecord.getProduct_name_arabic());
                                            subtxt_title.setText(daraAbayaDetailRecord.getBrand_name_arabic());
                                        } else {
                                            text_descp.setText(daraAbayaDetailRecord.getProduct_desc());
                                            txt_title.setText(daraAbayaDetailRecord.getProduct_name());
                                            subtxt_title.setText(daraAbayaDetailRecord.getBrand_name());
                                        }


                                    }


                                } catch (Exception ex) {
                                    ex.printStackTrace();
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
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appVersion", "1.1");
                    params.put("appSecret", "tefsal@123");
                    params.put("product_id", productId);
                    params.put("store_id", storeId);

                    Log.e(DaraAbayaProductDetailsActivity.class.getSimpleName(), url + new JSONObject(params));

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


    public void showSizeOnColorSelection(int position, Colors colors) {


        this.currentColorPosition = position;

        mainViewPager.removeAllSliders();

        String[] imgList = colors.getImages();

        if (imgList != null) {
            if (imgList.length <= 1) {
                mainViewPager.stopAutoCycle();
            } else {
                mainViewPager.startAutoCycle();
            }

            mainViewPager.removeAllSliders();
            for (String imgUrl : imgList) {

                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView.setOnSliderClickListener(onSliderClickListener);

                textSliderView
                        .image(imgUrl)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                mainViewPager.addSlider(textSliderView);

            }


        }


        TefalApp.getInstance().setPosition(0);
        zaraDaraSizesModel = colors.getSizes().size() > 0 ? colors.getSizes().get(0) : null;

        bindSelectedSizeData();

        productSizeAdapterHorizontalZaraDara.productSizesList = colors.getSizes();
        productSizeAdapterHorizontalZaraDara.notifyDataSetChanged();
        isSizeSelected = false;
    }


    public void showSelectedSizeData(ZaraDaraSizeModel zaraDaraSizeModel) {

        Log.e("zaraDaraSizeModel", new Gson().toJson(zaraDaraSizeModel));

        if (zaraDaraSizeModel != null) {
            zaraDaraSizesModel = zaraDaraSizeModel;

            bindSelectedSizeData();
            isSizeSelected = true;

        }


    }


    private void bindSelectedSizeData() {


        Log.e("bindSelectedSizeData", new Gson().toJson(zaraDaraSizesModel));

        if (zaraDaraSizesModel != null) {

            if (zaraDaraSizesModel.getQuantity() == 0) {
                add_cart_btn.setText("SOLD OUT");
                add_cart_btn.setEnabled(false);
                quntity_LL.setVisibility(View.GONE);

                return;
            } else {
                if (getIntent().hasExtra("view"))
                {
                    if (getIntent().getStringExtra("view").equalsIgnoreCase("fromPromo")) {
                        add_cart_btn.setEnabled(true);
                        add_cart_btn.setText("Add To Promo");
                        quntity_LL.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    add_cart_btn.setEnabled(true);
                    add_cart_btn.setText(getResources().getString(R.string.zaara_daraa_add_cart_btn_text));
                    quntity_LL.setVisibility(View.VISIBLE);
                }

            }

            if (zaraDaraSizesModel.getPrice() != null) {
                price = Float.parseFloat(zaraDaraSizesModel.getPrice());

                text_price.setText(String.format(new Locale("en"), "%.3f", price));
                meter = 1;
                meter_value.setText("" + meter);
            }

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(DaraAbayaProductDetailsActivity.class.getSimpleName(), "onResume");

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
                    }
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

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mainViewPager.stopAutoCycle();
        super.onStop();
    }

    @SuppressLint("LongLogTag")
    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        Log.i(TAG, "Setting screen name: " + "DaraAbayaProductDetailsActivity");
        mTracker.setScreenName("Image~" + "DaraAbayaProductDetailsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(this);

        JSONObject params = new JSONObject();

        System.out.println("QUANTITY====" + meter_value.getText());
        System.out.println("currentPosition====" + currentColorPosition);
        //System.out.println("ATTRIBUTE ID====" +   daraAbayaDetailRecord.getColors().get(currentColorPosition).getAttribute_id());
        try {
            if (session.getIsGuestId()) {
                params.put("unique_id", session.getCustomerId());
            } else {
                params.put("user_id", session.getCustomerId());
                params.put("access_token", session.getToken());
            }
            try {
                params.put("items", getItems());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params.put("appUser", "tefsal");
            params.put("appSecret", "tefsal@123");
            params.put("appVersion", "1.1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("Tefsal tailor == ", url + params);


        BaseHttpClient baseHttpClient = new BaseHttpClient();
        baseHttpClient.doPost(url, params, new BaseHttpClient.TaskCompleteListener<String>() {
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

                        String cart_id = jsonObject.getString("cart_id");
                        session.setKeyCartId(cart_id);

                        String itemType = jsonObject.getString("item_type");
                        DialogKart dg = new DialogKart(DaraAbayaProductDetailsActivity.this, false, itemType, "");
                        dg.show();

                        AdjustEvent event = new AdjustEvent("rxth0d");
                        event.addPartnerParameter("Dara Abaya Product Name", productRecord.getProduct_name());
                        Adjust.trackEvent(event);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    httpGetBadgesCall();

                } catch (Exception e1) {
                    e1.printStackTrace();
                    SimpleProgressBar.closeProgress();
                }
            }
        });
    }


    public JSONArray getItems() throws JSONException {


        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();

        if (productRecord.getFlag().equals("Daraa")) {

            obj.put("category_id", "3");
        }
        if (productRecord.getFlag().equals("Abaya")) {
            obj.put("category_id", "2");
        }
            obj.put("subcategory_id", "0");

        obj.put("product_id", productRecord.getTefsal_product_id());

        obj.put("item_id", zaraDaraSizesModel != null ? zaraDaraSizesModel.getAttribute_meta_id() : "");

        JSONObject item_details = new JSONObject();
        //item_details.put("size", zaraDaraSizesModel.getSize());
        // item_details.put("color", daraAbayaDetailRecord.getColors().get(currentColorPosition).getColor());
        item_details.put("item_quantity", meter_value.getText());
        obj.put("item_details", item_details);


        arry.put(obj);
        return arry;
    }


    private void setUiPageViewController() {

        //dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(20, 0, 20, 0);
            viewPagerCountDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Log.e(DishDashaProductActivity.class.getSimpleName(), "onSliderClick");
        showImageSingleDialog(slider.getUrl());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class MainPagerAdapter extends PagerAdapter {
        Context context;
        List<String> img;
        LayoutInflater layoutInflater;


        public MainPagerAdapter(Context context, List<String> img) {
            this.context = context;
            this.img = img;
            //  System.out.println("Image=="+img.size());
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);

            PhotoView imageView = (PhotoView) itemView.findViewById(R.id.zaara);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   /* imageView1=imageView;
                    System.out.println("POSITION==="+position);
                    System.out.println();
*/
                    try {
                        showImageSingleDialog(img.get(position));
                    } catch (Exception ex) {
                        Log.d("Error=", ex.fillInStackTrace().toString());

                    }

                    /* scaleGestureDetector = new ScaleGestureDetector(getApplicationContext(),new ScaleListener());*/

                }
            });

            // Picasso.with(DaraAbayaProductDetailsActivity.this).load(img.get(position)).into(imageView);
            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(context).asBitmap().load(img.get(position)).apply(options).into(imageView);


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public void gotoCart(View v) {
        Log.i(TAG, "Setting screen name: " + "DaraAbayaProductDetailsActivity");
        mTracker.setScreenName("Image~" + "DaraAbayaProductDetailsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        startActivity(new Intent(DaraAbayaProductDetailsActivity.this, CartActivity.class));

    }

    public void showImageSingleDialog(String image_url) {

        List<String> img = new ArrayList<>();
        img.add(image_url);

        dialog = new Dialog(DaraAbayaProductDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);


        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);


        MainPagerAdapterForDialog mainPagerAdapterForDialog = new MainPagerAdapterForDialog(DaraAbayaProductDetailsActivity.this, img);
        dialog_viewPager.setAdapter(mainPagerAdapterForDialog);
        dialog_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                  /*  for (int i = 0; i < dotsCount; i++) {
                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));
                    }
                    dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // setUiPageViewController();
        //  }
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        dialog_viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("You have clicked me");
            }
        });

    }


    public class MainPagerAdapterForDialog extends PagerAdapter {
        Context context;
        List<String> img;
        LayoutInflater layoutInflater;


        public MainPagerAdapterForDialog(Context context, List<String> img) {
            this.context = context;
            this.img = img;
            //  System.out.println("Image=="+img.size());
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);
            // LinearLayout LL_pager_holder=(LinearLayout) itemView.findViewById(R.id.LL_pager_holder);
            final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.zaara);

            try {
               /* PhotoViewAttacher photoAttacher;
                photoAttacher= new PhotoViewAttacher(imageView);
                photoAttacher.update();*/
            } catch (Exception ex) {

            }


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                   /* Toast.makeText(context, "HI", Toast.LENGTH_SHORT).show();
                    Log.e("ClickCall", "true");*/

                }
            });

            //Picasso.with(DaraAbayaProductDetailsActivity.this).load(img.get(position)).into(imageView);

            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(DaraAbayaProductDetailsActivity.this).asBitmap().load(img.get(position)).apply(options).into(imageView);


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}