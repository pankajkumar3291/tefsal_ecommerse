package com.tefal.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tefal.Models.GetCartRecord;
import com.tefal.R;

import java.util.List;

/**
 * Created by Dell on 04-03-2018.
 */

public class StringAdapter extends ArrayAdapter<GetCartRecord> {


    private List<GetCartRecord> list = null;


    private LayoutInflater inflater = null;


    public StringAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<GetCartRecord> objects) {
        super(context, R.layout.string_item, R.id.item, objects);

        this.list = objects;

        this.inflater = LayoutInflater.from ( context ) ;

    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return this.getView(position, convertView, parent);
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = this.inflater.inflate(R.layout.string_item, null);


        String itemName = this.getItem(position).getDishdasha_material();

        TextView item = (TextView) convertView.findViewById(R.id.item);

        item.setText(itemName);
        item.setTag(this.getItem(position));

        //Log.e("getItem_id",this.getItem(position).getItem_id());
        return convertView;
    }


}
