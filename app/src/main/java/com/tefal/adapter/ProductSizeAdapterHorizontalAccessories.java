package com.tefal.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.Models.Sizes;
import com.tefal.R;
import com.tefal.activity.AccessoryProductDetailsActivity;
import com.tefal.app.TefalApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/13/2018.
 */

public class ProductSizeAdapterHorizontalAccessories extends RecyclerView.Adapter<ProductSizeAdapterHorizontalAccessories.ViewHolder> {

    private List<Sizes> productSizesList;
    private Activity activity;



    public ProductSizeAdapterHorizontalAccessories(List<Sizes> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductSizeAdapterHorizontalAccessories.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontalAccessories.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ProductSizeAdapterHorizontalAccessories.ViewHolder holder, final int position) {

        // System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        System.out.println("productSizesList=======" + productSizesList.get(position).getSize());
        holder.sizeText.setText(productSizesList.get(position).getSize());

        if (position == TefalApp.getInstance().getSetAccColorPosition()) {

            holder.sizeText.setEnabled(true);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);

        }
        else
        {
            holder.sizeText.setEnabled(false);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_roundselected);
        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setPosition(position);
                notifyDataSetChanged();

                AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
                accessoryProductDetailsActivity.showSelectedSizeData(position);


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
