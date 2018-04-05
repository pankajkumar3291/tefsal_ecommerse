package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefsalkw.models.SeasonsList;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/28/2017.
 */

public class SeasonFilterAdapter extends RecyclerView.Adapter<SeasonFilterAdapter.ViewHolder>
{
   // private String record[];
    private Activity activity;
    private int limit;

    List<SeasonsList> seasonList = new ArrayList<>();


    public SeasonFilterAdapter(List<SeasonsList> seasonList,Activity activity, int limit)
    {
        this.seasonList=seasonList;
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



        holder.txt_season_name.setText(seasonList.get(position).getName());

        holder.imgSeason.setImageResource(seasonList.get(position).getImage());
        holder.seasonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTextileProducts.seasonWindow.dismiss();
                TefalApp.getInstance().setSeason(seasonList.get(position).getName().toLowerCase());
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
        return seasonList != null ?  seasonList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {


        @BindView(R.id.txt_season_name)
        TextView txt_season_name;


        @BindView(R.id.imgSeason)
        ImageView imgSeason;

        @BindView(R.id.seasonItem)
        LinearLayout seasonItem;


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
