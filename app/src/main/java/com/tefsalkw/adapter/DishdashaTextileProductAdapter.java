package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class DishdashaTextileProductAdapter extends RecyclerView.Adapter<DishdashaTextileProductAdapter.ViewHolder>
{

    private Activity activity;
    public static List<TextileProductModel> textileModels = new ArrayList<>();
    private Context context;



    String storeId,flag;

    public DishdashaTextileProductAdapter(Activity activity, List<TextileProductModel> textileModels, String storeId, String flag) {
        this.activity = activity;
        this.textileModels = textileModels;
        this.storeId = storeId;
        this.flag = flag;
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

        @BindView(R.id.txt_discount_off)
        TextView txt_discount_off;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        // This check is for whether the image is available or not..................................
        String discount_amount="";

        if(textileModels.get(holder.getAdapterPosition()).getProduct_image().length!=0)
        {
            Picasso.with(activity).load(textileModels.get(holder.getAdapterPosition()).getProduct_image()[0])
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid)
                    .into(holder.iv_pattern);
        }
        else
        {
            holder.iv_pattern.setImageResource(R.drawable.no_image_placeholder_grid);
        }
       System.out.println("PRODUCT IMAGE SIZE==="+textileModels.get(holder.getAdapterPosition()).getProduct_image().length);

        holder.name_text.setText(textileModels.get(holder.getAdapterPosition()).getProduct_name());
        holder.prize_text.setText(textileModels.get(holder.getAdapterPosition()).getPrice()+" KWD");

        if(textileModels.get(holder.getAdapterPosition()).getProduct_discount().equals(null)|| textileModels.get(holder.getAdapterPosition()).getProduct_discount().equals(""))
        {
            discount_amount="0%";
            holder.txt_discount_off.setVisibility(View.GONE);
            holder.txt_discount_amount.setVisibility(View.GONE);
        }
        else
        {
            discount_amount=textileModels.get(holder.getAdapterPosition()).getProduct_discount();
        }


        holder.txt_discount_amount.setText(discount_amount);

        holder.text_max_delivery_days.setText(textileModels.get(holder.getAdapterPosition()).getMax_delivery_days());
        // System.out.println("ITEM ID==="+textileModels.get(holder.getAdapterPosition()).getDishdasha_attribute_id());
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    activity.startActivity(new Intent(activity, TextileDetailActivity.class)
                            .putExtra("storeID", storeId)
                            .putExtra("pos", position)
                            .putExtra("product_name",textileModels.get(holder.getAdapterPosition()).getProduct_name())
                            .putExtra("textileProductModel",textileModels.get(position)));


            }
        });
    }


    @Override
    public int getItemCount() {
        return textileModels.size();
    }

}