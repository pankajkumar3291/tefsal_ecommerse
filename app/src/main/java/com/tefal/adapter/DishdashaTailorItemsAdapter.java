package com.tefal.adapter;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tefal.Models.SublistCartItems;
import com.tefal.Models.TailorProductModal;
import com.tefal.R;
import com.tefal.fragment.FragmentTailorStore;
import com.tefal.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 28-02-2018.
 */

public class DishdashaTailorItemsAdapter extends RecyclerView.Adapter<DishdashaTailorItemsAdapter.ViewHolder>  {

    private FragmentTailorStore activity;
    private List<TailorProductModal> storeModels = new ArrayList<>();
    private SessionManager session;


    SublistAdapter sublistAdapter;

    DishdashaTailorItemsAdapter dishdashaTailorItemsAdapter;
    private HashMap<Integer, List<SublistCartItems>> sublistCartItemsHashMap = new HashMap<Integer, List<SublistCartItems>>();


    public void onRemove(SublistCartItems sublistCartItems) {


        FragmentTailorStore fragmentTailorStore = (FragmentTailorStore)activity;

       // fragmentTailorStore.removeDishDashaCount(sublistCartItems.getProductId());


    }

    public interface OnAddItemClickListener {
        void onAdd(TailorProductModal tailorProductModal);

    }

    private OnAddItemClickListener onAddItemClickListener;

    public DishdashaTailorItemsAdapter(FragmentTailorStore activity, List<TailorProductModal> storeModels) {
        this.activity = activity;
        this.storeModels = storeModels;
        session = new SessionManager(activity.getContext());

        dishdashaTailorItemsAdapter = this;


    }

    public void setOnAddItemClickListener(OnAddItemClickListener listener) {
        onAddItemClickListener = listener;
    }


    @Override
    public DishdashaTailorItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tailors_items, parent, false);
        return new DishdashaTailorItemsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DishdashaTailorItemsAdapter.ViewHolder holder, int position) {

        try {
            holder.txtTailorItem.setText(storeModels.get(position).getDishdasha_tailor_product_name());
            holder.txtPrice.setText(storeModels.get(position).getDishdasha_tailor_product_price() + " KWD");
            holder.btnAdd.setTag(position);

            //Recycler

            LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            holder.recyclerSubList.setLayoutManager(layoutManager);
            holder.recyclerSubList.setItemAnimator(new DefaultItemAnimator());

            //sublistAdapter = new SublistAdapter(activity.getContext(), sublistCartItemsHashMap.get(position),dishdashaTailorItemsAdapter);

            holder.recyclerSubList.setAdapter(sublistAdapter);

        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return storeModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTailorItem)
        TextView txtTailorItem;

        @BindView(R.id.txtPrice)
        TextView txtPrice;

        @BindView(R.id.btnAdd)
        Button btnAdd;

        @BindView(R.id.recyclerSubList)
        RecyclerView recyclerSubList;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        int position = (Integer) view.getTag();

                        TailorProductModal tailorProductModal = storeModels.get(position);
                        tailorProductModal.setPosition(position);

                        onAddItemClickListener.onAdd(tailorProductModal);
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            });





        }
    }


    public void addSublistCartItem(int position, SublistCartItems sublistCartItems) {


        if (sublistCartItemsHashMap.containsKey(position)) {

            List<SublistCartItems> sublistitems = sublistCartItemsHashMap.get(position);
            sublistitems.add(sublistCartItems);
            sublistCartItemsHashMap.put(position, sublistitems);
            sublistAdapter.notifyDataSetChanged();
        } else {

            List<SublistCartItems> sublistitems = new ArrayList<>();
            sublistitems.add(sublistCartItems);

            sublistCartItemsHashMap.put(position, sublistitems);
            sublistAdapter.notifyDataSetChanged();

        }

    }
}
