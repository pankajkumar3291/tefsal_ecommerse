package com.tefsalkw.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import com.tefsalkw.adapter.AccessoriesProductAdapter;
import com.tefsalkw.adapter.DishdashaTextileOtherProductAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.AccessoriesListResponseNew;
import com.tefsalkw.models.AccessoriesModelNew;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.models.ColorsNew;
import com.tefsalkw.models.SizesNew;
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

public class AccessoriesStoreListingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;


    PopupWindow colorWindow;
    String sub_cat, flag;
    @BindView(R.id.view_cart_btn)
    RelativeLayout view_cart_btn;

    @BindView(R.id.total_badge_txt)
    TextView total_badge_txt;


    DishdashaTextileOtherProductAdapter dishdashaAdapter;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories_store_listing);

        ButterKnife.bind(this);


        view_cart_btn.setVisibility(View.VISIBLE);
        view_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(AccessoriesStoreListingActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        sub_cat = getIntent().getStringExtra("sub_cat");
        flag = getIntent().getStringExtra("flag");


        setSupportActionBar(toolbar);
        //toolbar_title.setText(getIntent().getStringExtra("title"));
        toolbar_title.setText(TefalApp.getInstance().getToolbar_title());
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        session = new SessionManager(AccessoriesStoreListingActivity.this);

        WebCallAccessoriesProducts();


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


    public void gotoCart(View v) {
        try {
            startActivity(new Intent(AccessoriesStoreListingActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void WebCallAccessoriesProducts() {
        SimpleProgressBar.showProgress(AccessoriesStoreListingActivity.this);
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
                                AccessoriesListResponseNew mResponse = g.fromJson(response, AccessoriesListResponseNew.class);

                                if (!mResponse.getStatus().equals("0") && mResponse.getRecord() != null) {


                                    List<AccessoriesRecord> accessoriesRecordList = new ArrayList<>();

                                    for (AccessoriesModelNew accessoriesModelNew : mResponse.getRecord()) {

                                        AccessoriesRecord accessoriesRecord = new AccessoriesRecord();
                                        accessoriesRecord.setTefsal_product_id(accessoriesModelNew.getTefsal_product_id());
                                        accessoriesRecord.setStore_id(accessoriesModelNew.getStore_id());

                                        accessoriesRecord.setBrandName(accessoriesModelNew.getBrand_name());
                                        accessoriesRecord.setProductName(accessoriesModelNew.getProduct_name());
                                        accessoriesRecord.setMax_delivery_days(accessoriesModelNew.getMax_delivery_days());
                                        accessoriesRecord.setProduct_discount(accessoriesModelNew.getProduct_discount());

                                        List<SizesNew> sizesNew = accessoriesModelNew.getSizes();

                                        if (sizesNew != null && sizesNew.size() > 0) {

                                            ColorsNew colorsNew = (ColorsNew)sizesNew.get(0).getColors();
                                            accessoriesRecord.setAccessory_product_image(colorsNew.getImages());

                                        }


                                        accessoriesRecordList.add(accessoriesRecord);


                                    }

                                    AccessoriesProductAdapter adapter = new AccessoriesProductAdapter(AccessoriesStoreListingActivity.this, accessoriesRecordList, sub_cat);
                                    recycler.setAdapter(adapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());
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

                    System.out.println("ACCESSORY======= STORE==" + sub_cat);
                    //System.out.println("ACCESSORY======= STORE=="+store_id);
                    System.out.println("ACCESSORY======= SUBCAT==" + getIntent().getStringExtra("sub_cat"));


                    // params.put("store_id", sub_cat);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("sub_cat_id", sub_cat);

                    Log.e("Tefsal store == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AccessoriesStoreListingActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
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
}