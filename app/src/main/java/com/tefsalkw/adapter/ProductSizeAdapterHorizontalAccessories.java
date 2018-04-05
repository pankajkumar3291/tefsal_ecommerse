package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.models.Sizes;
import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.app.TefalApp;

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


       final Sizes model = productSizesList.get(position);

        // System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        System.out.println("productSizesList=======" + model.getSize());

        holder.sizeText.setText(model.getSize());
        String colorIs = model.getColors().get(0).getColor();
        colorIs = colorIs != null ? colorIs : "Default";
        if (colorIs.equalsIgnoreCase(TefalApp.getInstance().getCurrentColorText())) {

            holder.sizeText.setEnabled(true);

            if (position == TefalApp.getInstance().getCurrentSizePositionIs()) {


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

        holder.sizeText.setTag(model);
        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setCurrentSizePositionIs(position);
                notifyDataSetChanged();

                AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
                accessoryProductDetailsActivity.showSelectedSizeData(position,model);


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
