package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tefsalkw.models.DishdashaStylesRecord;
import com.tefsalkw.R;
import com.tefsalkw.activity.DaraAbayaActivity;
import com.tefsalkw.activity.MeasermentActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.Config;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaStyleAdapter extends RecyclerView.Adapter<DishdashaStyleAdapter.ViewHolder> {

    private Activity activity;
    boolean isExtended = true;
    private List<DishdashaStylesRecord> record = new ArrayList<>();
    private TefalApp mTefalApp;
    private SessionManager session;

    public DishdashaStyleAdapter(Activity activity, List<DishdashaStylesRecord> record) {
        this.activity = activity;
        this.record = record;
        session=new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishdasha_style_white_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_style_name)
        TextView text_style_name;

        @BindView(R.id.txt_neck_value)
        TextView txt_neck_value;

        @BindView(R.id.txt_chest_value)
        TextView txt_chest_value;

        @BindView(R.id.txt_shoulder_value)
        TextView txt_shoulder_value;

        @BindView(R.id.txt_waist_value)
        TextView txt_waist_value;

        @BindView(R.id.txt_arm_value)
        TextView txt_arm_value;

        @BindView(R.id.txt_wrist_value)
        TextView txt_wrist_value;

        @BindView(R.id.txt_front_height_value)
        TextView txt_front_height_value;

        @BindView(R.id.txt_back_height_value)
        TextView txt_back_height_value;

        @BindView(R.id.innerView)
        LinearLayout innerView;

        @BindView(R.id.Rl_headerbar)
        RelativeLayout Rl_headerbar;

        @BindView(R.id.main_layout)
        RelativeLayout main_layout;

        @BindView(R.id.text_viewStyle)
        Button text_viewStyle;

        @BindView(R.id.txtBadge_collar_btn)
        TextView txtBadge_collar_btn;

        @BindView(R.id.txt_badge_coat)
        TextView txt_badge_coat;

        @BindView(R.id.txt_narrow_value)
        TextView txt_narrow_value;


        @BindView(R.id.divider)
        View divider;

        @BindView(R.id.mobile_pocket)
        ImageView mobile_pocket;

        @BindView(R.id.key_pocket)
        ImageView key_pocket;

        @BindView(R.id.pen_pocket)
        ImageView pen_pocket;

        @BindView(R.id.ic_cuflink)
        ImageView ic_cuflink;

        @BindView(R.id.ic_coat_button)
        ImageView ic_coat_button;

        @BindView(R.id.ic_coatCollar)
        ImageView ic_coatCollar;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);



        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.text_style_name.setText(record.get(position).getName());
        holder.txt_neck_value.setText(record.get(position).getNeck()+"cm");
        holder.txt_chest_value.setText(record.get(position).getChest()+"cm");
        holder.txt_shoulder_value.setText(record.get(position).getShoulder()+"cm");
        holder.txt_waist_value.setText(record.get(position).getWaist()+"cm");
        holder.txt_arm_value.setText(record.get(position).getArm()+"cm");
        holder.txt_wrist_value.setText(record.get(position).getWrist()+"cm");
        holder.txt_front_height_value.setText(record.get(position).getFront_height()+"cm");
        holder.txt_back_height_value.setText(record.get(position).getBack_height()+"cm");
        holder.txtBadge_collar_btn.setText(record.get(position).getButtons());
        holder.txt_badge_coat.setText(record.get(position).getButtons());
       // holder.txt_wide_value.setText(record.get(position).getWide()+"m");
        holder.txt_narrow_value.setText(record.get(position).getMin_meters()+"m");

        holder.text_viewStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.innerView.getVisibility() == View.VISIBLE)
                {
                    holder.Rl_headerbar.setBackgroundResource(R.drawable.four_points_top_shape_cd);
                    Config.collapse(holder.innerView);
                    holder.divider.setVisibility(View.GONE);
                    isExtended=false;
                }else
                {
                    holder.Rl_headerbar.setBackgroundResource(R.drawable.two_point_top_shape_cd);
                    Config.expand(holder.innerView);
                   // holder.divider.setVisibility(View.VISIBLE);
                    isExtended=true;
                }
            }
        });

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Your work here---
                //wrapStyleData( position, "edit");
                 TefalApp.getInstance().setToolbar_title("DISHDISHA STORES");
                 TefalApp.getInstance().setMin_meters(record.get(position).getMin_meters());
                 TefalApp.getInstance().setStyleName(record.get(position).getName());
                 activity.startActivity(new Intent(activity, DaraAbayaActivity.class).putExtra("flag","dish"));
            }
        });


        holder.txt_badge_coat.setText(record.get(position).getCollar_buttons());

        holder.txtBadge_collar_btn.setText(record.get(position).getButtons());

        if(record.get(position).getCufflink().equals("no"))
        {
            //holder.ic_cuflink.setVisibility(View.VISIBLE);
          //  holder.pen_pocket.setImageResource(R.drawable.pen_small);

        }
        else
        {
           // holder.pen_pocket.setColorFilter(R.color.colorAccent);
             holder.pen_pocket.setImageResource(R.drawable.pen_small);
        }


        if(record.get(position).getPen_pocket().equals("no"))
        {
            // holder.pen_pocket.setVisibility(View.INVISIBLE);
           // holder.pen_pocket.setColorFilter(R.color.colorDGray);
        }
        else
        {
            holder.pen_pocket.setImageResource(R.drawable.pen_small);
        }

        if(record.get(position).getMobile_pocket().equals("no"))
        {
            //holder.mobile_pocket.setColorFilter(R.color.colorDGray);
            //holder.mobile_pocket.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.mobile_pocket.setImageResource(R.drawable.phone_small);
        }
        if(record.get(position).getKey_pocket().equals("no"))
        {
            System.out.println("KEY POCKET STATUS=="+record.get(position).getKey_pocket());
           // holder.mobile_pocket.setColorFilter(R.color.colorDGray);

        }
        else
        {
            holder.key_pocket.setImageResource(R.drawable.key_small);
        }


            /*if(record.get(position).getKey_pocket().equals("no"))
            {
                holder.key_pocket.setColorFilter(R.color.colorDGray);
            }
            else
            {
                //
            }*/
    }

    @Override
    public int getItemCount() {
        return record.size();
    }

    public void wrapStyleData(int position, String action)
    {

        Bundle bundle=new Bundle();
        DishdashaStylesRecord  mDishdashaStylesRecord=new DishdashaStylesRecord();


        mDishdashaStylesRecord.setNeck(record.get(position).getNeck().toString());
        mDishdashaStylesRecord.setChest(record.get(position).getChest().toString());
        mDishdashaStylesRecord.setWrist(record.get(position).getWrist().toString());
        mDishdashaStylesRecord.setWaist(record.get(position).getWaist().toString());
        mDishdashaStylesRecord.setArm(record.get(position).getArm().toString());
        mDishdashaStylesRecord.setFront_height(record.get(position).getFront_height().toString());
        mDishdashaStylesRecord.setBack_height(record.get(position).getBack_height().toString());
        mDishdashaStylesRecord.setShoulder(record.get(position).getShoulder().toString());




        mDishdashaStylesRecord.setButtons(record.get(position).getButtons());
        mDishdashaStylesRecord.setPen_pocket(record.get(position).getPen_pocket());
        mDishdashaStylesRecord.setMobile_pocket(record.get(position).getMobile_pocket());
      //  mDishdashaStylesRecord.setWide(record.get(position).getWide());
        mDishdashaStylesRecord.setCollar_buttons(record.get(position).getCollar_buttons());
        mDishdashaStylesRecord.setCufflink(record.get(position).getCufflink());
        mDishdashaStylesRecord.setId(record.get(position).getId());



        mDishdashaStylesRecord.setCategory(record.get(position).getCategory());
        mDishdashaStylesRecord.setNarrow(record.get(position).getNarrow());
        mDishdashaStylesRecord.setUpdated_at(record.get(position).getUpdated_at());
        mDishdashaStylesRecord.setCreated_at(record.get(position).getCreated_at());
        mDishdashaStylesRecord.setName(record.get(position).getName());
        mDishdashaStylesRecord.setUser_id(session.getCustomerId());



        bundle.putSerializable("STYLE_DATA", mDishdashaStylesRecord);
        mTefalApp= TefalApp.getInstance();
        mTefalApp.setmAction(action);
        mTefalApp.setmCategory("1");

        /*bundle.putString("ACTION",action);
        bundle.putString("CATEGORY","2");*/

        Intent i=new Intent(activity, MeasermentActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtras(bundle);
        activity.startActivity(i);
    }

}