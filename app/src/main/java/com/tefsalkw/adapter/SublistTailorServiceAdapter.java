package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class SublistTailorServiceAdapter extends RecyclerView.Adapter<SublistTailorServiceAdapter.ViewHolder> {

    private Context activity;
    private List<Tailor_services> tailorServicesList = new ArrayList<>();

    public SublistTailorServiceAdapter(Context activity, List<Tailor_services> tailorServicesList) {
        this.activity = activity;

        this.tailorServicesList = tailorServicesList;

    }


    @Override
    public SublistTailorServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist_order, parent, false);
        return new SublistTailorServiceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistTailorServiceAdapter.ViewHolder holder, int position) {

        Tailor_services orderItems = tailorServicesList.get(position);

        holder.txtDesc.setText(orderItems.getServiceName());
        holder.txtQty.setText("");
        holder.txtPrice.setText(orderItems.getPrice() + " KWD");



    }

    @Override
    public int getItemCount() {
        return tailorServicesList != null ? tailorServicesList.size() : 0;
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
