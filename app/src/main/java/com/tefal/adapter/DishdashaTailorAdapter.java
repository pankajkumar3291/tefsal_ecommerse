package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefal.Models.TailorStoresModel;
import com.tefal.R;
import com.tefal.activity.DaraAbayaActivity;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.activity.ProductListOtherActivity;
import com.tefal.activity.ZaaraDaraaActivity;
import com.tefal.app.TefalApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaTailorAdapter extends RecyclerView.Adapter<DishdashaTailorAdapter.ViewHolder> {

    private Activity activity;
    private List<TailorStoresModel> storeModels = new ArrayList<>();
    String flag;

    public DishdashaTailorAdapter(Activity activity, List<TailorStoresModel> storeModels, String flag) {
        this.activity = activity;
        this.storeModels = storeModels;
        this.flag = flag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img)
        ImageView img;

        @Bind(R.id.ratingbar)
        RatingBar ratingbar;

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.main_layout)
        RelativeLayout main_layout;

        @Bind(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;


        @Bind(R.id.txt_discount_off)
        TextView txt_discount_off;

        @Bind(R.id.txt_discount_amount)
        TextView txt_discount_amount;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        String discount_amount;

        if (!storeModels.get(holder.getAdapterPosition()).getStore_image().isEmpty()) {
            Picasso.with(activity)
                    .load(storeModels.get(holder.getAdapterPosition()).getStore_image())
                    .error(R.drawable.no_image_placeholder_non_grid)
                    .placeholder(R.drawable.no_image_placeholder_non_grid)
                    .into(holder.img);
        }
        else
        {
            holder.img.setImageResource(R.drawable.no_image_placeholder_non_grid);
        }
        holder.title.setText(storeModels.get(holder.getAdapterPosition()).getStore_name());
        holder.ratingbar.setRating(Float.parseFloat(storeModels.get(holder.getAdapterPosition()).getStore_rating()));
        holder.text_max_delivery_days.setText(storeModels.get(holder.getAdapterPosition()).getMax_delivery_days());

        if(storeModels.get(holder.getAdapterPosition()).getStore_discount().equals("") || storeModels.get(holder.getAdapterPosition()).getStore_discount().equals(null))
        {
            holder.txt_discount_amount.setVisibility(View.GONE);
            holder.txt_discount_off.setVisibility(View.GONE);
        }
        else
        {
            discount_amount=storeModels.get(holder.getAdapterPosition()).getStore_discount();
            holder.txt_discount_amount.setText(discount_amount);
        }



        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.startActivity(new Intent(activity, DishDashaProductActivity.class)
//                        .putExtra("title",storeModels.get(holder.getAdapterPosition()).getStore_name())
//                        .putExtra("Flag","1")
//                        .putExtra("store_id",storeModels.get(holder.getAdapterPosition()).getStore_id()));

                if (flag.equals("dish")) {


                    //==================== This code used for fresh item list without having color , season, country filter ============================
                    TefalApp.getInstance().setColor("");
                    TefalApp.getInstance().setSeason("");
                    TefalApp.getInstance().setCountry("");

                    TefalApp.getInstance().setFlage("1");
                    TefalApp.getInstance().setStoreId(storeModels.get(holder.getAdapterPosition()).getStore_id());
                    TefalApp.getInstance().setStoreName(storeModels.get(holder.getAdapterPosition()).getStore_name());
                    TefalApp.getInstance().setWhereFrom("tailor");
                    TefalApp.getInstance().setTailor_id(storeModels.get(holder.getAdapterPosition()).getStore_id());

                    activity.startActivity(new Intent(activity, DishDashaProductActivity.class)
                            /*.putExtra("title", storeModels.get(holder.getAdapterPosition()).getStore_name())
                            .putExtra("Flag", "1")
                            .putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                            .putExtra("store_name",storeModels.get(holder.getAdapterPosition()).getStore_name())*/
                            );

                }else
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