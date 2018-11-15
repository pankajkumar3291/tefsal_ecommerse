package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoriesStoreListingActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.AccessoriesModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ViewHolder> {

    private Activity activity;
    private List<AccessoriesModel> accessoriesModels = new ArrayList<>();
    SessionManager session;

    public AccessoriesAdapter(Activity activity, List<AccessoriesModel> accessoriesModels) {
        this.activity = activity;
        this.accessoriesModels = accessoriesModels;
        session = new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accessories_card, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_accessories)
        ImageView iv_accessories;

        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        @BindView(R.id.acc_sub_cat_name)
        TextView acc_sub_cat_name;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

//        Picasso.with(activity).load(accessoriesModels.get(position2).getImage())
//                .error(R.drawable.no_image_placeholder_grid)
//                .placeholder(R.drawable.no_image_placeholder_grid)
//                .into(holder.iv_accessories);

        RequestOptions options = new RequestOptions()
                .priority(Priority.HIGH)
                .placeholder(R.drawable.no_image_placeholder_grid)
                .error(R.drawable.no_image_placeholder_grid);

        GlideApp.with(activity).asBitmap().load(accessoriesModels.get(position2).getImage()).apply(options).into(holder.iv_accessories);


        String acc_sub_cat_name = "";

        if (session.getKeyLang().equals("Arabic")) {
            acc_sub_cat_name = accessoriesModels.get(position2).getSub_cat_name_arabic();
        } else {
            acc_sub_cat_name = accessoriesModels.get(position2).getSub_cat_name();
        }
        holder.acc_sub_cat_name.setText(acc_sub_cat_name);


        String finalAcc_sub_cat_name = acc_sub_cat_name;
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Your work is from here....
                TefalApp.getInstance().setToolbar_title(finalAcc_sub_cat_name);
                activity.startActivity(new Intent(activity, AccessoriesStoreListingActivity.class).putExtra("flag", "Accessories").putExtra("sub_cat", accessoriesModels.get(position2).getSub_cat_id()));
                //activity.finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return accessoriesModels.size();
    }

}