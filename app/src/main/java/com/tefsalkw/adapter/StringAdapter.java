package com.tefsalkw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tefsalkw.models.TailoringRecord;
import com.tefsalkw.R;

import java.util.ArrayList;

/**
 * Created by Dell on 04-03-2018.
 */

public class StringAdapter extends ArrayAdapter<TailoringRecord> {


    private ArrayList<TailoringRecord> list = null;


    private LayoutInflater inflater = null;


    public StringAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<TailoringRecord> objects) {
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


        String itemName = this.getItem(position).getDishdasha_product_name();

        TextView item = (TextView) convertView.findViewById(R.id.item);

        item.setText(itemName);
        item.setTag(this.getItem(position));

        //Log.e("getItem_id",this.getItem(position).getItem_id());
        return convertView;
    }


}
