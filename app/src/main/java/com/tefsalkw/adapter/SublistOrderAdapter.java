package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.models.OrderItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SublistOrderAdapter extends RecyclerView.Adapter<SublistOrderAdapter.ViewHolder> {

    private Context activity;
    private List<OrderItems> orderItemsList = new ArrayList<>();

    float totalAmount = 0;

    public SublistOrderAdapter(Context activity, List<OrderItems> orderItemsList) {
        this.activity = activity;
        this.orderItemsList = orderItemsList;
    }


    @Override
    public SublistOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist_order, parent, false);
        return new SublistOrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistOrderAdapter.ViewHolder holder, int position) {

        OrderItems orderItems = orderItemsList.get(position);

        if (orderItems.getItem_type().equalsIgnoreCase("DTA")) {

            holder.txtDesc.setText(orderItems.getStore_name());
            holder.txtQty.setText("");
        }
        if (orderItems.getItem_type().equalsIgnoreCase("DTE")) {
            holder.txtDesc.setText(orderItems.getDishdasha_material());
            holder.txtQty.setText(orderItems.getItem_quantity() + "m");
        }

        if (orderItems.getItem_type().equalsIgnoreCase("A")) {

            String brandName = orderItems.getBrandname() != null ? orderItems.getBrandname() : "";
            String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
            String colorName = orderItems.getColor_name() != null ? orderItems.getColor_name() : "";

            holder.txtDesc.setText(brandName + " - " + sizeName + " - " + colorName);
            holder.txtQty.setText(orderItems.getItem_quantity());
        }

        if (orderItems.getItem_type().equalsIgnoreCase("DB")) {
            String brandName = orderItems.getBrandname() != null ? orderItems.getBrandname() : "";
            String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
            String colorName = orderItems.getColor_name() != null ? orderItems.getColor_name() : "";

            holder.txtDesc.setText(brandName + " - " + sizeName + " - " + colorName);
            holder.txtQty.setText(orderItems.getItem_quantity());
        }

        holder.txtPrice.setText(orderItems.getPrice() + " KWD");

        totalAmount += Float.parseFloat(orderItems.getPrice());
    }

    @Override
    public int getItemCount() {
        return orderItemsList != null ? orderItemsList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDesc)
        TextView txtDesc;

        @BindView(R.id.txtQty)
        TextView txtQty;

        @BindView(R.id.txtPrice)
        TextView txtPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
