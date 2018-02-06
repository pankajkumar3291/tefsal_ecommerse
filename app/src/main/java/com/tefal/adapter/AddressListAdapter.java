package com.tefal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefal.Models.AddressRecordModel;
import com.tefal.R;
import com.tefal.activity.MyAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 11/15/2017.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder>
{

    private List<AddressRecordModel> addressRecordModelArrayList;
    private Activity context;

    public AddressListAdapter(Activity context, List<AddressRecordModel> addressRecordModelArrayList)
    {
        this.context=context;
        this.addressRecordModelArrayList=addressRecordModelArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_item,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
            holder.address_name_txt.setText(addressRecordModelArrayList.get(position).getAddress_name());

            holder.LL_main_layout.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View v)
            {
                AddressRecordModel addressRecordModel=addressRecordModelArrayList.get(position);

                context.startActivity(new Intent(context, MyAddressActivity.class).putExtra("addressRecordModel",addressRecordModel));

                //  System.out.println("You click me");
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return addressRecordModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.address_name_txt)
        TextView address_name_txt;
        @Bind(R.id.LL_main_layout)
        LinearLayout LL_main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }
}
