package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefsalkw.models.TailorProductModal;
import com.tefsalkw.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class TailorProductAdapter extends RecyclerView.Adapter<TailorProductAdapter.ViewHolder> {

    private Activity activity;
    private List<TailorProductModal> tailorModels = new ArrayList<>();


    public TailorProductAdapter(Activity activity, List<TailorProductModal> tailorModels) {
        this.activity = activity;
        this.tailorModels = tailorModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tailor_product_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_product_name)
        TextView text_product_name;

        @BindView(R.id.text_price)
        TextView text_price;


        @BindView(R.id.main_layout)
        LinearLayout main_layout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {


        holder.text_product_name.setText(tailorModels.get(holder.getAdapterPosition()).getDishdasha_tailor_product_name());
        holder.text_price.setText(tailorModels.get(holder.getAdapterPosition()).getDishdasha_tailor_product_price()+".000 KWD");

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return tailorModels.size();
    }

}