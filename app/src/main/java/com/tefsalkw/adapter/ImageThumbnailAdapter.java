package com.tefsalkw.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.Models.CountryModel;
import com.tefsalkw.R;

import java.util.ArrayList;
import java.util.List;


public class ImageThumbnailAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    int flag = 0;

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction


    private List<CountryModel> data = new ArrayList<>();

    public ImageThumbnailAdapter(Context context, List<CountryModel> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.library_grid_item, null);
        }

        ImageView imgv_image = (ImageView) convertView.findViewById(R.id.imgv_image);
        TextView txt_country_name = (TextView) convertView.findViewById(R.id.txt_country_name);

        Picasso.with(context).load(data.get(position).getFlag()).into(imgv_image);

        txt_country_name.setText(data.get(position).getNicename());

        String fontPath2 = "fonts/Lato-Black.ttf";
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);

        txt_country_name.setTypeface(tf2);

        final View cView = convertView;


        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
