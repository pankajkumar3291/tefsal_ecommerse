package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishdashaTextileProductAdapter extends RecyclerView.Adapter<DishdashaTextileProductAdapter.ViewHolder> {

    private Activity activity;
    public static List<TextileProductModel> textileModels = new ArrayList<>();
    private Context context;

    SessionManager session;
    String storeId, flag;

    public DishdashaTextileProductAdapter(Activity activity, List<TextileProductModel> textileModels, String storeId, String flag) {
        this.activity = activity;
        this.textileModels = textileModels;
        this.storeId = storeId;
        this.flag = flag;
        session = new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textile_product_card, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pattern)
        ImageView iv_pattern;

        @BindView(R.id.name_text)
        TextView name_text;

        @BindView(R.id.prize_text)
        TextView prize_text;

        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;

        @BindView(R.id.txt_discount_amount)
        TextView txt_discount_amount;

        @BindView(R.id.store_text)
        TextView store_text;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        RequestOptions options = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.no_image_placeholder_grid)
                .error(R.drawable.no_image_placeholder_grid);

        String imgUrl = textileModels.get(position).getDefault_image();
        GlideApp.with(activity).load(imgUrl).apply(options).into(holder.iv_pattern);

        if(session.getKeyLang().equals("Arabic"))
        {
            holder.name_text.setText(textileModels.get(holder.getAdapterPosition()).getDishdasha_product_name_arabic());
        }
        else
        {
            holder.name_text.setText(textileModels.get(holder.getAdapterPosition()).getDishdasha_product_name());
        }

        holder.prize_text.setText(textileModels.get(holder.getAdapterPosition()).getPrice() + " KWD");

        if (textileModels.get(holder.getAdapterPosition()).getProduct_discount() == null || textileModels.get(holder.getAdapterPosition()).getProduct_discount().equals("")) {

            holder.txt_discount_amount.setVisibility(View.GONE);
        } else {
            String discount_amount = "";
            discount_amount = textileModels.get(holder.getAdapterPosition()).getProduct_discount();
            holder.txt_discount_amount.setText(discount_amount);
        }

        holder.store_text.setText(textileModels.get(position).getStore_name());

        holder.text_max_delivery_days.setText(textileModels.get(holder.getAdapterPosition()).getMax_delivery_days());
        // System.out.println("ITEM ID==="+textileModels.get(holder.getAdapterPosition()).getDishdasha_attribute_id());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("textileProductModel", new Gson().toJson(textileModels.get(position)));
                activity.startActivity(new Intent(activity, TextileDetailActivity.class)
                        .putExtra("storeID", storeId)
                        .putExtra("pos", position)
                        .putExtra("product_name", textileModels.get(holder.getAdapterPosition()).getProduct_name())
                        .putExtra("textileProductModel", textileModels.get(position)));


            }
        });

        Log.e("getFromPush", TefalApp.getInstance().getFromPush());
        Log.e("getStoreId", TefalApp.getInstance().getStoreId());
        Log.e("getProductId", TefalApp.getInstance().getProductId());
        if (TefalApp.getInstance().getFromPush().equals("yes")) {

            if (textileModels.get(position).getTefsal_product_id().equalsIgnoreCase(TefalApp.getInstance().getProductId())) {
                holder.main_layout.performClick();
            }

        }
    }


    @Override
    public int getItemCount() {
        return textileModels.size();
    }

}