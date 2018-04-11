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
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.AccessoryDetailRecord;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.Colors;
import com.tefsalkw.models.Sizes;
import com.tefsalkw.R;
import com.tefsalkw.adapter.ProductColorAdapterHorizontalAccesories;
import com.tefsalkw.adapter.ProductSizeAdapterHorizontalAccessories;
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

public class AccessoryProductDetailsActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private AccessoriesRecord accessoriesRecord;
   /* @BindView(R.id.productImage)
    ImageView imageView;*/


    @BindView(R.id.text_descp)
    TextView text_desc;

    @BindView(R.id.text_price)
    TextView text_price;

    @BindView(R.id.add_cart_btn)
    Button add_cart_btn;

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.subtxt_title)
    TextView subtxt_title;

    @BindView(R.id.back_btn)
    ImageView back_btn;

    private SessionManager session;
    private static Tracker mTracker;
    private static final String TAG = "AccesPrdctDtlsActivity";


    @BindView(R.id.product_image_viewPager)
    SliderLayout product_image_viewPager;


    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    private String[] Accessory_product_image;
    private int dotsCount;
    private ImageView[] dots;
    private AccessoryProductPagerAdapter accessoryProductPagerAdapter;


    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;

    /*
     * This dialog is used to show the image which can zoom in zoom out from view pager
     * */
    Dialog dialog;


    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;


    @BindView(R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;


    AccessoryDetailRecord accessoryDetailRecord;


    DefaultSliderView.OnSliderClickListener onSliderClickListener;
    DefaultSliderView textSliderView = null;


    ProductSizeAdapterHorizontalAccessories productSizeAdapterHorizontalAccessories;
    ProductColorAdapterHorizontalAccesories productColorAdapterHorizontalAccesories;

    int currentColorPosition = 0;

    boolean isSizeSelected = false;

    Set<String> uniqueColorSet = new HashSet<>();

    List<Sizes> productSizesList = new ArrayList<>();

    Colors colorModel = new Colors();
    public static int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_product_details);
        ButterKnife.bind(this);
        session = new SessionManager(this);
        onSliderClickListener = this;
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        accessoriesRecord = (AccessoriesRecord) getIntent().getSerializableExtra("accessoriesRecord");


        if (accessoriesRecord != null) {

            getAccessoriesProductDetails();

        }

        initSlider();
        setData();


        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accessoryDetailRecord == null) {

                    Toast.makeText(AccessoryProductDetailsActivity.this, "Please select color and size!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!isSizeSelected) {

                    Toast.makeText(AccessoryProductDetailsActivity.this, "Please select size!", Toast.LENGTH_SHORT).show();
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

        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(AccessoryProductDetailsActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void initSlider() {

        textSliderView = new DefaultSliderView(this);
        textSliderView.setOnSliderClickListener(onSliderClickListener);

        product_image_viewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        product_image_viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        product_image_viewPager.setCustomAnimation(new DescriptionAnimation());
        product_image_viewPager.setDuration(5000);
        product_image_viewPager.addOnPageChangeListener(this);

        if(accessoriesRecord != null)
        {
            textSliderView
                    .image(accessoriesRecord.getAccessory_product_image()[0])
                    .setScaleType(BaseSliderView.ScaleType.Fit);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(DishDashaProductActivity.class.getSimpleName(), "onResume");

        httpGetBadgesCall();

    }


    private void getAccessoriesProductDetails() {
        SimpleProgressBar.showProgress(AccessoryProductDetailsActivity.this);
        try {
            final String url = Contents.baseURL + "getAccessoriesProductDetails";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());


                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        String record = jsonObject.getString("record");
                                        Gson g = new Gson();
                                        accessoryDetailRecord = g.fromJson(record, AccessoryDetailRecord.class);

                                        initViewsPostCall(accessoryDetailRecord);
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
                    params.put("product_id", accessoriesRecord.getTefsal_product_id());
                    params.put("store_id", accessoriesRecord.getStore_id());

                    Log.e("Tefsal tailor == ", url + new JSONObject(params));

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


    private void initViewsPostCall(AccessoryDetailRecord accessoryDetailRecord) {


        //Color Array Fill


        //Filtered Colors
        for (Sizes sizes : accessoryDetailRecord.getSizes()) {

            if (uniqueColorSet.add(sizes.getColors().get(0).getColor())) {

                productSizesList.add(sizes);


            }


        }


        productColorAdapterHorizontalAccesories = new ProductColorAdapterHorizontalAccesories(productSizesList, this);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
        TefalApp.getInstance().setAccColorPosition(-1);
        colorRecyclerView.setAdapter(productColorAdapterHorizontalAccesories);


        // Bind Size Circle

        productSizeAdapterHorizontalAccessories = new ProductSizeAdapterHorizontalAccessories(accessoryDetailRecord.getSizes(), AccessoryProductDetailsActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(AccessoryProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        TefalApp.getInstance().setCurrentColorText("-1");
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontalAccessories);


        // Bind Slider

        //show images based on selection
        Accessory_product_image = accessoriesRecord.getAccessory_product_image();
        for (String imgUrl : Accessory_product_image) {


            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);




            product_image_viewPager.addSlider(textSliderView);

        }

    }

    public void showSizeOnColorSelection(Sizes sizes) {

        TefalApp.getInstance().setCurrentSizePositionIs(-1);
        String colorIs = sizes.getColors().get(0).getColor();

        TefalApp.getInstance().setCurrentColorText(colorIs != null ? colorIs : "Default");

        productSizeAdapterHorizontalAccessories.notifyDataSetChanged();

        isSizeSelected = false;

    }

    public void showSelectedSizeData(int position,Sizes sizes) {

        this.currentColorPosition = position;

        product_image_viewPager.removeAllSliders();
        for (String imgUrl : accessoryDetailRecord.getSizes().get(position).getColors().get(0).getImages()) {

            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            product_image_viewPager.addSlider(textSliderView);

        }



        if (sizes != null) {

            colorModel = sizes.getColors().get(0);

            int qty = colorModel.getQty() != null ? Integer.parseInt(colorModel.getQty()) : 0;
            if ( qty == 0) {
                add_cart_btn.setText("SOLD OUT");


            }

            if (colorModel.getPrice() != null) {
                price = Integer.parseInt(colorModel.getPrice());
                text_price.setText("PRICE : " + price + " KWD");
            }


        }


        isSizeSelected = true;


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


                            SimpleProgressBar.closeProgress();

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


//    public void gotoCart(View view) {
//        try {
//            startActivity(new Intent(AccessoryProductDetailsActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//    }


    private void setData() {

        txt_title.setText(accessoriesRecord.getProductName());
        subtxt_title.setText(accessoriesRecord.getStoreName());

        text_desc.setText(accessoriesRecord.getProductDesc());
        text_price.setText("PRICE: " + accessoriesRecord.getPrice() + " KWD");

    }


    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        Log.i(TAG, "Setting screen name: " + "AccessoryProductDetailsActivity");
        mTracker.setScreenName("Image~" + "AccessoryProductDetailsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(AccessoryProductDetailsActivity.this);


        JSONObject params = new JSONObject();


        try {
            params.put("access_token", session.getToken());
            params.put("user_id", session.getCustomerId());
            try {
                params.put("items", getItems(accessoriesRecord));
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
                        DialogKart dg = new DialogKart(AccessoryProductDetailsActivity.this, false, itemType, "");
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


    public void httpAddCartCall() {

        Log.i(TAG, "Setting screen name: " + "AccessoryProductDetailsActivity");
        mTracker.setScreenName("Image~" + "AccessoryProductDetailsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(AccessoryProductDetailsActivity.this);


        try {
            final String url = Contents.baseURL + "addCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("addCart response", response);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    String itemType = jsonObject.getString("item_type");
                                    DialogKart dg = new DialogKart(AccessoryProductDetailsActivity.this, false, itemType, "");
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
                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    try {
                        params.put("items", String.valueOf(getItems(accessoriesRecord)));
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
            RequestQueue requestQueue = Volley.newRequestQueue(AccessoryProductDetailsActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public JSONArray getItems(AccessoriesRecord accessoriesRecord) throws JSONException {


        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("product_id", accessoriesRecord.getTefsal_product_id());
        obj.put("item_id", accessoriesRecord.getAttribute_id());

        obj.put("category_id", "4");


        if (accessoryDetailRecord != null) {

            String colorSelected = accessoryDetailRecord.getSizes().get(currentColorPosition).getColors().get(0).getColor();

            JSONObject item_details = new JSONObject();
            item_details.put("size", accessoryDetailRecord.getSizes().get(currentColorPosition).getSize());
            item_details.put("color", colorSelected == null ? "Default" : colorSelected);
            item_details.put("item_quantity", "1");
            obj.put("item_details", item_details);
        }

        arry.put(obj);
        return arry;
    }


    public void showImageSingleDialog(String image_url) {


        String[] img = {image_url};
        dialog = new Dialog(AccessoryProductDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);

        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);


        AccessoryProductPagerAdapterForDialog accessoryProductPagerAdapterForDialog = new AccessoryProductPagerAdapterForDialog(AccessoryProductDetailsActivity.this, img);

        dialog_viewPager.setAdapter(accessoryProductPagerAdapterForDialog);
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


    public class AccessoryProductPagerAdapter extends PagerAdapter {
        Context context;
        String[] img;
        LayoutInflater layoutInflater;


        public AccessoryProductPagerAdapter(Context context, String[] img) {
            this.context = context;
            this.img = img;
            System.out.println("IMAGE COUNT======" + img.length);
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);

            final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.zaara);

            System.out.println("IMAGE   OF PRODUCT ====" + img[position]);
            Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        showImageSingleDialog(img[position]);
                    } catch (Exception ex) {
                        Log.d("Error=", ex.fillInStackTrace().toString());

                    }

                }
            });

            Picasso.with(AccessoryProductDetailsActivity.this).load(img[position]).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private void setUiPageViewController() {

        dotsCount = accessoryProductPagerAdapter.getCount();
        if (dotsCount != 0) {
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
        } else {
            //do nothing
        }

        System.out.println("COUNT======" + dotsCount);

    }

    public class AccessoryProductPagerAdapterForDialog extends PagerAdapter {
        Context context;
        String[] img;
        LayoutInflater layoutInflater;


        public AccessoryProductPagerAdapterForDialog(Context context, String[] img) {
            this.context = context;
            this.img = img;
            System.out.println("IMAGE COUNT======" + img.length);
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (LinearLayout) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);
            final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.zaara);
            // final ImageView imageView = (ImageView) itemView.findViewById(R.id.zaara);


           /* PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(imageView);
            photoAttacher.update();*/


            System.out.println("IMAGE   OF PRODUCT ====" + img[position]);
            Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    System.out.println("ON CLICK ====");
                }
            });

            Picasso.with(AccessoryProductDetailsActivity.this).load(img[position]).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }



}
