package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.Models.MyOrderResponse;
import com.tefsalkw.Models.OrderRecord;
import com.tefsalkw.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/13/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>
{
    private Activity activity;
    private List<OrderRecord> myOrderModel;

    public MyOrderAdapter(Activity activity, List<OrderRecord> myOrderModel)
    {
        this.activity=activity;
        this.myOrderModel=myOrderModel;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item, parent, false);
        return new MyOrderAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.order_date_txt.setText(myOrderModel.get(position).getCreated_at().toString());
        holder.order_status_txt.setText(myOrderModel.get(position).getOrder_status().toString());


    }

    @Override
    public int getItemCount() {
        return myOrderModel.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.order_status_txt)
        TextView order_status_txt;

        @BindView(R.id.order_date_txt)
        TextView order_date_txt;

        @BindView(R.id.order_image)
        ImageView order_image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }
    }


}
