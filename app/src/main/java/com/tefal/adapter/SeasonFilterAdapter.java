package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.R;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentTextileProducts;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/28/2017.
 */

public class SeasonFilterAdapter extends RecyclerView.Adapter<SeasonFilterAdapter.ViewHolder>
{
    private String record[];
    private Activity activity;
    private int limit;

    public SeasonFilterAdapter(String record[],Activity activity, int limit)
    {
        this.record=record;
        this.activity=activity;
        this.limit=limit;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_season_item, parent, false);
        return new SeasonFilterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.txt_season_name.setText(record[position]);

        holder.txt_season_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTextileProducts.seasonWindow.dismiss();
                TefalApp.getInstance().setSeason(record[position].toLowerCase());
                Intent intent=new Intent(activity,DishDashaProductActivity.class);
               /* intent.putExtra("store_id",TefalApp.getInstance().getStoreId());
                intent.putExtra("flag",TefalApp.getInstance().getFlage());
               intent.putExtra("store_name",TefalApp.getInstance().getStoreName());*/
                activity.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.finish();
               // System.out.println("SEASON NAME=="+record[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return limit;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {


        @BindView(R.id.txt_season_name)
        TextView txt_season_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setLimit(int limit)
    {
        this.limit=limit;
    }
}
