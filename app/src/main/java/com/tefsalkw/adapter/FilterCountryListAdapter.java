package com.tefsalkw.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.models.ProductCountryRecordModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rituparna Khadka on 11/29/2017.
 */

public class FilterCountryListAdapter extends RecyclerView.Adapter<FilterCountryListAdapter.ViewHolder> {

    private ArrayList<ProductCountryRecordModel> productCountryRecordModelArrayList;
    private FragmentTextileProducts activity;
    private SessionManager session;

    public FilterCountryListAdapter(ArrayList<ProductCountryRecordModel> productCountryRecordModelArrayList, FragmentTextileProducts activity) {
        this.productCountryRecordModelArrayList = productCountryRecordModelArrayList;
        this.activity = activity;
        session = new SessionManager(activity.getContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new FilterCountryListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // holder.country_image


        if(session.getKeyLang().equals("Arabic"))
        {
            holder.countryName.setText(productCountryRecordModelArrayList.get(position).getName_arabic());
        }
        else
        {
            holder.countryName.setText(productCountryRecordModelArrayList.get(position).getName());
        }

        holder.country_image.setBackgroundResource(0);


        if (TefalApp.getInstance().getCountry().equals(productCountryRecordModelArrayList.get(position).getId())) {
            Drawable drawableSelect = activity.getResources().getDrawable(R.drawable.round_mage_background_select);
            holder.country_image.setBackground(drawableSelect);
            holder.country_image.setPadding(6, 6, 6, 6);

            Picasso.with(activity.getContext()).load(productCountryRecordModelArrayList.get(position).getImage()).into(holder.country_image);

        } else {
            Drawable drawableNonSelect = activity.getResources().getDrawable(R.drawable.round_image_background);
            holder.country_image.setBackground(drawableNonSelect);
            holder.country_image.setPadding(6, 6, 6, 6);
            Picasso.with(activity.getContext()).load(productCountryRecordModelArrayList.get(position).getImage()).into(holder.country_image);
        }

        if (productCountryRecordModelArrayList.get(position).getId().equals(TefalApp.getInstance().getCountry())) {
            holder.country_image.setBorderColor(activity.getResources().getColor(R.color.colorYellow));
            holder.country_image.setBorderWidth(5);

        } else {
            holder.country_image.setBorderWidth(0);

        }


        holder.country_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TefalApp.getInstance().setCountry(productCountryRecordModelArrayList.get(position).getId());
                notifyDataSetChanged();

                if (FragmentTextileProducts.countryWindow != null) {
                    FragmentTextileProducts.countryWindow.dismiss();
                }

                activity.loadCountryFilteredProducts(productCountryRecordModelArrayList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return productCountryRecordModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.country_image)
        CircleImageView country_image;

        @BindView(R.id.countryName)
        TextView countryName;

       /* @BindView(R.id.country_name_txt)
        TextView country_name_txt;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
