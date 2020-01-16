package com.tefsalkw.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.models.SeasonsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rituparna Khadka on 11/28/2017.
 */

public class SeasonFilterAdapter extends RecyclerView.Adapter<SeasonFilterAdapter.ViewHolder> {
    // private String record[];
    private FragmentTextileProducts activity;
    private int limit;

    List<SeasonsList> seasonList = new ArrayList<>();


    public SeasonFilterAdapter(List<SeasonsList> seasonList, FragmentTextileProducts activity, int limit) {
        this.seasonList = seasonList;
        this.activity = activity;
        this.limit = limit;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_season_item, parent, false);
        return new SeasonFilterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txt_season_name.setText(seasonList.get(position).getName());

        holder.imgSeason.setImageResource(seasonList.get(position).getImage());
        holder.seasonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setSeason(seasonList.get(position).getName());
                notifyDataSetChanged();

                if (FragmentTextileProducts.seasonWindow != null) {
                    FragmentTextileProducts.seasonWindow.dismiss();
                }

                activity.loadSeasonFilteredProducts(seasonList.get(position));

            }
        });


        if (seasonList.get(position).getName().equals(TefalApp.getInstance().getSeason())) {
            holder.imgSeason.setBorderColor(activity.getResources().getColor(R.color.colorYellow));
            holder.imgSeason.setBorderWidth(5);

        } else {
            holder.imgSeason.setBorderWidth(0);
        }
    }

    @Override
    public int getItemCount() {
        return seasonList != null ? seasonList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txt_season_name)
        TextView txt_season_name;


        @BindView(R.id.imgSeason)
        CircleImageView imgSeason;

        @BindView(R.id.seasonItem)
        LinearLayout seasonItem;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
