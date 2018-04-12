package com.tefsalkw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.models.TailoringRecord;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/13/2018.
 */

public class CustomTailorCalculationProduct extends BaseAdapter {

    ArrayList<TailoringRecord> tailoringRecordArrayListOfCheckedTrue;
    private Activity activity;
    private LayoutInflater inflater;

    public CustomTailorCalculationProduct(Activity activity, ArrayList<TailoringRecord> tailoringRecordArrayListOfCheckedTrue) {
        this.activity = activity;
        this.tailoringRecordArrayListOfCheckedTrue = tailoringRecordArrayListOfCheckedTrue;
        inflater = LayoutInflater.from(this.activity);
    }

    @Override
    public int getCount() {
        return tailoringRecordArrayListOfCheckedTrue.size();
    }

    @Override
    public Object getItem(int position) {
        return tailoringRecordArrayListOfCheckedTrue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.tailor_product_remaining_item, parent, false);

        // Button add_btn=(Button)convertView.findViewById(R.id.add_btn);

        TextView tailor_product_details = (TextView) convertView.findViewById(R.id.tailor_product_details);
        TextView tailor_product_remaining_details = (TextView) convertView.findViewById(R.id.tailor_product_remaining_details);
        //tailor_product_details.setText(getCartRecordListOfCheckedTrue.get(position).getProduct_name()+"-"+getCartRecordListOfCheckedTrue.get(position).getItem_quantity()+"m");
        tailor_product_details.setText(tailoringRecordArrayListOfCheckedTrue.get(position).getDishdasha_product_name() + "-" + tailoringRecordArrayListOfCheckedTrue.get(position).getItem_quantity() + "m");

        tailor_product_remaining_details.setText(tailoringRecordArrayListOfCheckedTrue.get(position).getRemaining_dishdasha() + "/" + tailoringRecordArrayListOfCheckedTrue.get(position).getTotal_dishdasha() + " Dishdasha remaining");

        if (tailoringRecordArrayListOfCheckedTrue.get(position).getRemaining_dishdasha() == 0) {
            tailor_product_remaining_details.setTextColor(Color.GRAY);
        } else {
            tailor_product_remaining_details.setTextColor(Color.RED);
        }

        //  tailor_product_remaining_details.setText(remaining+"/"+TefalApp.getInstance().getMin_meters()+"Dishdasha remaining");


        return convertView;
    }
}
