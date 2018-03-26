package com.tefal.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tefal.Models.DaraAbayaProductsModel;
import com.tefal.Models.ProductRecord;
import com.tefal.Models.ProductsResponse;
import com.tefal.R;
import com.tefal.activity.ProductListOtherActivity;
import com.tefal.adapter.AccessoriesProductAdapter;
import com.tefal.adapter.DishdashaTextileOtherProductAdapter;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SubCategoryFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    String store_id, flag;
    DishdashaTextileOtherProductAdapter dishdashaAdapter;


    SessionManager session;

    ArrayList<ProductRecord>  records;

    public SubCategoryFragment() {

    }



    public static Fragment newInstance(ArrayList<ProductRecord> records1,String store_id) {

        SubCategoryFragment subCategoryFragment =  new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString("store_id",store_id);
        args.putSerializable("ProductRecord",records1);
        subCategoryFragment.setArguments(args);
        return  subCategoryFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this,rootView);
        session = new SessionManager(getActivity());


        flag = getActivity().getIntent().getStringExtra("flag");


        records = (ArrayList<ProductRecord>)  getArguments().getSerializable("ProductRecord");
         Log.e("recordssize ",records.size()+"");
        store_id =  getArguments().getString("store_id");

        Log.e("store_id ",store_id);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dishdashaAdapter = new DishdashaTextileOtherProductAdapter(getActivity(), records, store_id);
        recycler.setAdapter(dishdashaAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(mLayoutManager);
        recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler.setItemAnimator(new DefaultItemAnimator());

    }


    //region GridList Methods

    public void WebCallServiceStores1() {
        SimpleProgressBar.showProgress(getActivity());
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
                                    dishdashaAdapter = new DishdashaTextileOtherProductAdapter(getActivity(), mResponse.getRecord(), store_id);
                                    recycler.setAdapter(dishdashaAdapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());

                                } else {
                                    Toast.makeText(getActivity(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
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

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void WebCallAccessoriesProducts() {
        SimpleProgressBar.showProgress(getActivity());
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
                                    AccessoriesProductAdapter adapter = new AccessoriesProductAdapter(getActivity(), mResponse.getRecord(), store_id);
                                    recycler.setAdapter(adapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                } else {
                                    Toast.makeText(getActivity(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
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
                    System.out.println("ACCESSORY======= SUBCAT==" + getActivity().getIntent().getStringExtra("sub_cat"));


                    params.put("store_id", store_id);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    if (flag.equals("Accessories")) {
                        params.put("sub_cat_id", getActivity().getIntent().getStringExtra("sub_cat"));
                        System.out.println("ACCESSORY SUBCAT==" + getActivity().getIntent().getStringExtra("sub_cat"));
                    }
                    //System.out.println("Store ID=="+store_id+ "SubCategory ID=="+getIntent().getStringExtra("sub_cat"));

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    //endregion

}
