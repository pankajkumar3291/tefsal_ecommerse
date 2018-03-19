package com.tefal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefal.Models.ProductRecord;
import com.tefal.Models.ProductSizes;
import com.tefal.R;
import com.tefal.activity.ZaaraDaraaActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DishdashaTextileOtherProductAdapter extends RecyclerView.Adapter<DishdashaTextileOtherProductAdapter.ViewHolder> {

    private Activity activity;
    List<ProductRecord> productRecords = new ArrayList<>();
    private Context context;
    String storeId;

    public DishdashaTextileOtherProductAdapter(Activity activity, List<ProductRecord> productRecords, String storeId) {
        this.activity = activity;
        this.productRecords = productRecords;
        this.storeId = storeId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textile_product_card, viewGroup, false);
        TextView amount_text = (TextView) v.findViewById(R.id.amount_text);
        amount_text.setVisibility(View.VISIBLE);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pattern)
        ImageView iv_pattern;

        @BindView(R.id.name_text)
        TextView name_text;

        @BindView(R.id.prize_text)
        TextView prize_text;

        @BindView(R.id.amount_text)
        TextView amount_text;


        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;

        @BindView(R.id.txt_discount_amount)
        TextView txt_discount_amount;

        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        @BindView(R.id.txt_discount_off)
        TextView txt_discount_off;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String discaunt_amount;

        // String product_image = productRecords.get(position).getProduct_images().get(0);
        String brand_name = productRecords.get(position).getBrand_name();
        //String product_price=productRecords.get(position).getP
        String product_name = productRecords.get(position).getProduct_name();
        String max_delivery_days = productRecords.get(position).getMax_delivery_days();

        System.out.println("product_name===" + product_name);
//        if (productRecords.get(position).getProduct_discount().equals(null) || productRecords.get(position).getProduct_discount().equals("")) {
//            discaunt_amount = "0%";
//            holder.txt_discount_amount.setVisibility(View.GONE);
//            holder.txt_discount_off.setVisibility(View.GONE);
//        } else {
//            discaunt_amount = productRecords.get(position).getProduct_discount();
//            holder.txt_discount_amount.setText(discaunt_amount);
//        }
        //  holder.txt_discount_amount.setText(discaunt_amount);

        //This block of code responsible for getting the price of DARRA ABAYA Product price......
        List<ProductSizes> sizes = productRecords.get(position).getSizes();
        if (sizes != null) {
            holder.amount_text.setText(sizes.get(0).getPrice() + " KWD");
        }
        holder.prize_text.setText(product_name);
        holder.name_text.setText(brand_name);


        holder.text_max_delivery_days.setText(max_delivery_days);
//        Picasso.with(activity).load("")
//                .placeholder(R.drawable.no_image_placeholder_grid)
//                .error(R.drawable.no_image_placeholder_grid)
//                .into(holder.iv_pattern);

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ZaaraDaraaActivity.class);
                Bundle bundle = new Bundle();
                ProductRecord productRecord = productRecords.get(position);
                productRecord.setStore_id(storeId);
                bundle.putSerializable("productRecords", (Serializable) productRecord);
                intent.putExtras(bundle);
                activity.startActivity(intent);

                //System.out.println("Hello");

            }
        });
    }


    @Override
    public int getItemCount() {
        return productRecords.size();
    }

}