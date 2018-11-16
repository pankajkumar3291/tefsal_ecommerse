package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.models.Order_items;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SublistOrderAdapter extends RecyclerView.Adapter<SublistOrderAdapter.ViewHolder> {

    private Context activity;
    private List<Order_items> orderItemsList = new ArrayList<>();
    SessionManager session;

    public SublistOrderAdapter(Context activity, List<Order_items> orderItemsList) {
        this.activity = activity;
        this.orderItemsList = orderItemsList;
        session = new SessionManager(activity);
    }


    @Override
    public SublistOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist_order, parent, false);
        return new SublistOrderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistOrderAdapter.ViewHolder holder, int position) {


        Order_items orderItems = orderItemsList.get(position);

        String prodName = orderItems.getProduct_name() != null ? orderItems.getProduct_name() : "";

        if (session.getKeyLang().equals("Arabic")) {
            prodName = orderItems.getProduct_name() != null ? orderItems.getProduct_name() : "";
        } else {
            prodName = orderItems.getProduct_name() != null ? orderItems.getProduct_name() : "";
        }

        if (orderItems.getItem_type().equalsIgnoreCase("DTA")) {

            holder.txtDesc.setText(prodName);
            holder.txtQty.setText(orderItems.getItem_quantity() + "" + activity.getString(R.string.m_small));
        }


        if (orderItems.getItem_type().equalsIgnoreCase("DTE")) {

            holder.txtDesc.setText(prodName);
            holder.txtQty.setText(orderItems.getItem_quantity() + "" + activity.getString(R.string.m_small));
        }

        if (orderItems.getItem_type().equalsIgnoreCase("A")) {


            String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
            String colorName = orderItems.getColor() != null ? orderItems.getColor() : "";

            holder.txtDesc.setText(prodName + " - " + sizeName + " - " + colorName);
            holder.txtQty.setText(orderItems.getItem_quantity());
        }

        if (orderItems.getItem_type().equalsIgnoreCase("DB")) {

            String sizeName = orderItems.getSize() != null ? orderItems.getSize() : "";
            String colorName = orderItems.getColor() != null ? orderItems.getColor() : "";

            holder.txtDesc.setText(prodName + " - " + sizeName + " - " + colorName);
            holder.txtQty.setText(orderItems.getItem_quantity());
        }

        holder.txtPrice.setText(orderItems.getItem_price() + " " + activity.getString(R.string.kwd));


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
