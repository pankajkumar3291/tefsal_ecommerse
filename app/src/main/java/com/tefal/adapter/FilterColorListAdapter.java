package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tefal.Models.ColorRecordFromDishdashaFilteration;
import com.tefal.Models.ColorResponseModel;
import com.tefal.Models.ColorsRecordModel;
import com.tefal.Models.TextileProductResponse;
import com.tefal.R;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentTextileProducts;
import com.tefal.utils.Contents;
import com.tefal.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 12/27/2017.
 */

public class FilterColorListAdapter extends RecyclerView.Adapter<FilterColorListAdapter.ViewHolder>
{

    private ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList;
    private Activity activity;
    ArrayList<ColorsRecordModel> sub_colorsRecordModelArrayList=new ArrayList<ColorsRecordModel>();



    public FilterColorListAdapter(ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList, Activity activity )
    {
        this.activity=activity;
        this.colorsRecordModelArrayList=colorsRecordModelArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new FilterColorListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
           // holder.color;
           // Picasso.with(activity).load(colorsRecordModelArrayList.get(position).getImage()).into(holder.color);


        //color_item
            holder.colorText.setText(colorsRecordModelArrayList.get(position).getColor_name());
          // holder.color.setBackgroundColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));


        LayerDrawable layerDrawable = (LayerDrawable) activity.getResources()
                .getDrawable(R.drawable.round_image_background_for_color);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                .findDrawableByLayerId(R.id.item);
        gradientDrawable.setColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));
        holder.color.setBackground(layerDrawable);

            /* LayerDrawable bgDrawable = (LayerDrawable)holder.color.getBackground();
            GradientDrawable shape = (GradientDrawable)   bgDrawable.findDrawableByLayerId(R.id.shape);
           shape.setColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));*/
             holder.color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Flushing subColor before getting new Color
                TefalApp.getInstance().setSubColor("");
                TefalApp.getInstance().setColor(colorsRecordModelArrayList.get(position).getColor_id());


                System.out.println("COLOR FROM ADAPTER BEFORE SETTING IT TO SINGLETON==="+colorsRecordModelArrayList.get(position).getColor_id());

                getSubColorHttpCall(colorsRecordModelArrayList.get(position).getColor_id(), position);




               /* FragmentTextileProducts.colorWindow.dismiss();
                TefalApp.getInstance().setColor(colorsRecordModelArrayList.get(position).getId());
                Intent intent=new Intent(activity,DishDashaProductActivity.class);
                intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                intent.putExtra("flag",TefalApp.getInstance().getFlage());
                intent.putExtra("store_name",TefalApp.getInstance().getStoreName());
                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.finish();*/
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return colorsRecordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.color)
        ImageView color;
        @Bind(R.id.colorText)
        TextView colorText;

        @Bind(R.id.color_item)
        LinearLayout color_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private  void getSubColorHttpCall(final String color_id, final int position)
    {

       //ArrayList<ColorsRecordModel> l_colorsRecordModelArrayList=new ArrayList<ColorsRecordModel>();
        SimpleProgressBar.showProgress(activity);
        try {
            final String url = Contents.baseURL + "getSubColors";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                ColorResponseModel mResponse = g.fromJson(response, ColorResponseModel.class);
                                if (!mResponse.getStatus().equals("0"))
                                {

                                    sub_colorsRecordModelArrayList=mResponse.getRecord();
                                  //  FragmentTextileProducts.filterColorRecyclerView;

                                    FilterSubColorListAdapter filterSubColorListAdapter=new FilterSubColorListAdapter(sub_colorsRecordModelArrayList,activity);
                                    FragmentTextileProducts.filterColorRecyclerView.setAdapter(filterSubColorListAdapter);
                                   // System.out.println("SUB COPLOR==="+colorsRecordModelArrayList.size());
                                   // notifyDataSetChanged();


                                    System.out.println("RESPONSE SIZE=="+colorsRecordModelArrayList.size());
                                    // if(textileProductModelList.size()==0)
                                }
                                else
                                {
                                   // TefalApp.getInstance().setColor(colorsRecordModelArrayList.get(position).getColor_id());
                                    Intent intent=new Intent(activity,DishDashaProductActivity.class);
                                   /* intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                                    intent.putExtra("flag",TefalApp.getInstance().getFlage());
                                    intent.putExtra("store_name",TefalApp.getInstance().getStoreName());*/
                                    activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    activity.finish();
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
                    params.put("color_id",color_id );



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
       // return null;
    }
}
