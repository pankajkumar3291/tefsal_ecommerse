package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.models.Order_items;
import com.tefsalkw.models.Tailor_services;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SublistOrderAdapter extends RecyclerView.Adapter<SublistOrderAdapter.ViewHolder> {

    private Context activity;
    private List<Order_items> orderItemsList = new ArrayList<>();
    private List<Tailor_services> tailorServicesList = new ArrayList<>();
    int itemType = -1;

    public SublistOrderAdapter(Context activity, int itemType, List<Order_items> orderItemsList, List<Tailor_services> tailorServicesList) {
        this.activity = activity;
        this.orderItemsList = orderItemsList;
        this.tailorServicesList = tailorServicesList;
        this.itemType = itemType;
    }


    @Override
    public SublistOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist_order, parent, false);
        return new SublistOrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistOrderAdapter.ViewHolder holder, int position) {

        Log.e("itemType",itemType + "");
        if (itemType == 1) {
            Tailor_services orderItems = tailorServicesList.get(position);

            holder.txtDesc.setText(orderItems.getServiceName());
            holder.txtQty.setText("");
            holder.txtPrice.setText(orderItems.getPrice() + " KWD");
        } else {
            Order_items orderItems = orderItemsList.get(position);

            if (orderItems.getItem_type().equalsIgnoreCase("DTE")) {

                holder.txtDesc.setText(orderItems.getProduct_name());
                holder.txtQty.setText(orderItems.getItem_quantity() + "m");
            }

            if (orderItems.getItem_type().equalsIgnoreCase("A")) {

                String prodName = orderItems.getProduct_name() != null ? orderItems.getProduct_name() : "";
                String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
                String colorName = orderItems.getColor() != null ? orderItems.getColor() : "";

                holder.txtDesc.setText(prodName + " - " + sizeName + " - " + colorName);
                holder.txtQty.setText(orderItems.getItem_quantity());
            }

            if (orderItems.getItem_type().equalsIgnoreCase("DB")) {
                String prodName = orderItems.getProduct_name() != null ? orderItems.getProduct_name() : "";
                String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
                String colorName = orderItems.getColor() != null ? orderItems.getColor() : "";

                holder.txtDesc.setText(prodName + " - " + sizeName + " - " + colorName);
                holder.txtQty.setText(orderItems.getItem_quantity());
            }

            holder.txtPrice.setText(orderItems.getItem_price() + " KWD");

        }


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
