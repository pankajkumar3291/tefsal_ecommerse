package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.models.ProductCountryRecordModel;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/29/2017.
 */

public class FilterCountryListAdapter extends RecyclerView.Adapter<FilterCountryListAdapter.ViewHolder>
{

    private ArrayList<ProductCountryRecordModel> productCountryRecordModelArrayList;
    private Activity activity;

    public FilterCountryListAdapter(ArrayList<ProductCountryRecordModel> productCountryRecordModelArrayList,Activity activity)
    {
        this.productCountryRecordModelArrayList=productCountryRecordModelArrayList;
        this.activity=activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new FilterCountryListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
       // holder.country_image
        holder.countryName.setText(productCountryRecordModelArrayList.get(position).getName());
        holder.country_image.setBackgroundResource(0);


        if(TefalApp.getInstance().getCountry().equals(productCountryRecordModelArrayList.get(position).getId()))
        {
            Drawable drawableSelect = activity.getResources().getDrawable(R.drawable.round_mage_background_select);
            holder.country_image.setBackground(drawableSelect);
            holder.country_image.setPadding(6,6,6,6);

            Picasso.with(activity).load(productCountryRecordModelArrayList.get(position).getImage()).into(holder.country_image);

        }
        else
        {
            Drawable drawableNonSelect=activity.getResources().getDrawable(R.drawable.round_image_background);
            holder.country_image.setBackground(drawableNonSelect);
            holder.country_image.setPadding(6,6,6,6);

            Picasso.with(activity).load(productCountryRecordModelArrayList.get(position).getImage()).into(holder.country_image);

        }


        holder.country_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTextileProducts.countryWindow.dismiss();
                TefalApp.getInstance().setCountry(productCountryRecordModelArrayList.get(position).getId());
                Intent intent=new Intent(activity,DishDashaProductActivity.class);
              /*  intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                intent.putExtra("flag",TefalApp.getInstance().getFlage());
                intent.putExtra("store_name",TefalApp.getInstance().getStoreName());*/
                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productCountryRecordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.country_image)
        ImageView country_image;

        @BindView(R.id.countryName)
        TextView countryName;

       /* @BindView(R.id.country_name_txt)
        TextView country_name_txt;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
