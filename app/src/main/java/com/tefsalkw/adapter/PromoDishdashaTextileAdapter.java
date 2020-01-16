package com.tefsalkw.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.tefsalkw.activity.PromotionalItemsActivity;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.DishdashaTextile;
import com.tefsalkw.models.Payload;
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


public class PromoDishdashaTextileAdapter extends RecyclerView.Adapter<PromoDishdashaTextileAdapter.DishdashaTextileViewHolder>{
    Context context;
    List<TextileProductModel>dishdashaTextileList;
    List<TextileProductModel> selectedProductsList;
    String viewType;
    private  List<Payload> payloads;

    public PromoDishdashaTextileAdapter(List<Payload>payloads,List<TextileProductModel> dishdashaTextileList,Context context,List<TextileProductModel> selectedProductsList,String viewType) {
        this.context = context;
        this.dishdashaTextileList = dishdashaTextileList;
        this.selectedProductsList=selectedProductsList;
        this.viewType=viewType;
        this.payloads=payloads;
    }
    @NonNull
    @Override
    public DishdashaTextileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotional_items, parent, false);
        return new DishdashaTextileViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DishdashaTextileViewHolder holder, int position) {
        if (selectedProductsList.size()>0){
            for (TextileProductModel textileProductModel : selectedProductsList){
                if (textileProductModel.getPosition() == position) {
                    holder.btnselect.setText("Added");
                    holder.btnselect.setEnabled(false);
                }
            }
        }
        TextileProductModel dishdashaTextile=dishdashaTextileList.get(position);
        holder.title.setText(dishdashaTextile.getDishdasha_product_name());
        holder.tvdec.setText(dishdashaTextile.getBrand_name());
        holder.tvprice.setText(dishdashaTextile.getPrice()+"KWD");
        holder.btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

//                context.startActivity(new Intent(context, TextileDetailActivity.class)
//                        .putExtra("textileProductModel",dishdashaTextile)
//                        .putExtra("product_name",dishdashaTextile.getProductName()).putExtra("modeltype","promo"));
                ((Activity) context).startActivityForResult(new Intent(context, TextileDetailActivity.class)
                        .putExtra("storeID",dishdashaTextile.getStore_id())
                        .putExtra("pos", "")
                        .putExtra("position",position)
                        .putExtra("product_name", dishdashaTextile.getProduct_name())
                        .putExtra("view","fromPromo")
                        .putExtra("viewType",viewType)
                        .putExtra("payloads", (Serializable) payloads)
                        .putExtra("textileProductModel",(Serializable) dishdashaTextile),13);

                if (viewType.equalsIgnoreCase("Seperate"))
                {
                    ((Activity) context).finish();
                }

            }
        });
        Picasso.with(context).load(dishdashaTextile.getDefault_image()).resize(50,50).error(R.drawable.logo_blue).into(holder.ProductImg);
    }

    @Override
    public int getItemCount() {
        return dishdashaTextileList.size();
    }
    class DishdashaTextileViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,tvdec,tvprice;
        ImageView ProductImg;
        CardView cardView;
        Button btnselect;
        public DishdashaTextileViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.textView18);
            tvdec = itemView.findViewById(R.id.textView19);
            tvprice = itemView.findViewById(R.id.textView20);
            ProductImg = itemView.findViewById(R.id.imageView7);
            btnselect = itemView.findViewById(R.id.button2);
        }
    }



}
