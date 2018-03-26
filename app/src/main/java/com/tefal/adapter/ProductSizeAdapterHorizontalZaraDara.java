package com.tefal.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.Models.Colors;
import com.tefal.Models.ZaraDaraSizeModel;
import com.tefal.R;
import com.tefal.activity.AccessoryProductDetailsActivity;
import com.tefal.activity.ZaaraDaraaActivity;
import com.tefal.app.TefalApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 20-03-2018.
 */

public class ProductSizeAdapterHorizontalZaraDara  extends RecyclerView.Adapter<ProductSizeAdapterHorizontalZaraDara.ViewHolder> {

    private List<Colors> productSizesList;
    private Activity activity;

    public boolean isSet = false;


    public ProductSizeAdapterHorizontalZaraDara(List<Colors> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductSizeAdapterHorizontalZaraDara.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontalZaraDara.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ProductSizeAdapterHorizontalZaraDara.ViewHolder holder, final int position) {

        // System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        //System.out.println("productSizesList=======" + productSizesList.get(position).getSizes().get(0).getSize());
        ZaraDaraSizeModel model =  productSizesList.get(position).getSizes().get(0);

        holder.sizeText.setText(model.getSize());

        System.out.println("getCurrentColorText===" + TefalApp.getInstance().getCurrentColorText());
        if (productSizesList.get(position).getColor().equalsIgnoreCase(TefalApp.getInstance().getCurrentColorText())) {

            holder.sizeText.setEnabled(true);

            if(!isSet)
            {
                TefalApp.getInstance().setPosition(position);
                isSet = true;
            }

            if (position == TefalApp.getInstance().getPosition()) {
                holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
                holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);
            }
            else
            {
                holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
                holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);
            }


        } else {


            holder.sizeText.setEnabled(false);
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_roundselected);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setPosition(position);
                notifyDataSetChanged();


                ZaaraDaraaActivity zaaraDaraaActivity = (ZaaraDaraaActivity)activity;
                zaaraDaraaActivity.showSelectedSizeData(position);





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
