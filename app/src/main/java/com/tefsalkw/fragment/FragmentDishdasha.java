package com.tefsalkw.fragment;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.DishdashaAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.DishdashaStylesRecord;
import com.tefsalkw.models.DishdashaStylesResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentDishdasha extends BaseFragment implements DishdashaAdapter.OnDoneButtonListner {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;


    DishdashaAdapter dishdashaAdapter;
    SessionManager session;

    DishdashaAdapter.OnDoneButtonListner onDoneButtonListner;

    public static DishdashaStylesResponse mResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dishdasha_blank, container, false);
        // addNewStyle=(Button)v.findViewById(R.id.addnewBtn);
        ButterKnife.bind(this, v);

        onDoneButtonListner = this;

        session = new SessionManager(getActivity());

        System.out.println("Session id==" + session.getToken());
        System.out.println("Cust id==" + session.getCustomerId());


        WebCallServiceStores();

        return v;
    }

    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getAllMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                mResponse = g.fromJson(response, DishdashaStylesResponse.class);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycler.setLayoutManager(layoutManager);
                                recycler.setItemAnimator(new DefaultItemAnimator());

                                dishdashaAdapter = new DishdashaAdapter(getActivity(), mResponse.getRecord(), "1");
                                dishdashaAdapter.setOnDoneButtonListner(onDoneButtonListner);
                                recycler.setAdapter(dishdashaAdapter);


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

                    System.out.println("OUT PUT    access_token" + session.getToken());
                    System.out.println("OUT PUT    user_id" + session.getCustomerId());
                    System.out.println("OUT PUT    Category" + "1");

                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("category", "1");

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
            System.out.println("ERROR==" + surError);
            surError.printStackTrace();
        }
    }

    @Override
    public void onDone(DishdashaStylesRecord dishdashaStylesRecord) {

        WebCallServiceUpdateStyle(dishdashaStylesRecord);
    }


    public void WebCallServiceUpdateStyle(DishdashaStylesRecord dishdashaStylesRecord) {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "updateMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            session.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                WebCallServiceStores();
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


                    System.out.println("======waist" + dishdashaStylesRecord.getWaist());
                    System.out.println("======wrist" + dishdashaStylesRecord.getWrist());
                    System.out.println("======arm" + dishdashaStylesRecord.getArm());
                    System.out.println("======shoulder" + dishdashaStylesRecord.getShoulder());
                    System.out.println("======front_height" + dishdashaStylesRecord.getFront_height());
                    System.out.println("======back_height" + dishdashaStylesRecord.getBack_height());
                    System.out.println("======chest" + dishdashaStylesRecord.getChest());
                    System.out.println("======cufflink" + dishdashaStylesRecord.getCufflink());
                    System.out.println("======collar_buttons" + dishdashaStylesRecord.getCollar_buttons());
                    System.out.println("======neck" + dishdashaStylesRecord.getNeck());
                    System.out.println("======mobile_pocket" + dishdashaStylesRecord.getMobile_pocket());
                    System.out.println("======pen_pocket" + dishdashaStylesRecord.getPen_pocket());
                    System.out.println("======key_pocket" + dishdashaStylesRecord.getKey_pocket());
                    System.out.println("======buttons" + dishdashaStylesRecord.getButtons());
                    System.out.println("======name" + dishdashaStylesRecord.getName());
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("access_token", mSessionManager.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("wide", "1");
                    params.put("category", TefalApp.getInstance().getmCategory().toString());
                    params.put("narrow", "1");
                    params.put("waist", dishdashaStylesRecord.getWaist());
                    params.put("wrist", dishdashaStylesRecord.getWrist());
                    params.put("arm", dishdashaStylesRecord.getArm());
                    params.put("shoulder", dishdashaStylesRecord.getShoulder());
                    params.put("front_height", dishdashaStylesRecord.getFront_height());
                    params.put("back_height", dishdashaStylesRecord.getBack_height());
                    params.put("chest", dishdashaStylesRecord.getChest());
                    params.put("cufflink", dishdashaStylesRecord.getCufflink());
                    params.put("collar_buttons", dishdashaStylesRecord.getCollar_buttons());
                    params.put("neck", dishdashaStylesRecord.getNeck());
                    params.put("mobile_pocket", dishdashaStylesRecord.getMobile_pocket());
                    params.put("pen_pocket", dishdashaStylesRecord.getPen_pocket());
                    params.put("key_pocket", dishdashaStylesRecord.getKey_pocket());
                    params.put("buttons", dishdashaStylesRecord.getButtons());


                    params.put("name", dishdashaStylesRecord.getName());

                    params.put("id", dishdashaStylesRecord.getId());

                    params.put("collar_button_visibility", dishdashaStylesRecord.getCollar_button_visibility());
                    params.put("shirt_button_visibility", dishdashaStylesRecord.getShirt_button_visibility());
                    params.put("collar_buttons_push", dishdashaStylesRecord.getCollar_buttons_push());

                    //System.out.println("Button====="+)
                    Log.e("Tefsal tailor == ", url + params);

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


}
