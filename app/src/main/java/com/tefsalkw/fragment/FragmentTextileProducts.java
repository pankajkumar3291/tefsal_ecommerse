package com.tefsalkw.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.adapter.DishdashaTextileProductAdapter;
import com.tefsalkw.adapter.FilterColorListAdapter;
import com.tefsalkw.adapter.FilterCountryListAdapter;
import com.tefsalkw.adapter.SeasonFilterAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.ColorRecordFromDishdashaFilteration;
import com.tefsalkw.models.CountryRecordModel;
import com.tefsalkw.models.ProductCountryRecordModel;
import com.tefsalkw.models.ProductCountryResponseModel;
import com.tefsalkw.models.SeasonResponseModel;
import com.tefsalkw.models.SeasonsList;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.models.TextileProductResponse;
import com.tefsalkw.models.dishdashaFiletrationResponse;
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

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentTextileProducts extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;

    DishdashaTextileProductAdapter dishdashaAdapter;
    SessionManager session;

    @BindView(R.id.text_season)
    TextView text_season;

    @BindView(R.id.text_color)
    TextView text_color;

    @BindView(R.id.text_country)
    TextView text_country;

    @BindView(R.id.textNoProduct)
    TextView textNoProduct;

    public static PopupWindow colorWindow;
    public static PopupWindow seasonWindow;
    public static PopupWindow countryWindow;

    String store_id, flag;

    ArrayList<CountryRecordModel> countryRecordModelArrayList = new ArrayList<CountryRecordModel>();
    private ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList = new ArrayList<ColorRecordFromDishdashaFilteration>();
    private List<TextileProductModel> textileProductModelList = new ArrayList<TextileProductModel>();
    private ArrayList<ProductCountryRecordModel> productCountryRecordModelArrayList = new ArrayList<ProductCountryRecordModel>();
    SeasonResponseModel seasonResponseModel;
    String[] record;


    String color = "";
    String season = "";
    String country = "";
    String sub_color = "";


    public static AlertDialog alertDialog;
    public static View popupSeasonView;

    public static View popupCountryView;

    public static View getPopupColorView;

    public static RecyclerView filterColorRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_textile, container, false);
        ButterKnife.bind(this, v);

        text_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (colorsRecordModelArrayList == null) {
                        return;
                    }

                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    getPopupColorView = layoutInflater.inflate(R.layout.choose_color_panel, null);


                    FilterColorListAdapter filterColorListAdapter = new FilterColorListAdapter(colorsRecordModelArrayList, getActivity());

                    filterColorRecyclerView = (RecyclerView) getPopupColorView.findViewById(R.id.recycler_view);

                    RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 3);
                    filterColorRecyclerView.setLayoutManager(mLayoutManager2);
                    filterColorRecyclerView.setAdapter(filterColorListAdapter);


                    colorWindow = new PopupWindow(
                            getPopupColorView,
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

                    if (alertDialog != null && alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    colorWindow.showAsDropDown(v);

                } catch (Exception exc) {

                }

            }
        });

        text_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (productCountryRecordModelArrayList == null) {
                    return;
                }


                LayoutInflater LayoutInflater = getActivity().getLayoutInflater();
                popupCountryView = LayoutInflater.inflate(R.layout.choose_country_panel, null);
                RecyclerView recyclerViewCountry = (RecyclerView) popupCountryView.findViewById(R.id.recyclerViewCountry);

                FilterCountryListAdapter filterCountryListAdapter = new FilterCountryListAdapter(productCountryRecordModelArrayList, getActivity());
                RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 3);
                recyclerViewCountry.setLayoutManager(mLayoutManager2);
                recyclerViewCountry.setAdapter(filterCountryListAdapter);


                // FilterCountryListAdapter filterCountryListAdapter=new

                countryWindow = new PopupWindow(
                        popupCountryView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                countryWindow.setBackgroundDrawable(new BitmapDrawable());
                countryWindow.setOutsideTouchable(true);
                countryWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                countryWindow.showAsDropDown(v);
            }
        });

        text_season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (record == null) {
                    return;
                }


                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                popupSeasonView = layoutInflater.inflate(R.layout.choose_season_panel, null);

                List<SeasonsList> seasonList = new ArrayList<>();


                for (String season : record) {

                    SeasonsList seasonModel = new SeasonsList();
                    seasonModel.setName(season);

                    if (season.toLowerCase().contains("winter")) {
                        seasonModel.setImage(R.drawable.winter);
                    }

                    if (season.toLowerCase().contains("summer")) {
                        seasonModel.setImage(R.drawable.summer);
                    }

                    if (season.toLowerCase().contains("autumn")) {
                        seasonModel.setImage(R.drawable.autumn);
                    }

                    if (season.toLowerCase().contains("spring")) {
                        seasonModel.setImage(R.drawable.spring);
                    }

                    seasonList.add(seasonModel);


                }

                SeasonFilterAdapter seasonFilterAdapter = new SeasonFilterAdapter(seasonList, getActivity(), seasonList.size());

                RecyclerView recyclerViewSeason = (RecyclerView) popupSeasonView.findViewById(R.id.recyclerViewSeason);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);

                //LayoutManager(getActivity(), 3);
                recyclerViewSeason.setLayoutManager(mLayoutManager);
                recyclerViewSeason.setAdapter(seasonFilterAdapter);

                seasonWindow = new PopupWindow(
                        popupSeasonView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                seasonWindow.setBackgroundDrawable(new BitmapDrawable());
                seasonWindow.setOutsideTouchable(true);
                seasonWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });

                seasonWindow.showAsDropDown(v);

            }
        });

        session = new SessionManager(getActivity());


        store_id = getArguments().getString("store_id");
        flag = getArguments().getString("flag");


        System.out.println("TEST  STORE ID FROM ARG===" + store_id);
        System.out.println("TEST  STORE ID FROM SINGLETON===" + TefalApp.getInstance().getStoreId());

        color = TefalApp.getInstance().getColor();
        season = TefalApp.getInstance().getSeason();
        country = TefalApp.getInstance().getCountry();
        sub_color = TefalApp.getInstance().getSubColor();

        System.out.println("HELLO FROM START");
        System.out.println("HELLO COLOR==" + color);
        System.out.println("HELLO SUB COLOR==" + sub_color);
        System.out.println("HELLO SEAON==" + season);
        System.out.println("HELLO COUNTRY==" + country);
        System.out.println("HELLO FLAG==" + TefalApp.getInstance().getFlage());
        System.out.println("HELLO STORE ID==" + TefalApp.getInstance().getStoreId());

        WebCallServiceStores();


        // ------If you dont want to select multiple filter selection option remove the code------

       /* TefalApp.getInstance().setColor("");
        TefalApp.getInstance().setSeason("");
        TefalApp.getInstance().setCountry("");*/


        return v;
    }

    public void restartActivity() {
        Intent intent = new Intent(getActivity(), DishDashaProductActivity.class);
        /*intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
        intent.putExtra("flag",TefalApp.getInstance().getFlage());
        intent.putExtra("store_name",TefalApp.getInstance().getStoreName());*/
        getActivity().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        getActivity().finish();
    }

    public void showCountryList(View v) {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getDishdashaTextileProducts";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                TextileProductResponse mResponse = g.fromJson(response, TextileProductResponse.class);

                                if (!mResponse.getStatus().equals("0")) {

                                    textileProductModelList = mResponse.getRecord();
                                    dishdashaAdapter = new DishdashaTextileProductAdapter(getActivity(), textileProductModelList, store_id, flag);
                                    recycler.setAdapter(dishdashaAdapter);

                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                                    recycler.setLayoutManager(mLayoutManager);
                                    recycler.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
                                    recycler.setItemAnimator(new DefaultItemAnimator());

                                    // This is for new search request
                                    textNoProduct.setVisibility(View.GONE);
                                    if (recycler.getVisibility() == View.GONE) {
                                        recycler.setVisibility(View.VISIBLE);
                                    }

                                    // if(textileProductModelList.size()==0)


                                } else {
                                    // Toast.makeText(getActivity(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                            httpGetCountryCall();
                            httpGetColorCall();
                            httpGetFilterSeasonData();
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
                    params.put("store_id", store_id);

                    System.out.println("STORE ID===" + store_id);


                    System.out.println("Fragment Textile parameter HELLO COLOR==" + color);
                    System.out.println("Fragment Textile parameter HELLO SUB COLOR==" + sub_color);
                    System.out.println("Fragment Textile parameter HELLO SEAON==" + season);
                    System.out.println("Fragment Textile parameter HELLO COUNTRY==" + country);


                    params.put("color", color);
                    params.put("sub_color", sub_color);
                    params.put("country", country);
                    params.put("season", season);
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
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
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


                            //SimpleProgressBar.closeProgress();

                            //System.out.println("SEASONS RESPONSE==="+response);


                            if (response != null) {
                                try {
                                    JSONObject object = new JSONObject(response);

                                    Log.e(FragmentTextileProducts.class.getSimpleName(), String.valueOf(object));
                                    String status = object.getString("status");

                                    if (status.equals("1")) {
                                        // SimpleProgressBar.closeProgress();
                                        Gson g = new Gson();
                                        seasonResponseModel = g.fromJson(response, SeasonResponseModel.class);
                                        record = seasonResponseModel.getRecord();

                                        // This is for new search request
                                        textNoProduct.setVisibility(View.GONE);


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

                            //System.out.println("ERROR==="+error);
                            // SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            System.out.println("ERROR===" + surError);

            surError.printStackTrace();
        }
    }

    public void httpGetCountryCall() {
        //SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getProductCountries";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE OF COUNTRY===" + response);

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                ProductCountryResponseModel mResponse = g.fromJson(response, ProductCountryResponseModel.class);
                                if (mResponse.getStatus().equals("1")) {
                                    productCountryRecordModelArrayList = mResponse.getRecord();

                                    System.out.println("SIZE==============" + productCountryRecordModelArrayList.size());
                                    // This is for new search request
                                    textNoProduct.setVisibility(View.GONE);


                                } else {

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
                    params.put("store_id", store_id);

                    System.out.println("STORE ID===" + store_id);

                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e(FragmentTextileProducts.class.getSimpleName(), url + new JSONObject(params));

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

    public void httpGetColorCall() {
        try {
            final String url = Contents.baseURL + "dishdashaFiletration";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE OF COUNTRY===" + response);

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                dishdashaFiletrationResponse mResponse = g.fromJson(response, dishdashaFiletrationResponse.class);
                                if (mResponse.getStatus().equals("1")) {
                                    colorsRecordModelArrayList = mResponse.getColors();

                                    if (textileProductModelList.size() == 0) {
                                        textNoProduct.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.GONE);
                                    }


                                    System.out.println("RESPONSE SIZE==" + colorsRecordModelArrayList.size());

                                    // httpGetFilterSeasonData();

                                } else {

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


                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("store_id", TefalApp.getInstance().getStoreId());

                    System.out.println("STORE ID FOR DIAHSADSHA FITERATION====" + TefalApp.getInstance().getStoreId());

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
    /**
     * Converting dp to pixel
     */
   /* private int dpToPx(int dp) {
        Resources r = getActivity().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }*/
}
