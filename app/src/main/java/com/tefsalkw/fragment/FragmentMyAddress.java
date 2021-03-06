package com.tefsalkw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.models.MyAddressesModel;
import com.tefsalkw.R;
import com.tefsalkw.adapter.MyAddressAdapter;
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

public class FragmentMyAddress extends BaseFragment {


    @BindView(R.id.recycler)
    RecyclerView recycler;


    MyAddressAdapter adapter;
    SessionManager session;

    @BindView(R.id.empty_view)
    TextView empty_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.addresses_fragment, container, false);
        ButterKnife.bind(this, v);



//        WebCallServiceAddresses();


        return v;
    }
    @Override
    public void onResume() {
        super.onResume();

        session = new SessionManager(getActivity());
        WebCallServiceAddresses();

    }

    public void WebCallServiceAddresses() {
        SimpleProgressBar.showProgress(getContext());
        try {
            final String url = Contents.baseURL + "getCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                MyAddressesModel mResponse = g.fromJson(response, MyAddressesModel.class);

                                if (mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    empty_view.setVisibility(View.GONE);
                                        recycler.setVisibility(View.VISIBLE);
                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    adapter = new MyAddressAdapter(getActivity(), mResponse.getRecord());
                                    recycler.setAdapter(adapter);

                                    if(mResponse.getRecord().size() == 0)
                                    {


                                        empty_view.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.GONE);
                                        empty_view.setText(R.string.no_new_address);

                                    }

                                }
                                else {

                                    recycler.setVisibility(View.GONE);
                                    empty_view.setVisibility(View.VISIBLE);
                                    empty_view.setText(R.string.no_new_address);
                                   // Toast.makeText(getActivity(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
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

                   // System.out.println("ACCESS TOCKEN=="+session.getToken());
                    params.put("customer_id", session.getCustomerId());
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

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            //SimpleProgressBar.closeProgress();
            SimpleProgressBar.closeProgress();
        }
    }
}