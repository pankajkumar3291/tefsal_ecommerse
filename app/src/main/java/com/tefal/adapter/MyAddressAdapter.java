package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;
import com.tefal.Models.AddressRecord;
import com.tefal.Models.MyOrderResponse;
import com.tefal.R;
import com.tefal.activity.AddressUpdateActivity;
import com.tefal.activity.MyOrderActivity;
import com.tefal.fragment.FragmentMyAddress;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {

    private Activity activity;
    private List<AddressRecord> record;
    private SessionManager sessionManager;

    public MyAddressAdapter(Activity activity, List<AddressRecord> record) {
        this.activity = activity;
        this.record = record;
        sessionManager=new SessionManager(this.activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_addresses_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.address_text)
        TextView address_text;

        @Bind(R.id.address_name)
        TextView address_name;

        @Bind(R.id.address_layout)
        LinearLayout address_layout;




        @Bind(R.id.address_edit_btn)
        Button address_edit_btn;

        @Bind(R.id.address_delete_btn)
        Button  address_delete_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {


        holder.address_name.setText(record.get(holder.getAdapterPosition()).getAddress_name());
        holder.address_text.setText("Block "+record.get(holder.getAdapterPosition()).getBlock()
                +" , Street "+record.get(holder.getAdapterPosition()).getStreet()
                +" , House / Building "+record.get(holder.getAdapterPosition()).getHouse()
                +" , City "+record.get(holder.getAdapterPosition()).getCity()
                +" , "+record.get(holder.getAdapterPosition()).getCountry());

        holder.address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.address_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Bundle bundle=new Bundle();
                bundle.putSerializable("addressRecord",record.get(position2));
                activity.startActivity(new Intent(activity, AddressUpdateActivity.class).putExtras(bundle));
                //activity.finish();

            }
        });

        holder.address_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               showNamePrompt(position2);
            }
        });
    }


    @Override
    public int getItemCount() {
        return record.size();
    }

    private void httpDeleteAddressCall(final int position2)
    {
        SimpleProgressBar.showProgress(activity);
        try {
            final String url = Contents.baseURL + "deleteCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SimpleProgressBar.closeProgress();

                            System.out.println("ADDRESS DELETE RESPONSE==="+response);

                            if (response != null)
                            {
                                try
                                {
                                    JSONObject object=new JSONObject(response);
                                    String status=object.getString("status");
                                    String message=object.getString("message");
                                    if(status.equals("1"))
                                    {
                                        //new FragmentMyAddress();
                                        record.remove(position2);
                                        notifyDataSetChanged();
                                        //activity.finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch(Exception ex)
                                {

                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error=="+error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customer_id", sessionManager.getCustomerId());
                    params.put("access_token", sessionManager.getToken());
                    params.put("address_id", record.get(position2).getAddress_id());
                    params.put("appUser","tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");


                    Log.e("Tefsal tailor == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void showNamePrompt(final int pos) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater LayoutInflater = activity.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_delete_dailog, null);

        Button dialog_ok_btn=(Button)dialogView .findViewById(R.id.dialog_ok_btn);
        Button dialog_cancel_btn=(Button)dialogView .findViewById(R.id.dialog_cancel_btn);

        /*input_layout_style_name=(TextInputLayout)dialogView.findViewById(R.id.input_layout_style_name);
        input_style_name=(EditText)dialogView.findViewById(R.id.input_style_name);
        dialog_ok_btn=(Button)dialogView.findViewById(R.id.dialog_ok_btn);
        dialog_cancel_btn=(Button)dialogView.findViewById(R.id.dialog_cancel_btn);*/
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //System.out.println("OK");
                alertDialog.dismiss();
                httpDeleteAddressCall(pos);
               // httpDeleteCartItemCall(pos);
                // WebCallServiceDeletStyle(pos);

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

}