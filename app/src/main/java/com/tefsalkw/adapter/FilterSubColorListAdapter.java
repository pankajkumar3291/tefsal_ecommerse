package com.tefsalkw.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.models.ColorsRecordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 1/3/2018.
 */

public class FilterSubColorListAdapter extends RecyclerView.Adapter<FilterSubColorListAdapter.ViewHolder> {
    private ArrayList<ColorsRecordModel> colorsRecordModelArrayList;
    private FragmentTextileProducts activity;

    public FilterSubColorListAdapter(ArrayList<ColorsRecordModel> colorsRecordModelArrayList, FragmentTextileProducts activity) {
        this.activity = activity;
        this.colorsRecordModelArrayList = colorsRecordModelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new FilterSubColorListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // Picasso.with(activity).load(colorsRecordModelArrayList.get(position).getImage()).into(holder.color);

        try {
            holder.colorText.setText(colorsRecordModelArrayList.get(position).getName());

            LayerDrawable layerDrawable = (LayerDrawable) activity.getResources().getDrawable(R.drawable.round_image_background_for_color);


            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                    .findDrawableByLayerId(R.id.item);

            String hexColor = colorsRecordModelArrayList.get(position).getHexa_value();

            if (hexColor != null) {
                gradientDrawable.setColor(Color.parseColor(hexColor));
            }

            holder.color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (FragmentTextileProducts.colorWindow != null) {
                        FragmentTextileProducts.colorWindow.dismiss();
                    }
                    if (colorsRecordModelArrayList != null) {

                        activity.loadSubColorFilteredProducts(colorsRecordModelArrayList.get(position));


                    }

                }
            });
        } catch (Exception exc) {

        }


    }

    @Override
    public int getItemCount() {
        return colorsRecordModelArrayList != null ? colorsRecordModelArrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color)
        ImageView color;
        @BindView(R.id.colorText)
        TextView colorText;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
