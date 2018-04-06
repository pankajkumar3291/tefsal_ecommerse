package com.tefsalkw.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.fragment.TailorTextileChooseFragment;
import com.tefsalkw.models.TailoringRecord;
import com.tefsalkw.utils.CircleTransform;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/12/2018.
 */

public class TailorProductFromCartAdapterListView extends BaseAdapter {
    private ArrayList<TailoringRecord> record;
    Activity activity;
    LayoutInflater inflater;


    public TailorProductFromCartAdapterListView(Activity activity, ArrayList<TailoringRecord> record) {
        this.activity = activity;
        this.record = record;

        inflater = LayoutInflater.from(this.activity);
    }

    @Override
    public int getCount() {
        return record.size();
    }

    @Override
    public Object getItem(int position) {
        return record.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inflater.inflate(R.layout.tailor_product_item_new, parent, false);

        //  TextView tv=(TextView)convertView.findViewById(R.id.tv);
        TextView txt_product_name = (TextView) convertView.findViewById(R.id.txt_product_name);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        txt_product_name.setText(record.get(position).getDishdasha_product_name());
        Picasso.with(activity)
                .load(record.get(position).getImage())
                .error(R.drawable.no_image_placeholder_grid)
                .transform(new CircleTransform())
                .into(imageView);


        if (record.get(position).getChecked()) {
            checkBox.setChecked(true);
            TailorTextileChooseFragment.ownTextileCheckBox.setChecked(false);
        } else {
            // if(getCartRecordList.size()!=0)
            checkBox.setChecked(false);
            //getCartRecordList.remove()
        }


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    record.get(position).setChecked(true);
                    //getCartRecordList.get(position).setChecked(true);
                    // getCartRecordList=getCartRecordList2;
                    notifyDataSetChanged();
                } else {
                    record.get(position).setChecked(false);
                    // getCartRecordList.get(position).setChecked(false);
                    //getCartRecordList=getCartRecordList2;
                    notifyDataSetChanged();
                }
            }
        });

          /*  else
            {
                checkBox.setChecked(false);
            }*/

     /*   GetCartRecord getCartRecord=new GetCartRecord();
        getCartRecord.setProduct_name(getCartRecordList2.get(position).getProduct_name());
        getCartRecordList.add(getCartRecord);*/


        return convertView;
    }

    public void resetData() {
        for (int i = 0; i < record.size(); i++) {
            record.get(i).setChecked(false);
        }
    }

    public ArrayList<TailoringRecord> getData() {

        return record;
    }
}

