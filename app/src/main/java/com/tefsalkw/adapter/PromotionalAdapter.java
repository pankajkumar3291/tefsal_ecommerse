package com.tefsalkw.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.Accessory;
import com.tefsalkw.models.BaseModel;
import com.tefsalkw.models.DaraaAndAbaya;
import com.tefsalkw.models.DishdashaTextile;
import com.tefsalkw.models.GetCartPromo;
import com.tefsalkw.models.Payload;
import com.tefsalkw.utils.Contents;

import java.io.Serializable;
import java.util.List;
import java.util.zip.Inflater;
public class PromotionalAdapter extends RecyclerView.Adapter<PromotionalAdapter.PromotionalAdapterViewHolder> {
    private List<AccessoriesRecord> list;
    private List<AccessoriesRecord> selectedProductsList;
    private Context context;
    private String viewType;
    private  List<Payload> payloads;
    public PromotionalAdapter(List<Payload>payloads,List<AccessoriesRecord> list, Context context,List<AccessoriesRecord> selectedProductsList,String viewType) {
        this.list = list;
        this.context = context;
        this.viewType=viewType;
        this.payloads=payloads;
        this.selectedProductsList=selectedProductsList;
    }
    @NonNull
    @Override
    public PromotionalAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_promotional_items, parent, false);
        return new PromotionalAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PromotionalAdapterViewHolder holder, int position) {

        if (selectedProductsList.size()>0)
        {
            for (AccessoriesRecord accessoriesRecord:selectedProductsList)
            {
                if (accessoriesRecord.getPosition()==position)
                {
                    holder.btnselect.setText("Added");
                    holder.btnselect.setEnabled(false);
                }
            }
        }
        AccessoriesRecord accessory = list.get(position);
        holder.title.setText(accessory.getBrandName());
        holder.tvdec.setText(accessory.getProductDesc());
        holder.tvprice.setText(accessory.getPrice() + "KWD");
        Picasso.with(context).load(Contents.imageUrl + accessory.getAccessory_product_image()).resize(50, 50).error(R.drawable.logo_blue).into(holder.ProductImg);
        holder.btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).startActivityForResult(new Intent(context, AccessoryProductDetailsActivity.class).putExtra("accessoriesRecord", (Serializable) accessory).putExtra("view","fromPromo").putExtra("position",position).putExtra("viewType",viewType).putExtra("payloads", (Serializable) payloads),12);
                if (viewType.equalsIgnoreCase("Seperate"))
                {
                    ((Activity) context).finish();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    class PromotionalAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title, tvdec, tvprice;
        ImageView ProductImg;
        Button btnselect;
        public PromotionalAdapterViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView18);
            tvdec = itemView.findViewById(R.id.textView19);
            tvprice = itemView.findViewById(R.id.textView20);
            ProductImg = itemView.findViewById(R.id.imageView7);
            btnselect = itemView.findViewById(R.id.button2);
        }
    }
}
