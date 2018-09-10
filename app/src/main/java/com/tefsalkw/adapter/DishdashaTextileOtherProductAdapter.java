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

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.DaraAbayaProductDetailsActivity;
import com.tefsalkw.models.ProductRecord;

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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dara_abaya_products, viewGroup, false);

        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        @BindView(R.id.iv_pattern)
        ImageView iv_pattern;

        @BindView(R.id.txt_brand_name)
        TextView txt_brand_name;

        @BindView(R.id.txt_product_name)
        TextView txt_product_name;

        @BindView(R.id.txt_default_price)
        TextView txt_default_price;


        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder( ViewHolder holder,  int position) {


        String brand_name = productRecords.get(position).getBrand_name();
        String product_name = productRecords.get(position).getProduct_name();
        String max_delivery_days = productRecords.get(position).getMax_delivery_days();
        String default_img = productRecords.get(position).getDefault_product_image();
        String default_price = productRecords.get(position).getDefault_price();

        holder.txt_brand_name.setText(brand_name);
        holder.txt_product_name.setText(product_name);
        holder.text_max_delivery_days.setText(max_delivery_days);
        holder.txt_default_price.setText(default_price);

        RequestOptions options = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.no_image_placeholder_grid)
                .error(R.drawable.no_image_placeholder_grid);

        GlideApp.with(activity).asBitmap().load(default_img).apply(options).into(holder.iv_pattern);


        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, DaraAbayaProductDetailsActivity.class);
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