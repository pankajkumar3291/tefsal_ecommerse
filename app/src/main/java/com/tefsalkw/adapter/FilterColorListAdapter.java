package com.tefsalkw.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.models.ColorRecordFromDishdashaFilteration;
import com.tefsalkw.models.ColorsRecordModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 12/27/2017.
 */

public class FilterColorListAdapter extends RecyclerView.Adapter<FilterColorListAdapter.ViewHolder> {

    private ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList;
    private FragmentTextileProducts activity;
    private SessionManager session;

    public FilterColorListAdapter(ArrayList<ColorRecordFromDishdashaFilteration> colorsRecordModelArrayList, FragmentTextileProducts activity) {
        this.activity = activity;
        this.colorsRecordModelArrayList = colorsRecordModelArrayList;
        session = new SessionManager(activity.getContext());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new FilterColorListAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if(session.getKeyLang().equals("Arabic"))
        {
            holder.colorText.setText(colorsRecordModelArrayList.get(position).getName_arabic());
        }
        else {
            holder.colorText.setText(colorsRecordModelArrayList.get(position).getName());
        }

        holder.color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setColor(colorsRecordModelArrayList.get(position).getId());
                notifyDataSetChanged();

                activity.selectedColorModel = colorsRecordModelArrayList.get(position);

                if (FragmentTextileProducts.colorWindow != null) {
                    FragmentTextileProducts.colorWindow.dismiss();
                }

                if (colorsRecordModelArrayList != null && colorsRecordModelArrayList.size() > 0) {
                    activity.loadColorFilteredProducts(colorsRecordModelArrayList.get(position));
                }


            }
        });


        if (colorsRecordModelArrayList.get(position).getId().equals(TefalApp.getInstance().getColor())) {

            LayerDrawable layerDrawable = (LayerDrawable) activity.getResources()
                    .getDrawable(R.drawable.round_mage_background_select);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                    .findDrawableByLayerId(R.id.item);
            gradientDrawable.setColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));
            holder.color.setBackground(layerDrawable);


        } else {

            LayerDrawable layerDrawable = (LayerDrawable) activity.getResources()
                    .getDrawable(R.drawable.round_image_background_for_color);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                    .findDrawableByLayerId(R.id.item);
            gradientDrawable.setColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));
            holder.color.setBackground(layerDrawable);

        }


    }

    @Override
    public int getItemCount() {
        return colorsRecordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color)
        ImageView color;
        @BindView(R.id.colorText)
        TextView colorText;

        @BindView(R.id.color_item)
        LinearLayout color_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
