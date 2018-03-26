package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefal.Models.D_StoreRecord;
import com.tefal.R;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.activity.ProductListOtherActivity;
import com.tefal.app.TefalApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaAllAdapter extends RecyclerView.Adapter<DishdashaAllAdapter.ViewHolder> {

    private Activity activity;
    private List<D_StoreRecord> storeModels = new ArrayList<>();

    String flag ;


    public DishdashaAllAdapter(Activity activity, List<D_StoreRecord> storeModels, String flag) {
        this.activity = activity;
        this.storeModels = storeModels;
        this.flag = flag;
        //System.out.println("SIZE===="+storeModels.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ratingbar)
        RatingBar ratingbar;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.main_layout)
        RelativeLayout main_layout;

        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        if (!storeModels.get(holder.getAdapterPosition()).getStore_image().isEmpty())
            Picasso.with(activity).load(storeModels.get(holder.getAdapterPosition()).getStore_image()).into(holder.img);

        holder.title.setText(storeModels.get(holder.getAdapterPosition()).getStore_name());
        holder.ratingbar.setRating(Float.parseFloat(storeModels.get(holder.getAdapterPosition()).getStore_rating()));
        holder.text_max_delivery_days.setText(storeModels.get(holder.getAdapterPosition()).getMax_delivery_days());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("dish")) {
                    //==================== This code used for fresh item list without having color , season, country filter ============================
                    TefalApp.getInstance().setColor("");
                    TefalApp.getInstance().setSeason("");
                    TefalApp.getInstance().setCountry("");
                    //==========================================================
                    activity.startActivity(new Intent(activity, DishDashaProductActivity.class)
                            .putExtra("title", storeModels.get(holder.getAdapterPosition()).getStore_name())
                            .putExtra("Flag", "1")
                            .putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                            .putExtra("store_name",storeModels.get(holder.getAdapterPosition()).getStore_name()));

                }
                else
                    activity.startActivity(new Intent(activity, ProductListOtherActivity.class)
                            .putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                            .putExtra("Flag", flag));

            }
        });
    }


    @Override
    public int getItemCount() {
        return storeModels.size();
    }

}