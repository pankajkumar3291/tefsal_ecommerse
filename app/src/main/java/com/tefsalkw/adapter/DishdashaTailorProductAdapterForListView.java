package com.tefsalkw.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefsalkw.R;
import com.tefsalkw.fragment.FragmentTailorProducts;
import com.tefsalkw.models.GetAssignedItemsRecord;
import com.tefsalkw.models.SublistCartItems;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rituparna Khadka on 1/12/2018.
 */

public class DishdashaTailorProductAdapterForListView extends BaseAdapter {


    public boolean isOwnTextile = false;
    private ArrayList<GetAssignedItemsRecord> assignedItemsRecordArrayList;
    private FragmentTailorProducts activity;
    LayoutInflater inflater;

    RecyclerView recyclerSubList;
    SublistAdapter sublistAdapter;
    public HashMap<Integer, List<SublistCartItems>> sublistCartItemsHashMap = new HashMap<Integer, List<SublistCartItems>>();

    DishdashaTailorProductAdapterForListView dishdashaTailorProductAdapterForListView;

    int selectedPosition = -1;

    public DishdashaTailorProductAdapterForListView(FragmentTailorProducts activity, ArrayList<GetAssignedItemsRecord> assignedItemsRecordArrayList) {
        this.activity = activity;
        this.assignedItemsRecordArrayList = assignedItemsRecordArrayList;
        inflater = LayoutInflater.from(this.activity.getActivity());
        dishdashaTailorProductAdapterForListView = this;
    }

    @Override
    public int getCount() {
        return assignedItemsRecordArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignedItemsRecordArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dishdasha_tailor_product_item, parent, false);
        }


        Button add_btn = (Button) convertView.findViewById(R.id.add_btn);
        TextView product_price = (TextView) convertView.findViewById(R.id.product_price);
        TextView product_name = (TextView) convertView.findViewById(R.id.product_name);
        View view = (View) convertView.findViewById(R.id.view);

        RecyclerView recyclerSubList = (RecyclerView) convertView.findViewById(R.id.recyclerSubList);
        //Recycler

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerSubList.setLayoutManager(layoutManager);
        recyclerSubList.setItemAnimator(new DefaultItemAnimator());

        sublistAdapter = new SublistAdapter(activity, sublistCartItemsHashMap.get(position), dishdashaTailorProductAdapterForListView,isOwnTextile);

        recyclerSubList.setAdapter(sublistAdapter);


        product_price.setText(assignedItemsRecordArrayList.get(position).getDishdasha_tailor_product_price() + " KWD");
        product_name.setText(assignedItemsRecordArrayList.get(position).getDishdasha_tailor_product_name());

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  assignTailorHttpCall(assignedItemsRecordArrayList.get(position));
                selectedPosition = position;
                activity.showDialog(position);


            }
        });


        // product_name.setText(dishdashaTailorProductRecordArrayList.get(position).getDishdasha_tailor_product_price()+"KWD");

        return convertView;
    }


    public void addSublistCartItem(SublistCartItems sublistCartItems, boolean isOwn) {

        Log.e("selectedPosition",selectedPosition+"");

        isOwnTextile = isOwn;

        if (sublistCartItemsHashMap.containsKey(selectedPosition)) {



            List<SublistCartItems> sublistitems = sublistCartItemsHashMap.get(selectedPosition);
            sublistitems.add(sublistCartItems);

            sublistCartItemsHashMap.put(selectedPosition, sublistitems);
            sublistAdapter.notifyDataSetChanged();


        } else {


            List<SublistCartItems> sublistitems = new ArrayList<>();
            sublistitems.add(sublistCartItems);

            sublistCartItemsHashMap.put(selectedPosition, sublistitems);
            sublistAdapter.notifyDataSetChanged();

        }


    }


}
