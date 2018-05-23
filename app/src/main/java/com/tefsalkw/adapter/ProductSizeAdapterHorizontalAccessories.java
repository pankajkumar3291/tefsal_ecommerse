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
import com.tefsalkw.models.ZaraDaraSizeModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/13/2018.
 */

public class ProductSizeAdapterHorizontalAccessories extends RecyclerView.Adapter<ProductSizeAdapterHorizontalAccessories.ViewHolder> {

    private List<ZaraDaraSizeModel> productSizesList;
    private Activity activity;


    public ProductSizeAdapterHorizontalAccessories(List<ZaraDaraSizeModel> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductSizeAdapterHorizontalAccessories.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontalAccessories.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ProductSizeAdapterHorizontalAccessories.ViewHolder holder, int position) {


        final ZaraDaraSizeModel model = productSizesList.get(position);


        holder.sizeText.setText(model.getSize());


        if (model.getSize().equalsIgnoreCase(TefalApp.getInstance().getCurrentSizeText())) {

            holder.sizeText.setEnabled(true);

            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);


        } else {


            holder.sizeText.setEnabled(false);
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_roundselected);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));

        }

        holder.sizeText.setTag(model);
        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setCurrentSizePositionIs(position);
                notifyDataSetChanged();

               // AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
              //  accessoryProductDetailsActivity.showSelectedSizeData();


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
