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
import com.tefsalkw.models.OrderRecordCustom;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {


    private Activity activity;
    private List<OrderRecordCustom> orderItems;

    SublistOrderAdapter sublistAdapter;

    public OrderDetailsAdapter(Activity activity, List<OrderRecordCustom> orderItems) {
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

            OrderRecordCustom orderRecordCustom = orderItems.get(position);

            holder.txtStoreName.setText(orderRecordCustom.getStoreName());
            holder.txtOrderItemSerial.setText("ORDER ITEM #" + (position + 1));
            holder.txtTotalAmount.setText(orderRecordCustom.getTotalAmount() + " KWD");
            holder.txtOrderAction.setText(Html.fromHtml("<i>" + orderRecordCustom.getDelivery_status() + "</i>"));
            holder.txtExpectedDelivery.setText("Expected Delivery: " + orderRecordCustom.getExpected_delivery_date());
            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);
            GlideApp.with(activity).load(orderRecordCustom.getStoreImage()).apply(options).into(holder.imgStore);

            if (orderItems.get(position).getItemType().equalsIgnoreCase("DTA") || orderItems.get(position).getItemType().equalsIgnoreCase("DTE")) {

                int itemCount = 0;

                //fill
                if (orderRecordCustom.getTextileItems() != null && orderRecordCustom.getTextileItems().size() > 0) {
                    itemCount += orderRecordCustom.getTextileItems().size();

                    holder.llSectionOne.setVisibility(View.VISIBLE);

                    holder.lblFirst.setText("Textiles:");

                    sublistAdapter = new SublistOrderAdapter(activity, orderRecordCustom.getTextileItems());
                    holder.rcvFirst.setAdapter(sublistAdapter);


                } else {
                    holder.llSectionOne.setVisibility(View.GONE);
                    holder.lblFirst.setText("");
                }

                if (orderRecordCustom.getTailorItems() != null && orderRecordCustom.getTailorItems().size() > 0) {
                    itemCount += orderRecordCustom.getTailorItems().size();
                    holder.llSectionTwo.setVisibility(View.VISIBLE);

                    holder.lblSecond.setText("Tailors:");

                    sublistAdapter = new SublistOrderAdapter(activity, orderRecordCustom.getTailorItems());

                    holder.rcvSecond.setAdapter(sublistAdapter);


                } else {
                    holder.llSectionTwo.setVisibility(View.GONE);
                    holder.lblSecond.setText("");
                }

                holder.txtItemCount.setText(itemCount + "x Dishdasha");

                holder.txtStyleUsed.setText(Html.fromHtml("<i>" + "Style Used: " + orderRecordCustom.getStyleName() + "</i>"));
                holder.txtOrderItemType.setText("TAILOR & TEXTILE");

            }


            if (orderItems.get(position).getItemType().equalsIgnoreCase("DB")) {

                if (orderRecordCustom.getOtherItems() != null && orderRecordCustom.getOtherItems().size() > 0) {
                    holder.txtItemCount.setText(orderRecordCustom.getOtherItems().size() > 1 ? orderRecordCustom.getOtherItems().size() + " Items" : orderRecordCustom.getOtherItems().size() + " Item");
                    holder.lblFirst.setText("Item Description");
                    holder.lblSecond.setText("");
                }


                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.llSectionTwo.setVisibility(View.GONE);
                holder.txtStyleUsed.setVisibility(View.GONE);
                holder.txtOrderItemType.setText("DARA / ABAYA");

                sublistAdapter = new SublistOrderAdapter(activity, orderRecordCustom.getOtherItems());

                holder.rcvFirst.setAdapter(sublistAdapter);


            }


            if (orderItems.get(position).getItemType().equalsIgnoreCase("A")) {
                if (orderRecordCustom.getOtherItems() != null && orderRecordCustom.getOtherItems().size() > 0) {
                    holder.txtItemCount.setText(orderRecordCustom.getOtherItems().size() > 1 ? orderRecordCustom.getOtherItems().size() + " Items" : orderRecordCustom.getOtherItems().size() + " Item");
                    holder.lblFirst.setText("Item Description:");
                    holder.lblSecond.setText("");

                }

                holder.llSectionOne.setVisibility(View.VISIBLE);
                holder.llSectionTwo.setVisibility(View.GONE);
                holder.txtStyleUsed.setVisibility(View.GONE);
                holder.txtOrderItemType.setText("ACCESSORIES");

                sublistAdapter = new SublistOrderAdapter(activity, orderRecordCustom.getOtherItems());

                holder.rcvFirst.setAdapter(sublistAdapter);

            }


        } catch (Exception exc) {

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



