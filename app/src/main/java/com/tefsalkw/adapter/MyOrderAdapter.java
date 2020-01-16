package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.OrderDetailsActivity;
import com.tefsalkw.models.OrderRecord;
import com.tefsalkw.utils.DateTimeHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/13/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Activity activity;
    private List<OrderRecord> myOrderModel;
    private boolean isArabic;

    public MyOrderAdapter(Activity activity, List<OrderRecord> myOrderModel, boolean isArabic) {
        this.activity = activity;
        this.myOrderModel = myOrderModel;
        this.isArabic = isArabic;
    }

    public String getArabicString(String status) {

        if (status.contains("Pending")) {
            status = " جاري التجهيز";
        }
        if (status.contains("Out")) {
            status = "جاري التوصيل";
        }

        if (status.contains("Delivered")) {
            status = "تم التوصيل";
        }

        if (status.contains("Canceled")) {
            status = "الطلب ملغى";
        }

        if (status.contains("Returned")) {
            status = "مسترجع";
        }

        return status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorder_item, parent, false);
        return new MyOrderAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String dateTimeIs = myOrderModel.get(position).getCreated_at();

        if (dateTimeIs != null) {
//            holder.txtOrderDate.setTypeface(Typeface.createFromAsset(activity.getAssets(),"fonts/Lato-Bold.ttf"));
            holder.txtOrderDate.setText(DateTimeHelper.getFormattedDate(myOrderModel.get(position).getCreated_at()));
        }

        holder.txtOrderId.setText("#" + myOrderModel.get(position).getOrder_id());
        holder.txtOrderAmount.setText(myOrderModel.get(position).getAmount());


        if (isArabic) {
            holder.btnStatus.setText(getArabicString(myOrderModel.get(position).getOrder_status() + ""));
        } else {
            holder.btnStatus.setText((myOrderModel.get(position).getOrder_status() + "").toUpperCase());
        }


        if (myOrderModel.get(position).getOrder_status() != null && myOrderModel.get(position).getOrder_status().toLowerCase().equalsIgnoreCase("pending")) {
            holder.btnStatus.setBackground(activity.getResources().getDrawable(R.drawable.my_button_bg));
            holder.btnStatus.setTextColor(activity.getResources().getColor(R.color.colorAccent));
        }

        if (myOrderModel.get(position).getOrder_status() != null && myOrderModel.get(position).getOrder_status().toLowerCase().equalsIgnoreCase("completed")) {
            holder.btnStatus.setBackground(activity.getResources().getDrawable(R.drawable.my_button_bg_round));
            holder.btnStatus.setTextColor(activity.getResources().getColor(R.color.colorWhite));
        }


        holder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, OrderDetailsActivity.class)
                        .putExtra("OrderRecord", myOrderModel.get(holder.getAdapterPosition())));

            }
        });


    }


    @Override
    public int getItemCount() {
        return myOrderModel.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtOrderDate)
        TextView txtOrderDate;

        @BindView(R.id.txtOrderId)
        TextView txtOrderId;

        @BindView(R.id.txtOrderAmount)
        TextView txtOrderAmount;

        @BindView(R.id.btnStatus)
        Button btnStatus;

        @BindView(R.id.rlParent)
        RelativeLayout rlParent;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }


}
