package com.tefsalkw.activity;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tefsalkw.Models.BadgeRecordModel;
import com.tefsalkw.Models.Colors;
import com.tefsalkw.Models.DaraAbayaDetailRecord;
import com.tefsalkw.Models.ProductMeasurement;
import com.tefsalkw.Models.ProductRecord;
import com.tefsalkw.Models.ZaraDaraSizeModel;
import com.tefsalkw.R;
import com.tefsalkw.adapter.ProductColorAdapterHorizontalZaraDara;
import com.tefsalkw.adapter.ProductSizeAdapterHorizontalZaraDara;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

//import android.widget.RelativeLayout.LayoutParams;

public class ZaaraDaraaActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

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


    int amount;
    String sizeFlage = "";
    String sizeGuideResponseHtml;

    //This member variable used in ProductSizeAdapterHorizontal adapter
    public static int price;
    public static int meter = 1;

    public static ZaraDaraSizeModel zaraDaraSizesModel = null;


    SessionManager session;
    public ProductRecord productRecord;
    public DaraAbayaDetailRecord daraAbayaDetailRecord;
    public static List<ProductMeasurement> productMeasurementList;

    private static Tracker mTracker;
    private static final String TAG = "ZaaraDaraaActivity";

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


    int currentPosition = 0;

    int currentColorPosition = 0;

    DefaultSliderView.OnSliderClickListener onSliderClickListener;
    DefaultSliderView textSliderView = null;

    List<ZaraDaraSizeModel> zaraDaraSizeModelList = new ArrayList<>();

    Set<String> uniqueColorSet = new HashSet<>();
    Set<String> uniqueSizeSet = new HashSet<>();


    @BindView(R.id.quntity_LL)
    LinearLayout quntity_LL;

    boolean isSizeSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zaara_daraa);


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

        if (productRecord != null) {


            getProductDetails();

        }


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
        productRecord = (ProductRecord) bundle.getSerializable("productRecords");

    }


    private void initBindViews() {
        //Bind Text Views

        text_descp.setText(productRecord.getProduct_desc());
        txt_title.setText(productRecord.getProduct_name());
        subtxt_title.setText(productRecord.getBrand_name());

    }

    private void initEvents() {
        sizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(ZaaraDaraaActivity.this,SizeGuideActivirty.class));
                try {
                    Intent intent = new Intent(ZaaraDaraaActivity.this, SizeGuideActivirty.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("sizeGuideResponseHtml", sizeGuideResponseHtml);

                    intent.putExtras(bundle);

                    //intent.putExtra("sizeGuideResponseHtml",sizeGuideResponseHtml);

                    startActivity(intent);
                } catch (Exception ex) {
                    System.out.println("Error==" + ex);
                }

            }
        });
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (daraAbayaDetailRecord == null) {

                    Toast.makeText(ZaaraDaraaActivity.this, "Please select color and size!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!isSizeSelected) {

                    Toast.makeText(ZaaraDaraaActivity.this, "Please select size!", Toast.LENGTH_SHORT).show();
                    return;
                }

                WebCallServiceAddCartNew();


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
                        amount = meter * price;
                        text_price.setText("PRICE : " + amount + " KWD");
                        meter_value.setText("" + meter);
                        meter++;

                    }

                } else {
                    Toast.makeText(ZaaraDaraaActivity.this, "Please select size!", Toast.LENGTH_SHORT).show();
                }


            }

        });
        less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meter > 1) {
                    meter--;
                    amount = meter * price;
                    text_price.setText("PRICE : " + amount + " KWD");
                    meter_value.setText("" + meter);
                }
            }
        });


    }


    private void initSlider() {

        textSliderView = new DefaultSliderView(this);
        textSliderView.setOnSliderClickListener(onSliderClickListener);

        mainViewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mainViewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainViewPager.setCustomAnimation(new DescriptionAnimation());
        mainViewPager.setDuration(5000);
        mainViewPager.addOnPageChangeListener(this);


    }

    private void initViewsPostCall() {

        //Size Guide Html
        sizeGuideResponseHtml = daraAbayaDetailRecord.getMeasurements();


        //Color Array Fill

        for (Colors colors : daraAbayaDetailRecord.getColors()) {

            if (uniqueColorSet.add(colors.getColor())) {
                ZaraDaraSizeModel daraSizeModel = colors.getSizes().get(0);
                ZaraDaraSizeModel zaraDaraSizeModel = new ZaraDaraSizeModel();
                zaraDaraSizeModel.setColor(colors.getColor());
                zaraDaraSizeModel.setPrice(daraSizeModel.getPrice());
                zaraDaraSizeModel.setQuantity(daraSizeModel.getQuantity());
                zaraDaraSizeModel.setSize(daraSizeModel.getSize());
                zaraDaraSizeModelList.add(zaraDaraSizeModel);
            }


        }


        //Fill color


        productColorAdapterHorizontalZaraDara = new ProductColorAdapterHorizontalZaraDara(zaraDaraSizeModelList, ZaaraDaraaActivity.this);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(ZaaraDaraaActivity.this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
        TefalApp.getInstance().setColorPosition(-1);
        colorRecyclerView.setAdapter(productColorAdapterHorizontalZaraDara);


        //Fill Initial Slider

        for (String imgUrl : daraAbayaDetailRecord.getColors().get(0).getImages()) {


            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            mainViewPager.addSlider(textSliderView);

        }


        // Bind sizes
        productSizeAdapterHorizontalZaraDara = new ProductSizeAdapterHorizontalZaraDara(daraAbayaDetailRecord.getColors(), ZaaraDaraaActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ZaaraDaraaActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        TefalApp.getInstance().setCurrentColorText("-1");
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontalZaraDara);


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
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        String record = jsonObject.getString("record");
                                        System.out.println("record==" + record);

                                        Gson g = new Gson();
                                        daraAbayaDetailRecord = g.fromJson(record, DaraAbayaDetailRecord.class);

                                        if (daraAbayaDetailRecord != null) {
                                            initViewsPostCall();
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
                            System.out.println("Error==" + error.toString());
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
                    params.put("product_id", productRecord.getTefsal_product_id());
                    params.put("store_id", productRecord.getStore_id());

                    Log.e(ZaaraDaraaActivity.class.getSimpleName(), url + new JSONObject(params));

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


    public void showSizeOnColorSelection(ZaraDaraSizeModel position) {

        TefalApp.getInstance().setPosition(-1);
        productSizeAdapterHorizontalZaraDara.isSet = false;

        TefalApp.getInstance().setCurrentColorText(position.getColor());
        productSizeAdapterHorizontalZaraDara.notifyDataSetChanged();
        isSizeSelected = false;


    }


    public void showSelectedSizeData(int position, ZaraDaraSizeModel colors) {


        this.currentPosition = position;

        mainViewPager.removeAllSliders();
        for (String imgUrl : daraAbayaDetailRecord.getColors().get(currentPosition).getImages()) {

            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mainViewPager.addSlider(textSliderView);

        }

        if (colors != null) {
            zaraDaraSizesModel = colors;

            if (zaraDaraSizesModel.getQuantity() == 0) {
                add_cart_btn.setText("SOLD OUT");
                quntity_LL.setVisibility(View.GONE);

            }

            if (zaraDaraSizesModel.getPrice() != null) {
                price = Integer.parseInt(zaraDaraSizesModel.getPrice());
                text_price.setText("PRICE : " + price + " KWD");
            }


        }

        isSizeSelected = true;


    }


    @Override
    protected void onResume() {
        super.onResume();


        Log.e(DaraAbayaActivity.class.getSimpleName(), "onResume");

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
                            System.out.println("Error==" + error.toString());
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


    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        Log.i(TAG, "Setting screen name: " + "ZaaraDaraaActivity");
        mTracker.setScreenName("Image~" + "ZaaraDaraaActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(this);

        JSONObject params = new JSONObject();


        System.out.println("QUANTITY====" + meter_value.getText());
        System.out.println("currentPosition====" + currentPosition);
        //System.out.println("ATTRIBUTE ID====" +   daraAbayaDetailRecord.getColors().get(currentPosition).getAttribute_id());

        try {
            params.put("access_token", session.getToken());
            params.put("user_id", session.getCustomerId());
            try {
                params.put("items", getItems(productRecord));
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
                        DialogKart dg = new DialogKart(ZaaraDaraaActivity.this, false, itemType, "");
                        dg.show();

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

    public void WebCallServiceAddCart() {

        Log.i(TAG, "Setting screen name: " + "ZaaraDaraaActivity");
        mTracker.setScreenName("Image~" + "ZaaraDaraaActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(this);
        try {
            final String url = Contents.baseURL + "addCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);


                                System.out.println("ADD CART RESPONSE====" + response);

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String itemType = jsonObject.getString("item_type");
                                    DialogKart dg = new DialogKart(ZaaraDaraaActivity.this, false, itemType, "");
                                    dg.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                httpGetBadgesCall();

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


                    System.out.println("QUANTITY====" + meter_value.getText());
                    System.out.println("currentPosition====" + currentPosition);
                    //System.out.println("ATTRIBUTE ID====" +   daraAbayaDetailRecord.getColors().get(currentPosition).getAttribute_id());

                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    try {
                        params.put("items", String.valueOf(getItems(productRecord)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal tailor == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ZaaraDaraaActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public JSONArray getItems(ProductRecord productRecord) throws JSONException {


        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("product_id", productRecord.getTefsal_product_id());
        obj.put("item_id", "" + daraAbayaDetailRecord.getColors().get(currentPosition).getAttribute_id());
        if (productRecord.getFlag().equals("Daraa")) {
            obj.put("category_id", "3");
        } else if (productRecord.getFlag().equals("Abaya")) {
            obj.put("category_id", "2");
        } else if (productRecord.getFlag().equals("Accessories")) {
            obj.put("category_id", "4");
        }


        if (zaraDaraSizesModel != null) {
            JSONObject item_details = new JSONObject();
            item_details.put("size", zaraDaraSizesModel.getSize());
            item_details.put("color", daraAbayaDetailRecord.getColors().get(currentPosition).getColor());
            item_details.put("item_quantity", meter_value.getText());
            obj.put("item_details", item_details);
        }


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

            Picasso.with(ZaaraDaraaActivity.this).load(img.get(position)).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public void gotoCart(View v) {
        Log.i(TAG, "Setting screen name: " + "ZaaraDaraaActivity");
        mTracker.setScreenName("Image~" + "ZaaraDaraaActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        startActivity(new Intent(ZaaraDaraaActivity.this, CartActivity.class));

    }

    public void showImageSingleDialog(String image_url) {
        dialog = new Dialog(ZaaraDaraaActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);


        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);


        MainPagerAdapterForDialog mainPagerAdapterForDialog = new MainPagerAdapterForDialog(ZaaraDaraaActivity.this, productRecord.getProduct_images());
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

            Picasso.with(ZaaraDaraaActivity.this).load(img.get(position)).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}