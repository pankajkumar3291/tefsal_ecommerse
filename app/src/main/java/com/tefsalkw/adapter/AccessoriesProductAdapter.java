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
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.models.AccessoriesRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;


public class AccessoriesProductAdapter extends RecyclerView.Adapter<AccessoriesProductAdapter.ViewHolder> {
    private Activity activity;
    public  List<AccessoriesRecord> productRecords = new ArrayList<>();
    private Context context;
    String storeId;


    public AccessoriesProductAdapter(Activity activity, List<AccessoriesRecord> productRecords, String storeId) {
        this.activity = activity;
        this.productRecords = productRecords;
        this.storeId = storeId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accessories_products, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pattern)
        ImageView iv_pattern;


        @BindView(R.id.txtBrandName)
        TextView txtBrandName;

        @BindView(R.id.txtDiscount)
        TextView txtDiscount;

        @BindView(R.id.txtProductName)
        TextView txtProductName;

        @BindView(R.id.txtStoreName)
        TextView txtStoreName;

        @BindView(R.id.txtAmount)
        TextView txtAmount;


        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        try {


            String discount_amount;
            String brand_name = productRecords.get(position).getBrandName();
            String product_name = productRecords.get(position).getProductName();
            String store = productRecords.get(position).getStoreName();
            String max_delivery_days = productRecords.get(position).getMax_delivery_days();

            String product_price = productRecords.get(position).getDefault_price();

            String accessory_product_image = productRecords.get(position).getDefault_image();


            if (productRecords.get(position).getProduct_discount() == null || productRecords.get(position).getProduct_discount().equals("")) {
                holder.txtDiscount.setVisibility(GONE);
                holder.txtDiscount.setVisibility(GONE);
            } else {
                discount_amount = productRecords.get(position).getProduct_discount();
                holder.txtDiscount.setText(discount_amount + " Off");
            }

            holder.txtStoreName.setText(store);
            holder.txtProductName.setText(product_name);
            holder.txtBrandName.setText(brand_name);
            holder.txtAmount.setText(product_price);
            holder.text_max_delivery_days.setText(max_delivery_days);


            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(activity).load(accessory_product_image).apply(options).into(holder.iv_pattern);


            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(activity, AccessoryProductDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    AccessoriesRecord accessoriesRecord = productRecords.get(position);
                    bundle.putSerializable("accessoriesRecord", (Serializable) accessoriesRecord);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);


                }
            });
        } catch (Exception exc) {

        }

    }


    @Override
    public int getItemCount() {
        return productRecords.size();
    }

}