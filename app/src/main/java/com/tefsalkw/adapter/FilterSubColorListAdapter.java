package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tefsalkw.Models.ColorsRecordModel;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.app.TefalApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 1/3/2018.
 */

public class FilterSubColorListAdapter extends RecyclerView.Adapter<FilterSubColorListAdapter.ViewHolder>
{
    private ArrayList<ColorsRecordModel> colorsRecordModelArrayList;
    private Activity activity;

    public FilterSubColorListAdapter(ArrayList<ColorsRecordModel> colorsRecordModelArrayList, Activity activity)
    {
        this.activity=activity;
        this.colorsRecordModelArrayList=colorsRecordModelArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, parent, false);
        return new FilterSubColorListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
       // Picasso.with(activity).load(colorsRecordModelArrayList.get(position).getImage()).into(holder.color);
        holder.colorText.setText(colorsRecordModelArrayList.get(position).getName());

        LayerDrawable layerDrawable = (LayerDrawable) activity.getResources()
                .getDrawable(R.drawable.round_image_background_for_color);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                .findDrawableByLayerId(R.id.item);
        gradientDrawable.setColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));
        //System.out.println("SUB COLOR CODE==="+colorsRecordModelArrayList.get(position).getHexa_value());
       // holder.color.setBackgroundColor(Color.parseColor(colorsRecordModelArrayList.get(position).getHexa_value()));
        holder.color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  Toast.makeText(activity, "Hi you are from subcolor", Toast.LENGTH_SHORT).show();

                System.out.println("COLOR FROM ADAPTER BEFORE SETTING IT TO SINGLETON  SUB==="+colorsRecordModelArrayList.get(position).getId());
                TefalApp.getInstance().setSubColor(colorsRecordModelArrayList.get(position).getId());
                Intent intent=new Intent(activity,DishDashaProductActivity.class);
               /* intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                intent.putExtra("flag",TefalApp.getInstance().getFlage());
                intent.putExtra("store_name",TefalApp.getInstance().getStoreName());*/
                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.finish();
               // getSubColorHttpCall(colorsRecordModelArrayList.get(position).getId());


               /* FragmentTextileProducts.colorWindow.dismiss();
                TefalApp.getInstance().setColor(colorsRecordModelArrayList.get(position).getId());
                Intent intent=new Intent(activity,DishDashaProductActivity.class);
                intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                intent.putExtra("flag",TefalApp.getInstance().getFlage());
                intent.putExtra("store_name",TefalApp.getInstance().getStoreName());
                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.finish();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorsRecordModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

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
