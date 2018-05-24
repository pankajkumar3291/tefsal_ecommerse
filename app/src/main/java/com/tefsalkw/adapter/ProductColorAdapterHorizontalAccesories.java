package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.ColorsAcc;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/26/2018.
 */

public class ProductColorAdapterHorizontalAccesories extends RecyclerView.Adapter<ProductColorAdapterHorizontalAccesories.ViewHolder> {

    private List<ColorsAcc> productSizesList;
    private Activity activity;


    public ProductColorAdapterHorizontalAccesories(List<ColorsAcc> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductColorAdapterHorizontalAccesories.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductColorAdapterHorizontalAccesories.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ProductColorAdapterHorizontalAccesories.ViewHolder holder, final int position) {


        String colorIs = productSizesList.get(position).getColor();

        if (colorIs != null) {
            holder.sizeText.setText(colorIs);

        } else {
            holder.sizeText.setText("Default");
        }

        if (position == TefalApp.getInstance().getAccColorPosition()) {

            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);
            TefalApp.getInstance().setCurrentSizeText(productSizesList.get(position).getSizes().getSize());

            AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
            accessoryProductDetailsActivity.showSizeOnColorSelection(productSizesList.get(position));


        } else {

            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TefalApp.getInstance().setAccColorPosition(position);
                notifyDataSetChanged();

                AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
                accessoryProductDetailsActivity.showSizeOnColorSelection(productSizesList.get(position));


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


    public void doManualClick() {


    }


}

