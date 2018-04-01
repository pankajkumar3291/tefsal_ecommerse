package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.Models.Sizes;
import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.app.TefalApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/26/2018.
 */

public class ProductColorAdapterHorizontalAccesories extends RecyclerView.Adapter<ProductColorAdapterHorizontalAccesories.ViewHolder> {

    private List<Sizes> productSizesList;
    private Activity activity;


    public ProductColorAdapterHorizontalAccesories(List<Sizes> productSizesList, Activity activity) {
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

        // System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        System.out.println("productSizesList=======" + productSizesList.get(position).getColors());
        String colorIs = productSizesList.get(position).getColors().get(0).getColor();

        if (colorIs != null) {
            holder.sizeText.setText(productSizesList.get(position).getColors().get(0).getColor());

        } else {
            holder.sizeText.setText("Default");
        }

        if (position == TefalApp.getInstance().getSetAccColorPosition()) {

            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);


        } else {

            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TefalApp.getInstance().setSetAccColorPosition(position);
                notifyDataSetChanged();

                AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
                accessoryProductDetailsActivity.showSizeOnColorSelection(position);


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

