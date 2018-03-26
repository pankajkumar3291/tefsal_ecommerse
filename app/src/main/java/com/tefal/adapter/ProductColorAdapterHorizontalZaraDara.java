package com.tefal.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.Models.ZaraDaraSizeModel;
import com.tefal.R;
import com.tefal.activity.AccessoryProductDetailsActivity;
import com.tefal.activity.ZaaraDaraaActivity;
import com.tefal.app.TefalApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/26/2018.
 */

public class ProductColorAdapterHorizontalZaraDara extends RecyclerView.Adapter<ProductColorAdapterHorizontalZaraDara.ViewHolder> {

    private List<ZaraDaraSizeModel> productSizesList;
    private Activity activity;


    public ProductColorAdapterHorizontalZaraDara(List<ZaraDaraSizeModel> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductColorAdapterHorizontalZaraDara.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductColorAdapterHorizontalZaraDara.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ProductColorAdapterHorizontalZaraDara.ViewHolder holder, final int position) {

        // System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        System.out.println("productSizesList=======" + productSizesList.get(position).getColor());
        holder.sizeText.setText(productSizesList.get(position).getColor());

        if (position == TefalApp.getInstance().getColorPosition()) {

            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);


        } else {

            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TefalApp.getInstance().setColorPosition(position);
                notifyDataSetChanged();



                if (activity.getClass().getSimpleName().equalsIgnoreCase("ZaaraDaraaActivity")) {
                    ZaaraDaraaActivity zaaraDaraaActivity = (ZaaraDaraaActivity) activity;
                    zaaraDaraaActivity.showSizeOnColorSelection(productSizesList.get(position));
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return productSizesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sizeText)
        TextView sizeText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

