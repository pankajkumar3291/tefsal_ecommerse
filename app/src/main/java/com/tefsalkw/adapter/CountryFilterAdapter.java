package com.tefsalkw.adapter;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
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

public class CountryFilterAdapter extends RecyclerView.Adapter<CountryFilterAdapter.ViewHolder> {


    private ArrayList<FilterCountryModel> filterCountryModelArrayList;
    private Activity activity;
    private int limit;

    public CountryFilterAdapter( ArrayList<FilterCountryModel> filterCountryModelArrayList, Activity activity,int limit)
    {
        this.activity=activity;
        this.filterCountryModelArrayList=filterCountryModelArrayList;
        this.limit=limit;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
        return new CountryFilterAdapter.ViewHolder(v);
        //return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.txt_country_name.setText(filterCountryModelArrayList.get(position).getCountry_name().toUpperCase());

         System.out.println("Country code Of model=="+filterCountryModelArrayList.get(position).getCountry_id());
        System.out.println("Country code Of dump=="+ TefalApp.getInstance().getCountry());
        holder.country_flag_imgage.setBackgroundResource(0);


        if(filterCountryModelArrayList.get(position).getCountry_id().equals(TefalApp.getInstance().getCountry()))
       {


           Drawable drawableSelect = activity.getResources().getDrawable(R.drawable.round_mage_background_select);
           holder.country_flag_imgage.setBackground(drawableSelect);
           holder.country_flag_imgage.setPadding(6,6,6,6);
         //  holder.country_flag_imgage.setPadding(4,4,4,4);
           Picasso.with(activity).load(filterCountryModelArrayList.get(position).getCountry_image()).transform(new CircleTransform()).into(holder.country_flag_imgage);

          // holder.country_flag_imgage.setColorFilter(ContextCompat.getColor(activity, R.color.colorYellowTransperent), android.graphics.PorterDuff.Mode.MULTIPLY);
       }
       else
        {
            Drawable drawableNonSelect=activity.getResources().getDrawable(R.drawable.round_image_background);
            holder.country_flag_imgage.setBackground(drawableNonSelect);
            holder.country_flag_imgage.setPadding(6,6,6,6);
           // holder.country_flag_imgage.setPadding(4,4,4,4);
            Picasso.with(activity).load(filterCountryModelArrayList.get(position).getCountry_image()).transform(new CircleTransform()).into(holder.country_flag_imgage);

            //  holder.country_flag_imgage.setColorFilter(ContextCompat.getColor(activity, R.color.colorTranspernt), android.graphics.PorterDuff.Mode.MULTIPLY);
        }




        holder.filter_item_panel_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* Drawable d = activity.getResources().getDrawable(R.drawable.round_mage_background_select);
                holder.country_flag_imgage.setBackground(d);*/
                TefalApp.getInstance().setCountry(filterCountryModelArrayList.get(position).getCountry_id());
                notifyDataSetChanged();

             //  ColorFilter colorFilter= holder.country_flag_imgage.getColorFilter();
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
