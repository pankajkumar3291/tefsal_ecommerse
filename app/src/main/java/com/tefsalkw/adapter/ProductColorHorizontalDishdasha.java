package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.Colors;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductColorHorizontalDishdasha extends RecyclerView.Adapter<ProductColorHorizontalDishdasha.ViewHolder> {

    private List<Colors> productSizesList;
    private Activity activity;


    public ProductColorHorizontalDishdasha(List<Colors> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;


    }


    @Override
    public ProductColorHorizontalDishdasha.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductColorHorizontalDishdasha.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ProductColorHorizontalDishdasha.ViewHolder holder, int position) {


        String subColorIs = productSizesList.get(position).getSub_color();

        if (subColorIs != null) {
            holder.sizeText.setText(subColorIs);

        } else {

            String colorIs = productSizesList.get(position).getColor();
            if (colorIs != null) {
                holder.sizeText.setText(colorIs);
            } else {
                holder.sizeText.setText("Default");
            }

        }

        if (position == TefalApp.getInstance().getAccColorPosition()) {

            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg_round);

            TextileDetailActivity textileDetailActivity = (TextileDetailActivity) activity;
            textileDetailActivity.showSizeOnColorSelection(productSizesList.get(position));


        } else {

            holder.sizeText.setBackgroundResource(R.drawable.my_button_bg);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TefalApp.getInstance().setAccColorPosition(position);
                notifyDataSetChanged();

                TextileDetailActivity textileDetailActivity = (TextileDetailActivity) activity;
                textileDetailActivity.showSizeOnColorSelection(productSizesList.get(position));


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


