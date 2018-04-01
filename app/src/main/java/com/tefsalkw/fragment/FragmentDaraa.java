package com.tefsalkw.fragment;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.Models.DishdashaStylesResponse;
import com.tefsalkw.R;
import com.tefsalkw.adapter.DishdashaAdapter;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentDaraa extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;

    DishdashaAdapter dishdashaAdapter;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dishdasha, container, false);
        ButterKnife.bind(this, v);

        session = new SessionManager(getActivity());

        WebCallServiceStores();

        return v;
    }
    public void WebCallServiceStores() {
       // SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getAllMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                DishdashaStylesResponse mResponse = g.fromJson(response, DishdashaStylesResponse.class);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycler.setLayoutManager(layoutManager);
                                recycler.setItemAnimator(new DefaultItemAnimator());
                                dishdashaAdapter = new DishdashaAdapter(getActivity(),mResponse.getRecord(),"2");
                                recycler.setAdapter(dishdashaAdapter);

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("category", "2");
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
}