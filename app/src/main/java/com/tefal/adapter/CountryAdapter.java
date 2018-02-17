package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefal.Models.CountryModel;
import com.tefal.Models.FilterCountryModel;
import com.tefal.R;
import com.tefal.activity.AddAddresssAfterSignUp;
import com.tefal.activity.SignupActivity;
import com.tefal.app.TefalApp;
import com.tefal.dialogs.CountryDialog;
import com.tefal.utils.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 15-02-2018.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {


    private ArrayList<CountryModel> filterCountryModelArrayList;

    private ArrayList<CountryModel> filteredData;

    private ItemFilter mFilter = new ItemFilter();

    private Activity activity;
    CountryDialog dialog;
    String phoneType = "1";

    public CountryAdapter(ArrayList<CountryModel> filterCountryModelArrayList, Activity activity, CountryDialog dialog, String phoneType) {
        this.activity = activity;
        this.filterCountryModelArrayList = filterCountryModelArrayList;
        this.filteredData = filterCountryModelArrayList;
        this.dialog = dialog;
        this.phoneType = phoneType;
    }


    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_country, parent, false);
        return new CountryAdapter.ViewHolder(v);
        //return null;
    }

    @Override
    public void onBindViewHolder(final CountryAdapter.ViewHolder holder, final int position) {
        holder.txtCountryName.setText(filteredData.get(position).getNicename().toUpperCase());
        holder.txtCountryCode.setText(filteredData.get(position).getPhonecode().toUpperCase());

        Picasso.with(activity).load(filteredData.get(position).getFlag()).into(holder.imgCountry);

        holder.filter_item_panel_LL.setTag(filteredData.get(position));

        holder.filter_item_panel_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CountryModel countryModel = (CountryModel)v.getTag();

                Log.e("phoneType", phoneType);

                if (phoneType.equalsIgnoreCase("1")) {

                    SignupActivity.selectedMobileCountry = countryModel;

                    SignupActivity signupActivity = (SignupActivity) activity;

                    signupActivity.setCountryFlag1();


                }

                if (phoneType.equalsIgnoreCase("2")) {
                    SignupActivity.selectedHomeCountry = countryModel;

                    SignupActivity signupActivity = (SignupActivity) activity;

                    signupActivity.setCountryFlag2();


                }


                if (phoneType.equalsIgnoreCase("3")) {


                    AddAddresssAfterSignUp.selectedMobileCountry = countryModel;

                    AddAddresssAfterSignUp signupActivity = (AddAddresssAfterSignUp) activity;

                    signupActivity.setCountryFlag();


                }

                dialog.dismiss();


            }
        });


    }

    @Override
    public int getItemCount() {
        return filteredData != null ? filteredData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgCountry)
        ImageView imgCountry;

        @BindView(R.id.txtCountryName)
        TextView txtCountryName;

        @BindView(R.id.txtCountryCode)
        TextView txtCountryCode;

        @BindView(R.id.filter_item_panel_LL)
        RelativeLayout filter_item_panel_LL;


        public ViewHolder(View itemView) {
            super(itemView);


            ButterKnife.bind(this, itemView);

        }


    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<CountryModel> list = filterCountryModelArrayList;

            int count = list.size();
            final ArrayList<CountryModel> nlist = new ArrayList<CountryModel>(count);


            for (CountryModel row : filterCountryModelArrayList) {

                if (row.getNicename().toLowerCase().contains(filterString.toLowerCase()) || row.getPhonecode().contains(filterString)) {
                    nlist.add(row);
                }
            }


            Log.d("Filtred",nlist.size()+"");

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<CountryModel>) results.values;
            notifyDataSetChanged();
        }

    }


    public Filter getFilter() {
        return mFilter;
    }


}

