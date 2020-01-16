package com.tefsalkw.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.models.AccessoriesRecord;
import com.tefsalkw.models.BaseModel;
import com.tefsalkw.models.Payload;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.TextileProductModel;

import java.util.List;
public class GetPromoAddedItemsAdapter extends RecyclerView.Adapter<GetPromoAddedItemsAdapter.GetPromoItemsViewHolder> {
    private static final String TAG = GetPromoAddedItemsAdapter.class.getSimpleName();
    Context context;
    String discount;
    List<AccessoriesRecord> Accessorylist;
    List<ProductRecord> darraaList;
    List<TextileProductModel> dishdashaList;
    private List<BaseModel> productDetailsList;
    public GetPromoAddedItemsAdapter(Context context, List<BaseModel> productDetails,String discount){
        this.context = context;
        this.productDetailsList = productDetails;

        this.discount=discount;
    }
    @NonNull
    @Override
    public GetPromoItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_promotional_items, parent, false);
        return new GetPromoItemsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GetPromoItemsViewHolder holder, int position) {
        holder.btnselect.setVisibility(View.GONE);


        if (productDetailsList.get(position) instanceof ProductRecord) {

            if (((ProductRecord) productDetailsList.get(position)).getDiscount() != null||!discount.equalsIgnoreCase("0")) {
                if ((!((ProductRecord) productDetailsList.get(position)).getDefault_price().equalsIgnoreCase("0")) )
                {
                    holder.promo_discount_layout.setVisibility(View.VISIBLE);
                    float totalAmount = Float.parseFloat(((ProductRecord) productDetailsList.get(position)).getDefault_price());
                    float discountRate = Float.parseFloat(discount);
                    float amountAfterDiscount =(((discountRate  * totalAmount)/ 100));
                    holder.tvdiscount.setText(discount+"% Discount");
                    holder.tvdiscount_ammount.setText("-"+amountAfterDiscount);
                }
                else
                {
                    holder.promo_discount_layout.setVisibility(View.GONE);
                }
            }
            holder.title.setText(((ProductRecord) productDetailsList.get(position)).getProduct_name());
            holder.tvdec.setText(((ProductRecord) productDetailsList.get(position)).getProduct_desc());
            holder.tvprice.setText(((ProductRecord) productDetailsList.get(position)).getDefault_price()+"KWD");
            Picasso.with(context).load(((ProductRecord) productDetailsList.get(position)).getDefault_product_image()).error(R.drawable.logo_blue).into(holder.ProductImg);
        } else if (productDetailsList.get(position) instanceof TextileProductModel) {

            if (((TextileProductModel) productDetailsList.get(position)).getProduct_discount() != null||!discount.equalsIgnoreCase("0")) {
                if (!((TextileProductModel) productDetailsList.get(position)).getPrice().equalsIgnoreCase("0"))
                {
                    holder.promo_discount_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.promo_discount_layout.setVisibility(View.GONE);
                }
                float totalAmount = Float.parseFloat(((TextileProductModel) productDetailsList.get(position)).getPrice());
//                float discountRate = Float.parseFloat(discount);
//                float amountAfterDiscount = totalAmount - (((discountRate  * totalAmount)/ 100));
                holder.tvdiscount.setText(discount+"% Discount");
//                holder.tvdiscount_ammount.setText(amountAfterDiscount + "");
            }
            holder.title.setText(((TextileProductModel) productDetailsList.get(position)).getDishdasha_product_name());
            holder.tvdec.setText(((TextileProductModel) productDetailsList.get(position)).getBrand_name());
            holder.tvprice.setText(((TextileProductModel) productDetailsList.get(position)).getPrice()+"KWD");
            Picasso.with(context).load(((TextileProductModel) productDetailsList.get(position)).getDefault_image()).error(R.drawable.logo_blue).into(holder.ProductImg);
        } else if (productDetailsList.get(position) instanceof AccessoriesRecord) {

            if (((AccessoriesRecord) productDetailsList.get(position)).getProduct_discount() != null||!discount.equalsIgnoreCase("0")) {
                if (!((AccessoriesRecord) productDetailsList.get(position)).getDefault_price().equalsIgnoreCase("0"))
                {
                    holder.promo_discount_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.promo_discount_layout.setVisibility(View.GONE);
                }
                float totalAmount = Float.parseFloat(((AccessoriesRecord) productDetailsList.get(position)).getDefault_price());
                float discountRate = Float.parseFloat(discount);
                float amountAfterDiscount = totalAmount - (((discountRate  * totalAmount)/ 100));
                holder.tvdiscount.setText(discount+"% Discount");
                holder.tvdiscount_ammount.setText(amountAfterDiscount + "");
            }
            holder.title.setText(((AccessoriesRecord) productDetailsList.get(position)).getProductName());
            holder.tvdec.setText(((AccessoriesRecord) productDetailsList.get(position)).getProductDesc());
            holder.tvprice.setText(((AccessoriesRecord) productDetailsList.get(position)).getDefault_price()+"KWD");
            Picasso.with(context).load(((AccessoriesRecord) productDetailsList.get(position)).getDefault_image()).error(R.drawable.logo_blue).into(holder.ProductImg);
        } else {
            Log.d(TAG, "onBindViewHolder: " + position);
        }
    }
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + productDetailsList.size());
        return productDetailsList.size();
    }
    class GetPromoItemsViewHolder extends RecyclerView.ViewHolder {
        TextView title, tvdec, tvprice, tvdiscount, tvdiscount_ammount;
        ImageView ProductImg;
        Button btnselect;
        ConstraintLayout promo_discount_layout;
        public GetPromoItemsViewHolder(View itemView) {
            super(itemView);
            tvdiscount_ammount = itemView.findViewById(R.id.textView29);
            tvdiscount = itemView.findViewById(R.id.textView27);
            promo_discount_layout = itemView.findViewById(R.id.promo_discount_layout);
            title = itemView.findViewById(R.id.textView18);
            tvdec = itemView.findViewById(R.id.textView19);
            tvprice = itemView.findViewById(R.id.textView20);
            ProductImg = itemView.findViewById(R.id.imageView7);
            btnselect = itemView.findViewById(R.id.button2);
        }
    }
}
