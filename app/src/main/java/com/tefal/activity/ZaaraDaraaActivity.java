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
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tefal.Models.BadgeRecordModel;
import com.tefal.Models.ProductMeasurement;
import com.tefal.Models.ProductRecord;
import com.tefal.Models.ProductSizes;
import com.tefal.R;
import com.tefal.adapter.ProductSizeAdapterHorizontal;
import com.tefal.app.TefalApp;
import com.tefal.app.TefsalApplication;
import com.tefal.dialogs.DialogKart;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

//import android.widget.RelativeLayout.LayoutParams;

public class ZaaraDaraaActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private int dotsCount;

    private ImageView[] dots;


    @BindView(R.id.scrollView)
    ScrollView scrollView;


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

    @BindView(R.id.color_text)
    TextView color_text;

    @BindView(R.id.sizeGuide)
    TextView sizeGuide;

    /*@BindView(R.id.txt_size_s)
    TextView txt_size_s;

    @BindView(R.id.txt_size_m)
    TextView txt_size_m;

    @BindView(R.id.txt_size_l)
    TextView txt_size_l;

    @BindView(R.id.txt_size_xl)
    TextView txt_size_xl;*/

    //@BindView(R.id.text_price)

    @BindView(R.id.add_btn)
    ImageView add_btn;

    @BindView(R.id.less_btn)
    ImageView less_btn;


    @BindView(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;


    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;


    int amount;
    String sizeFlage = "";
    String sizeGuideResponseHtml;

    //This member variable used in ProductSizeAdapterHorizontal adapter
    public static int price;
    public static int meter = 1;
    public static TextView text_price;
    public static TextView stockQuantity;
    public static TextView meter_value;


    SessionManager session;
    public ProductRecord productRecord;
    public static List<ProductMeasurement> productMeasurementList;
    private List<ProductSizes> productSizesList = new ArrayList<ProductSizes>();
    private static Tracker mTracker;
    private static final String TAG = "ZaaraDaraaActivity";
    ProductSizeAdapterHorizontal productSizeAdapterHorizontal;
    private HashMap<String, String> sizePriceMap;


    /*
   * This dialog is used to show the image which can zoom in zoom out from view pager
   * */
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zaara_daraa);

        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        sizePriceMap = new HashMap<String, String>();
        productRecord = (ProductRecord) bundle.getSerializable("productRecords");
        sizeGuideResponseHtml = productRecord.getMeasurements();
        productSizesList = productRecord.getSizes();

        text_price = (TextView) findViewById(R.id.text_price);
        stockQuantity = (TextView) findViewById(R.id.stockQuantity);
        meter_value = (TextView) findViewById(R.id.meter_value);


        System.out.println("sizeGuideResponseHtml===" + sizeGuideResponseHtml);
        //  amount=meter * Double.parseDouble(productRecord.get)
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        // productMeasurementList=productRecord.getMeasurements();

        text_descp.setText(productRecord.getProduct_desc());
        txt_title.setText(productRecord.getProduct_name());
        subtxt_title.setText(productRecord.getBrand_name());
        color_text.setText(productRecord.getColor());

        session = new SessionManager(getApplicationContext());
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
                WebCallServiceAddCart();


            }
        });

      //  LL_continer.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meter++;
                if (meter >= 0 && meter <= 10) {
                    amount = meter * price;
                    text_price.setText("PRICE : " + amount + " KWD");
                    meter_value.setText("" + meter);

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

        initView();

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


    private void initView() {
       // LL_continer.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        productSizeAdapterHorizontal = new ProductSizeAdapterHorizontal(productSizesList, ZaaraDaraaActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ZaaraDaraaActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        TefalApp.getInstance().setPosition(0);
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontal);


        mainViewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mainViewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainViewPager.setCustomAnimation(new DescriptionAnimation());
        mainViewPager.setDuration(5000);
        mainViewPager.addOnPageChangeListener(this);

        List<String> stringList = productRecord.getProduct_images();

        for (String imgUrl : stringList) {

            DefaultSliderView textSliderView = new DefaultSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(imgUrl)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            mainViewPager.addSlider(textSliderView);

        }

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mainViewPager.stopAutoCycle();
        super.onStop();
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
                            //     SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();


                    System.out.println("QUANTITY====" + meter_value.getText());
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
        obj.put("item_id", productRecord.getAttribute_id());
        obj.put("item_quantity", meter_value.getText());

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