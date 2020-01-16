package com.tefsalkw.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.tefsalkw.R;
import com.tefsalkw.activity.PromotionalItemsActivity;

import java.util.List;
public class GetPromoAdapter extends  RecyclerView.Adapter<GetPromoAdapter.GetPromoViewHolder>{
    Context context;
    List<Integer> promolist;
    public GetPromoAdapter(Context context, List<Integer> promolist) {
        this.context = context;
        this.promolist = promolist;
    }
    @NonNull
    @Override
    public GetPromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_getpromo_layout,parent,false);
        return new GetPromoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull GetPromoViewHolder holder, int position) {
        holder.btnPromotionalItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,promolist.get(position).toString(),Toast.LENGTH_SHORT).show();
             context.startActivity(new Intent(context,new PromotionalItemsActivity().getClass()).putExtra("promoid",String.valueOf(promolist.get(position))));
            }
        });
    }
    @Override
    public int getItemCount() {
        return promolist.size();
    }
    class  GetPromoViewHolder  extends RecyclerView.ViewHolder
    {

        Button btnPromotionalItems;
        public GetPromoViewHolder(View itemView) {
            super(itemView);
            btnPromotionalItems=itemView.findViewById(R.id.button4);
        }
    }

}
