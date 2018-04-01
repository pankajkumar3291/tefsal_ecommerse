package com.tefsalkw.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.Models.FilterBrandModel;
import com.tefsalkw.Models.FilterCountryModel;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/25/2017.
 */

public class BrandFilterAdapter extends RecyclerView.Adapter<BrandFilterAdapter.ViewHolder>
{

    private ArrayList<FilterBrandModel> filterBrandModelArrayList;
    private Activity activity;
    private int limit;

    public BrandFilterAdapter(ArrayList<FilterBrandModel> filterBrandModelArrayList,Activity activity,int limit)
    {
        this.filterBrandModelArrayList=filterBrandModelArrayList;
        this.activity=activity;
        this.limit=limit;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
        return new BrandFilterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.txt_country_name.setText(filterBrandModelArrayList.get(position).getBrand_name().toUpperCase());
        holder.country_flag_imgage.setBackgroundResource(0);

        if(filterBrandModelArrayList.get(position).getBrand_id().equals(TefalApp.getInstance().getBrand()))
        {
            Drawable drawableSelect = activity.getResources().getDrawable(R.drawable.round_mage_background_select);
            holder.country_flag_imgage.setBackground(drawableSelect);
            holder.country_flag_imgage.setPadding(6,6,6,6);

            Picasso.with(activity).load(filterBrandModelArrayList.get(position).getBrand_image()).transform(new CircleTransform()).into(holder.country_flag_imgage);

        }
        else
        {
            Drawable drawableNonSelect=activity.getResources().getDrawable(R.drawable.round_image_background);
            holder.country_flag_imgage.setBackground(drawableNonSelect);
            holder.country_flag_imgage.setPadding(6,6,6,6);

            Picasso.with(activity).load(filterBrandModelArrayList.get(position).getBrand_image()).transform(new CircleTransform()).into(holder.country_flag_imgage);
        }
       // Picasso.with(activity).load(filterBrandModelArrayList.get(position).getBrand_image()).transform(new CircleTransform()).into(holder.country_flag_imgage);
        holder.filter_item_panel_LL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TefalApp.getInstance().setBrand(filterBrandModelArrayList.get(position).getBrand_id());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return limit;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        @BindView(R.id.country_flag_image)
        ImageView country_flag_imgage;

        @BindView(R.id.txt_country_name)
        TextView txt_country_name;

        @BindView(R.id.filter_item_panel_LL)
        LinearLayout filter_item_panel_LL;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setLimit(int limit)
    {
        this.limit=limit;
    }
}
