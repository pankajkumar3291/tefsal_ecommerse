package com.tefal.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.tefal.Models.DishdashaStoreModel;
import com.tefal.Models.DishdashaStylesResponse;
import com.tefal.R;
import com.tefal.activity.MeasermentActivity;
import com.tefal.adapter.DishdashaAdapter;
import com.tefal.adapter.DishdashaAllAdapter;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;



public class FragmentDishdasha extends Fragment {


    @Bind(R.id.recycler)
    RecyclerView recycler;

    @Bind(R.id.loading)
    ProgressBar loading;


    DishdashaAdapter dishdashaAdapter;
    SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dishdasha_blank, container, false);
       // addNewStyle=(Button)v.findViewById(R.id.addnewBtn);
        ButterKnife.bind(this, v);


        session = new SessionManager(getActivity());

        System.out.println("Session id=="+session.getToken());
        System.out.println("Cust id=="+session.getCustomerId());




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
                                DishdashaStylesResponse mResponse = g.fromJson(response, DishdashaStylesResponse.class);

                                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycler.setLayoutManager(layoutManager);
                                recycler.setItemAnimator(new DefaultItemAnimator());

                                /*recycler.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recycler, new MyClickListener()
                                {

                                    @Override
                                    public void onClick(View view, int position)
                                    {
                                        Toast.makeText(getActivity(), "onClick==" + view.toString(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "onClick==" + view.toString(), Toast.LENGTH_SHORT).show();
                                        System.out.println("View=="+view.toString());
                                       // Toast.makeText(getActivity(), "onLongClick==" + position, Toast.LENGTH_SHORT).show();
                                    }


                                    @Override
                                    public void onLongClick(View view, int position) {
                                       // Toast.makeText(getActivity(), "onLongClick==" + position, Toast.LENGTH_SHORT).show();
                                    }
                                }));*/

                                dishdashaAdapter = new DishdashaAdapter(getActivity(),mResponse.getRecord(),"1");
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

                    System.out.println("OUT PUT    access_token"+session.getToken());
                    System.out.println("OUT PUT    user_id"+session.getCustomerId());
                    System.out.println("OUT PUT    Category"+"1");

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
            System.out.println("ERROR=="+surError);
            surError.printStackTrace();
        }
    }

    /*lass RecyclerViewTouchListener implements  RecyclerView.OnItemTouchListener
    {

        private GestureDetector gastureDetector;
        private MyClickListener clickListener;
        public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final MyClickListener clickListener)
        {

            this.clickListener=clickListener;
            gastureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e)
                {
                    clickListener.onClick(recyclerView.findChildViewUnder(e.getX(),e.getY()),recyclerView.getChildLayoutPosition(recyclerView.findChildViewUnder(e.getX(),e.getY())));
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e)
                {
                    clickListener.onLongClick(recyclerView.findChildViewUnder(e.getX(),e.getY()),recyclerView.getChildLayoutPosition(recyclerView.findChildViewUnder(e.getX(),e.getY())));
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener!=null )
            {
                gastureDetector.onTouchEvent(e);
                // clickListener.onClick(child,rv.getChildLayoutPosition(child));
            }
            return false;

        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public  static interface MyClickListener
    {
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }*/
}
