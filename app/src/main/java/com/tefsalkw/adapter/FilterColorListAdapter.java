package com.tefsalkw.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
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
import com.tefsalkw.R;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.models.ColorRecordFromDishdashaFilteration;
import com.tefsalkw.models.ColorResponseModel;
import com.tefsalkw.models.ColorsRecordModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 12/27/2017.
 */

public class FilterColorListAdapter extends RecyclerView.Adapter<FilterColorListAdapter.ViewHolder> {

    private ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList;
    private FragmentTextileProducts activity;
    ArrayList<ColorsRecordModel> sub_colorsRecordModelArrayList = new ArrayList<ColorsRecordModel>();


    public FilterColorListAdapter(ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList, FragmentTextileProducts activity) {
        this.activity = activity;
        this.colorsRecordModelArrayList = colorsRecordModelArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new FilterColorListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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



                activity.selectedColorModel = colorsRecordModelArrayList.get(position);

                getSubColorHttpCall(colorsRecordModelArrayList.get(position).getColor_id(), position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return colorsRecordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color)
        ImageView color;
        @BindView(R.id.colorText)
        TextView colorText;

        @BindView(R.id.color_item)
        LinearLayout color_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void getSubColorHttpCall(final String color_id, final int position) {

        //ArrayList<ColorsRecordModel> l_colorsRecordModelArrayList=new ArrayList<ColorsRecordModel>();
        SimpleProgressBar.showProgress(activity.getContext());
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
                                if (mResponse.getStatus().equals("1")) {



                                    sub_colorsRecordModelArrayList = mResponse.getRecord();


                                    FilterSubColorListAdapter filterSubColorListAdapter = new FilterSubColorListAdapter(sub_colorsRecordModelArrayList, activity);
                                    FragmentTextileProducts.filterColorRecyclerView.setAdapter(filterSubColorListAdapter);
                                    System.out.println("RESPONSE SIZE==" + colorsRecordModelArrayList.size());


                                } else {

                                    if (FragmentTextileProducts.colorWindow != null) {
                                        FragmentTextileProducts.colorWindow.dismiss();
                                    }

                                    if (colorsRecordModelArrayList != null && colorsRecordModelArrayList.size() > 0) {
                                        activity.loadColorFilteredProducts(colorsRecordModelArrayList.get(position));
                                    }

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
                    params.put("color_id", color_id);


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
            RequestQueue requestQueue = Volley.newRequestQueue(activity.getContext());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
        // return null;
    }
}
