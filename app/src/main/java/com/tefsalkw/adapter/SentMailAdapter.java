package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefsalkw.models.SentMailModel;
import com.tefsalkw.R;
import com.tefsalkw.activity.MailDetailActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.NotificationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by new on 9/26/2017.
 */

public class SentMailAdapter extends RecyclerView.Adapter<SentMailAdapter.ViewHolder> {

    private Activity activity;
    List<SentMailModel>  data;

    public SentMailAdapter(Activity activity, List<SentMailModel>  data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.subject_text)
        TextView subject_text;

        @BindView(R.id.mail_text)
        TextView mail_text;

        @BindView(R.id.date_text)
        TextView date_text;

        @BindView(R.id.main_layout)
        LinearLayout main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        holder.subject_text.setText(data.get(position2).getSubject());
        holder.mail_text.setText(data.get(position2).getMessage());
        holder.date_text.setText(NotificationUtils.getFormattedDate(data.get(position2).getCreated_at()));

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                        TefalApp.getInstance().setWhereFromInMail("from sent");
                        activity.startActivity(new Intent(activity, MailDetailActivity.class).putExtra("sentMailModel",data.get(position2)));
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

}