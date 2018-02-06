package com.tefal.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tefal.Models.MailModel;
import com.tefal.R;
import com.tefal.activity.MailDetailActivity;
import com.tefal.app.TefalApp;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private Activity activity;
    List<MailModel>  record;



    public InboxAdapter(Activity activity,  List<MailModel>  record) {
        this.activity = activity;
        this.record = record;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.subject_text)
        TextView subject_text;

        @Bind(R.id.mail_text)
        TextView mail_text;

        @Bind(R.id.date_text)
        TextView date_text;

        @Bind(R.id.main_layout)
        LinearLayout main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        holder.subject_text.setText(record.get(position2).getSubject());
        holder.mail_text.setText(record.get(position2).getMessage());
        holder.date_text.setText(record.get(position2).getCreated_at());

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                TefalApp.getInstance().setWhereFromInMail("from inbox");
                activity.startActivity(new Intent(activity, MailDetailActivity.class).putExtra("mailModel",record.get(position2)));
            }
        });

    }
    @Override
    public int getItemCount() {
        return record.size();
    }

}