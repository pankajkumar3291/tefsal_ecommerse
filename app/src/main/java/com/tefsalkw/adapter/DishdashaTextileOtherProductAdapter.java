package com.tefsalkw.adapter;

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

import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.activity.ZaaraDaraaActivity;
import com.tefsalkw.models.Colors;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.ZaraDaraSizeModel;

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
    String flag;

    public DishdashaTextileOtherProductAdapter(Activity activity, List<ProductRecord> productRecords, String storeId, String flag) {
        this.activity = activity;
        this.productRecords = productRecords;
        this.storeId = storeId;
        this.flag = flag;
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

        holder.txt_discount_amount.setVisibility(View.GONE);
        holder.txt_discount_off.setVisibility(View.GONE);

        List<Colors> colorsList = productRecords.get(position).getColors();

        List<ZaraDaraSizeModel> sizes = colorsList != null ? colorsList.get(0).getSizes() : null;
        if (sizes != null) {
            holder.amount_text.setText(sizes.get(0).getPrice() + " KWD");

        }

        holder.prize_text.setText(product_name);
        holder.name_text.setText(brand_name);


        holder.text_max_delivery_days.setText(max_delivery_days);

        Picasso.with(activity).load(productRecords.get(position).getThumb_url())
                .placeholder(R.drawable.no_image_placeholder_grid)
                .error(R.drawable.no_image_placeholder_grid)
                .into(holder.iv_pattern);

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, ZaaraDaraaActivity.class);
                Bundle bundle = new Bundle();
                ProductRecord productRecord = productRecords.get(position);
                productRecord.setStore_id(storeId);
                productRecord.setFlag(flag);
                bundle.putSerializable("productRecords", (Serializable) productRecord);
                intent.putExtras(bundle);
                activity.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount() {
        return productRecords.size();
    }

}