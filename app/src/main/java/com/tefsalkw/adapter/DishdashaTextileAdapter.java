package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.activity.ProductListOtherActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.TextileStoresModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaTextileAdapter extends RecyclerView.Adapter<DishdashaTextileAdapter.ViewHolder> {

    private Activity activity;
    private List<TextileStoresModel> storeModels = new ArrayList<>();
    String flag;
    SessionManager session;

    public DishdashaTextileAdapter(Activity activity, List<TextileStoresModel> storeModels, String flag) {
        this.activity = activity;
        this.storeModels = storeModels;
        this.flag = flag;
        session = new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ratingbar)
        RatingBar ratingbar;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;

        @BindView(R.id.main_layout)
        RelativeLayout main_layout;

        @BindView(R.id.txt_discount_amount)
        TextView txt_discount_amount;

        @BindView(R.id.txt_discount_amount1)
        TextView txt_discount_amount1;


        @BindView(R.id.LL_di)
        LinearLayout LL_di;

        @BindView(R.id.LL_diRTL)
        LinearLayout LL_diRTL;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {


        try {
            String dis_amount = "";


            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(activity).load(storeModels.get(holder.getAdapterPosition()).getStore_image()).apply(options).into(holder.img);


            holder.title.setText(storeModels.get(holder.getAdapterPosition()).getStore_name());
            holder.ratingbar.setRating(Float.parseFloat(storeModels.get(holder.getAdapterPosition()).getStore_rating()));
            /// holder.ratingbar.setRating(Float.parseFloat("3"));
            holder.text_max_delivery_days.setText(storeModels.get(holder.getAdapterPosition()).getMax_delivery_days());


            if (session.getCustomerId().equals("")) {
                System.out.println("DISCOUNT AMOUNT====OF STORE ID==" + storeModels.get(holder.getAdapterPosition()).getStore_id() + "===" + storeModels.get(holder.getAdapterPosition()).getStore_discount());
            }


            if (storeModels.get(holder.getAdapterPosition()).getStore_discount() == null || storeModels.get(holder.getAdapterPosition()).getStore_discount().equals("")) {
                dis_amount = "0%";
                holder.LL_di.setVisibility(GONE);
                holder.LL_diRTL.setVisibility(GONE);
            } else {
                dis_amount = storeModels.get(holder.getAdapterPosition()).getStore_discount();

                if (session.isRTL()) {
                    holder.LL_diRTL.setVisibility(View.GONE);
                    holder.LL_di.setVisibility(View.GONE);
                } else {
                    holder.LL_diRTL.setVisibility(View.GONE);
                    holder.LL_di.setVisibility(View.VISIBLE);
                }

                holder.txt_discount_amount.setText(dis_amount + " OFF");
                holder.txt_discount_amount1.setText(dis_amount + " OFF");

            }


            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (flag.equals("dish")) {

                        //==================== This code used for fresh item list without having color , season, country filter ============================
                        TefalApp.getInstance().setColor("");
                        TefalApp.getInstance().setSeason("");
                        TefalApp.getInstance().setCountry("");
                        TefalApp.getInstance().setSubColor("");
                        TefalApp.getInstance().setBrand("");
                        TefalApp.getInstance().setPattern("");


                        TefalApp.getInstance().setFlage("1");
                        TefalApp.getInstance().setStoreId(storeModels.get(holder.getAdapterPosition()).getStore_id());
                        TefalApp.getInstance().setStoreName(storeModels.get(holder.getAdapterPosition()).getStore_name());
                        TefalApp.getInstance().setWhereFrom("textile");


                        activity.startActivity(new Intent(activity, DishDashaProductActivity.class)
                                .putExtra("title", storeModels.get(holder.getAdapterPosition()).getStore_name())
                                //.putExtra("Flag", "1")
                                /*.putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                                .putExtra("store_name",storeModels.get(holder.getAdapterPosition()).getStore_name())
                                */.putExtra("fromWhere", "textile"));
                    } else
                        activity.startActivity(new Intent(activity, ProductListOtherActivity.class)
                                .putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                                .putExtra("Flag", flag));
                }
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return storeModels.size();
    }

}