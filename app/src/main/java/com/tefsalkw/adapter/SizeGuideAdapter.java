package com.tefsalkw.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tefsalkw.models.ProductMeasurement;
import com.tefsalkw.R;

import java.util.List;

/**
 * Created by Rituparna Khadka on 12/20/2017.
 */

public class SizeGuideAdapter extends BaseAdapter
{
    private Activity activity;
    private List<ProductMeasurement> productMeasurementList;
    //private LayoutInflater mInflater;

    public SizeGuideAdapter(Activity activity,List<ProductMeasurement> productMeasurementList)
    {
        this.activity=activity;
        this.productMeasurementList=productMeasurementList;
      //  mInflater = LayoutInflater.from(activity);
    }
    @Override
    public int getCount() {
        return productMeasurementList.size();
    }

    @Override
    public Object getItem(int position) {
        return productMeasurementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         ViewHolder holder=null;
        if(convertView==null)
        {
            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            holder =  new ViewHolder();
            holder.size_grid_item=(TextView)convertView.findViewById(R.id.size_gridItem);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.size_grid_item.setText(productMeasurementList.get(position).getSize_m());
      //  holder.txtName.setText(name[position]);
        return convertView;
    }


   private class ViewHolder
    {
        TextView  size_grid_item;

    }
}
