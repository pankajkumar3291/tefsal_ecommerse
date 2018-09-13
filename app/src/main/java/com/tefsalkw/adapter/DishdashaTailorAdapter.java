package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.activity.MeasermentActivity;
import com.tefsalkw.activity.ProductListOtherActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.DishdashaStylesRecord;
import com.tefsalkw.models.TailorStoresModel;
import com.tefsalkw.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaTailorAdapter extends RecyclerView.Adapter<DishdashaTailorAdapter.ViewHolder> {

    private Activity activity;
    private List<TailorStoresModel> storeModels = new ArrayList<>();
    String flag;
    SessionManager session;

    public DishdashaTailorAdapter(Activity activity, List<TailorStoresModel> storeModels, String flag) {
        this.activity = activity;
        this.storeModels = storeModels;
        this.flag = flag;
        session = new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.ratingbar)
        RatingBar ratingbar;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.main_layout)
        RelativeLayout main_layout;

        @BindView(R.id.text_max_delivery_days)
        TextView text_max_delivery_days;


        @BindView(R.id.txt_discount_amount)
        TextView txt_discount_amount;

        @BindView(R.id.LL_di)
        LinearLayout LL_di;

        @BindView(R.id.storeCloseLayout)
        RelativeLayout storeCloseLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        try {

            if (!storeModels.get(holder.getAdapterPosition()).getIs_active().equalsIgnoreCase("Y")) {
                holder.main_layout.setVisibility(GONE);
            } else {
                holder.main_layout.setVisibility(View.VISIBLE);
            }


            if (storeModels.get(holder.getAdapterPosition()).getIs_open().equalsIgnoreCase("Y")) {

                holder.storeCloseLayout.setVisibility(View.VISIBLE);


            } else {
                holder.storeCloseLayout.setVisibility(View.GONE);
            }


            String discount_amount;

            if (!storeModels.get(holder.getAdapterPosition()).getStore_image().isEmpty()) {

                RequestOptions options = new RequestOptions()
                        .priority(Priority.HIGH)
                        .placeholder(R.drawable.no_image_placeholder_grid)
                        .error(R.drawable.no_image_placeholder_grid);

                GlideApp.with(activity).load(storeModels.get(holder.getAdapterPosition()).getStore_image()).apply(options).into(holder.img);

            } else {
                holder.img.setImageResource(R.drawable.no_image_placeholder_non_grid);
            }
            holder.title.setText(storeModels.get(holder.getAdapterPosition()).getStore_name());
            holder.ratingbar.setRating(Float.parseFloat(storeModels.get(holder.getAdapterPosition()).getStore_rating()));
            holder.text_max_delivery_days.setText(storeModels.get(holder.getAdapterPosition()).getMax_delivery_days());

            if (storeModels.get(holder.getAdapterPosition()).getStore_discount() == null || storeModels.get(holder.getAdapterPosition()).getStore_discount().equals("")) {
                holder.LL_di.setVisibility(View.GONE);
                /// holder.txt_discount_off.setVisibility(View.GONE);
            } else {
                holder.LL_di.setVisibility(View.VISIBLE);
                discount_amount = storeModels.get(holder.getAdapterPosition()).getStore_discount();
                holder.txt_discount_amount.setText(discount_amount + " OFF");
            }


            holder.main_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (session.getIsGuestId()) {

                        Toast.makeText(activity, R.string.sign_up_to_continue, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (flag.equals("dish")) {

                        String styleName = TefalApp.getInstance().getStyleName();

                        if (styleName == null) {
                            TefalApp.getInstance().setStyleName("TefsalDefault");
                            styleName = "TefsalDefault";
                        }
                        if (styleName.equalsIgnoreCase("TefsalDefault")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage(R.string.no_style_warning_text)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.create_new, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            showNamePrompt();
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });

                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle(activity.getString(R.string.no_style_available));
                            alert.show();
                            return;
                        }


                        //==================== This code used for fresh item list without having color , season, country filter ============================
                        TefalApp.getInstance().setColor("");
                        TefalApp.getInstance().setSeason("");
                        TefalApp.getInstance().setCountry("");

                        TefalApp.getInstance().setFlage("1");
                        TefalApp.getInstance().setStoreId(storeModels.get(holder.getAdapterPosition()).getStore_id());
                        TefalApp.getInstance().setStoreName(storeModels.get(holder.getAdapterPosition()).getStore_name());
                        TefalApp.getInstance().setWhereFrom("tailor");
                        TefalApp.getInstance().setTailor_id(storeModels.get(holder.getAdapterPosition()).getStore_id());

                        activity.startActivity(new Intent(activity, DishDashaProductActivity.class));


                    } else
                        activity.startActivity(new Intent(activity, ProductListOtherActivity.class)
                                .putExtra("store_id", storeModels.get(holder.getAdapterPosition()).getStore_id())
                                .putExtra("Flag", flag));
                }
            });
        } catch (Exception exc) {

        }


        if (!storeModels.get(holder.getAdapterPosition()).getIs_open().equalsIgnoreCase("Y")) {

            holder.storeCloseLayout.setVisibility(View.VISIBLE);
            holder.main_layout.setClickable(false);
            holder.storeCloseLayout.setClickable(false);

        } else {
            holder.storeCloseLayout.setVisibility(View.GONE);
            holder.main_layout.setClickable(true);
            holder.storeCloseLayout.setClickable(true);
        }

    }

    @Override
    public int getItemCount() {
        return storeModels.size();
    }


    public void showNamePrompt() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater LayoutInflater = activity.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_name_dialog, null);

        TextInputLayout input_layout_style_name = (TextInputLayout) dialogView.findViewById(R.id.input_layout_style_name);
        EditText input_style_name = (EditText) dialogView.findViewById(R.id.input_style_name);
        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input_style_name.getText().toString().trim().equals("")) {
                    input_layout_style_name.setError(activity.getString(R.string.style_name_validation));
                    requestFocus(input_style_name);

                } else {
                    measurementActivityGo(input_style_name.getText().toString());
                    alertDialog.dismiss();
                }


            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void measurementActivityGo(String styleName) {
        Bundle bundle = new Bundle();
        DishdashaStylesRecord mDishdashaStylesRecord = new DishdashaStylesRecord();


        mDishdashaStylesRecord.setNeck("0.0");
        mDishdashaStylesRecord.setChest("0.0");
        mDishdashaStylesRecord.setWrist("0.0");
        mDishdashaStylesRecord.setWaist("0.0");
        mDishdashaStylesRecord.setArm("0.0");
        mDishdashaStylesRecord.setFront_height("0.0");
        mDishdashaStylesRecord.setBack_height("0.0");
        mDishdashaStylesRecord.setShoulder("0.0");


        mDishdashaStylesRecord.setButtons("1");
        mDishdashaStylesRecord.setPen_pocket("no");
        mDishdashaStylesRecord.setMobile_pocket("no");
        mDishdashaStylesRecord.setKey_pocket("no");
        mDishdashaStylesRecord.setWide("0");
        mDishdashaStylesRecord.setCollar_buttons("3");
        mDishdashaStylesRecord.setCufflink("no");
        mDishdashaStylesRecord.setId("");


        mDishdashaStylesRecord.setCategory("0");
        mDishdashaStylesRecord.setNarrow("0");

        mDishdashaStylesRecord.setUpdated_at("");
        mDishdashaStylesRecord.setCreated_at("");

        mDishdashaStylesRecord.setName(styleName);

        mDishdashaStylesRecord.setUser_id(session.getCustomerId());


        bundle.putSerializable("STYLE_DATA", mDishdashaStylesRecord);
        bundle.putString("flow", "DishdishaStyleActivity");

        TefalApp.getInstance().setmCategory("1");
        TefalApp.getInstance().setmAction("create");

        Intent i = new Intent(activity, MeasermentActivity.class);
        i.putExtras(bundle);

        activity.startActivity(i);
        activity.finish();
    }


}