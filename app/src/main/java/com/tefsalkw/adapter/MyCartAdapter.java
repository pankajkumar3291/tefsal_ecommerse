package com.tefsalkw.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.tefsalkw.GlideApp;
import com.tefsalkw.R;
import com.tefsalkw.activity.CartActivity;
import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.models.Tailor_services;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by new on 9/26/2017.
 */


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private Activity activity;
    private List<GetCartRecord> storeModels = new ArrayList<>();
    private SessionManager session;
    private boolean activate;


    public interface OnCartItemDeletedListener {
        void onCartItemDeleted(int currentCount);
    }


    OnCartItemDeletedListener onCartItemDeletedListener;

    public void setOnCartItemDeletedListener(OnCartItemDeletedListener onCartItemDeletedListener) {
        this.onCartItemDeletedListener = onCartItemDeletedListener;
    }


    public MyCartAdapter(Activity activity, List<GetCartRecord> storeModels) {
        this.activity = activity;
        this.storeModels = storeModels;
        session = new SessionManager(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_Tailor_name)
        TextView text_Tailor_name;

        @BindView(R.id.text_textile)
        TextView text_textile;

        @BindView(R.id.sub_text_textile)
        TextView sub_text_textile;

        @BindView(R.id.text_size)
        TextView text_size;

        @BindView(R.id.text_price)
        TextView text_price;

        @BindView(R.id.text_price_discounted)
        TextView text_price_discounted;


        @BindView(R.id.product_img)
        ImageView product_img;

        @BindView(R.id.ll_header)
        LinearLayout ll_header;

        @BindView(R.id.cart_item_delete)
        ImageView cart_item_delete;

        @BindView(R.id.cart_item_delete1)
        ImageView cart_item_delete1;


        @BindView(R.id.txtTailorTitle)
        TextView txtTailorTitle;

        @BindView(R.id.txtTailorQty)
        TextView txtTailorQty;

        @BindView(R.id.txtTailorPrice)
        TextView txtTailorPrice;

        @BindView(R.id.txtTailorPriceDicounted)
        TextView txtTailorPriceDicounted;

        @BindView(R.id.llTailorContainer)
        LinearLayout llTailorContainer;

        @BindView(R.id.llTailorContainerSep)
        View llTailorContainerSep;


        @BindView(R.id.txtShopName)
        TextView txtShopName;

        @BindView(R.id.txtTailorDesc)
        TextView txtTailorDesc;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position2) {

        System.out.println("ITEM TYPE==" + storeModels.get(position2).getItem_type());

        try
        {


            if (storeModels.get(position2).getItem_type().equals("DTE")) {

                holder.llTailorContainer.setVisibility(GONE);
                holder.llTailorContainerSep.setVisibility(GONE);
                System.out.println("Image ===" + storeModels.get(position2).getPattern_image());
                holder.text_Tailor_name.setText("Order - " + (position2 + 1) + " Textile");
                holder.txtShopName.setText(storeModels.get(position2).getStore_name());
                holder.text_textile.setText(storeModels.get(position2).getProduct_name());
                holder.sub_text_textile.setText(storeModels.get(position2).getPrice() + " KWD / METER");
                // holder.sub_text_textile.setVisibility(GONE);
                // holder.sub_text_textile.setText();
                holder.text_size.setText("SIZE: " + storeModels.get(position2).getItem_quantity() + " METERS");

                if (storeModels.get(position2).getDiscount() > 0) {
                    holder.text_price.setText(Html.fromHtml("<strike>" + storeModels.get(position2).getItem_quantity() * storeModels.get(position2).getPrice() + " KWD</strike>"));
                    holder.text_price_discounted.setText(Html.fromHtml("<i>" + storeModels.get(position2).getTotal_amount() + " KWD</i>"));
                    holder.text_price_discounted.setVisibility(View.VISIBLE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                } else {
                    holder.text_price.setText(storeModels.get(position2).getTotal_amount() + " KWD");
                    holder.text_price_discounted.setVisibility(GONE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorRed));
                }


            }

            if (storeModels.get(position2).getItem_type().equals("DB")) {

                holder.llTailorContainer.setVisibility(GONE);
                holder.llTailorContainerSep.setVisibility(GONE);

                System.out.println("Image ===" + storeModels.get(position2).getImage());

                holder.text_Tailor_name.setText("Order - " + (position2 + 1) + " Dara / Abaya");
                holder.txtShopName.setText(storeModels.get(position2).getStore_name());
                holder.text_textile.setText(storeModels.get(position2).getProduct_name());
                holder.sub_text_textile.setText(storeModels.get(position2).getPrice() + " KWD / METER");
                // holder.sub_text_textile.setVisibility(GONE);
                //  holder.sub_text_textile.setText(storeModels.get(position2).getDishdasha_material());
                holder.text_size.setText("QTY: " + storeModels.get(position2).getItem_quantity());
                if (storeModels.get(position2).getDiscount() > 0) {
                    holder.text_price.setText(Html.fromHtml("<strike>" + storeModels.get(position2).getItem_quantity() * storeModels.get(position2).getPrice() + " KWD</strike>"));
                    holder.text_price_discounted.setText(Html.fromHtml("<i>" + storeModels.get(position2).getTotal_amount() + " KWD</i>"));
                    holder.text_price_discounted.setVisibility(View.VISIBLE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                } else {
                    holder.text_price.setText(storeModels.get(position2).getTotal_amount() + " KWD");
                    holder.text_price_discounted.setVisibility(GONE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorRed));
                }

            }
            if (storeModels.get(position2).getItem_type().equals("A")) {

                holder.llTailorContainer.setVisibility(GONE);
                holder.llTailorContainerSep.setVisibility(GONE);

                System.out.println("Image ===" + storeModels.get(position2).getStore_image());

                holder.text_Tailor_name.setText("Order - " + (position2 + 1) + " Accessories");
                holder.txtShopName.setText(storeModels.get(position2).getStore_name());
                holder.text_textile.setText(storeModels.get(position2).getProduct_name());
                holder.sub_text_textile.setText(storeModels.get(position2).getPrice() + " KWD / QTY");
                //  holder.sub_text_textile.setText(storeModels.get(position2).getDishdasha_material());
                //  holder.sub_text_textile.setVisibility(GONE);
                holder.text_size.setText("QTY: " + storeModels.get(position2).getItem_quantity());
                if (storeModels.get(position2).getDiscount() > 0) {
                    holder.text_price.setText(Html.fromHtml("<strike>" + storeModels.get(position2).getItem_quantity() * storeModels.get(position2).getPrice() + " KWD</strike>"));
                    holder.text_price_discounted.setText(Html.fromHtml("<i>" + storeModels.get(position2).getTotal_amount() + " KWD</i>"));
                    holder.text_price_discounted.setVisibility(View.VISIBLE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                } else {
                    holder.text_price.setText(storeModels.get(position2).getTotal_amount() + " KWD");
                    holder.text_price_discounted.setVisibility(GONE);
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorRed));
                }
            }

            if (storeModels.get(position2).getItem_type().equals("DTA")) {


                holder.text_Tailor_name.setText("Order - " + (position2 + 1) + " Dishdasha Tailoring");
                holder.txtShopName.setText(storeModels.get(position2).getStore_name());
                holder.text_textile.setText(storeModels.get(position2).getProduct_name());
                holder.sub_text_textile.setText(storeModels.get(position2).getPrice() + " KWD / METER");
                //  holder.sub_text_textile.setText(storeModels.get(position2).getDishdasha_material());
                //holder.sub_text_textile.setVisibility(GONE);
                holder.text_size.setText("SIZE: " + storeModels.get(position2).getItem_quantity() + " METERS");
                if (storeModels.get(position2).getDiscount() > 0) {

                    holder.text_price.setText(Html.fromHtml("<strike>" + storeModels.get(position2).getItem_quantity() * storeModels.get(position2).getPrice() + " KWD</strike>"));

                    holder.text_price_discounted.setText(Html.fromHtml("<i>" + storeModels.get(position2).getTotal_amount() + " KWD<i>"));
                    holder.text_price_discounted.setVisibility(View.VISIBLE);

                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorBlack));

                } else {
                    holder.text_price.setText(storeModels.get(position2).getTotal_amount() + " KWD");
                    holder.text_price.setTextColor(activity.getResources().getColor(R.color.colorRed));
                    holder.text_price_discounted.setVisibility(GONE);
                }

                System.out.println("Image ===" + storeModels.get(position2).getStore_image());

                //Adding Dishdasha services


//                if (storeModels.get(position2).getTailor_services() != null) {
//
//                    holder.llTailorContainer.setVisibility(View.VISIBLE);
//                    holder.llTailorContainerSep.setVisibility(View.VISIBLE);
//
//                    List<Tailor_services> tailor_services = storeModels.get(position2).getTailor_services();
//
//
//                    holder.txtTailorTitle.setText(tailor_services.getServiceName());
//                    holder.txtTailorDesc.setText(tailor_services.getPrice() + " / QTY");
//
//                    holder.txtTailorQty.setText("QTY: " + tailor_services.getQty() + " Dishdasha");
//
//
//                    holder.txtTailorPrice.setText(tailor_services.getTotalAmount() + " KWD");
//                    holder.txtTailorPrice.setTextColor(activity.getResources().getColor(R.color.colorRed));
//                    holder.txtTailorPriceDicounted.setVisibility(GONE);
//
//
//
//                } else {
//                    holder.llTailorContainer.setVisibility(View.GONE);
//                    holder.llTailorContainerSep.setVisibility(View.GONE);
//                }


            }


            if (this.activate) {
                holder.cart_item_delete.setVisibility(View.VISIBLE);
                holder.cart_item_delete1.setVisibility(View.VISIBLE);
            } else {
                holder.cart_item_delete.setVisibility(GONE);
                holder.cart_item_delete1.setVisibility(View.GONE);

            }

            holder.cart_item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("CLICKK==");
                    showNamePrompt(position2);
                    // notifyDataSetChanged();
                }
            });

            RequestOptions options = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.no_image_placeholder_grid)
                    .error(R.drawable.no_image_placeholder_grid);

            GlideApp.with(activity).asBitmap().load(storeModels.get(position2).getImage()).apply(options).into(holder.product_img);

        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return storeModels.size();
    }

    public void showNamePrompt(final int pos) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater LayoutInflater = activity.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_delete_dailog, null);

        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);


        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("OK");
                alertDialog.dismiss();
                httpDeleteCartItemCall(pos);
                // WebCallServiceDeletStyle(pos);

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public void httpDeleteCartItemCall(final int pos) {

        SimpleProgressBar.showProgress(activity);
        try {
            final String url = Contents.baseURL + "deleteCartItem";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("RESPONSE CART DELETE====" + response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if (status.equals("1")) {
                                        storeModels.remove(pos);
                                        if (onCartItemDeletedListener != null)
                                            onCartItemDeletedListener.onCartItemDeleted(storeModels.size());

                                        notifyDataSetChanged();
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                                        ((CartActivity) activity).updateUifromAdapter();

                                    } else {
                                        Toast.makeText(activity, "Something gone wrong", Toast.LENGTH_SHORT).show();

                                    }

                                    // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();

                                    //startActivity(new Intent(getApplicationContext(), MailFragment.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    //finish();
                                } catch (JSONException e) {
                                    System.out.println("EX==" + e);
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error==" + error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("cart_item_id", storeModels.get(pos).getCart_item_id());
                    System.out.println("CART ID==" + storeModels.get(pos).getCart_item_id());
                    System.out.println("");
                    params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal store == ", url + params);

                    return params;

                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void activateDeleteOption(boolean activate) {
        //this.activate = activate;
        this.activate = activate;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }

    public int getCartItemsCount() {
        return storeModels != null ? storeModels.size() : 0;
    }

}