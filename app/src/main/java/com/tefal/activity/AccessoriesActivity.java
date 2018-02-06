package com.tefal.activity;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.tefal.Models.AccessoriesResponse;
import com.tefal.Models.TextileProductResponse;
import com.tefal.R;
import com.tefal.adapter.AccessoriesAdapter;
import com.tefal.adapter.DishdashaTextileProductAdapter;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class AccessoriesActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.loading)
    ProgressBar loading;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.view_cart_btn)
    ImageView view_cart_btn;



    AccessoriesAdapter accessoriesAdapter;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_accessories);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar_title.setText("Accessories");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(getApplicationContext());

        view_cart_btn.setVisibility(View.VISIBLE);
        view_cart_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    startActivity(new Intent(AccessoriesActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        WebCallServiceStores();
    }
    public void WebCallServiceStores() {
        try {
            final String url = Contents.baseURL + "getAccessoriesSubCats";
            SimpleProgressBar.showProgress(AccessoriesActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                AccessoriesResponse mResponse = g.fromJson(response, AccessoriesResponse.class);

                                if (!mResponse.getStatus().equals("0")) {
                                    accessoriesAdapter = new AccessoriesAdapter(AccessoriesActivity.this, mResponse.getRecord());
                                    recycler.setAdapter(accessoriesAdapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AccessoriesActivity.this, 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                }
                                else
                                {
                                    Toast.makeText(AccessoriesActivity.this,mResponse.getMessage(),Toast.LENGTH_LONG).show();
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
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal accessories == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AccessoriesActivity.this);
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
