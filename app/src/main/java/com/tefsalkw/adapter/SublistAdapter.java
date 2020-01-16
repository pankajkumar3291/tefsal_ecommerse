package com.tefsalkw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTailorProducts;
import com.tefsalkw.models.SublistCartItems;
import com.tefsalkw.R;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 05-03-2018.
 */

public class SublistAdapter extends RecyclerView.Adapter<SublistAdapter.ViewHolder> {

    private FragmentTailorProducts activity;
    private List<SublistCartItems> storeModels = new ArrayList<>();
    private SessionManager session;

    DishdashaTailorProductAdapterForListView dishdashaTailorProductAdapterForListView;

    boolean isOwnTextile = false;
    public SublistAdapter(FragmentTailorProducts activity, List<SublistCartItems> storeModels,DishdashaTailorProductAdapterForListView dishdashaTailorProductAdapterForListView, boolean isOwnTextile) {
        this.activity = activity;
        this.storeModels = storeModels;
        session = new SessionManager(activity.getContext());
        this.dishdashaTailorProductAdapterForListView = dishdashaTailorProductAdapterForListView;

        this.isOwnTextile = isOwnTextile;
    }



    @Override
    public SublistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sublist, parent, false);
        return new SublistAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SublistAdapter.ViewHolder holder, int position) {

        holder.txtSize.setText("SIZE: "+Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters()))+" METERS");
        holder.cartItem.setText(storeModels.get(position).getItemName());



        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                FragmentTailorProducts fragmentTailorProducts =  (FragmentTailorProducts)activity;

                fragmentTailorProducts.removeItem(storeModels.get(position),storeModels.get(position).getItemPosition());

                storeModels.remove(position);

                notifyDataSetChanged();

            }
        });

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



        }
    }
}
