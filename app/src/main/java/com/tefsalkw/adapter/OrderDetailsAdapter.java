package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.models.Order_items;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {


    private Activity activity;
    private List<Order_items> orderItems;

    SublistOrderAdapter sublistAdapter;
    SublistTailorServiceAdapter sublistTailorServiceAdapter;
    public OrderDetailsAdapter(Activity activity, List<Order_items> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_details, parent, false);
        return new OrderDetailsAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(OrderDetailsAdapter.ViewHolder holder, int position) {

        try {

            Order_items orderRecordCustom = orderItems.get(position);

            String orderStatus = orderRecordCustom.getStatus() != null ? orderRecordCustom.getStatus() : "Pending";

            holder.txtStoreName.setText(orderRecordCustom.getStore_name());
            holder.txtOrderItemSerial.setText("ORDER ITEM #" + (position + 1));

            holder.txtOrderAction.setText(Html.fromHtml("<i>" + orderStatus + "</i>"));
            holder.txtExpectedDelivery.setText("Expected Delivery: " + orderRecordCustom.getExpected_delivery_date());


            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(activity).load(orderRecordCustom.getImage()).apply(options).into(holder.imgStore);


            if (orderRecordCustom.getItem_type().equalsIgnoreCase("DTA")) {

                holder.txtTotalAmount.setText(orderRecordCustom.getGrand_total() + " KWD");
                holder.txtOrderItemType.setText("TAILOR & TEXTILE");
                holder.txtStyleUsed.setText(Html.fromHtml("<i>" + "Style Used: " + orderRecordCustom.getStyleName() + "</i>"));
                holder.txtItemCount.setText(orderRecordCustom.getTailor_total_qty() + "x Dishdasha");

                //Textile section
                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.lblFirst.setText("Textiles:");


                List<Order_items> orderItemsList = new ArrayList<>();
                orderItemsList.add(orderRecordCustom);

                sublistAdapter = new SublistOrderAdapter(activity, orderItemsList);
                holder.rcvFirst.setAdapter(sublistAdapter);

                //Tailor section
                holder.llSectionTwo.setVisibility(View.VISIBLE);
                holder.lblSecond.setText("Tailors:");

                sublistTailorServiceAdapter = new SublistTailorServiceAdapter(activity, orderRecordCustom.getTailor_services());
                holder.rcvSecond.setAdapter(sublistTailorServiceAdapter);


            }


            if (orderRecordCustom.getItem_type().equalsIgnoreCase("DTE")) {

                holder.txtTotalAmount.setText(orderRecordCustom.getTotal_amount() + " KWD");
                holder.txtItemCount.setText(orderRecordCustom.getItem_quantity() + "x Dishdasha");
                holder.txtOrderItemType.setText("TEXTILE");
                holder.txtStyleUsed.setText(Html.fromHtml("<i>" + "Style Used: " + orderRecordCustom.getStyleName() + "</i>"));

                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.llSectionTwo.setVisibility(View.GONE);
                holder.lblSecond.setText("");
                holder.txtStyleUsed.setVisibility(View.VISIBLE);
                holder.lblFirst.setText("Textiles:");


                List<Order_items> orderItemsList = new ArrayList<>();
                orderItemsList.add(orderRecordCustom);
                sublistAdapter = new SublistOrderAdapter(activity,  orderItemsList);
                holder.rcvFirst.setAdapter(sublistAdapter);

            }


            if (orderItems.get(position).getItem_type().equalsIgnoreCase("DB")) {

                holder.txtTotalAmount.setText(orderRecordCustom.getTotal_amount() + " KWD");
                holder.txtItemCount.setText(orderRecordCustom.getItem_quantity() + " Items");
                holder.lblFirst.setText("Item Description");
                holder.lblSecond.setText("");


                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.llSectionTwo.setVisibility(View.GONE);
                holder.txtStyleUsed.setVisibility(View.GONE);
                holder.txtOrderItemType.setText("DARA / ABAYA");

                List<Order_items> orderItemsList = new ArrayList<>();
                orderItemsList.add(orderRecordCustom);
                sublistAdapter = new SublistOrderAdapter(activity,  orderItemsList);
                holder.rcvFirst.setAdapter(sublistAdapter);

            }


            if (orderItems.get(position).getItem_type().equalsIgnoreCase("A")) {
                holder.txtTotalAmount.setText(orderRecordCustom.getTotal_amount() + " KWD");
                holder.txtItemCount.setText(orderRecordCustom.getItem_quantity() + " Items");
                holder.lblFirst.setText("Item Description:");
                holder.lblSecond.setText("");
                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.llSectionTwo.setVisibility(View.GONE);
                holder.txtStyleUsed.setVisibility(View.GONE);
                holder.txtOrderItemType.setText("ACCESSORIES");
                List<Order_items> orderItemsList = new ArrayList<>();
                orderItemsList.add(orderRecordCustom);

                sublistAdapter = new SublistOrderAdapter(activity,  orderItemsList);

                holder.rcvFirst.setAdapter(sublistAdapter);

            }


        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return orderItems != null ? orderItems.size() : 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txtOrderItemSerial)
        TextView txtOrderItemSerial;

        @BindView(R.id.txtOrderItemType)
        TextView txtOrderItemType;

        @BindView(R.id.txtOrderAction)
        TextView txtOrderAction;

        @BindView(R.id.txtExpectedDelivery)
        TextView txtExpectedDelivery;

        @BindView(R.id.imgStore)
        CircleImageView imgStore;


        @BindView(R.id.txtStoreName)
        TextView txtStoreName;


        @BindView(R.id.txtItemCount)
        TextView txtItemCount;
        @BindView(R.id.txtTotalAmount)
        TextView txtTotalAmount;


        @BindView(R.id.lblFirst)
        TextView lblFirst;

        @BindView(R.id.rcvFirst)
        RecyclerView rcvFirst;


        @BindView(R.id.lblSecond)
        TextView lblSecond;

        @BindView(R.id.rcvSecond)
        RecyclerView rcvSecond;


        @BindView(R.id.txtStyleUsed)
        TextView txtStyleUsed;

        @BindView(R.id.llSectionOne)
        LinearLayout llSectionOne;

        @BindView(R.id.llSectionTwo)
        LinearLayout llSectionTwo;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            rcvFirst.setLayoutManager(layoutManager);
            rcvFirst.setItemAnimator(new DefaultItemAnimator());

            LinearLayoutManager layoutManager2 = new LinearLayoutManager(activity);
            layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

            rcvSecond.setLayoutManager(layoutManager2);
            rcvSecond.setItemAnimator(new DefaultItemAnimator());

        }
    }


}



