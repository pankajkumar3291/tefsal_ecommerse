package com.tefsalkw.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.adapter.BrandFilterAdapter;
import com.tefsalkw.adapter.CountryFilterAdapter;
import com.tefsalkw.adapter.DishdashaTextileProductAdapter;
import com.tefsalkw.adapter.PatternFilterAdapter;
import com.tefsalkw.adapter.SeasonFilterAdapter;
import com.tefsalkw.adapter.TextileDetailPager;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.models.ColorsRecordModel;
import com.tefsalkw.models.DishdishaFilterResponseModel;
import com.tefsalkw.models.FilterBrandModel;
import com.tefsalkw.models.FilterCountryModel;
import com.tefsalkw.models.FilterPatternModel;
import com.tefsalkw.models.SeasonResponseModel;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.models.dishdashaFiletrationResponse;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AC 101 on 12-10-2017.
 */

public class TextileDetailActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.product_image_viewPager)
    SliderLayout product_image_viewPager;

    @BindView(R.id.no_image_holder)
    ImageView no_image_holder;

    @BindView(R.id.viewPagerIndicator)
    RelativeLayout viewPagerIndicator;

    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;

   /* @BindView(R.id.product_img)
    ImageView product_img;*/

    @BindView(R.id.ic_filter)
    ImageView ic_filter;

    @BindView(R.id.txt_price)
    TextView txt_price;

    @BindView(R.id.txt_min_meter)
    TextView txt_min_meter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.add_to_cart_lbl)
    TextView add_to_cart_lbl;


    @BindView(R.id.done_txt)
    TextView done_txt;

    @BindView(R.id.add_to_cart_btn)
    RelativeLayout add_to_cart_btn;


    @BindView(R.id.add_btn)
    ImageView add_btn;

    @BindView(R.id.less_btn)
    ImageView less_btn;

    @BindView(R.id.meter_value)
    TextView meter_value;


    @BindView(R.id.RL_no_product_found_container)
    RelativeLayout RL_no_product_found_container;

    @BindView(R.id.RL_product_exist_container)
    RelativeLayout RL_product_exist_container;


    @BindView(R.id.LL_min_max_controller)
    LinearLayout LL_min_max_controller;


    TextView see_all_country_text;
    TextView see_all_brand_text;
    TextView see_all_pattern_text;

    public static TextView text_price;
    //private int meter=2;
    private int stock_meter = 2;
    private int stock_meter_check = 2;
    private int price;

    //private int check_meter;
    private float min_meter;
    private float min_meter_check;

    LinearLayout see_all_pattern_text_LL;

    RecyclerView recyclerViewCountry;
    RecyclerView recyclerViewBrand;
    RecyclerView recyclerViewPattern;


    public static String StoreID;
    public static int position;

    PopupWindow filterWindow;

    private boolean isExpanded = false;
    private boolean filterWindowFlag = true;

    DishdishaFilterResponseModel dishdishaFilterResponseModel;

    dishdashaFiletrationResponse _mdishdashaFiletrationResponse;
    SeasonResponseModel seasonResponseModel;

    CountryFilterAdapter countryFilterAdapter;
    BrandFilterAdapter brandFilterAdapter;
    PatternFilterAdapter patternFilterAdapter;

    SeasonFilterAdapter seasonFilterAdapter;


    ArrayList<FilterCountryModel> filterCountryModelArrayList = new ArrayList<FilterCountryModel>();
    ArrayList<FilterPatternModel> filterPatternModelArrayList = new ArrayList<FilterPatternModel>();
    ArrayList<FilterBrandModel> filterBrandModelArrayList = new ArrayList<FilterBrandModel>();
    ArrayList<ColorsRecordModel> colorsRecordModelArrayList = new ArrayList<ColorsRecordModel>();

    ArrayList<TextileProductModel> textileProductModelArrayList = new ArrayList<TextileProductModel>();


    String seasonRecord[];


    boolean country_flage = true;
    boolean brand_flage = true;
    boolean pattern_flage = true;
    //  boolean color_flage=true;
    //boolean season_flage=true;


    SessionManager session;


    // Those member variable holds information about the color, subcolor, country, season, info-----

    private String colorString;
    private String subColorString;
    private String countryString;
    private String seasonString;


    private String[] product_image;
    //  private String[] str;
    private int dotsCount;

    private ImageView[] dots;
    private DishDashaProductPagerAdapter dishDashaProductPagerAdapter;


// This model object hold the data of the product from product list;

    TextileProductModel textileProductModel = new TextileProductModel();

    //colorWindow

    /*
     * This dialog is used to show the image which can zoom in zoom out from view pager
     * */
    Dialog dialog;

    //SessionManager sessionManager;


    DefaultSliderView.OnSliderClickListener onSliderClickListener;
    DefaultSliderView textSliderView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textile_detail);

        ButterKnife.bind(this);

        session = new SessionManager(TextileDetailActivity.this);
        setSupportActionBar(toolbar);


        ic_filter.setVisibility(View.VISIBLE);

        textileProductModel = (TextileProductModel) getIntent().getSerializableExtra("textileProductModel");
        text_price = (TextView) findViewById(R.id.text_price);
        System.out.println("Tefsal");


        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorPrimaryDark));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }


        System.out.println("OUTPUT OF PRODUCT========" + textileProductModel.toString());

        if (textileProductModel == null) {
            RL_no_product_found_container.setVisibility(View.VISIBLE);
            RL_product_exist_container.setVisibility(View.GONE);
            return;
        } else {
            RL_no_product_found_container.setVisibility(View.GONE);
            RL_product_exist_container.setVisibility(View.VISIBLE);
        }


        try {
            //meter=Integer.parseInt(textileProductModel.getDishdasha_qty_meters());
            min_meter = Float.parseFloat(TefalApp.getInstance().getMin_meters());
            min_meter_check = Float.parseFloat(TefalApp.getInstance().getMin_meters());
            stock_meter = Integer.parseInt(textileProductModel.getDishdasha_qty_meters());
            stock_meter_check = Integer.parseInt(textileProductModel.getDishdasha_qty_meters());

            System.out.println("Tefsal output======Minimum MIter===" + min_meter);
            System.out.println("Tefsal output======Dishdasha stock===" + stock_meter);

            product_image = textileProductModel.getProduct_image();
            price = Integer.parseInt(textileProductModel.getPrice());//.split(" ");

            System.out.println("Price=====" + price);
        } catch (Exception ex) {
            min_meter = 2;
            stock_meter = 1;

            System.out.println("Error ===========1" + ex);
        }

        // This block of code is used to detetermine the max and min

        if (min_meter > stock_meter) {
            LL_min_max_controller.setVisibility(View.GONE);
            add_to_cart_lbl.setText("SOLD OUT");
            text_price.setVisibility(View.GONE);
            add_to_cart_btn.setEnabled(false);
        } else {

        }

        System.out.println("HELLO THE SIZE OF textileProductModel" + textileProductModel.getProduct_image().length);


        // System.out.println("IMAGE PRODUCT========"+product_image[0]);
        // System.out.println("IMAGE PRODUCT========"+product_image[1]);


        // Getting info of color, subcolor,country, season etc........


        colorString = TefalApp.getInstance().getColor();
        seasonString = TefalApp.getInstance().getSeason();
        countryString = TefalApp.getInstance().getCountry();
        subColorString = TefalApp.getInstance().getSubColor();


        System.out.println("TextileDetailActivity==============colorString==" + colorString);
        System.out.println("TextileDetailActivity==============seasonString==" + seasonString);
        System.out.println("TextileDetailActivity==============countryString==" + countryString);
        System.out.println("TextileDetailActivity==============StoreId==" + TefalApp.getInstance().getStoreId());
        try {

            text_price.setText("PRICE : " + Math.round(price * min_meter) + " KWD");
            txt_min_meter.setText("" + TefalApp.getInstance().getMin_meters() + " MIN METERS REQUIRED");
        } catch (Exception ex) {
            System.out.println("Error===============" + ex);
        }
        /*text_price=(TextView)findViewById(R.id.text_price);
        text_price.setText("PRICE : "+Integer.parseInt(str[0])*meter+" KWD");
        txt_min_meter.setText(""+meter+" MIN METERS REQUIRED");*/


        // toolbar_title.setText(DishdashaTextileProductAdapter.textileModels.get(position).getPrice()+".000 KWD");
        ic_filter.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        httpGetFilterData();

        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(TextileDetailActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(TextileDetailActivity.this);
                }
                builder.setTitle("Disclaimer")
                        .setMessage("Please make sure that you have viewed the textile with your full brightness, the color and pattern you receive may vary depending on your screen type and brightness. Please make sure to read the color name before Continuing.\n")
                        .setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                dialog.cancel();
                                WebCallServiceAddCartNew();


                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.cancel();
                            }
                        })
                        .show();



            }
        });

        ic_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_filter.setVisibility(View.GONE);
                initPopupFilter(v, filterWindowFlag);

                done_txt.setVisibility(View.VISIBLE);

            }
        });

        done_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("I m hitted");
                // done_txt.setVisibility(View.GONE);
                //ic_filter.setVisibility(View.VISIBLE);

                httpGetFilterDataAfterFilter();
                filterWindow.dismiss();
            }
        });

        StoreID = getIntent().getStringExtra("storeID");
        position = getIntent().getIntExtra("pos", 0);

        txt_price.setText(textileProductModel.getPrice() + " KWD / METER");
        toolbar_title.setText(textileProductModel.getDishdasha_product_name());


        // if (!DishdashaTextileProductAdapter.textileModels.get(position).getProduct_image().equals(""))
        //Picasso.with(this).load(DishdashaTextileProductAdapter.textileModels.get(position).getProduct_image()).into(product_img);


        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("FEEL"));
        tabLayout.addTab(tabLayout.newTab().setText("MATERIAL"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Creating our pager adapter
        TextileDetailPager adapter = new TextileDetailPager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        initSlider();

//        if (product_image != null) {
//            if (product_image.length != 0) {
//                dishDashaProductPagerAdapter = new DishDashaProductPagerAdapter(TextileDetailActivity.this, product_image);
//                product_image_viewPager.setAdapter(dishDashaProductPagerAdapter);
//                //  product_image_viewPager.setOffscreenPageLimit(dishDashaProductPagerAdapter.getCount());
//                product_image_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                        // if()
//                        //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        for (int i = 0; i < dotsCount; i++) {
//                            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));
//                        }
//                        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//
//                    }
//                });
//
//
//                setUiPageViewController();
//            }
//        } else {
//            no_image_holder.setVisibility(View.VISIBLE);
//            no_image_holder.setImageResource(R.drawable.no_image_placeholder_non_grid);
//        }


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (min_meter < stock_meter_check) {
                    min_meter++;
                    meter_value.setText("" + Math.round(min_meter));


                }

                TextileDetailActivity.text_price.setText("PRICE : " + Math.round(Integer.valueOf(price) * min_meter) + " KWD");

            }
        });
        less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (min_meter > min_meter_check) {
                    min_meter--;
                    meter_value.setText("" + Math.round(min_meter));
                }

                TextileDetailActivity.text_price.setText("PRICE : " + Math.round(Integer.valueOf(price) * min_meter) + " KWD");
            }
        });


        meter_value.setText("" + Math.round(min_meter));


    }


    private void initSlider() {

        onSliderClickListener = this;

        textSliderView = new DefaultSliderView(this);
        textSliderView.setOnSliderClickListener(onSliderClickListener);

        product_image_viewPager.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        product_image_viewPager.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        product_image_viewPager.setCustomAnimation(new DescriptionAnimation());
        product_image_viewPager.setDuration(5000);
        product_image_viewPager.addOnPageChangeListener(this);


        if (product_image != null) {
            product_image_viewPager.removeAllSliders();
            for (String imgUrl : product_image) {


                textSliderView
                        .image(imgUrl)
                        .setScaleType(BaseSliderView.ScaleType.Fit);


                product_image_viewPager.addSlider(textSliderView);

            }

            if (product_image.length > 1) {
                product_image_viewPager.startAutoCycle();

            } else {
                product_image_viewPager.stopAutoCycle();
            }
        }


    }

    public JSONArray getItems() {
        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            obj.put("product_id", DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getTefsal_product_id());
            obj.put("item_id", DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getDishdasha_attribute_id());
            obj.put("category_id", "1");

            JSONObject item_details = new JSONObject();
            item_details.put("item_quantity", meter_value.getText());
            obj.put("item_details", item_details);

            arry.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("CART RESPONSE ARRAY item_id==" + DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getDishdasha_attribute_id());
        System.out.println("CART RESPONSE ARRAY==" + arry.toString());
        return arry;
    }

    private void initPopupFilter(View v, boolean _filterWindowFlag) {
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.filter_layout_design, null);

        see_all_country_text = (TextView) popupView.findViewById(R.id.see_all_contry_text);
        see_all_pattern_text = (TextView) popupView.findViewById(R.id.see_all_pattern_text);
        see_all_brand_text = (TextView) popupView.findViewById(R.id.see_all_brand_text);
        see_all_pattern_text_LL = (LinearLayout) popupView.findViewById(R.id.see_all_pattern_text_LL);
        recyclerViewCountry = (RecyclerView) popupView.findViewById(R.id.recyclerViewCountry);
        recyclerViewPattern = (RecyclerView) popupView.findViewById(R.id.recyclerViewPattern);
        recyclerViewBrand = (RecyclerView) popupView.findViewById(R.id.recyclerViewBrand);
        //this.filterWindowFlag=false

        filterWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        filterWindow.setBackgroundDrawable(new BitmapDrawable());
        filterWindow.setOutsideTouchable(false);
        filterWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //done_txt.setVisibility(View.GONE);
                //ic_filter.setVisibility(View.VISIBLE);
            }
        });


        //--------------------This for country list----------------------------------------------------

        if (filterCountryModelArrayList.size() != 0) {
            countryFilterAdapter = new CountryFilterAdapter(filterCountryModelArrayList, TextileDetailActivity.this, filterCountryModelArrayList.size() <= 3 ? filterCountryModelArrayList.size() : 3);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TextileDetailActivity.this, 3);
            recyclerViewCountry.setLayoutManager(mLayoutManager);
            recyclerViewCountry.setItemAnimator(new DefaultItemAnimator());
            recyclerViewCountry.setAdapter(countryFilterAdapter);
        } else {

        }


        //------------------------------------This for Seasons list--------------------------------------------------------
     /*   seasonFilterAdapter=new SeasonFilterAdapter(seasonRecord,TextileDetailActivity.this,seasonRecord.length<=3 ? seasonRecord.length : 3);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(TextileDetailActivity.this, 3);
        recyclerViewSeason.setLayoutManager(mLayoutManager2);
        recyclerViewSeason.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSeason.setAdapter(seasonFilterAdapter);*/


        //------------------------This is for Brand list-------------------------------------------------------------------

        if (filterBrandModelArrayList.size() != 0) {
            brandFilterAdapter = new BrandFilterAdapter(filterBrandModelArrayList, TextileDetailActivity.this, filterBrandModelArrayList.size() <= 3 ? filterBrandModelArrayList.size() : 3);
            RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(TextileDetailActivity.this, 3);
            recyclerViewBrand.setLayoutManager(mLayoutManager2);
            recyclerViewBrand.setItemAnimator(new DefaultItemAnimator());
            recyclerViewBrand.setAdapter(brandFilterAdapter);
        } else {

        }


        //--------------------This is for Pattern List------------------------------------------------------------------------

        if (filterPatternModelArrayList.size() != 0) {
            patternFilterAdapter = new PatternFilterAdapter(filterPatternModelArrayList, TextileDetailActivity.this, filterPatternModelArrayList.size() <= 3 ? filterPatternModelArrayList.size() : 3);
            RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(TextileDetailActivity.this, 3);
            recyclerViewPattern.setLayoutManager(mLayoutManager3);
            recyclerViewPattern.setItemAnimator(new DefaultItemAnimator());
            recyclerViewPattern.setAdapter(patternFilterAdapter);
        } else {

        }


        if (filterCountryModelArrayList.size() <= 3) {
            see_all_country_text.setVisibility(View.GONE);
        }
        if (filterBrandModelArrayList.size() <= 3) {
            see_all_brand_text.setVisibility(View.GONE);
        }
        if (filterPatternModelArrayList.size() <= 3) {
            see_all_pattern_text.setVisibility(View.GONE);
            see_all_pattern_text_LL.setVisibility(View.GONE);
        }

        see_all_brand_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brand_flage) {
                    brandFilterAdapter.setLimit(filterBrandModelArrayList.size());
                    brandFilterAdapter.notifyDataSetChanged();
                    brand_flage = false;
                    see_all_brand_text.setText("Hide");

                } else {

                    brandFilterAdapter.setLimit(filterBrandModelArrayList.size() <= 3 ? filterBrandModelArrayList.size() : 3);
                    brandFilterAdapter.notifyDataSetChanged();
                    brand_flage = true;
                    see_all_brand_text.setText("See all");

                }
            }
        });
        see_all_pattern_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pattern_flage) {
                    patternFilterAdapter.setLimit(filterPatternModelArrayList.size());//<=3 ? filterPatternModelArrayList.size() : 3);
                    patternFilterAdapter.notifyDataSetChanged();
                    pattern_flage = false;
                    see_all_pattern_text.setText("Hide");
                } else {
                    patternFilterAdapter.setLimit(filterPatternModelArrayList.size() <= 3 ? filterPatternModelArrayList.size() : 3);
                    patternFilterAdapter.notifyDataSetChanged();
                    pattern_flage = true;
                    see_all_pattern_text.setText("See all");
                }
            }
        });

        see_all_country_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (country_flage) {
                    countryFilterAdapter.setLimit(filterCountryModelArrayList.size());
                    countryFilterAdapter.notifyDataSetChanged();
                    country_flage = false;
                    see_all_country_text.setText("Hide");

                } else {

                    country_flage = true;
                    see_all_country_text.setText("See all");
                    countryFilterAdapter.setLimit(filterCountryModelArrayList.size() <= 3 ? filterCountryModelArrayList.size() : 3);
                    countryFilterAdapter.notifyDataSetChanged();


                }
            }
        });


        if (filterWindow != null) {
            filterWindow.showAsDropDown(v);
        }

       /* if(_filterWindowFlag)
        {
            this.filterWindowFlag=false;
            filterWindow.showAsDropDown(v);
        }
        else
        {
            this.filterWindowFlag=true;
            filterWindow.dismiss();
        }*/

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void httpGetFilterData() {
        SimpleProgressBar.showProgress(TextileDetailActivity.this);
        try {
            final String url = Contents.baseURL + "dishdashaFiletration";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            SimpleProgressBar.closeProgress();

                            System.out.println("FILTER RESPONSE===" + response);


                            if (response != null) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    System.out.println("STATUS===" + status);

                                    if (status.equals("1")) {
                                        Gson g = new Gson();
                                        _mdishdashaFiletrationResponse = g.fromJson(response, dishdashaFiletrationResponse.class);

                                        filterBrandModelArrayList = _mdishdashaFiletrationResponse.getBrands();
                                        filterCountryModelArrayList = _mdishdashaFiletrationResponse.getCountries();
                                        filterPatternModelArrayList = _mdishdashaFiletrationResponse.getPatterns();

                                        // colorsRecordModelArrayList=_mdishdashaFiletrationResponse.getColors();
                                        textileProductModelArrayList = _mdishdashaFiletrationResponse.getProducts();


                                        System.out.println("TextileDetailActivity==============colorString==" + colorString);
                                        System.out.println("TextileDetailActivity==============seasonString==" + seasonString);
                                        System.out.println("TextileDetailActivity==============countryString==" + countryString);
                                        System.out.println("TextileDetailActivity==============subColor==" + subColorString);
                                        System.out.println("TextileDetailActivity==============StoreId==" + TefalApp.getInstance().getStoreId());

                                        System.out.println("TextileDetailActivity==============filterBrandModelArrayList==" + filterBrandModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============filterCountryModelArrayList==" + filterCountryModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============filterPatternModelArrayList==" + filterPatternModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============textileProductModelArrayList==" + textileProductModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============colorsRecordModelArrayList==" + colorsRecordModelArrayList.size());

                                        //initPopupFilter();
                                    } else {
                                        String errors = object.getString("errors");
                                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("ERROR===" + ex);
                                }

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("ERROR===" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("user_id", session.getCustomerId());
                    //params.put("access_token", session.getToken());

                    System.out.println("TextileDetailActivity======Parameter season===" + seasonString);
                    System.out.println("TextileDetailActivity======Parameter color_id===" + colorString);
                    System.out.println("TextileDetailActivity======Parameter sub_color_id===" + subColorString);
                    System.out.println("TextileDetailActivity======Parameter country_id===" + countryString);
                    System.out.println("TextileDetailActivity======Parameter brand_id===");
                    System.out.println("TextileDetailActivity======Parameter pattern_id===");
                    System.out.println("TextileDetailActivity======Parameter store_id===" + TefalApp.getInstance().getStoreId());

                    params.put("store_id", TefalApp.getInstance().getStoreId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("season", seasonString);
                    params.put("color_id", colorString);
                    params.put("sub_color_id", subColorString);
                    params.put("country_id", countryString);
                    params.put("brand_id", "");
                    params.put("pattern_id", "");


                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(TextileDetailActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            System.out.println("ERROR===" + surError);

            surError.printStackTrace();
        }
    }


    private void httpGetFilterDataAfterFilter() {
        SimpleProgressBar.showProgress(TextileDetailActivity.this);
        try {
            final String url = Contents.baseURL + "dishdashaFiletration";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            SimpleProgressBar.closeProgress();

                            System.out.println("FILTER RESPONSE===" + response);


                            if (response != null) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    System.out.println("STATUS===" + status);

                                    if (status.equals("1")) {
                                        Gson g = new Gson();
                                        _mdishdashaFiletrationResponse = g.fromJson(response, dishdashaFiletrationResponse.class);

                                       /* filterBrandModelArrayList=_mdishdashaFiletrationResponse.getBrands();
                                        filterCountryModelArrayList=_mdishdashaFiletrationResponse.getCountries();
                                        filterPatternModelArrayList=_mdishdashaFiletrationResponse.getPatterns();*/

                                        // colorsRecordModelArrayList=_mdishdashaFiletrationResponse.getColors();
                                        textileProductModelArrayList = _mdishdashaFiletrationResponse.getProducts();

                                        if (textileProductModelArrayList == null) {
                                            textileProductModel = null;
                                        } else {
                                            textileProductModel = textileProductModelArrayList.get(0);
                                        }
                                        startActivity(new Intent(TextileDetailActivity.this, TextileDetailActivity.class).putExtra("textileProductModel", textileProductModel));
                                        finish();


                                        System.out.println("TextileDetailActivity==============AfterFilter colorString==" + colorString);
                                        System.out.println("TextileDetailActivity==============AfterFilter seasonString==" + seasonString);
                                        System.out.println("TextileDetailActivity==============AfterFilter countryString==" + countryString);
                                        System.out.println("TextileDetailActivity==============AfterFilter subColor==" + subColorString);
                                        System.out.println("TextileDetailActivity==============AfterFilter StoreId==" + TefalApp.getInstance().getStoreId());

                                        System.out.println("TextileDetailActivity==============filterBrandModelArrayList==" + filterBrandModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============filterCountryModelArrayList==" + filterCountryModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============filterPatternModelArrayList==" + filterPatternModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============textileProductModelArrayList==" + textileProductModelArrayList.size());
                                        System.out.println("TextileDetailActivity==============colorsRecordModelArrayList==" + colorsRecordModelArrayList.size());

                                        //initPopupFilter();
                                    } else {
                                        String errors = object.getString("errors");
                                        Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("ERROR===" + ex);
                                }

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("ERROR===" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("user_id", session.getCustomerId());
                    //params.put("access_token", session.getToken());

                    System.out.println("TextileDetailActivity======Parameter season===" + seasonString);
                    System.out.println("TextileDetailActivity======Parameter color_id===" + colorString);
                    System.out.println("TextileDetailActivity======Parameter sub_color_id===" + subColorString);
                    System.out.println("TextileDetailActivity======Parameter country_id===" + countryString);
                    System.out.println("TextileDetailActivity======Parameter brand_id===");
                    System.out.println("TextileDetailActivity======Parameter pattern_id===");
                    System.out.println("TextileDetailActivity======Parameter store_id===" + TefalApp.getInstance().getStoreId());

                    params.put("store_id", TefalApp.getInstance().getStoreId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("season", seasonString);
                    params.put("color_id", colorString);
                    params.put("sub_color_id", subColorString);
                    params.put("country_id", countryString);
                    params.put("brand_id", TefalApp.getInstance().getBrand());
                    params.put("pattern_id", TefalApp.getInstance().getPattern());


                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(TextileDetailActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            System.out.println("ERROR===" + surError);

            surError.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (filterWindow != null) {
            filterWindow.dismiss();
        }
    }

    private void httpGetFilterSeasonData() {
        // SimpleProgressBar.showProgress(TextileDetailActivity.this);
        try {
            final String url = Contents.baseURL + "getSeasons";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            SimpleProgressBar.closeProgress();

                            System.out.println("SEASONS RESPONSE===" + response);


                            if (response != null) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    if (status.equals("1")) {
                                        SimpleProgressBar.closeProgress();
                                        Gson g = new Gson();
                                        seasonResponseModel = g.fromJson(response, SeasonResponseModel.class);
                                        seasonRecord = seasonResponseModel.getRecord();


                                        /// System.out.println("RECORDS==="+records.length);


                                        //filterPatternModelArrayList=dishdishaFilterResponseModel.getPatterns();
                                    } else {
                                       /* String errors=object.getString("errors");
                                        Toast.makeText(getApplicationContext(),errors,Toast.LENGTH_SHORT).show();*/
                                    }
                                } catch (Exception ex) {

                                }


                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("ERROR===" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("user_id", session.getCustomerId());
                    //params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(TextileDetailActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            System.out.println("ERROR===" + surError);

            surError.printStackTrace();
        }
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


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void showImageSingleDialog(String image_url) {
        dialog = new Dialog(TextileDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);

        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);

        //==========================================================================
        if (product_image.length != 0) {

            DishDashaProductPagerAdapterForDialog dishDashaProductPagerAdapterForDialog = new DishDashaProductPagerAdapterForDialog(TextileDetailActivity.this, product_image);
            //dishDashaProductPagerAdapter = new DishDashaProductPagerAdapter(TextileDetailActivity.this, product_image);
            dialog_viewPager.setAdapter(dishDashaProductPagerAdapterForDialog);
            //  product_image_viewPager.setOffscreenPageLimit(dishDashaProductPagerAdapter.getCount());
            dialog_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // if()
                    //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();

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
        }

        //==================================================================================


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


    }


    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        SimpleProgressBar.showProgress(this);

        JSONObject params = new JSONObject();


        try {
            params.put("access_token", session.getToken());
            params.put("user_id", session.getCustomerId());
            params.put("cart_id", session.getKeyCartId());
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

                        String itemType = "";
                        if (session.getKeyCartId().equals("")) {
                            String cart_id = jsonObject.getString("cart_id");
                            session.setKeyCartId(cart_id);
                            System.out.println("OUTPUT   CART ID FIRST TIME===" + session.getKeyCartId());

                            itemType = jsonObject.getString("item_type");
                        }

                        String categoryId = "1";
                        DialogKart dg = new DialogKart(TextileDetailActivity.this, false, itemType, categoryId);
                        dg.show();


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

    public void WebCallServiceAddCart() {
        SimpleProgressBar.showProgress(TextileDetailActivity.this);
        try {
            final String url = Contents.baseURL + "addCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            System.out.println("CART RESPONSE==" + response);


                            if (response != null) {

                                Log.e("stores response", response);
                                try {

                                    String itemType = "";
                                    if (session.getKeyCartId().equals("")) {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String cart_id = jsonObject.getString("cart_id");
                                        session.setKeyCartId(cart_id);
                                        System.out.println("OUTPUT   CART ID FIRST TIME===" + session.getKeyCartId());

                                        itemType = jsonObject.getString("item_type");
                                    }

                                    String categoryId = "1";
                                    DialogKart dg = new DialogKart(TextileDetailActivity.this, false, itemType, categoryId);
                                    dg.show();


                                } catch (Exception ex) {

                                }


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("Exception==" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("items", getItems().toString());
                    params.put("cart_id", session.getKeyCartId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal add carttailor", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(TextileDetailActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    public class DishDashaProductPagerAdapter extends PagerAdapter {
        Context context;
        String[] img;
        LayoutInflater layoutInflater;


        public DishDashaProductPagerAdapter(Context context, String[] img) {
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

            ImageView imageView = (ImageView) itemView.findViewById(R.id.zaara);

            System.out.println("IMAGE   OF PRODUCT ====" + img[position]);

            Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                   /* imageView1=imageView;
                    System.out.println("POSITION==="+position);
                    System.out.println();
*/
                    try {
                        showImageSingleDialog(img[position]);
                    } catch (Exception ex) {
                        Log.d("Error=", ex.fillInStackTrace().toString());

                    }

                    /* scaleGestureDetector = new ScaleGestureDetector(getApplicationContext(),new ScaleListener());*/

                }
            });

            Picasso.with(TextileDetailActivity.this).load(img[position]).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private void setUiPageViewController() {

        dotsCount = dishDashaProductPagerAdapter.getCount();
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

    // This adapter is used to controll the viewPager of Dialog

    public class DishDashaProductPagerAdapterForDialog extends PagerAdapter {
        Context context;
        String[] img;
        LayoutInflater layoutInflater;


        public DishDashaProductPagerAdapterForDialog(Context context, String[] img) {
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

            /*PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(imageView);
            photoAttacher.update();*/

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    System.out.println("You have clicked===" + position);
                    dialog.dismiss();
                    // Toast.makeText(context, "U have clicked=="+position, Toast.LENGTH_SHORT).show();

                }
            });

            Picasso.with(TextileDetailActivity.this).load(img[position]).into(imageView);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
