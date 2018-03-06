package com.tefal.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefal.Models.SublistCartItems;
import com.tefal.Models.TailorProductModal;
import com.tefal.R;
import com.tefal.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 05-03-2018.
 */

public class SublistAdapter extends RecyclerView.Adapter<SublistAdapter.ViewHolder> {

    private Context activity;
    private List<SublistCartItems> storeModels = new ArrayList<>();
    private SessionManager session;

    DishdashaTailorItemsAdapter dishdashaTailorItemsAdapter;

    public SublistAdapter(Context activity, List<SublistCartItems> storeModels,DishdashaTailorItemsAdapter dishdashaTailorItemsAdapter) {
        this.activity = activity;
        this.storeModels = storeModels;
        session = new SessionManager(activity);
        this.dishdashaTailorItemsAdapter = dishdashaTailorItemsAdapter;
    }



    @Override
    public SublistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist, parent, false);
        return new SublistAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistAdapter.ViewHolder holder, int position) {

        holder.cartItem.setText(storeModels.get(position).getItemName());
        holder.imgDelete.setTag(position);
    }

    @Override
    public int getItemCount() {
        return storeModels != null ? storeModels.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cartItem)
        TextView cartItem;

        @BindView(R.id.txtSize)
        TextView txtSize;

        @BindView(R.id.imgDelete)
        ImageView imgDelete;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = (Integer) view.getTag();
                    dishdashaTailorItemsAdapter.onRemove(storeModels.get(position));
                    storeModels.remove(position);


                    notifyDataSetChanged();

                }
            });

        }
    }
}
