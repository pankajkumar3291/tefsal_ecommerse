package com.tefal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tefal.Models.ProductDaraAbayaResponse;
import com.tefal.Models.ProductMeasurement;
import com.tefal.Models.ProductRecord;
import com.tefal.Models.ProductSizes;
import com.tefal.Models.TailorStoresResponseModel;
import com.tefal.R;
import com.tefal.adapter.DishdashaTailorAdapter;
import com.tefal.adapter.DishdashaTextileProductAdapter;
import com.tefal.adapter.ProductSizeAdapterHorizontal;
import com.tefal.app.TefalApp;
import com.tefal.app.TefsalApplication;
import com.tefal.fragment.HomeFragment;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import android.widget.RelativeLayout.LayoutParams;

import android.widget.LinearLayout.LayoutParams;
import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ZaaraDaraaActivity extends BaseActivity {

    private int dotsCount;

    private ImageView[] dots;

    MainPagerAdapter adapter;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.LL_continer)
    LinearLayout LL_continer;
    @Bind(R.id.back_btn)
    ImageView back_btn;

    @Bind(R.id.mainViewPager)
    ViewPager mainViewPager;

    @Bind(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

    @Bind(R.id.add_cart_btn)
    Button add_cart_btn;

    @Bind(R.id.txt_title)
    TextView txt_title;

    @Bind(R.id.subtxt_title)
    TextView subtxt_title;

    @Bind(R.id.text_descp)
    TextView text_descp;

    @Bind(R.id.color_text)
    TextView color_text;

    @Bind(R.id.sizeGuide)
    TextView sizeGuide;

    /*@Bind(R.id.txt_size_s)
    TextView txt_size_s;

    @Bind(R.id.txt_size_m)
    TextView txt_size_m;

    @Bind(R.id.txt_size_l)
    TextView txt_size_l;

    @Bind(R.id.txt_size_xl)
    TextView txt_size_xl;*/

    //@Bind(R.id.text_price)

    @Bind(R.id.add_btn)
    ImageView add_btn;

    @Bind(R.id.less_btn)
    ImageView less_btn;



    @Bind(R.id.sizeRecyclerView)
    RecyclerView sizeRecyclerView;





    int amount;
    String sizeFlage="";
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
    private List<ProductSizes> productSizesList=new ArrayList<ProductSizes>();
    private static Tracker mTracker;
    private static final String TAG = "ZaaraDaraaActivity";
    ProductSizeAdapterHorizontal productSizeAdapterHorizontal;
    private HashMap<String,String> sizePriceMap;


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
        sizePriceMap=new HashMap<String, String>();
        productRecord = (ProductRecord) bundle.getSerializable("productRecords");
        sizeGuideResponseHtml=productRecord.getMeasurements();
        productSizesList=productRecord.getSizes();

        text_price=(TextView)findViewById(R.id.text_price);
        stockQuantity=(TextView)findViewById(R.id.stockQuantity);
        meter_value=(TextView)findViewById(R.id.meter_value);



        System.out.println("sizeGuideResponseHtml==="+sizeGuideResponseHtml);
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
                   Bundle bundle=new Bundle();
                   bundle.putString("sizeGuideResponseHtml",sizeGuideResponseHtml);

                   intent.putExtras(bundle);

                   //intent.putExtra("sizeGuideResponseHtml",sizeGuideResponseHtml);

                   startActivity(intent);
               }
               catch (Exception ex)
               {
                   System.out.println("Error=="+ex);
               }

            }
        });
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebCallServiceAddCart();


            }
        });

        LL_continer.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    meter++;
                    if(meter>=0 && meter<=10)
                    {
                        amount=meter* price;
                        text_price.setText("PRICE : "+amount+" KWD");
                        meter_value.setText(""+meter);

                    }

            }

        });
        less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    if(meter>1)
                    {
                        meter--;amount=meter*price;text_price.setText("PRICE : "+amount+" KWD");meter_value.setText(""+meter);
                    }
            }
        });

        initView();

    }

    private void initView()
    {
        LL_continer.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        productSizeAdapterHorizontal=new ProductSizeAdapterHorizontal(productSizesList,ZaaraDaraaActivity.this);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ZaaraDaraaActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sizeRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        TefalApp.getInstance().setPosition(0);
        sizeRecyclerView.setAdapter(productSizeAdapterHorizontal);

        adapter = new MainPagerAdapter(ZaaraDaraaActivity.this, productRecord.getProduct_images());
        mainViewPager.setAdapter(adapter);
        // mainViewPager.setOffscreenPageLimit(adapter.getCount());
        mainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                // if()
                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();

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


                                System.out.println("ADD CART RESPONSE===="+response);

                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ZaaraDaraaActivity.this);
                                builder.setMessage("Your product is successfully added to your cart")
                                        .setCancelable(false)
                                        .setPositiveButton("Go to Cart", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
//                                                startActivity(new Intent(getActivity(), DishDashaProductActivity.class).putExtra("Flag", "2").putExtra("store_id", TextileDetailActivity.StoreID));
                                                startActivity(new Intent(ZaaraDaraaActivity.this, CartActivity.class));
                                            }
                                        })
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
                                            }
                                        });

                                //Creating dialog box
                                android.support.v7.app.AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Item added successfully");
                                alert.show();

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


                    System.out.println("QUANTITY===="+meter_value.getText());
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
        JSONObject obj  = new JSONObject();
        obj.put("product_id",productRecord.getTefsal_product_id());
        obj.put("item_id",productRecord.getAttribute_id());
        obj.put("item_quantity",meter_value.getText());

        arry.put(obj);
        return arry;
    }



    private void    setUiPageViewController() {

        dotsCount = adapter.getCount();
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
            return view == (LinearLayout)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.zaara);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   /* imageView1=imageView;
                    System.out.println("POSITION==="+position);
                    System.out.println();
*/
                            try
                            {
                                showImageSingleDialog(img.get(position));
                            }
                            catch(Exception ex)
                            {
                              Log.d("Error=",ex.fillInStackTrace().toString()) ;

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
    public void gotoCart(View v)
    {
            Log.i(TAG, "Setting screen name: " + "ZaaraDaraaActivity");
            mTracker.setScreenName("Image~" + "ZaaraDaraaActivity");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        startActivity(new Intent(ZaaraDaraaActivity.this,CartActivity.class));

    }

    public void showImageSingleDialog(String image_url) {
         dialog = new Dialog(ZaaraDaraaActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);


        ViewPager dialog_viewPager=(ViewPager)dialog.findViewById(R.id.dialog_viewPager);


            MainPagerAdapterForDialog mainPagerAdapterForDialog=new MainPagerAdapterForDialog(ZaaraDaraaActivity.this,productRecord.getProduct_images());
            dialog_viewPager.setAdapter(mainPagerAdapterForDialog);
            dialog_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                {


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
            return view == (LinearLayout)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.zaara_daraa_first, container, false);
          // LinearLayout LL_pager_holder=(LinearLayout) itemView.findViewById(R.id.LL_pager_holder);
            final PhotoView imageView = (PhotoView) itemView.findViewById(R.id.zaara);

            try
            {
               /* PhotoViewAttacher photoAttacher;
                photoAttacher= new PhotoViewAttacher(imageView);
                photoAttacher.update();*/
            }catch(Exception ex)
            {

            }


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
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