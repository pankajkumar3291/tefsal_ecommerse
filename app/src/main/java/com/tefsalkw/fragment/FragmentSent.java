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
import com.tefsalkw.adapter.SentMailAdapter;
import com.tefsalkw.models.SentMailsResponseModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentSent extends BaseFragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.no_mail_text)
    TextView no_mail_text;

    SentMailAdapter adapter;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mail_list, container, false);
        ButterKnife.bind(this, v);

        session = new SessionManager(getActivity());

        // WebCallServiceStores();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        //System.out.println("Start====");

// add your code here which executes when the Fragment gets visible.
    }

    @Override
    public void onResume() {
        super.onResume();
        WebCallServiceStores();
        //System.out.println("Start====");

// add your code here which executes when the Fragment gets visible.
    }

    public void WebCallServiceStores() {
        // SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getSentMails";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                SentMailsResponseModel mResponse = g.fromJson(response, SentMailsResponseModel.class);


                                if (mResponse != null && mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    adapter = new SentMailAdapter(getActivity(), mResponse.getRecord().getMails());
                                    recycler.setAdapter(adapter);

                                } else {
                                    no_mail_text.setVisibility(View.VISIBLE);
                                    recycler.setVisibility(View.GONE);
                                    // Toast.makeText(getActivity(), mResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //  SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("sender_id", session.getCustomerId());
                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal mail == ", url + new JSONObject(params));

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
