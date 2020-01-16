package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lid.lib.LabelImageView;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.utils.SessionManager;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        LabelImageView iv_pattern;
        @BindView(R.id.promotionImageLabel)
        LabelImageView promotionImageLabel;
        @BindView(R.id.discountImageLabel)
        LabelImageView discountImageLabel;
        @BindView(R.id.name_text)
        TextView name_text;
        @BindView(R.id.prize_text)
        TextView prize_text;
        @BindView(R.id.imageView11)
        ImageView promoright;
        @BindView(R.id.imageView14)
        ImageView discountright;
        @BindView(R.id.main_layout)
        ConstraintLayout main_layout;//TODO change layout LabelLinearView to ConstraintLayout
        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;
        @BindView(R.id.txt_discount_amount)
        TextView txt_discount_amount;
        @BindView(R.id.store_text)
        TextView store_text;
        @BindView(R.id.textView17)
        TextView promo;
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

        if (textileModels.get(position).getPromo().size() != 0){
            holder.promo.setVisibility(View.VISIBLE);
            holder.promoright.setVisibility(View.VISIBLE);
        } else {
            holder.promo.setVisibility(View.GONE);
            holder.promoright.setVisibility(View.GONE);
        }
        if (session.getKeyLang().equals("Arabic")) {
            holder.promoright.setRotation(-90);
            holder.discountright.setRotation(90);
            holder.promo.setRotation(-45);
            holder.txt_discount_amount.setRotation(45);
        }
        if (session.getKeyLang().equals("Arabic")) {
            holder.promo.setText("الترويجي");
            holder.name_text.setText(textileModels.get(holder.getAdapterPosition()).getDishdasha_product_name_arabic());
        } else {
            holder.promo.setText("PROMO");
            holder.name_text.setText(textileModels.get(holder.getAdapterPosition()).getDishdasha_product_name());
        }
        //String price = String.format(Locale.ENGLISH, "x%d", Double.parseDouble(textileModels.get(holder.getAdapterPosition()).getPrice())) + "KWD";
        String price = nFormate(Double.parseDouble(textileModels.get(holder.getAdapterPosition()).getPrice()));
        String textPrice = price + "KWD";
        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(), "fonts/Lato-Bold.ttf");
        holder.prize_text.setTypeface(custom_font);
        holder.prize_text.setText(textPrice);
        if (textileModels.get(holder.getAdapterPosition()).getProduct_discount() == null || textileModels.get(holder.getAdapterPosition()).getProduct_discount().equals("")) {
            holder.txt_discount_amount.setVisibility(View.GONE);
            holder.discountright.setVisibility(View.GONE);
        } else {
            String discount_amount = "";
            discount_amount = textileModels.get(holder.getAdapterPosition()).getProduct_discount();
            if (discount_amount.equalsIgnoreCase("0.0") || discount_amount == null) {
                holder.discountright.setVisibility(View.GONE);
                holder.txt_discount_amount.setVisibility(View.GONE);
            } else {
                discount_amount = discount_amount.replace("%", "");
                holder.discountright.setVisibility(View.VISIBLE);
                holder.txt_discount_amount.setVisibility(View.VISIBLE);
                if (session.getKeyLang().equalsIgnoreCase("Arabic")) {
                    holder.txt_discount_amount.setText(discount_amount + "٪إيقاف");
                } else {
                    holder.txt_discount_amount.setText(discount_amount + "%OFF");
                }
            }
        }
        holder.store_text.setText(textileModels.get(position).getStore_name());
        holder.text_max_delivery_days.setText(textileModels.get(holder.getAdapterPosition()).getMax_delivery_days());
        System.out.println("ITEM ID===" + textileModels.get(holder.getAdapterPosition()).getDishdasha_attribute_id());
        holder.main_layout.setOnClickListener(new View.OnClickListener(){
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
//                holder.main_layout.performClick();
            }
        }
    }
    public static String nFormate(double d) {
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(10);
        String st = nf.format(d);
        return st;
    }
    @Override
    public int getItemCount() {
        return textileModels.size();
    }
}