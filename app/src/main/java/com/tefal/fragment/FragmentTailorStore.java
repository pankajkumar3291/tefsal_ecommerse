package com.tefal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefal.Models.GetCartRecord;
import com.tefal.Models.GetCartResponse;
import com.tefal.Models.SublistCartItems;
import com.tefal.Models.TailorProductModal;
import com.tefal.Models.TailorProductResponse;
import com.tefal.Models.TailorStoresResponseModel;
import com.tefal.Models.TextileStoresResponseModel;
import com.tefal.R;
import com.tefal.adapter.DishdashaTailorAdapter;
import com.tefal.adapter.DishdashaTailorItemsAdapter;
import com.tefal.adapter.DishdashaTextileAdapter;
import com.tefal.adapter.SublistAdapter;
import com.tefal.adapter.TailorCartItemsAdapter;
import com.tefal.dialogs.DialogKartDropdown;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentTailorStore extends Fragment implements DishdashaTailorItemsAdapter.OnAddItemClickListener {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.recycler2)
    RecyclerView recycler2;

    @BindView(R.id.loading)
    ProgressBar loading;

    TailorCartItemsAdapter dishdashaAdapter;
    DishdashaTailorAdapter dishdashaTailorAdapter1;

    DishdashaTailorItemsAdapter dishdashaTailorAdapter;
    private DishdashaTailorItemsAdapter.OnAddItemClickListener onAddItemClickListener;

    SessionManager session;

    String flag;
    List<GetCartRecord> storeModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_abaya, container, false);
        ButterKnife.bind(this, v);
        onAddItemClickListener = this;

        session = new SessionManager(getActivity());
        WebCallServiceStoresCart();
        WebCallServiceStores();

        flag = getArguments().getString("flag");
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void WebCallServiceStores() {
        //SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getDishdashaTailorProducts";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("getDishdashaTailorProducts response", response);
                                Gson g = new Gson();
                                TailorProductResponse mResponse = g.fromJson(response, TailorProductResponse.class);

                                if (mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler2.setLayoutManager(layoutManager);
                                    recycler2.setItemAnimator(new DefaultItemAnimator());
                                    dishdashaTailorAdapter = new DishdashaTailorItemsAdapter(FragmentTailorStore.this, mResponse.getRecord());
                                    dishdashaTailorAdapter.setOnAddItemClickListener(onAddItemClickListener);
                                    recycler2.setAdapter(dishdashaTailorAdapter);
                                } else {
                                    Toast.makeText(getActivity(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("sub_category", "5");

                    if (flag.equals("dish"))
                        params.put("category", "1");
                    else if (flag.equals("Daraa"))
                        params.put("category", "3");
                    else if (flag.equals("Abaya"))
                        params.put("category", "2");
                    else if (flag.equals("Accessories"))
                        params.put("category", "4");

                    Log.e("Tefsal store == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            // SimpleProgressBar.closeProgress();
        }
    }


    public void WebCallServiceStoresCart() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("getCart response", response);
                                Gson g = new Gson();
                                GetCartResponse mResponse = g.fromJson(response, GetCartResponse.class);

                                if (mResponse.getStatus().equals("1")) {

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    storeModels = mResponse.getRecord();
                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    dishdashaAdapter = new TailorCartItemsAdapter(getActivity(), mResponse.getRecord());

                                    recycler.setAdapter(dishdashaAdapter);

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
                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("user_id", session.getCustomerId());


                    Log.e("Tefsal getCart == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            SimpleProgressBar.closeProgress();
        }
    }

    @Override
    public void onAdd(TailorProductModal tailorProductModal) {

        DialogKartDropdown dg = new DialogKartDropdown(storeModels, FragmentTailorStore.this,tailorProductModal.getPosition());
        dg.show();

    }


    public void addItemToTailorItem(GetCartRecord cartRecord,int position,String dropdownId) {

       // Log.e("dropdownId1",dropdownId);
        SublistCartItems sublistCartItems = new SublistCartItems();
        sublistCartItems.setItemName(cartRecord.getDishdasha_material());
        sublistCartItems.setProductId(cartRecord.getItem_id());

        dishdashaTailorAdapter.addSublistCartItem(position,sublistCartItems);

        dishdashaTailorAdapter.notifyDataSetChanged();

        dishdashaAdapter.updateDishDashacount(dropdownId);


    }


    public void removeDishDashaCount(String dropdownId)
    {
        dishdashaAdapter.updateDishDashacountRemove(dropdownId);
    }


    public void WebCallServiceStoresTailor() {
        // SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getStores";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                TailorStoresResponseModel mResponse = g.fromJson(response, TailorStoresResponseModel.class);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


                                recycler.setLayoutManager(layoutManager);
                                recycler.setItemAnimator(new DefaultItemAnimator());
                                dishdashaTailorAdapter1 = new DishdashaTailorAdapter(getActivity(), mResponse.getRecord(),flag);
                                recycler.setAdapter(dishdashaTailorAdapter1);

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("sub_category", "6");

                    if (flag.equals("dish"))
                        params.put("category", "1");
                    else if (flag.equals("Daraa"))
                        params.put("category", "3");
                    else if (flag.equals("Abaya"))
                        params.put("category", "2");
                    else if (flag.equals("Accessories"))
                        params.put("category", "4");

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
            // SimpleProgressBar.closeProgress();
        }
    }


}
