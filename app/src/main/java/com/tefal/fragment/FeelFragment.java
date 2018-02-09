package com.tefal.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.tefal.Models.TailorProductResponse;
import com.tefal.R;
import com.tefal.activity.AccessoriesActivity;
import com.tefal.activity.CartActivity;
import com.tefal.activity.DaraAbayaActivity;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.activity.MeasermentActivity;
import com.tefal.activity.TextileDetailActivity;
import com.tefal.adapter.DishdashaTextileProductAdapter;
import com.tefal.adapter.TailorProductAdapter;
import com.tefal.utils.Contents;
import com.tefal.utils.NumberCheck;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeelFragment extends Fragment {


    /*@BindView(R.id.add_to_cart_btn)
    Button add_to_cart_btn;*/



    @BindView(R.id.header_txt)
    TextView header_txt;

    //@BindView(R.id.meter_value)
    public static TextView meter_value;

    @BindView(R.id.add_btn)
    ImageView add_btn;

    @BindView(R.id.less_btn)
    ImageView less_btn;

    @BindView(R.id.spin)
    Spinner spin;

    int meter = 2;
    int check_meter;

    SessionManager session;
    String[] narrow = {"PALLET"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feel, container, false);

        ButterKnife.bind(this, v);
        try {

            meter = Integer.parseInt(DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getDishdasha_qty_meters());
            check_meter=meter;

        }
        catch (Exception ex)
        {
            meter=2;
            check_meter=2;
        }
        meter_value=(TextView)v.findViewById(R.id.meter_value);
        meter_value.setText("" + meter);

        session = new SessionManager(getActivity());

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,narrow);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

       header_txt.setText(DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getFeel());

        String[] str = DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getPrice().split(" ");

        //Bug possible LOC=====================================
       // text_price.setText("PRICE : " + Double.valueOf(str[0]) * meter + "00 KWD");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meter < 10) {
                    meter++;
                    meter_value.setText("" + meter);


                }
                String[] str = DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getPrice().split(" ");



                if(NumberCheck.isInteger(str[0]))
                {
                    TextileDetailActivity.text_price.setText("PRICE : " + Integer.valueOf(str[0]) * meter + " KWD");
                }
                else
                {
                    TextileDetailActivity.text_price.setText("PRICE : " + Double.valueOf(str[0]) * meter + " KWD");
                }

            }
        });
        less_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meter > check_meter) {
                    meter--;
                    meter_value.setText("" + meter);
                }
                String[] str = DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getPrice().split(" ");

                // This block of code for checking whether the price in flaot or integerFormat from server
                if(NumberCheck.isInteger(str[0]))
                {
                    TextileDetailActivity.text_price.setText("PRICE : " + Integer.valueOf(str[0]) * meter + " KWD");
                }
                else
                {
                    TextileDetailActivity.text_price.setText("PRICE : " + Double.valueOf(str[0]) * meter + " KWD");
                }
            }
        });


       /* add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebCallServiceAddCart();

            }
        });*/

        return v;
    }

    public JSONArray getItems() {
        JSONArray arry = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            obj.put("product_id", DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getTefsal_product_id());
            obj.put("item_id", DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getDishdasha_attribute_id());
            obj.put("item_quantity", meter_value.getText());

            arry.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("CART RESPONSE ARRAY item_id=="+DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getDishdasha_attribute_id());
        System.out.println("CART RESPONSE ARRAY=="+arry.toString());
        return arry;
    }

    public void WebCallServiceAddCart() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "addCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                              SimpleProgressBar.closeProgress();

                            System.out.println("CART RESPONSE=="+response);

                            if (response != null) {

                                Log.e("stores response", response);

                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                                builder.setMessage("Your textile is successfully added to your cart")
                                        .setCancelable(false)
                                        .setPositiveButton("Go to Cart", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
//                                                startActivity(new Intent(getActivity(), DishDashaProductActivity.class).putExtra("Flag", "2").putExtra("store_id", TextileDetailActivity.StoreID));
                                                startActivity(new Intent(getActivity(), CartActivity.class));
                                            }
                                        })
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();
                                            }
                                        });

                                //Creating dialog box
                                android.support.v7.app.AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Item added successfully");
                                alert.show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("Exception=="+error);
                               SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("items", getItems().toString());
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
