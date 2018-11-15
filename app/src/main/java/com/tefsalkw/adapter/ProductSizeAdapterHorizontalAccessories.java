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
import com.tefsalkw.models.AccSizes;
import com.tefsalkw.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 03/13/2018.
 */

public class ProductSizeAdapterHorizontalAccessories extends RecyclerView.Adapter<ProductSizeAdapterHorizontalAccessories.ViewHolder> {

    private List<AccSizes> productSizesList;
    private Activity activity;
    SessionManager session;

    public ProductSizeAdapterHorizontalAccessories(List<AccSizes> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;
        session = new SessionManager(activity);

    }


    @Override
    public ProductSizeAdapterHorizontalAccessories.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontalAccessories.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ProductSizeAdapterHorizontalAccessories.ViewHolder holder, int position) {


        final AccSizes model = productSizesList.get(position);


        if (model != null && model.getSize() != null) {
            if (session.getKeyLang().equals("Arabic")) {
                holder.sizeText.setText(model.getSize_arabic());
            } else {
                holder.sizeText.setText(model.getSize());
            }
        } else {
            holder.sizeText.setText("Default");
        }


        if (position == TefalApp.getInstance().getCurrentSizePositionIs()) {


            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);

            AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
            accessoryProductDetailsActivity.showSelectedSizeData();


        } else {


            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_roundselected);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));

        }


        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setCurrentSizePositionIs(position);
                notifyDataSetChanged();

                AccessoryProductDetailsActivity accessoryProductDetailsActivity = (AccessoryProductDetailsActivity) activity;
                accessoryProductDetailsActivity.showSelectedSizeData();


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
