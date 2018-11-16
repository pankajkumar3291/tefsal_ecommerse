package com.tefsalkw.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.adapter.BrandFilterAdapter;
import com.tefsalkw.adapter.CountryFilterAdapter;
import com.tefsalkw.adapter.DishdashaTextileProductAdapter;
import com.tefsalkw.adapter.PatternFilterAdapter;
import com.tefsalkw.adapter.ProductColorHorizontalDishdasha;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.fragment.FeelFragment;
import com.tefsalkw.fragment.MaterialFragment;
import com.tefsalkw.models.Colors;
import com.tefsalkw.models.ColorsRecordModel;
import com.tefsalkw.models.FilterBrandModel;
import com.tefsalkw.models.FilterCountryModel;
import com.tefsalkw.models.FilterPatternModel;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.models.dishdashaFiletrationResponse;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.PreferencesUtil;
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

    @BindView(R.id.linear1)
    RelativeLayout linear1;


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

    @BindView(R.id.subText)
    TextView subText;

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

    @BindView(R.id.text_price)
    TextView text_price;


    //private int meter=2;
    private float stock_meter = 0;
    private float price;

    //private int check_meter;
    private float min_meter = 3;

    float amount;

    public static float meter = 3;

    LinearLayout see_all_pattern_text_LL;

    RecyclerView recyclerViewCountry;
    RecyclerView recyclerViewBrand;
    RecyclerView recyclerViewPattern;


    public static String StoreID;
    public static int position;

    PopupWindow filterWindow;

    private boolean isExpanded = false;
    private boolean filterWindowFlag = true;


    dishdashaFiletrationResponse _mdishdashaFiletrationResponse;

    CountryFilterAdapter countryFilterAdapter;
    BrandFilterAdapter brandFilterAdapter;
    PatternFilterAdapter patternFilterAdapter;


    ArrayList<FilterCountryModel> filterCountryModelArrayList = new ArrayList<FilterCountryModel>();
    ArrayList<FilterPatternModel> filterPatternModelArrayList = new ArrayList<FilterPatternModel>();
    ArrayList<FilterBrandModel> filterBrandModelArrayList = new ArrayList<FilterBrandModel>();
    ArrayList<ColorsRecordModel> colorsRecordModelArrayList = new ArrayList<ColorsRecordModel>();

    ArrayList<TextileProductModel> textileProductModelArrayList = new ArrayList<TextileProductModel>();


    boolean country_flage = true;
    boolean brand_flage = true;
    boolean pattern_flage = true;


    SessionManager session;


    // Those member variable holds information about the color, subcolor, country, season, info-----

    private String colorString;
    private String subColorString;
    private String countryString;
    private String seasonString;

    private String brandString = "";
    private String patternString = "";

    private String[] product_image;
    //  private String[] str;
    private int dotsCount;

    private ImageView[] dots;


// This model object hold the data of the product from product list;

    TextileProductModel textileProductModel = new TextileProductModel();
    //colorWindowō
    /*
     * This dialog is used to show the image which can zoom in zoom out from view pager
     * */
    Dialog dialog;
    //SessionManager sessionManager;

    DefaultSliderView.OnSliderClickListener onSliderClickListener;
    DefaultSliderView textSliderView = null;
    ProductColorHorizontalDishdasha productColorHorizontalDishdasha;

    @BindView(R.id.colorRecyclerView)
    RecyclerView colorRecyclerView;

    Colors selectedColor = null;

    public static String feelString = "", materialString = "";


    @BindView(R.id.relSlide4)
    RelativeLayout relSlide4;

    @BindView(R.id.relSlide5)
    RelativeLayout relSlide5;


    @BindView(R.id.btnClose)
    Button btnClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textile_detail);


        try {
            ButterKnife.bind(this);

            session = new SessionManager(TextileDetailActivity.this);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            ic_filter.setVisibility(View.GONE);

            textileProductModel = (TextileProductModel) getIntent().getSerializableExtra("textileProductModel");

            Log.e("textileProductModel", new Gson().toJson(textileProductModel));

            if (textileProductModel != null) {
                productColorHorizontalDishdasha = new ProductColorHorizontalDishdasha(textileProductModel.getColors(), this);
                LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                colorRecyclerView.setLayoutManager(horizontalLayoutManagaer1);
                TefalApp.getInstance().setColorPosition(0);
                colorRecyclerView.setAdapter(productColorHorizontalDishdasha);


                if (session.getKeyLang().equals("Arabic")) {
                    TefalApp.getInstance().setStoreName(textileProductModel.getStore_name_arabic());
                } else {
                    TefalApp.getInstance().setStoreName(textileProductModel.getStore_name());
                }

            }


            String minMtr = TefalApp.getInstance().getMin_meters();
            if (session.getIsGuestId()) {
                minMtr = minMtr != null ? minMtr : "3";
                TefalApp.getInstance().setMin_meters("3");
            } else {
                minMtr = "3";
            }


            min_meter = Float.parseFloat(minMtr);

            meter = min_meter;
            meter_value.setText("" + meter);


            String styleName = TefalApp.getInstance().getStyleName();

            if (session.getKeyLang().equals("Arabic")) {

                styleName = styleName != null ? styleName : "Default";
                txt_min_meter.setText(min_meter + " = " + styleName);

            } else {
                styleName = styleName != null ? styleName : "Default";

                txt_min_meter.setText(styleName + " = " + min_meter);
            }


            StoreID = getIntent().getStringExtra("storeID");
            position = getIntent().getIntExtra("pos", 0);

            if (session.getKeyLang().equals("Arabic")) {
                toolbar_title.setText(textileProductModel.getDishdasha_product_name_arabic());
            } else {
                toolbar_title.setText(textileProductModel.getDishdasha_product_name());
            }


            View root = tabLayout.getChildAt(0);
            if (root instanceof LinearLayout) {
                ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(getResources().getColor(R.color.colorPrimaryDark));
                drawable.setSize(2, 1);
                ((LinearLayout) root).setDividerPadding(10);
                ((LinearLayout) root).setDividerDrawable(drawable);
            }


            if (textileProductModel == null) {

                RL_no_product_found_container.setVisibility(View.VISIBLE);
                RL_product_exist_container.setVisibility(View.GONE);
                return;

            } else {
                RL_no_product_found_container.setVisibility(View.GONE);
                RL_product_exist_container.setVisibility(View.VISIBLE);


                colorString = textileProductModel.getColor_id();
                seasonString = textileProductModel.getSeason().toLowerCase();
                countryString = textileProductModel.getCountry_id();
                subColorString = textileProductModel.getSub_color_id();
                brandString = textileProductModel.getBrand_id();
                patternString = textileProductModel.getPattern_id();


                TefalApp.getInstance().setColor(colorString);
                TefalApp.getInstance().setSeason(seasonString);
                TefalApp.getInstance().setSubColor(subColorString);


                TefalApp.getInstance().setCountry(countryString);
                TefalApp.getInstance().setBrand(brandString);
                TefalApp.getInstance().setPattern(patternString);
            }


            ic_filter.setVisibility(View.GONE);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


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
                    builder.setTitle(R.string.disclaimer)
                            .setMessage(R.string.textile_warning_text)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    dialog.cancel();
                                    WebCallServiceAddCartNew();


                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
                    ic_filter.setClickable(false);

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

                    ic_filter.setClickable(true);
                }
            });


            //Adding the tabs using addTab() method
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.feel)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.material)));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


            //Creating our pager adapter
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(FeelFragment.newInstance());
            adapter.addFragment(MaterialFragment.newInstance());


            //Adding adapter to pager
            viewPager.setAdapter(adapter);

            //Adding onTabSelectedListener to swipe views
            tabLayout.setOnTabSelectedListener(this);

            tabLayout.setupWithViewPager(viewPager);


            if (session.getKeyLang().equals("Arabic")) {

//                Log.e("CustomView","CustomView");
////
////                String fontPath2 = "fonts/GESSTwoMedium-Medium.otf";
////                Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
////
////                for (int i = 0; i < tabLayout.getTabCount(); i++) {
////                    //R.layout is the previous defined xml
////                    TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
////                    tv.setTypeface(tf2);
////                    tabLayout.getTabAt(i).setCustomView(tv);
////
////                }

            }
            initSlider();


            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    meter++;
                    //  Log.e("meter",meter+"");
                    if (meter > 0 && meter < stock_meter) {


                        amount = price * meter;

                        text_price.setText(String.format(new Locale("en"), "%.3f", amount));
                        meter_value.setText("" + meter);


                    }


                }
            });
            less_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (meter > min_meter) {
                        meter--;
                        amount = price * meter;
                        text_price.setText(String.format(new Locale("en"), "%.3f", amount));

                        meter_value.setText("" + meter);
                    }


                }
            });


            try {

                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
                float dpWidth = (displayMetrics.widthPixels / displayMetrics.density);


                int dpWidthInt = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());
                Log.e("dpWidthInt", dpWidthInt + "");
                RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpWidthInt);

                linear1.setLayoutParams(rel_btn);


            } catch (Exception exc) {

            }


            relSlide5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    PreferencesUtil.putBool(getApplicationContext(), "isFirstTimeLaunch5", false);
                    relSlide5.setVisibility(View.GONE);
                    relSlide4.setVisibility(View.VISIBLE);

                    return true;
                }
            });

            relSlide4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    PreferencesUtil.putBool(getApplicationContext(), "isFirstTimeLaunch4", false);

                    relSlide5.setVisibility(View.GONE);
                    relSlide4.setVisibility(View.GONE);

                    btn_back.setVisibility(View.VISIBLE);
                    btnClose.setVisibility(View.GONE);

                    return true;
                }
            });


            boolean isFirstTimeLaunch = PreferencesUtil.getBool(getApplicationContext(), "isFirstTimeLaunch5", true);
            if (isFirstTimeLaunch) {

                relSlide5.setVisibility(View.VISIBLE);
                relSlide4.setVisibility(View.GONE);

                btn_back.setVisibility(View.GONE);
                btnClose.setVisibility(View.VISIBLE);


            } else {

                relSlide5.setVisibility(View.GONE);
                relSlide4.setVisibility(View.GONE);

                btn_back.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.GONE);

            }


            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean isFirstTimeLaunch = PreferencesUtil.getBool(getApplicationContext(), "isFirstTimeLaunch5", true);

                    if (isFirstTimeLaunch) {

                        PreferencesUtil.putBool(getApplicationContext(), "isFirstTimeLaunch5", false);
                        relSlide5.setVisibility(View.GONE);
                        relSlide4.setVisibility(View.VISIBLE);

                    } else {
                        boolean isFirstTimeLaunch4 = PreferencesUtil.getBool(getApplicationContext(), "isFirstTimeLaunch4", true);

                        if (isFirstTimeLaunch4) {
                            PreferencesUtil.putBool(getApplicationContext(), "isFirstTimeLaunch4", false);

                            relSlide5.setVisibility(View.GONE);
                            relSlide4.setVisibility(View.GONE);

                            btn_back.setVisibility(View.VISIBLE);
                            btnClose.setVisibility(View.GONE);
                        }

                    }


                }
            });


        } catch (Exception exc) {

        }


    }

    private void changeTabsFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    AssetManager mgr = getAssets();
                    Typeface tf = Typeface.createFromAsset(mgr, "fonts/GESSTwoBold-Bold.otf");//Font file in /assets
                    ((TextView) tabViewChild).setTypeface(tf);
                }
            }
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getString(R.string.feel) : getString(R.string.material);
        }


    }


    public void showSizeOnColorSelection(Colors colors) {

        this.selectedColor = colors;


        if (selectedColor != null) {

            try {

                price = Float.parseFloat(colors.getPrice() != null ? colors.getPrice() : "0");

                stock_meter = Float.parseFloat(colors.getStock_in_meters());


                text_price.setText(String.format(new Locale("en"), "%.3f", price * meter));

                txt_price.setText(price + "");

            } catch (Exception ex) {

                System.out.println("Error ===========1" + ex);
            }


            if (min_meter > stock_meter) {

                LL_min_max_controller.setVisibility(View.GONE);
                add_to_cart_lbl.setText(R.string.sold_out);
                text_price.setVisibility(View.GONE);
                add_to_cart_btn.setEnabled(false);
            } else {
                LL_min_max_controller.setVisibility(View.VISIBLE);
                add_to_cart_lbl.setText(R.string.textile_detail_add_to_cart_lbl_text);
                text_price.setVisibility(View.VISIBLE);
                add_to_cart_btn.setEnabled(true);
            }


            if (session.getKeyLang().equals("Arabic")) {
                feelString = selectedColor.getFeel_arabic();
                feelString = feelString != null ? feelString : "";
                materialString = selectedColor.getMaterial_arabic();
                materialString = materialString != null ? materialString : "";
                subText.setText(selectedColor.getPattern_name_arabic() != null ? selectedColor.getPattern_name_arabic() : "");
            } else {
                feelString = selectedColor.getFeel();
                feelString = feelString != null ? feelString : "";
                materialString = selectedColor.getMaterial();
                materialString = materialString != null ? materialString : "";
                subText.setText(selectedColor.getPattern_name() != null ? selectedColor.getPattern_name() : "");
            }


            subText.setVisibility(View.VISIBLE);


            //Creating our pager adapter
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(FeelFragment.newInstance());
            adapter.addFragment(MaterialFragment.newInstance());


            //Adding adapter to pager
            viewPager.setAdapter(adapter);

            if (viewPager.getAdapter() != null) {
                viewPager.getAdapter().notifyDataSetChanged();
            }

            if (selectedColor.getProduct_image() != null && selectedColor.getProduct_image().length <= 1) {
                product_image_viewPager.stopAutoCycle();
            } else {
                product_image_viewPager.startAutoCycle();
            }


            product_image_viewPager.removeAllSliders();
            if (selectedColor.getProduct_image() != null) {
                product_image = selectedColor.getProduct_image();
                for (String imgUrl : selectedColor.getProduct_image()) {

                    DefaultSliderView textSliderView = new DefaultSliderView(TextileDetailActivity.this);
                    textSliderView.setOnSliderClickListener(onSliderClickListener);
                    textSliderView
                            .image(imgUrl)
                            .setScaleType(BaseSliderView.ScaleType.Fit);


                    product_image_viewPager.addSlider(textSliderView);

                }
            }


        }


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


    }

    public JSONArray getItems() {
        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();

        try {

            obj.put("category_id", "1");
            obj.put("product_id", DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getTefsal_product_id());
            obj.put("item_id", selectedColor.getAttribute_id());

            JSONObject item_details = new JSONObject();

            item_details.put("item_quantity", meter_value.getText());
            obj.put("item_details", item_details);
            arry.put(obj);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        LinearLayout llOutside = (LinearLayout) popupView.findViewById(R.id.llOutside);
        llOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filterWindow != null) {
                    filterWindow.dismiss();
                }
            }
        });

        //this.filterWindowFlag=false

        filterWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        filterWindow.setBackgroundDrawable(new BitmapDrawable());
        filterWindow.setOutsideTouchable(false);
        filterWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                done_txt.setVisibility(View.GONE);
                ic_filter.setVisibility(View.VISIBLE);
                ic_filter.setClickable(true);
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


        //------------------------This is for Brand list-------------------------------------------------------------------

        if (filterBrandModelArrayList.size() != 0) {
            brandFilterAdapter = new BrandFilterAdapter(filterBrandModelArrayList, TextileDetailActivity.this, filterBrandModelArrayList.size() <= 3 ? filterBrandModelArrayList.size() : 3);
            RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(TextileDetailActivity.this, 3);
            recyclerViewBrand.setLayoutManager(mLayoutManager2);
            recyclerViewBrand.setItemAnimator(new DefaultItemAnimator());
            recyclerViewBrand.setAdapter(brandFilterAdapter);
        }


        //--------------------This is for Pattern List------------------------------------------------------------------------

        if (filterPatternModelArrayList.size() != 0) {
            patternFilterAdapter = new PatternFilterAdapter(filterPatternModelArrayList, TextileDetailActivity.this, filterPatternModelArrayList.size() <= 3 ? filterPatternModelArrayList.size() : 3);
            RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(TextileDetailActivity.this, 3);
            recyclerViewPattern.setLayoutManager(mLayoutManager3);
            recyclerViewPattern.setItemAnimator(new DefaultItemAnimator());
            recyclerViewPattern.setAdapter(patternFilterAdapter);
        }


        see_all_brand_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (brandFilterAdapter != null) {
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

            }
        });
        see_all_pattern_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (patternFilterAdapter != null) {
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

            }
        });

        see_all_country_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countryFilterAdapter != null) {
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

            }
        });


        if (filterWindow != null) {
            filterWindow.showAsDropDown(v);
        }


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

                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("store_id", TefalApp.getInstance().getStoreId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("season", seasonString != null ? seasonString : "");
                    params.put("color_id", colorString != null ? colorString : "");
                    params.put("sub_color_id", subColorString != null ? subColorString : "");
                    params.put("country_id", countryString != null ? countryString : "");
                    params.put("brand_id", brandString != null ? brandString : "");
                    params.put("pattern_id", "");


                    Log.e("Tefsal store == ", url + new JSONObject(params));

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


                                        textileProductModelArrayList = _mdishdashaFiletrationResponse.getProducts();

                                        if (textileProductModelArrayList == null) {
                                            textileProductModel = null;
                                        } else {
                                            textileProductModel = textileProductModelArrayList.get(0);
                                            startActivity(new Intent(TextileDetailActivity.this, TextileDetailActivity.class).putExtra("textileProductModel", textileProductModel));
                                            finish();
                                        }


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

                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("store_id", TefalApp.getInstance().getStoreId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("season", seasonString != null ? seasonString : "");
                    params.put("color_id", colorString != null ? colorString : "");
                    params.put("sub_color_id", subColorString != null ? subColorString : "");
                    params.put("country_id", countryString != null ? countryString : "");
                    params.put("brand_id", TefalApp.getInstance().getBrand() != null ? TefalApp.getInstance().getBrand() : "");
                    params.put("pattern_id", TefalApp.getInstance().getPattern() != null ? TefalApp.getInstance().getPattern() : "");


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


    public void showImageSingleDialog(String image_url) {
        dialog = new Dialog(TextileDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_show_dialog);
        dialog.setCanceledOnTouchOutside(true);


        ViewPager dialog_viewPager = (ViewPager) dialog.findViewById(R.id.dialog_viewPager);

        //==========================================================================
        if (product_image != null && product_image.length != 0) {

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
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();


        }


    }


    public void WebCallServiceAddCartNew() {


//        String styleId = TefalApp.getInstance().getStyleId();
//
//        if (styleId == null) {
//            Toast.makeText(this, R.string.create_stlye_warning, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        if (styleId.equals("")) {
//            Toast.makeText(this, R.string.create_stlye_warning, Toast.LENGTH_SHORT).show();
//            return;
//        }


        final String url = Contents.baseURL + "addCart";


        SimpleProgressBar.showProgress(this);

        JSONObject params = new JSONObject();


        try {
            if (session.getIsGuestId()) {
                params.put("unique_id", session.getCustomerId());
            } else {
                params.put("user_id", session.getCustomerId());
                params.put("access_token", session.getToken());
            }
            params.put("style_id", TefalApp.getInstance().getStyleId());
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

                        String cart_id = jsonObject.getString("cart_id");
                        session.setKeyCartId(cart_id);
                        System.out.println("OUTPUT   CART ID FIRST TIME===" + session.getKeyCartId());
                        String itemType = jsonObject.getString("item_type");

                        String categoryId = "1";
                        DialogKart dg = new DialogKart(TextileDetailActivity.this, false, itemType, categoryId);
                        dg.show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(TextileDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        SimpleProgressBar.closeProgress();
                    }


                } catch (Exception e1) {
                    Toast.makeText(TextileDetailActivity.this, e1.getMessage(), Toast.LENGTH_SHORT).show();
                    e1.printStackTrace();
                    SimpleProgressBar.closeProgress();
                }


            }
        });


    }


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
            //   Picasso.with(context).load(img[position]).error(R.drawable.placeholder_no_image).placeholder(R.drawable.placeholder_image_loading).into(imageView);

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

            // Picasso.with(context).load(img[position]).into(imageView);

            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(context).load(img[position]).apply(options).into(imageView);


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


    @Override
    public void onBackPressed() {

        //TefalApp.getInstance().setFromPush("no");


        super.onBackPressed();
    }
}
