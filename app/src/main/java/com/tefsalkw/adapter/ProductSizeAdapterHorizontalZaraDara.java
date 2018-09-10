package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.activity.DaraAbayaProductDetailsActivity;
import com.tefsalkw.models.ZaraDaraSizeModel;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 20-03-2018.
 */

public class ProductSizeAdapterHorizontalZaraDara extends RecyclerView.Adapter<ProductSizeAdapterHorizontalZaraDara.ViewHolder> {

    public List<ZaraDaraSizeModel> productSizesList;
    private Activity activity;


    public ProductSizeAdapterHorizontalZaraDara(List<ZaraDaraSizeModel> productSizesList, Activity activity) {
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


        final ZaraDaraSizeModel sizes = productSizesList.get(position);

        holder.sizeText.setText(sizes.getSize());


        if (position == TefalApp.getInstance().getPosition()) {


            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);

        } else {


            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);

        }


        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setPosition(position);
                notifyDataSetChanged();

                //ZaraDaraSizeModel zaraDaraSizeModel = productSizesList.get(position).getSizes();

                DaraAbayaProductDetailsActivity zaaraDaraaActivity = (DaraAbayaProductDetailsActivity) activity;
                zaaraDaraaActivity.showSelectedSizeData(sizes);

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
