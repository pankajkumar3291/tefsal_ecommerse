package com.tefal.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefal.R;

import java.util.ArrayList;

/**
 * Created by jagbirsinghkang on 26/07/17.
 */

public class DrawerAdapter extends BaseAdapter {

    Activity activity;
    String[] menuText;
    int[] menuIcons;
    private static LayoutInflater inflater=null;

    public DrawerAdapter(Activity activity, String[] menuText, int[] menuIcons)
    {
        this.activity = activity;
        this.menuText = menuText;
        this.menuIcons = menuIcons;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return menuText.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.menu_list_item, null);

        TextView textView = (TextView)vi.findViewById(R.id.menuText);
        ImageView imageView = (ImageView)vi.findViewById(R.id.menu_icon);

        textView.setText(menuText[position]);
        imageView.setImageResource(menuIcons[position]);

        return vi;
    }
}
