package com.tefal.activity;

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
import com.tefal.Models.AccessoriesRecord;
import com.tefal.Models.AccessoryDetailRecord;
import com.tefal.Models.BadgeRecordModel;
import com.tefal.Models.Colors;
import com.tefal.R;
import com.tefal.adapter.ProductColorAdapterHorizontalAccesories;
import com.tefal.adapter.ProductSizeAdapterHorizontalAccessories;
import com.tefal.app.TefalApp;
import com.tefal.app.TefsalApplication;
import com.tefal.dialogs.DialogKart;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

            String storeId = getIntent().getStringExtra("storeId");
            getAccessoriesProductDetails(storeId);

        }

        initSlider();
        setData();


        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                httpAddCartCall();

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


    private void getAccessoriesProductDetails(final String storeId) {
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
                    params.put("store_id", storeId);

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


    private void initViewsPostCall(AccessoryDetailRecord accessoryDetailRecord) {


        //Color Array Fill


        productColorAdapterHorizontalAccesories = new ProductColorAdapterHorizontalAccesories(accessoryDetailRecord.getSizes(), this);
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
        TefalApp.getInstance().setSetAccColorPosition(0);
        colorRecyclerView.setAdapter(productColorAdapterHorizontalAccesories);


        // Bind Size Circle

        productSizeAdapterHorizontalAccessories = new ProductSizeAdapterHorizontalAccessories(accessoryDetailRecord.getSizes(), AccessoryProductDetailsActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(AccessoryProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        TefalApp.getInstance().setPosition(0);
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontalAccessories);


        // Bind Slider

        //show images based on selection
        Accessory_product_image = accessoriesRecord.getAccessory_product_image();
        for (String imgUrl : Accessory_product_image) {


            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            product_image_viewPager.addSlider(textSliderView);

        }

    }

    public void showSizeOnColorSelection(int position) {


        productSizeAdapterHorizontalAccessories.notifyDataSetChanged();

    }

    public void showSelectedSizeData(int position) {


        product_image_viewPager.removeAllSliders();
        for (String imgUrl : accessoryDetailRecord.getSizes().get(position).getColors().get(0).getImages()) {


            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            product_image_viewPager.addSlider(textSliderView);

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


    public void gotoCart(View view) {
        try {
            startActivity(new Intent(AccessoryProductDetailsActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private void setData() {

        txt_title.setText(accessoriesRecord.getProductName());
        subtxt_title.setText(accessoriesRecord.getBrandName());

        text_desc.setText(accessoriesRecord.getProductDesc());
        text_price.setText("PRICE: " + accessoriesRecord.getPrice() + " KWD");

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

                            //  SimpleProgressBar.closeProgress();

                            if (response != null) {
                                SimpleProgressBar.closeProgress();
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
        obj.put("item_quantity", "1");

        arry.put(obj);
        return arry;
    }


    public void showImageSingleDialog(String image_url) {
        dialog = new Dialog(AccessoryProductDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);

        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);


        AccessoryProductPagerAdapterForDialog accessoryProductPagerAdapterForDialog = new AccessoryProductPagerAdapterForDialog(AccessoryProductDetailsActivity.this, Accessory_product_image);

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
