package com.tefsalkw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefsalkw.R;
import com.tefsalkw.activity.MeasermentActivity;
import com.tefsalkw.activity.TabbarActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.DishdashaStylesRecord;
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

/**
 * Created by new on 9/26/2017.
 */

public class DishdashaAdapter extends RecyclerView.Adapter<DishdashaAdapter.ViewHolder> {

    private Activity activity;
    private List<DishdashaStylesRecord> record = new ArrayList<>();
    private SessionManager session;
    private String mCategory;
    private TefalApp mTefalApp;


    public interface OnDoneButtonListner {
        void onDone(DishdashaStylesRecord dishdashaStylesRecord);


    }

    private OnDoneButtonListner onDoneButtonListner;

    public void setOnDoneButtonListner(OnDoneButtonListner listener) {
        onDoneButtonListner = listener;
    }

    public DishdashaAdapter(Activity activity, List<DishdashaStylesRecord> record, String category) {
        this.activity = activity;
        this.record = record;
        session = new SessionManager(activity);
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishdasha_style_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_style_name)
        TextView text_style_name;

        @BindView(R.id.txt_neck_value)
        TextView txt_neck_value;

        @BindView(R.id.txt_chest_value)
        TextView txt_chest_value;

        @BindView(R.id.txt_shoulder_value)
        TextView txt_shoulder_value;

        @BindView(R.id.txt_waist_value)
        TextView txt_waist_value;

        @BindView(R.id.txt_arm_value)
        TextView txt_arm_value;

        @BindView(R.id.txt_wrist_value)
        TextView txt_wrist_value;

        @BindView(R.id.txt_front_height_value)
        TextView txt_front_height_value;

        @BindView(R.id.txt_back_height_value)
        TextView txt_back_height_value;


        @BindView(R.id.btn_delete)
        Button btn_delete;

        @BindView(R.id.btn_edit)
        Button btn_edit;

        @BindView(R.id.txtBadge_collar_btn)
        TextView txtBadge_collar_btn;

        @BindView(R.id.txt_badge_coat)
        TextView txt_badge_coat;

        @BindView(R.id.ic_coatCollar)
        ImageView ic_coatCollar;

        @BindView(R.id.ic_coat_button)
        ImageView ic_coat_button;

        @BindView(R.id.ic_cuflink)
        ImageView ic_cuflink;

        @BindView(R.id.txt_narrow_value)
        TextView txt_narrow_value;

        @BindView(R.id.pen_pocket)
        ImageView pen_pocket;

        @BindView(R.id.mobile_pocket)
        ImageView mobile_pocket;

        @BindView(R.id.key_pocket)
        ImageView key_pocket;


        //Edit
        @BindView(R.id.text_style_name1)
        EditText text_style_name1;

        @BindView(R.id.txt_neck_value1)
        EditText txt_neck_value1;

        @BindView(R.id.txt_chest_value1)
        EditText txt_chest_value1;

        @BindView(R.id.txt_shoulder_value1)
        EditText txt_shoulder_value1;

        @BindView(R.id.txt_waist_value1)
        EditText txt_waist_value1;

        @BindView(R.id.txt_arm_value1)
        EditText txt_arm_value1;

        @BindView(R.id.txt_wrist_value1)
        EditText txt_wrist_value1;

        @BindView(R.id.txt_front_height_value1)
        EditText txt_front_height_value1;

        @BindView(R.id.txt_back_height_value1)
        EditText txt_back_height_value1;

        @BindView(R.id.btnDone)
        Button btnDone;

        @BindView(R.id.btnCancel)
        Button btnCancel;

        @BindView(R.id.txtBadge_collar_btn1)
        TextView txtBadge_collar_btn1;

        @BindView(R.id.txt_badge_coat1)
        TextView txt_badge_coat1;

        @BindView(R.id.ic_coatCollar1)
        ImageView ic_coatCollar1;

        @BindView(R.id.ic_coat_button1)
        ImageView ic_coat_button1;

        @BindView(R.id.ic_cuflink1)
        ImageView ic_cuflink1;

        @BindView(R.id.txt_narrow_value1)
        TextView txt_narrow_value1;

        @BindView(R.id.pen_pocket1)
        ImageView pen_pocket1;

        @BindView(R.id.mobile_pocket1)
        ImageView mobile_pocket1;

        @BindView(R.id.key_pocket1)
        ImageView key_pocket1;


        @BindView(R.id.relEdiStyle)
        RelativeLayout relEdiStyle;

        @BindView(R.id.relViewStyle)
        RelativeLayout relViewStyle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (record.size() > 0) {


            if (record.get(position).isEditMode()) {
                holder.relEdiStyle.setVisibility(View.VISIBLE);
                holder.relViewStyle.setVisibility(View.GONE);

                //Edit

                holder.text_style_name1.setText(record.get(position).getName());
                holder.txt_neck_value1.setText(record.get(position).getNeck());
                holder.txt_chest_value1.setText(record.get(position).getChest());
                holder.txt_shoulder_value1.setText(record.get(position).getShoulder());
                holder.txt_waist_value1.setText(record.get(position).getWaist());
                holder.txt_arm_value1.setText(record.get(position).getArm());
                holder.txt_wrist_value1.setText(record.get(position).getWrist());
                holder.txt_front_height_value1.setText(record.get(position).getFront_height());
                holder.txt_back_height_value1.setText(record.get(position).getBack_height());

                holder.txtBadge_collar_btn1.setText(record.get(position).getButtons());
                holder.txt_badge_coat1.setText(record.get(position).getButtons());
                holder.txt_narrow_value1.setText(record.get(position).getMin_meters() + activity.getString(R.string.m_small));
                holder.txt_badge_coat1.setText(record.get(position).getCollar_buttons());
                holder.txtBadge_collar_btn1.setText(record.get(position).getButtons());


                if (record.get(position).getCollar_button_visibility() != null && !record.get(position).getCollar_button_visibility().equals("yes")) {
                    holder.txtBadge_collar_btn1.setVisibility(View.VISIBLE);

                } else {
                    holder.txtBadge_collar_btn1.setVisibility(View.GONE);
                }



                if (record.get(position).getShirt_button_visibility() != null && !record.get(position).getShirt_button_visibility().equals("yes")) {
                    holder.txt_badge_coat1.setVisibility(View.VISIBLE);

                } else {
                    holder.txt_badge_coat1.setVisibility(View.GONE);
                }


                if (record.get(position).getCufflink() != null && record.get(position).getCufflink().equals("yes")) {
                    holder.ic_cuflink1.setVisibility(View.VISIBLE);

                }

                if (record.get(position).getPen_pocket() != null && record.get(position).getPen_pocket().equals("yes")) {
                    holder.pen_pocket1.setImageResource(R.drawable.pen_w);

                }

                if (record.get(position).getMobile_pocket() != null && record.get(position).getMobile_pocket().equals("yes")) {

                    holder.mobile_pocket1.setImageResource(R.drawable.phone_w);
                }

                if (record.get(position).getKey_pocket() != null && record.get(position).getKey_pocket().equals("yes")) {

                    holder.key_pocket1.setImageResource(R.drawable.key_w);
                }


                holder.ic_cuflink1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        wrapStyleData(position, "edit", true);
                    }
                });

                holder.pen_pocket1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        wrapStyleData(position, "edit", true);
                    }
                });


                holder.mobile_pocket1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wrapStyleData(position, "edit", true);
                    }
                });


                holder.key_pocket1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wrapStyleData(position, "edit", true);
                    }
                });

                holder.ic_coatCollar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wrapStyleData(position, "edit", true);
                    }
                });

                holder.ic_coat_button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wrapStyleData(position, "edit", true);
                    }
                });


                holder.btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DishdashaStylesRecord dishdashaStylesRecord = record.get(position);

                        dishdashaStylesRecord.setName(holder.text_style_name1.getText().toString());
                        dishdashaStylesRecord.setNeck(holder.txt_neck_value1.getText().toString());
                        dishdashaStylesRecord.setChest(holder.txt_chest_value1.getText().toString());
                        dishdashaStylesRecord.setShoulder(holder.txt_shoulder_value1.getText().toString());
                        dishdashaStylesRecord.setWaist(holder.txt_waist_value1.getText().toString());
                        dishdashaStylesRecord.setArm(holder.txt_arm_value1.getText().toString());
                        dishdashaStylesRecord.setWrist(holder.txt_wrist_value1.getText().toString());
                        dishdashaStylesRecord.setFront_height(holder.txt_front_height_value1.getText().toString());
                        dishdashaStylesRecord.setBack_height(holder.txt_back_height_value1.getText().toString());

                        onDoneButtonListner.onDone(dishdashaStylesRecord);

                    }
                });

                holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        record.get(position).setEditMode(false);
                        notifyDataSetChanged();
                    }
                });


            } else {
                holder.relEdiStyle.setVisibility(View.GONE);
                holder.relViewStyle.setVisibility(View.VISIBLE);

                holder.text_style_name.setText(record.get(position).getName());
                holder.txt_neck_value.setText(record.get(position).getNeck() + activity.getString(R.string.cm));
                holder.txt_chest_value.setText(record.get(position).getChest() + activity.getString(R.string.cm));
                holder.txt_shoulder_value.setText(record.get(position).getShoulder() + activity.getString(R.string.cm));
                holder.txt_waist_value.setText(record.get(position).getWaist() + activity.getString(R.string.cm));
                holder.txt_arm_value.setText(record.get(position).getArm() + activity.getString(R.string.cm));
                holder.txt_wrist_value.setText(record.get(position).getWrist() + activity.getString(R.string.cm));
                holder.txt_front_height_value.setText(record.get(position).getFront_height() + activity.getString(R.string.cm));
                holder.txt_back_height_value.setText(record.get(position).getBack_height() + activity.getString(R.string.cm));
                holder.txtBadge_collar_btn.setText(record.get(position).getButtons());
                holder.txt_badge_coat.setText(record.get(position).getButtons());
                holder.txt_narrow_value.setText(record.get(position).getMin_meters() + activity.getString(R.string.m_small));
                holder.txt_badge_coat.setText(record.get(position).getCollar_buttons());
                holder.txtBadge_collar_btn.setText(record.get(position).getButtons());




                if (record.get(position).getCollar_button_visibility() != null && !record.get(position).getCollar_button_visibility().equals("yes")) {
                    holder.txtBadge_collar_btn.setVisibility(View.VISIBLE);

                } else {
                    holder.txtBadge_collar_btn.setVisibility(View.GONE);
                }


                if (record.get(position).getShirt_button_visibility() != null && !record.get(position).getShirt_button_visibility().equals("yes")) {
                    holder.txt_badge_coat.setVisibility(View.VISIBLE);

                } else {
                    holder.txt_badge_coat.setVisibility(View.GONE);
                }



                if (record.get(position).getCufflink() != null && record.get(position).getCufflink().equals("yes")) {
                    holder.ic_cuflink.setVisibility(View.VISIBLE);

                }

                if (record.get(position).getPen_pocket() != null && record.get(position).getPen_pocket().equals("yes")) {
                    holder.pen_pocket.setImageResource(R.drawable.pen_w);

                }

                if (record.get(position).getMobile_pocket() != null && record.get(position).getMobile_pocket().equals("yes")) {

                    holder.mobile_pocket.setImageResource(R.drawable.phone_w);
                }

                if (record.get(position).getKey_pocket() != null && record.get(position).getKey_pocket().equals("yes")) {

                    holder.key_pocket.setImageResource(R.drawable.key_w);
                }


                holder.pen_pocket.setOnClickListener(null);
                holder.mobile_pocket.setOnClickListener(null);
                holder.key_pocket.setOnClickListener(null);
                holder.ic_coatCollar.setOnClickListener(null);
                holder.ic_coat_button.setOnClickListener(null);

                holder.btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        record.get(position).setEditMode(true);
                        notifyDataSetChanged();

                    }
                });

                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNamePrompt(position);
                        //WebCallServiceDeletStyle(position);
                        // wrapStyleData(position,"delete");
                        //System.out.println("Delete button click=="+record.get(position).getId());

                    }
                });


            }


        }

    }


    @Override
    public int getItemCount() {
        return record.size();
    }

    public void WebCallServiceDeletStyle(final int position) {
        SimpleProgressBar.showProgress(activity);
        try {
            final String url = Contents.baseURL + "deleteMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("Delete response==" + response.toString());

                            session.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                session.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Toast.makeText(activity, object.getString("message"), Toast.LENGTH_LONG).show();
                                    activity.startActivity(new Intent(activity, TabbarActivity.class));
                                    activity.finish();

                                } catch (JSONException e) {
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

                   /* System.out.println("============================");
                    System.out.println("access_token=="+session.getToken());
                    System.out.println("user_id=="+session.getCustomerId());
                    System.out.println("appUser=="+ "tefsal");
                    System.out.println("appSecret=="+ "tefsal@123");
                    System.out.println("appVersion=="+ "1.1");
                    System.out.println("id=="+record.get(position).getId());*/


                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("id", record.get(position).getId());

                    Log.e("Tefsal tailor == ", url + params);

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

    public void showNamePrompt(final int pos) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater LayoutInflater = activity.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_delete_dailog, null);

        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);

        /*input_layout_style_name=(TextInputLayout)dialogView.findViewById(R.id.input_layout_style_name);
        input_style_name=(EditText)dialogView.findViewById(R.id.input_style_name);
        dialog_ok_btn=(Button)dialogView.findViewById(R.id.dialog_ok_btn);
        dialog_cancel_btn=(Button)dialogView.findViewById(R.id.dialog_cancel_btn);*/
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("OK");
                alertDialog.dismiss();
                WebCallServiceDeletStyle(pos);

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public void showNamePromptEdit(int position, String styleName) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater LayoutInflater = activity.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_name_dialog, null);

        TextInputLayout input_layout_style_name = (TextInputLayout) dialogView.findViewById(R.id.input_layout_style_name);
        EditText input_style_name = (EditText) dialogView.findViewById(R.id.input_style_name);
        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);
        // ButterKnife.bind(this, dialogView);

        input_style_name.setText(styleName);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapStyleData(position, "edit", false);
                alertDialog.dismiss();


            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    public void wrapStyleData(int position, String action, boolean isCustom) {


        Bundle bundle = new Bundle();
        DishdashaStylesRecord mDishdashaStylesRecord = new DishdashaStylesRecord();


        mDishdashaStylesRecord.setNeck(record.get(position).getNeck().toString());
        mDishdashaStylesRecord.setChest(record.get(position).getChest().toString());
        mDishdashaStylesRecord.setWrist(record.get(position).getWrist().toString());
        mDishdashaStylesRecord.setWaist(record.get(position).getWaist().toString());
        mDishdashaStylesRecord.setArm(record.get(position).getArm().toString());
        mDishdashaStylesRecord.setFront_height(record.get(position).getFront_height().toString());
        mDishdashaStylesRecord.setBack_height(record.get(position).getBack_height().toString());
        mDishdashaStylesRecord.setShoulder(record.get(position).getShoulder().toString());


        mDishdashaStylesRecord.setButtons(record.get(position).getButtons());
        mDishdashaStylesRecord.setCollar_button_visibility(record.get(position).getCollar_button_visibility());
        // mDishdashaStylesRecord.setCollar_button_visibility(record.get(position).getShirt_button_visibility());
        mDishdashaStylesRecord.setCollar_buttons_push(record.get(position).getCollar_buttons_push());

        mDishdashaStylesRecord.setPen_pocket(record.get(position).getPen_pocket());
        mDishdashaStylesRecord.setMobile_pocket(record.get(position).getMobile_pocket());
        mDishdashaStylesRecord.setKey_pocket(record.get(position).getKey_pocket());
        mDishdashaStylesRecord.setWide(record.get(position).getWide());

        mDishdashaStylesRecord.setCollar_buttons(record.get(position).getCollar_buttons());
        mDishdashaStylesRecord.setShirt_button_visibility(record.get(position).getShirt_button_visibility());
        // mDishdashaStylesRecord.setShirt_button_visibility(record.get(position).getCollar_button_visibility());

        mDishdashaStylesRecord.setCufflink(record.get(position).getCufflink());
        mDishdashaStylesRecord.setId(record.get(position).getId());


        mDishdashaStylesRecord.setCategory(record.get(position).getCategory());
        mDishdashaStylesRecord.setNarrow(record.get(position).getNarrow());
        mDishdashaStylesRecord.setUpdated_at(record.get(position).getUpdated_at());
        mDishdashaStylesRecord.setCreated_at(record.get(position).getCreated_at());
        mDishdashaStylesRecord.setName(record.get(position).getName());
        mDishdashaStylesRecord.setUser_id(session.getCustomerId());


        bundle.putSerializable("STYLE_DATA", mDishdashaStylesRecord);
        bundle.putString("flow", "TabbarActivity");
        mTefalApp = TefalApp.getInstance();
        mTefalApp.setmAction(action);
        mTefalApp.setmCategory(mCategory);

        TefalApp.getInstance().setmAction(action);

        /*bundle.putString("ACTION",action);
        bundle.putString("CATEGORY","2");*/

        Intent i = new Intent(activity, MeasermentActivity.class);
        i.putExtras(bundle);
        if (isCustom) {
            i.putExtra("isCustom", "1");
        }
        activity.startActivity(i);
    }


}

