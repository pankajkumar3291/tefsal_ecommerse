package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
import com.google.gson.Gson;
import com.tefsalkw.Models.AccessoriesProductsResponse;
import com.tefsalkw.Models.BadgeRecordModel;
import com.tefsalkw.Models.DaraAbayaCategoriesModel;
import com.tefsalkw.Models.DaraAbayaProductListResponse;
import com.tefsalkw.R;
import com.tefsalkw.fragment.SubCategoryFragment;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListOtherActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;


    String store_id, flag;
    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;

    SessionManager session;

    //*************************** New Layout Components *******************************

    @BindView(R.id.tabSubCategory)
    TabLayout tabLayout;

    @BindView(R.id.viewPageSubCat)
    ViewPager viewPager;


    ViewPagerAdapter viewPagerAdapter;


    //*********************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_other);

        ButterKnife.bind(this);
        session = new SessionManager(ProductListOtherActivity.this);

        view_cart_btn.setVisibility(View.VISIBLE);
        store_id = getIntent().getStringExtra("store_id");
        flag = getIntent().getStringExtra("flag");

        setSupportActionBar(toolbar);
        //toolbar_title.setText(getIntent().getStringExtra("title"));
        toolbar_title.setText(getIntent().getStringExtra("store_name"));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initViews();


        if (!flag.equals("Accessories"))
            WebCallServiceStores();
        else {
            WebCallAccessoriesProducts();
        }


    }


    //*********************************** New Methods *********************************


    //region New Methods
    private void initViews() {

        // setupViewPager(viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < 3; i++) {
            viewPagerAdapter.addFragment(new SubCategoryFragment(), "ONE");
//            adapter.addFragment(new TwoFragment(), "TWO");
            viewPager.setAdapter(viewPagerAdapter);
        }
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    //endregion


    //*********************************** End Methods *********************************


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

    public void gotoCart(View v) {
        try {
            startActivity(new Intent(ProductListOtherActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(this);
        try {

            final String url = Contents.baseURL + "getDaraaAbayaProducts";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();

                                DaraAbayaProductListResponse mResponse = g.fromJson(response, DaraAbayaProductListResponse.class);

                                if (mResponse.getStatus().equalsIgnoreCase("1")) {

                                    for (int i = 0; i < mResponse.getRecord().getCategories().size(); i++) {

                                        DaraAbayaCategoriesModel daraAbayaCategoriesModel = mResponse.getRecord().getCategories().get(i);


                                        //  Log.e("getSub_category",daraAbayaCategoriesModel.getSub_category()+"");
                                        //SubCategoryFragment subCategoryFragment =     new SubCategoryFragment();
                                        // subCategoryFragment.setProducts(daraAbayaCategoriesModel.getProducts());


                                        if (daraAbayaCategoriesModel.getSub_category() != null) {

                                            viewPagerAdapter.addFragment(SubCategoryFragment.newInstance(daraAbayaCategoriesModel.getProducts(), store_id), daraAbayaCategoriesModel.getSub_category());
                                        } else {
                                            viewPagerAdapter.addFragment(SubCategoryFragment.newInstance(daraAbayaCategoriesModel.getProducts(), store_id), "Sub Category");

                                        }


                                    }


                                    viewPager.setAdapter(viewPagerAdapter);

                                    if (mResponse.getRecord().getCategories().size() == 1) {
                                        tabLayout.setVisibility(View.GONE);
                                    }
                                }

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
                    System.out.println("STORE ID==" + store_id);
//                    params.put("access_token", session.getToken());
                    params.put("store_id", store_id);
                    params.put("color", "");
                    params.put("country", "");
                    params.put("season", "");
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("category", flag);

                    Log.e("Tefsal store == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(ProductListOtherActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void WebCallAccessoriesProducts() {
        SimpleProgressBar.showProgress(ProductListOtherActivity.this);
        try {

            final String url = Contents.baseURL + "getAccessoriesProducts";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                AccessoriesProductsResponse mResponse = g.fromJson(response, AccessoriesProductsResponse.class);

                                if (mResponse.getStatus().equals("1")) {


                                } else {
                                    Toast.makeText(getApplicationContext(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }

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
//                    params.put("access_token", session.getToken());

                    System.out.println("ACCESSORY======= STORE==" + store_id);
                    //System.out.println("ACCESSORY======= STORE=="+store_id);
                    System.out.println("ACCESSORY======= SUBCAT==" + getIntent().getStringExtra("sub_cat"));


                    params.put("store_id", store_id);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    if (flag.equals("Accessories")) {
                        params.put("sub_cat_id", getIntent().getStringExtra("sub_cat"));
                        System.out.println("ACCESSORY SUBCAT==" + getIntent().getStringExtra("sub_cat"));
                    }
                    //System.out.println("Store ID=="+store_id+ "SubCategory ID=="+getIntent().getStringExtra("sub_cat"));

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


}