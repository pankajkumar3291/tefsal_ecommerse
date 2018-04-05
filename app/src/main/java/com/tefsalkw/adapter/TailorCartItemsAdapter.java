package com.tefsalkw.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.R;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 26-02-2018.
 */

public class TailorCartItemsAdapter extends RecyclerView.Adapter<TailorCartItemsAdapter.ViewHolder> {

    private Activity activity;
    private List<GetCartRecord> storeModels = new ArrayList<>();
    private SessionManager session;


    public TailorCartItemsAdapter(Activity activity, List<GetCartRecord> storeModels) {
        this.activity = activity;
        this.storeModels = storeModels;
        session = new SessionManager(activity);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tailor_cart, parent, false);
        return new TailorCartItemsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.cartItem.setText(storeModels.get(position).getDishdasha_material() + " - " + storeModels.get(position).getItem_quantity() + "m");

        holder.txtDishdashaRemaining.setText(storeModels.get(position).getDishDashaRemaining() + "/2 Dishdasha remaining");

        if (storeModels.get(position).getDishDashaRemaining() == 0) {
            holder.txtDishdashaRemaining.setTextColor(activity.getResources().getColor(R.color.colorGreyDark));
        }
    }

    @Override
    public int getItemCount() {
        return storeModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cartItem)
        TextView cartItem;

        @BindView(R.id.txtDishdashaRemaining)
        TextView txtDishdashaRemaining;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


    public void updateDishDashacount(String dropdownId) {

        Log.e("dropdownId2", dropdownId);

        int pos = 0;
        for (GetCartRecord cartRecord : storeModels) {

            Log.e("cartRecord", cartRecord.getItem_id());
            if (cartRecord.getItem_id().equalsIgnoreCase(dropdownId)) {

                int getrRemainingDishDasha = cartRecord.getDishDashaRemaining();
                getrRemainingDishDasha = getrRemainingDishDasha - 1;

                storeModels.get(pos).setDishDashaRemaining(getrRemainingDishDasha);

            }

            pos++;
        }
        notifyDataSetChanged();

    }


    public void updateDishDashacountRemove(String dropdownId) {

        Log.e("dropdownId2", dropdownId);

        int pos = 0;
        for (GetCartRecord cartRecord : storeModels) {

            Log.e("cartRecord", cartRecord.getItem_id());
            if (cartRecord.getItem_id().equalsIgnoreCase(dropdownId)) {

                int getrRemainingDishDasha = cartRecord.getDishDashaRemaining();
                getrRemainingDishDasha = getrRemainingDishDasha + 1;

                storeModels.get(pos).setDishDashaRemaining(getrRemainingDishDasha);

            }

            pos++;
        }
        notifyDataSetChanged();

    }



}
