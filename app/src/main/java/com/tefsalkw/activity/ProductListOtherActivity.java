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
import com.tefsalkw.R;
import com.tefsalkw.fragment.SubCategoryFragment;
import com.tefsalkw.models.AccessoriesProductsResponse;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.DaraAbayaCategoriesModel;
import com.tefsalkw.models.DaraAbayaProductListResponse;
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
        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(ProductListOtherActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

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

        WebCallServiceStores();



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


        Log.e(ProductListOtherActivity.class.getSimpleName(), "onResume");

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


                                try {
                                    Log.e("stores response", response);

                                    JSONObject jsonObject = new JSONObject(response);

                                    if (jsonObject.getInt("status") == 1) {


                                        Gson g = new Gson();
                                        DaraAbayaProductListResponse mResponse = g.fromJson(response, DaraAbayaProductListResponse.class);


                                        if (mResponse.getRecord().getProducts() != null && mResponse.getRecord().getProducts().size() > 0) {

                                            viewPagerAdapter.addFragment(SubCategoryFragment.newInstance(mResponse.getRecord().getProducts(), store_id), "Default");


                                        }


                                        if (mResponse.getRecord().getCategories() != null) {

                                            for (int i = 0; i < mResponse.getRecord().getCategories().size(); i++) {

                                                DaraAbayaCategoriesModel daraAbayaCategoriesModel = mResponse.getRecord().getCategories().get(i);

                                                //  Log.e("getSub_category",daraAbayaCategoriesModel.getSub_category()+"");
                                                //SubCategoryFragment subCategoryFragment =     new SubCategoryFragment();
                                                // subCategoryFragment.setProducts(daraAbayaCategoriesModel.getProducts());

                                                viewPagerAdapter.addFragment(SubCategoryFragment.newInstance(daraAbayaCategoriesModel.getProducts(), store_id), daraAbayaCategoriesModel.getName());


                                            }

                                        } else {
                                            tabLayout.setVisibility(View.GONE);
                                        }


                                        viewPager.setAdapter(viewPagerAdapter);

                                        if (viewPager.getAdapter().getCount() <= 1) {
                                            tabLayout.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Toast.makeText(ProductListOtherActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception exc) {
                                    Toast.makeText(ProductListOtherActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
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




}