package com.tefsalkw.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.activity.DaraAbayaProductDetailsActivity;
import com.tefsalkw.activity.PromotionalItemsActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.models.DaraaAndAbaya;
import com.tefsalkw.models.Payload;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.TextileProductModel;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
public class PromoDaraaAndAbayaAdapter extends RecyclerView.Adapter<PromoDaraaAndAbayaAdapter.DaraaAndAbayaViewHolder> {
    private Context context;
    private List<ProductRecord>daraAndAbayaList;
    private List<ProductRecord> selectedProductsList;
    private String viewType;
    private  List<Payload> payloads;
    public PromoDaraaAndAbayaAdapter(List<Payload> payloads,List<ProductRecord> daraAndAbayaList,Context context,List<ProductRecord> selectedProductsList,String viewType ) {
        this.context = context;
        this.selectedProductsList = selectedProductsList;
        this.daraAndAbayaList = daraAndAbayaList;
        this.viewType=viewType;
        this.payloads=payloads;

    }
    @NonNull
    @Override
    public DaraaAndAbayaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotional_items, parent, false);
        return new DaraaAndAbayaViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DaraaAndAbayaViewHolder holder, int position) {

        if (selectedProductsList.size()>0){
            for (ProductRecord productRecord : selectedProductsList) {
                if (productRecord.getPosition() == position) {
                    holder.btnselect.setText("Added");
                    holder.btnselect.setEnabled(false);
                }
            }
        }
        ProductRecord daraaAndAbaya=daraAndAbayaList.get(position);
        holder.title.setText(daraaAndAbaya.getProduct_name());
        holder.tvdec.setText(daraaAndAbaya.getProduct_desc());
        holder.tvprice.setText(daraaAndAbaya.getDefault_price()+"KWD");
        Picasso.with(context).load(Contents.imageUrl+daraaAndAbaya.getDefault_product_image()).resize(50,50).error(R.drawable.logo_blue).into(holder.ProductImg);
        holder.btnselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DaraAbayaProductDetailsActivity.class);
                intent.putExtra("view","fromPromo");
                intent.putExtra("position",position);
                intent.putExtra("viewType",viewType);
                intent.putExtra("payloads", (Serializable) payloads);
                Bundle bundle = new Bundle();
                ProductRecord productRecord = daraAndAbayaList.get(position);
                productRecord.setStore_id(productRecord.getStore_id());
                productRecord.setFlag(productRecord.getStore_name());
                bundle.putSerializable("productRecords", (Serializable) productRecord);
                intent.putExtras(bundle);
                ((Activity) context).startActivityForResult(intent,14);


                if (viewType.equalsIgnoreCase("Seperate"))
                {
                    ((Activity) context).finish();
                }


                }
        });
    }
    @Override
    public int getItemCount() {
        return daraAndAbayaList.size();
    }
    class DaraaAndAbayaViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,tvdec,tvprice;
        ImageView ProductImg;
        Button btnselect;
        public DaraaAndAbayaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView18);
            tvdec = itemView.findViewById(R.id.textView19);
            tvprice = itemView.findViewById(R.id.textView20);
            ProductImg = itemView.findViewById(R.id.imageView7);
            btnselect = itemView.findViewById(R.id.button2);
        }
    }


}
