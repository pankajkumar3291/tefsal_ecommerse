package com.tefal.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import com.tefal.Models.AccessoriesProductsResponse;
import com.tefal.Models.ProductsResponse;
import com.tefal.Models.TextileProductResponse;
import com.tefal.R;
import com.tefal.adapter.AccessoriesProductAdapter;
import com.tefal.adapter.DishdashaTextileOtherProductAdapter;
import com.tefal.adapter.DishdashaTextileProductAdapter;
import com.tefal.fragment.FragmentTextileProducts;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListOtherActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.loading)
    ProgressBar loading;

    /*@Bind(R.id.text_season)
    TextView text_season;

    @Bind(R.id.text_color)
    TextView text_color;

    @Bind(R.id.text_country)
    TextView text_country;*/

    PopupWindow colorWindow;
    String store_id,flag;
    @Bind(R.id.view_cart_btn)
    ImageView view_cart_btn;

    DishdashaTextileOtherProductAdapter dishdashaAdapter;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_other);

        ButterKnife.bind(this);
        view_cart_btn.setVisibility(View.VISIBLE);
        store_id =  getIntent().getStringExtra("store_id");
        flag =  getIntent().getStringExtra("flag");

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



       /* text_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.choose_color_panel, null);

                colorWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                colorWindow.setBackgroundDrawable(new BitmapDrawable());
                colorWindow.setOutsideTouchable(true);
                colorWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                colorWindow.showAsDropDown(v);
            }
        });*/

        session = new SessionManager(ProductListOtherActivity.this);

        if (!flag.equals("Accessories"))
        WebCallServiceStores();
        else
            WebCallAccessoriesProducts();

    }



    public  void gotoCart(View v)
    {
        try {
            startActivity(new Intent(ProductListOtherActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(ProductListOtherActivity.this);
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
                                ProductsResponse mResponse = g.fromJson(response, ProductsResponse.class);

                                if (!mResponse.getStatus().equals("0")) {
                                    dishdashaAdapter = new DishdashaTextileOtherProductAdapter(ProductListOtherActivity.this, mResponse.getRecord(),store_id);
                                    recycler.setAdapter(dishdashaAdapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
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
                    System.out.println("STORE ID=="+store_id);
//                    params.put("access_token", session.getToken());
                    params.put("store_id", store_id);
                    params.put("color", "");
                    params.put("country", "");
                    params.put("season", "");
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("category", flag);

                    Log.e("Tefsal store == ", url + params);

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

                                if (!mResponse.getStatus().equals("0")) {
                                    AccessoriesProductAdapter adapter = new AccessoriesProductAdapter(ProductListOtherActivity.this, mResponse.getRecord(),store_id);
                                    recycler.setAdapter(adapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
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

                    System.out.println("ACCESSORY======= STORE=="+store_id);
                    //System.out.println("ACCESSORY======= STORE=="+store_id);
                    System.out.println("ACCESSORY======= SUBCAT=="+getIntent().getStringExtra("sub_cat"));


                    params.put("store_id", store_id);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    if (flag.equals("Accessories"))
                    {
                    params.put("sub_cat_id", getIntent().getStringExtra("sub_cat"));
                        System.out.println("ACCESSORY SUBCAT=="+getIntent().getStringExtra("sub_cat"));
                    }
                    //System.out.println("Store ID=="+store_id+ "SubCategory ID=="+getIntent().getStringExtra("sub_cat"));

                    Log.e("Tefsal store == ", url + params);

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