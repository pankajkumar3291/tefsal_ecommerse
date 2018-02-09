package com.tefal.fragment;


import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextClock;
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
import com.tefal.Models.DishdashaStoreModel;
import com.tefal.Models.DishdashaTailorProductResponse;
import com.tefal.Models.GetAssignedItemsRecord;
import com.tefal.Models.GetAssignedItemsResponse;
import com.tefal.Models.GetCartRecord;
import com.tefal.Models.TailorProductResponse;
import com.tefal.Models.TailoringRecord;
import com.tefal.R;
import com.tefal.activity.CartActivity;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.adapter.CustomTailorCalculationProduct;
import com.tefal.adapter.DishdashaAllAdapter;
import com.tefal.adapter.DishdashaTailorProductAdapterForListView;
import com.tefal.adapter.DishdashaTailorProductsAdapter;
import com.tefal.adapter.TailorProductAdapter;
import com.tefal.app.TefalApp;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentTailorProducts extends Fragment {


   /* List<GetCartRecord> getCartRecordListOfChecked=new ArrayList<GetCartRecord>();
    List<GetCartRecord> getCartRecordListOfCheckedTrue=new ArrayList<GetCartRecord>();*/

    ArrayList<TailoringRecord> tailoringRecordArrayListOfChecked=new ArrayList<TailoringRecord>();
    ArrayList<TailoringRecord> tailoringRecordArrayListOfCheckedTrue=new ArrayList<TailoringRecord>();

    ArrayList<GetAssignedItemsRecord> assignedItemsRecordArrayList = new ArrayList<GetAssignedItemsRecord>();
    GetAssignedItemsResponse getAssignedItemsResponse;

    private String ownTextileString;



    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.tailor_add)
    Button add;

    @BindView(R.id.dishInfoText)
    TextView dishInfoText;


   /* @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;*/

   @BindView(R.id.list)
   ListView list;

    @BindView(R.id.list2)
    ListView list2;

    @BindView(R.id.ownTextileText)
    TextView ownTextileText;

    TailorProductAdapter productAdapter;
    SessionManager session;

    String store_id;

    SessionManager sessionManager;

    DishdashaTailorProductsAdapter dishdashaTailorProductsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tailor_product, container, false);
        ButterKnife.bind(this, v);

        sessionManager=new SessionManager(getContext());

        System.out.println("SYSTEM STORE ID FRAGMENTTAILORPRODUCT==="+ TefalApp.getInstance().getStoreId());

        Bundle bundle=getArguments();
        tailoringRecordArrayListOfChecked=(ArrayList<TailoringRecord>)bundle.getSerializable("tailoringRecordArrayListOfChecked");
        ownTextileString=bundle.getString("ownTextileString");

        dishInfoText.setText(TefalApp.getInstance().getStyleName()+" / "+Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters())) +" meter = 1 Dishdasha");

        System.out.println("SYSTEM STORE ID FRAGMENTTAILORPRODUCT==="+ tailoringRecordArrayListOfChecked);

        if(tailoringRecordArrayListOfChecked!=null)
        {
            for(int i=0;i<tailoringRecordArrayListOfChecked.size();i++)
            {
                if(tailoringRecordArrayListOfChecked.get(i).getChecked())
                {
                   // GetCartRecord getCartRecord=new GetCartRecord();
                    tailoringRecordArrayListOfCheckedTrue.add(tailoringRecordArrayListOfChecked.get(i));

                }

            }

            CustomTailorCalculationProduct customTailorCalculationProduct=new CustomTailorCalculationProduct(getActivity(),tailoringRecordArrayListOfCheckedTrue);
            list.setAdapter(customTailorCalculationProduct);

        }
        else
        {
            ownTextileText.setText(ownTextileString);
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* Intent i = new Intent(getActivity(), CartActivity.class);
                startActivity(i);*/
            }
        });

       WebCallServiceStores();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void WebCallServiceStores() {
         SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getAssignedItems";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                             SimpleProgressBar.closeProgress();
                            try {
                                if (response != null) {

                                    Log.e("stores response", response);
                                    Gson g = new Gson();
                                    getAssignedItemsResponse= g.fromJson(response, GetAssignedItemsResponse.class);

                                    if (getAssignedItemsResponse.getStatus().equals("1"))
                                    {


                                        System.out.println("DISHDASHA TAILOR SIZE===="+getAssignedItemsResponse.getRecord().size());

                                       // dishdashaTailorProductsAdapter=new DishdashaTailorProductsAdapter(getActivity(),mResponse.getRecord());
                                        DishdashaTailorProductAdapterForListView dishdashaTailorProductAdapterForListView=new DishdashaTailorProductAdapterForListView(getActivity(),getAssignedItemsResponse.getRecord());

                                        list2.setAdapter(dishdashaTailorProductAdapterForListView);



                                        //  recycler.setAdapter(dishdashaTailorProductsAdapter);

                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(), getAssignedItemsResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
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

                    System.out.println("CART ID======"+sessionManager.getKeyCartId());
//                  params.put("access_token", session.getToken());
                    params.put("cart_id", sessionManager.getKeyCartId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

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
