package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTailorProducts;
import com.tefsalkw.models.SublistCartItems;
import com.tefsalkw.models.Tailor_services;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class SublistTailorAdapter  extends RecyclerView.Adapter<SublistTailorAdapter.ViewHolder> {

    private Context activity;
    private List<Tailor_services> tailorList = new ArrayList<>();



    public SublistTailorAdapter(Context activity, List<Tailor_services> tailorList) {
        this.activity = activity;
        this.tailorList = tailorList;

    }



    @Override
    public SublistTailorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.getcart_tailor_list, parent, false);
        return new SublistTailorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistTailorAdapter.ViewHolder holder, int position) {

        holder.txtTailorTitle.setText(tailorList.get(position).getServiceName());
        holder.txtTailorDesc.setText(tailorList.get(position).getPrice() + " / "+activity.getString(R.string.qty));

        holder.txtTailorQty.setText(activity.getString(R.string.qty) + tailorList.get(position).getQty() + " Dishdasha");


        holder.txtTailorPrice.setText(tailorList.get(position).getTotalAmount() + activity.getString(R.string.kwd));
        holder.txtTailorPrice.setTextColor(activity.getResources().getColor(R.color.colorRed));
        holder.txtTailorPriceDicounted.setVisibility(GONE);

        if((position+1) == tailorList.size())
        {
            holder.llSep.setVisibility(GONE);
        }
        else
        {
            holder.llSep.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return tailorList != null ? tailorList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTailorTitle)
        TextView txtTailorTitle;

        @BindView(R.id.txtTailorQty)
        TextView txtTailorQty;

        @BindView(R.id.txtTailorPrice)
        TextView txtTailorPrice;

        @BindView(R.id.txtTailorPriceDicounted)
        TextView txtTailorPriceDicounted;

        @BindView(R.id.llSep)
        View llSep;



        @BindView(R.id.txtTailorDesc)
        TextView txtTailorDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }
    }
}

