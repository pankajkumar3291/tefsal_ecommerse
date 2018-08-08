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
import com.tefsalkw.adapter.ProductColorAdapterHorizontalAccesories;
import com.tefsalkw.adapter.ProductSizeAdapterHorizontalAccessories;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.models.AccColor;
import com.tefsalkw.models.AccSizes;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.AccessoryDetailRecord;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccessoryProductDetailsActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private AccessoriesRecord accessoriesRecord;

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


    private int dotsCount;
    private ImageView[] dots;
    private AccessoryProductPagerAdapter accessoryProductPagerAdapter;


    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;


    Dialog dialog;


    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;


    @BindView(R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;


    AccessoryDetailRecord accessoryDetailRecord;


    DefaultSliderView.OnSliderClickListener onSliderClickListener;


    ProductSizeAdapterHorizontalAccessories productSizeAdapterHorizontalAccessories;
    ProductColorAdapterHorizontalAccesories productColorAdapterHorizontalAccesories;


    boolean isSizeSelected = false;


    public static float price;


    AccColor selectedColor = null;

    AccSizes selectedSize = null;

    String default_image = null;

    String productId = "";
    String storeId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_product_details);
        ButterKnife.bind(this);
        session = new SessionManager(this);
        onSliderClickListener = this;
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();


        Intent intent = getIntent();

        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase("FromPushNotification")) {

            storeId = intent.getStringExtra("store_id");
            productId = intent.getStringExtra("product_id");

        } else {
            accessoriesRecord = (AccessoriesRecord) getIntent().getSerializableExtra("accessoriesRecord");
        }


        if (accessoriesRecord != null) {

            default_image = accessoriesRecord.getDefault_image();
            storeId = accessoriesRecord.getStore_id();
            productId = accessoriesRecord.getTefsal_product_id();

        }

        getAccessoriesProductDetails();


        initSlider();
        setData();


        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedColor == null) {

                    Toast.makeText(AccessoryProductDetailsActivity.this, "Please select color and size!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (price == 0) {

                    Toast.makeText(AccessoryProductDetailsActivity.this, "Sorry, Not available!", Toast.LENGTH_SHORT).show();
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


        product_image_viewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        product_image_viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        product_image_viewPager.setCustomAnimation(new DescriptionAnimation());
        product_image_viewPager.setDuration(5000);
        product_image_viewPager.addOnPageChangeListener(this);


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


                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                try {

                                    Gson g = new Gson();
                                    accessoryDetailRecord = g.fromJson(response, AccessoryDetailRecord.class);
                                    if (accessoryDetailRecord != null) {
                                        txt_title.setText(accessoryDetailRecord.getProductName());
                                        subtxt_title.setText(accessoryDetailRecord.getStoreName());

                                        text_desc.setText(accessoryDetailRecord.getProductDesc());
                                    }
                                    Log.e("accessoryDetailRecord", response);
                                    initViewsPostCall(accessoryDetailRecord);


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
                    params.put("product_id", productId);
                    params.put("store_id", storeId);

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


        productColorAdapterHorizontalAccesories = new ProductColorAdapterHorizontalAccesories(accessoryDetailRecord.getColors(), this);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
        TefalApp.getInstance().setAccColorPosition(0);
        colorRecyclerView.setAdapter(productColorAdapterHorizontalAccesories);


    }


    public void showSizeOnColorSelection(AccColor accColor) {

        selectedColor = accColor;

        if (selectedColor != null) {


            //Fill Size

            productSizeAdapterHorizontalAccessories = new ProductSizeAdapterHorizontalAccessories(selectedColor.getSizes(), AccessoryProductDetailsActivity.this);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(AccessoryProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
            sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);
            TefalApp.getInstance().setCurrentSizePositionIs(0);
            sizeRecyclerView.setAdapter(productSizeAdapterHorizontalAccessories);


            //Show images
            product_image_viewPager.removeAllSliders();

            List<String> imagesList = selectedColor.getImages();

            if (imagesList != null && imagesList.size() <= 1) {
                product_image_viewPager.stopAutoCycle();
            } else {
                product_image_viewPager.startAutoCycle();
            }

            if (imagesList != null && imagesList.size() > 0) {
                for (String imgUrl : imagesList) {

                    DefaultSliderView textSliderView = new DefaultSliderView(AccessoryProductDetailsActivity.this);
                    textSliderView.setOnSliderClickListener(onSliderClickListener);
                    textSliderView
                            .image(imgUrl)
                            .setScaleType(BaseSliderView.ScaleType.Fit);


                    product_image_viewPager.addSlider(textSliderView);

                }
            } else {
                DefaultSliderView textSliderView = new DefaultSliderView(AccessoryProductDetailsActivity.this);
                textSliderView.setOnSliderClickListener(onSliderClickListener);
                textSliderView
                        .image(default_image)
                        .setScaleType(BaseSliderView.ScaleType.Fit);


                product_image_viewPager.addSlider(textSliderView);
            }


        }


    }


    public void showSelectedSizeData() {


        if (selectedColor != null) {

            selectedSize = selectedColor.getSizes().get(TefalApp.getInstance().getCurrentSizePositionIs());

            if (selectedSize != null) {
                int qty = selectedSize.getQuantity() != null ? selectedSize.getQuantity() : 0;

                if (qty == 0) {
                    add_cart_btn.setText("SOLD OUT");
                    add_cart_btn.setEnabled(false);

                } else {
                    add_cart_btn.setEnabled(true);
                    add_cart_btn.setText(getResources().getString(R.string.zaara_daraa_add_cart_btn_text));

                }


                if (selectedSize.getPrice() != null) {

                    price = Float.parseFloat(selectedSize.getPrice().toString());
                    text_price.setText("PRICE : " + price + " KWD");

                } else {

                    price = 0;
                    text_price.setText("NOT AVAILABLE");
                }
            }


        }


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


    private void setData() {

        if (accessoriesRecord != null) {
            txt_title.setText(accessoriesRecord.getProductName());
            subtxt_title.setText(accessoriesRecord.getStoreName());

            text_desc.setText(accessoriesRecord.getProductDesc());
        }


    }


    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        Log.i(TAG, "Setting screen name: " + "AccessoryProductDetailsActivity");
        mTracker.setScreenName("Image~" + "AccessoryProductDetailsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(AccessoryProductDetailsActivity.this);


        JSONObject params = new JSONObject();


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


    public JSONArray getItems() throws JSONException {


        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("category_id", "4");
        obj.put("product_id", accessoryDetailRecord.getTefsalProductId());
        obj.put("item_id", selectedSize != null ? selectedSize.getAttribute_meta_id() : "");


        if (selectedColor != null) {

            JSONObject item_details = new JSONObject();
            // item_details.put("size", selectedColor.getSizes().get(TefalApp.getInstance().getAccColorPosition()).getSize());
            //item_details.put("color", selectedColor == null ? "Default" : selectedColor.getColor());
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

            //  Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);
            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(context).asBitmap().load(img[position]).apply(options).into(imageView);

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


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
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
            // Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);

            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(context).asBitmap().load(img[position]).apply(options).into(imageView);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    System.out.println("ON CLICK ====");
                }
            });


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}
