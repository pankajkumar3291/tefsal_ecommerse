package com.tefal.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tefal.Models.GetCartRecord;
import com.tefal.R;
import com.tefal.activity.CartActivity;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by new on 9/26/2017.
 */

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private Activity activity;
    private List<GetCartRecord> storeModels = new ArrayList<>();
    private SessionManager session;
    private boolean activate;


    public MyCartAdapter(Activity activity, List<GetCartRecord> storeModels) {
        this.activity = activity;
        this.storeModels = storeModels;
        session=new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_Tailor_name)
        TextView text_Tailor_name;

        @Bind(R.id.text_textile)
        TextView text_textile;

        @Bind(R.id.sub_text_textile)
        TextView sub_text_textile;

        @Bind(R.id.text_size)
        TextView text_size;

        @Bind(R.id.text_price)
        TextView text_price;

        @Bind(R.id.product_img)
        ImageView product_img;

        @Bind(R.id.ll_header)
        LinearLayout ll_header;

        @Bind(R.id.cart_item_delete)
        ImageView cart_item_delete;


//
//
//        @Bind(R.id.main_layout)
//        RelativeLayout main_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        System.out.println("ITEM TYPE=="+storeModels.get(position2).getItem_type());

        if(storeModels.get(position2).getItem_type().equals("DTE"))
        {
            Picasso.with(activity)
                    .load(storeModels.get(position2).getStore_image())
                    .error(R.drawable.no_image_placeholder_grid)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .into(holder.product_img);
            System.out.println("Image ==="+storeModels.get(position2).getPattern_image());
            holder.text_Tailor_name.setText(storeModels.get(position2).getStore_name());
            holder.text_textile.setText(storeModels.get(position2).getDishdasha_pattern()+" "+storeModels.get(position2).getDishdasha_material());
            holder.sub_text_textile.setVisibility(GONE);
           // holder.sub_text_textile.setText(storeModels.get(position2).getDishdasha_material());
            holder.text_size.setText("QTY: "+storeModels.get(position2).getItem_quantity()+" Dishdasha");
            holder.text_price.setText(storeModels.get(position2).getPrice()+" KWD");
        }
        if(storeModels.get(position2).getItem_type().equals("DB"))
        {
            Picasso.with(activity)
                    .load(storeModels.get(position2).getStore_image())
                    .error(R.drawable.no_image_placeholder_grid)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .into(holder.product_img);
            System.out.println("Image ==="+storeModels.get(position2).getImage());

            holder.text_Tailor_name.setText(storeModels.get(position2).getStore_name());
            holder.text_textile.setText(storeModels.get(position2).getProduct_name());
            holder.sub_text_textile.setVisibility(GONE);
            // holder.sub_text_textile.setText(storeModels.get(position2).getDishdasha_material());
            holder.text_size.setText("SIZE: "+storeModels.get(position2).getItem_quantity());
            holder.text_price.setText(storeModels.get(position2).getPrice()+" KWD");

        }
        if(storeModels.get(position2).getItem_type().equals("A"))
        {
            Picasso.with(activity)
                    .load(storeModels.get(position2).getStore_image())
                    .error(R.drawable.no_image_placeholder_grid)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .into(holder.product_img);
            System.out.println("Image ==="+storeModels.get(position2).getStore_image());

            holder.text_Tailor_name.setText(storeModels.get(position2).getStore_name());
            holder.text_textile.setText(storeModels.get(position2).getProduct_name());
             holder.sub_text_textile.setVisibility(GONE);
            holder.text_size.setText("SIZE: "+storeModels.get(position2).getItem_quantity()+" METERS");
            holder.text_price.setText(storeModels.get(position2).getPrice()+" KWD");
        }
        if(storeModels.get(position2).getItem_type().equals("DTA"))
        {
            Picasso.with(activity)
                    .load(storeModels.get(position2).getStore_image())
                    .error(R.drawable.placeholder_no_image)
                    .placeholder(R.drawable.placeholder_image_loading)
                    .into(holder.product_img);

            holder.text_Tailor_name.setText(storeModels.get(position2).getStore_name());
            holder.text_textile.setText(storeModels.get(position2).getProduct_name());
            holder.sub_text_textile.setVisibility(GONE);
            holder.text_size.setText("QTY: "+storeModels.get(position2).getItem_quantity()+" METERS");
            holder.text_price.setText(storeModels.get(position2).getTotal_amount()+" KWD");

            System.out.println("Image ==="+storeModels.get(position2).getStore_image());
        }



       /* if(storeModels.get(position2).isDelete())
        {
            holder.cart_item_delete.setVisibility(View.VISIBLE);
        }*/
       if(this.activate)
       {
           holder.cart_item_delete.setVisibility(View.VISIBLE);
       }
       else
       {
           holder.cart_item_delete.setVisibility(GONE);

       }

        holder.cart_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("CLICKK==");
                showNamePrompt(position2);
               // notifyDataSetChanged();
            }
        });
//        holder.main_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return storeModels.size();
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
                httpDeleteCartItemCall(pos);
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

    public void httpDeleteCartItemCall(final int pos)
    {

        SimpleProgressBar.showProgress(activity);
        try {
            final String url = Contents.baseURL + "deleteCartItem";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("RESPONSE CART DELETE===="+response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null)
                            {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status=jsonObject.getString("status");
                                    String message=jsonObject.getString("message");
                                    if(status.equals("1"))
                                    {
                                        storeModels.remove(pos);
                                        notifyDataSetChanged();
                                        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
                                        ((CartActivity)activity).updateUifromAdapter(storeModels);

                                    }
                                    else
                                    {
                                        Toast.makeText(activity,"Something gone wrong",Toast.LENGTH_SHORT).show();

                                    }

                                    // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();

                                    //startActivity(new Intent(getApplicationContext(), MailFragment.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    //finish();
                                } catch (JSONException e) {
                                    System.out.println("EX=="+e);
                                    e.printStackTrace();
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
                    params.put("cart_item_id",storeModels.get(pos).getCart_item_id());
                    System.out.println("CART ID=="+storeModels.get(pos).getCart_item_id());
                    System.out.println("");
                    params.put("access_token", session.getToken());
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
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void activateDeleteOption(boolean activate) {
        //this.activate = activate;
        this.activate=activate;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }

}