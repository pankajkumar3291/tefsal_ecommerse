package com.tefsalkw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.Models.TextileStoresResponseModel;
import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.adapter.DishdashaTextileAdapter;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentTextileStore extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;

    DishdashaTextileAdapter dishdashaAdapter;
    SessionManager session;

    String flag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daraa, container, false);
        ButterKnife.bind(this, v);


        session = new SessionManager(getActivity());
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
        // SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getStores";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //  SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e(FragmentTextileStore.class.getSimpleName(), response);
                                Gson g = new Gson();
                                TextileStoresResponseModel mResponse = g.fromJson(response, TextileStoresResponseModel.class);


                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycler.setLayoutManager(layoutManager);
                                recycler.setItemAnimator(new DefaultItemAnimator());
                                dishdashaAdapter = new DishdashaTextileAdapter(getActivity(), mResponse.getRecord(), flag);
                                recycler.setAdapter(dishdashaAdapter);

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
            // SimpleProgressBar.closeProgress();
        }
    }
}
