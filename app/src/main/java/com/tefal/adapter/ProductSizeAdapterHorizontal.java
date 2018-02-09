package com.tefal.adapter;

import android.app.Activity;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefal.Models.ProductSizes;
import com.tefal.R;
import com.tefal.activity.ZaaraDaraaActivity;
import com.tefal.app.TefalApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rituparna Khadka on 12/28/2017.
 */

public class ProductSizeAdapterHorizontal extends RecyclerView.Adapter<ProductSizeAdapterHorizontal.ViewHolder>
{

    private List<ProductSizes> productSizesList;
    private Activity activity;






   public ProductSizeAdapterHorizontal(List<ProductSizes> productSizesList,Activity activity)
   {
       this.activity= activity;
       this.productSizesList=productSizesList;
       TefalApp.getInstance().setPaintOverSizeText("paint");

       System.out.println("SIZE======="+productSizesList.size());

   }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item, parent, false);
        return new ProductSizeAdapterHorizontal.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        System.out.println("NISSAN==="+TefalApp.getInstance().getPaintOverSizeText());
        holder.sizeText.setText(productSizesList.get(position).getSize());
        if(position==TefalApp.getInstance().getPosition())
        {

                holder.sizeText.setPaintFlags( holder.sizeText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorBlack));

                //Communaction channel with ZaraaDaraaActivity================================

                ZaaraDaraaActivity.text_price.setText("PRICE : "+productSizesList.get(position).getPrice()+" KWD");
                ZaaraDaraaActivity.stockQuantity.setText(productSizesList.get(position).getQuantity());
                ZaaraDaraaActivity.meter_value.setText("1");
                ZaaraDaraaActivity.price= Integer.parseInt(productSizesList.get(position).getPrice());
                ZaaraDaraaActivity.meter=1;


                //TefalApp.getInstance().setPaintOverSizeText("");

        }
        else
        {

                holder.sizeText.setPaintFlags( holder.sizeText.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                holder.sizeText.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));

        }

        holder.sizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                TefalApp.getInstance().setPosition(position);
                notifyDataSetChanged();
            }
        });




            /*System.out.println("HI=="+productSizesList.get(position).getSize());
            System.out.println("===============================================");*/
    }

    @Override
    public int getItemCount() {
        return productSizesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.sizeText)
        TextView sizeText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
