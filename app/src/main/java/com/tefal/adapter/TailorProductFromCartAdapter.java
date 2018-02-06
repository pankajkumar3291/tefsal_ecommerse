package com.tefal.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.Models.GetCartRecord;
import com.tefal.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 1/12/2018.
 */

public class TailorProductFromCartAdapter  extends RecyclerView.Adapter<TailorProductFromCartAdapter.MyViewHolder>
{
    List<GetCartRecord> getCartRecordList2;
    Activity activity;

    public TailorProductFromCartAdapter(Activity activity, List<GetCartRecord> getCartRecordList2)
    {
       this.getCartRecordList2=getCartRecordList2;
       System.out.println("HELLO SIR===="+getCartRecordList2.size());

       this.activity=activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tailor_product_from_cart_item, parent, false);
        return new TailorProductFromCartAdapter.MyViewHolder(v);
       // return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
                holder.tv.setText("RITUPARNA");
                System.out.println("HI RITUPARNA");

    }

    @Override
    public int getItemCount() {
        return getCartRecordList2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        @Bind(R.id.tv)
        TextView tv;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
