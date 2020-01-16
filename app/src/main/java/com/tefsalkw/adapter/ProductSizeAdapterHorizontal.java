package com.tefsalkw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.models.ProductSizes;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 12/28/2017.
 */

public class ProductSizeAdapterHorizontal extends RecyclerView.Adapter<ProductSizeAdapterHorizontal.ViewHolder> {

    private List<ProductSizes> productSizesList;
    private Activity activity;

    ArrayList<String> colorPickerArray = new ArrayList<>();


    public ProductSizeAdapterHorizontal(List<ProductSizes> productSizesList, Activity activity) {
        this.activity = activity;
        this.productSizesList = productSizesList;
        TefalApp.getInstance().setPaintOverSizeText("paint");

        System.out.println("SIZE=======" + productSizesList.size());

        fillColorPickerList();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontal.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        System.out.println("NISSAN===" + TefalApp.getInstance().getPaintOverSizeText());
        holder.sizeText.setText(productSizesList.get(position).getSize());
        GradientDrawable bgDrawable = (GradientDrawable) holder.sizeText.getBackground();
        bgDrawable.setColor(Color.parseColor((colorPickerArray.get(position))));

        if (position == TefalApp.getInstance().getPosition()) {

           // holder.sizeText.setPaintFlags(holder.sizeText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));



            //Communaction channel with ZaraaDaraaActivity================================



            //TefalApp.getInstance().setPaintOverSizeText("");

        } else {

           // holder.sizeText.setPaintFlags(holder.sizeText.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setPosition(position);
                notifyDataSetChanged();
            }
        });




            /*System.out.println("HI=="+productSizesList.get(position).getSize());
            System.out.println("===============================================");*/
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

    private void fillColorPickerList() {

        colorPickerArray.add("#FF4081");
        colorPickerArray.add("#303F9F");
        colorPickerArray.add("#3F51B5");
        colorPickerArray.add("#303F9F");
        colorPickerArray.add("#FF4081");


    }

}
