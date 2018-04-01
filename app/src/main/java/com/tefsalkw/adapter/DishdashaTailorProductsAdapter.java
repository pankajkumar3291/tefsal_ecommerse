package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tefsalkw.Models.DishdashaTailorProductRecord;
import com.tefsalkw.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 1/12/2018.
 */

public class DishdashaTailorProductsAdapter extends RecyclerView.Adapter<DishdashaTailorProductsAdapter.MyViewHolder>
{

    private ArrayList<DishdashaTailorProductRecord> dishdashaTailorProductRecordArrayList;
    private Activity activity;



    public DishdashaTailorProductsAdapter(Activity activity,ArrayList<DishdashaTailorProductRecord> dishdashaTailorProductRecordArrayList)
    {
        this.activity=activity;
        this.dishdashaTailorProductRecordArrayList=dishdashaTailorProductRecordArrayList;

      //  System.out.println("HELLO =======SIZE==="+dishdashaTailorProductRecordArrayList.size());

    }

    @Override
    public DishdashaTailorProductsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishdasha_tailor_product_item, parent, false);
        return new DishdashaTailorProductsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DishdashaTailorProductsAdapter.MyViewHolder holder, int position)
    {
            holder.product_name.setText(dishdashaTailorProductRecordArrayList.get(position).getDishdasha_tailor_product_name());
    }

    @Override
    public int getItemCount()
    {
        return dishdashaTailorProductRecordArrayList.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder
    {
       @BindView(R.id.product_name)
       TextView product_name;
        @BindView(R.id.product_price)
        TextView product_price;

        @BindView(R.id.add_btn)
        Button add_btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
