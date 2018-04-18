package com.tefsalkw.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.tefsalkw.models.GetCartRecord;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.models.TailoringRecord;
import com.tefsalkw.models.TailoringResponse;
import com.tefsalkw.R;
import com.tefsalkw.activity.DaraAbayaActivity;
import com.tefsalkw.activity.TailorProductActivity;
import com.tefsalkw.adapter.TailorProductFromCartAdapterListView;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * A simple {@link Fragment} subclass.
 */
public class TailorTextileChooseFragment extends BaseFragment {
    private String store_id, flag;
    @BindView(R.id.RL_container)
    RelativeLayout RL_container;

    @BindView(R.id.tailor_add_btn)
    Button tailor_add_btn;

    @BindView(R.id.RR_newTextile)
    LinearLayout RR_newTextile;

    @BindView(R.id.view)
    View view;

    @BindView(R.id.orText)
    TextView orText;

    // @BindView(R.id.ownTextileCheckBox)
    public static CheckBox ownTextileCheckBox;


    @BindView(R.id.list)
    ListView list;


    SessionManager session;
    GetCartResponse mResponse;

    TailoringResponse tailoringResponse;
    ArrayList<TailoringRecord> tailoringRecordArrayList = new ArrayList<TailoringRecord>();
    ArrayList<TailoringRecord> tailoringRecordArrayListOfChecked = new ArrayList<TailoringRecord>();

    /*List<GetCartRecord> getCartRecordList;
    List<GetCartRecord> getCartRecordList3=new ArrayList<GetCartRecord>();
    List<GetCartRecord> getCartRecordListOfChecked=new ArrayList<GetCartRecord>();*/

    TailorProductFromCartAdapterListView tailorProductFromCartAdapter;

    //This list is used to stored the filter data of Tailor product having itemtype DTA from GetCart Response
    List<GetCartRecord> getCartRecordList2;

    @BindView(R.id.textView2)
    TextView textView2;

    public TailorTextileChooseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //store_id = getArguments().getString("store_id");
        /* store_id = getArguments().getString("store_id");*/


        // Inflate the layout for this fragment

        System.out.println("SYSTEM STORE ID TAILORTEXTILE CHOOSE FRAGMENT==" + TefalApp.getInstance().getStoreId());
        session = new SessionManager(getActivity());

        View v = inflater.inflate(R.layout.fragment_tailor_textile_choose, container, false);
        ButterKnife.bind(this, v);

        ownTextileCheckBox = (CheckBox) v.findViewById(R.id.ownTextileCheckBox);


        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SimpleTooltip.Builder builder = new SimpleTooltip.Builder(getActivity());
                builder.gravity(Gravity.BOTTOM);
                builder.animated(true);
                builder.transparentOverlay(true);
                String textToShow = "The customer is responsible of providing the textile according to his requirements, Tefsal will be in contact to pickup the textile";
                builder.anchorView(textView2);
                builder.text(textToShow);
                builder.build().show();

            }
        });

        tailor_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myOwnTextileString = "I have my own textile";
                if (tailoringRecordArrayList.size() > 0) {

                    boolean isOwn = true;
                    tailoringRecordArrayListOfChecked = tailorProductFromCartAdapter.getData();


                    for (int i = 0; i < tailoringRecordArrayListOfChecked.size(); i++) {
                        if (tailoringRecordArrayListOfChecked.get(i).getChecked()) {
                            isOwn = false;
                            break;
                        }
                    }

                    if (isOwn) {
                        tailoringRecordArrayListOfChecked = null;


                        if (ownTextileCheckBox.isChecked()) {
                            tailoringRecordArrayListOfChecked = null;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
                            bundle.putString("ownTextileString", myOwnTextileString);
                            startActivity(new Intent(getActivity(), TailorProductActivity.class).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            Toast.makeText(getContext(), "Please select available textiles or select your own!", Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
                        bundle.putString("ownTextileString", myOwnTextileString);
                        startActivity(new Intent(getActivity(), TailorProductActivity.class).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                    }
                    //if(isOwn)

                } else {
                    tailoringRecordArrayListOfChecked = null;


                    if (ownTextileCheckBox.isChecked()) {
                        tailoringRecordArrayListOfChecked = null;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
                        bundle.putString("ownTextileString", myOwnTextileString);
                        startActivity(new Intent(getActivity(), TailorProductActivity.class).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
                        Toast.makeText(getContext(), "Kindly checked the mentioned above option", Toast.LENGTH_SHORT).show();

                    }
                }








              /*  Bundle bundle=new Bundle();
                bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
                bundle.putString("ownTextileString",myOwnTextileString);

                startActivity(new Intent(getActivity(), TailorProductActivity.class).putExtras(bundle).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));*/


            }
        });

        RR_newTextile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TefalApp.getInstance().setToolbar_title("DISHDISHA STORES");
                startActivity(new Intent(getActivity(), DaraAbayaActivity.class).putExtra("flag", "dish"));

                //DishDashaProductActivity.viewPager.setCurrentItem(0);
            }
        });

        //   httpGetTailorProductCall();


        ownTextileCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (tailoringRecordArrayList.size() > 0) {
                    tailoringRecordArrayListOfChecked = tailorProductFromCartAdapter.getData();

                    for (int i = 0; i < tailoringRecordArrayListOfChecked.size(); i++) {
                        System.out.println("DATA RITUPARNA======= BEFORE RESET===" + tailoringRecordArrayListOfChecked.get(i).getChecked());
                    }
                    tailorProductFromCartAdapter.resetData();

                    for (int i = 0; i < tailoringRecordArrayListOfChecked.size(); i++) {
                        System.out.println("DATA RITUPARNA======= BEFORE RESET===" + tailoringRecordArrayListOfChecked.get(i).getChecked());
                    }
                    tailorProductFromCartAdapter.notifyDataSetChanged();
                }


                if (isChecked) {

                    showInputPrompt();

                }


            }
        });


        //WebCallServiceCart();
        getTailoringHttpCall();

        return v;
    }

    public void showInputPrompt() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCancelable(false);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.layout_dishdasha_user_have, null);
        TextView txt_min_meter = (TextView) dialogView.findViewById(R.id.txt_min_meter);
        final EditText inputmeter = (EditText) dialogView.findViewById(R.id.inputmeter);


        //txt_min_meter.setText("You are "+min_dishdasha+"meter as per our \n is that Correct?");

        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_yes_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_no_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (android.text.TextUtils.isDigitsOnly(inputmeter.getText().toString())) {
                    alertDialog.dismiss();


                    String min_meters = inputmeter.getText().toString();

                    TefalApp.getInstance().setNumberDishdashaUserHave(min_meters);


                } else {
                    Toast.makeText(getActivity(), "Input must be digit", Toast.LENGTH_SHORT).show();
                }
                //min_meters=inputmeter.getText().toString();
                //WebCallServiceCreateStyle();

                // Toast.makeText(MeasermentActivity.this, "You clicked OK", Toast.LENGTH_SHORT).show();

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ownTextileCheckBox.setChecked(false);
                alertDialog.dismiss();
            }
        });
    }





    private List<GetCartRecord> getCartItemTailorProduct(List<GetCartRecord> getCartRecordList) {

        List<GetCartRecord> Local_getCartRecordList2 = new ArrayList<GetCartRecord>();


        for (int i = 0; i < getCartRecordList.size(); i++) {

            if (getCartRecordList.get(i).getItem_type().equals("DTA")) {
                GetCartRecord getCartRecord = new GetCartRecord();

                getCartRecord.setTotal_amount(getCartRecordList.get(i).getTotal_amount());
                getCartRecord.setCart_item_id(getCartRecordList.get(i).getCart_item_id());
                getCartRecord.setProduct_id(getCartRecordList.get(i).getProduct_id());
                getCartRecord.setItem_quantity(getCartRecordList.get(i).getItem_quantity());
                getCartRecord.setStore_image(getCartRecordList.get(i).getStore_image());
                getCartRecord.setDishdasha_pattern(getCartRecordList.get(i).getDishdasha_pattern());
                getCartRecord.setBrand_name(getCartRecordList.get(i).getBrand_name());
                getCartRecord.setDishdasha_feel(getCartRecordList.get(i).getBrand_image());
                getCartRecord.setPrice(getCartRecordList.get(i).getPrice());
                getCartRecord.setPrice(getCartRecordList.get(i).getPattern_image());
                getCartRecord.setColor_image(getCartRecordList.get(i).getColor_image());
                getCartRecord.setDishdasha_material(getCartRecordList.get(i).getDishdasha_material());
                getCartRecord.setStore_name(getCartRecordList.get(i).getStore_name());
                getCartRecord.setItem_type(getCartRecordList.get(i).getItem_type());
                getCartRecord.setProduct_name(getCartRecordList.get(i).getProduct_name());
                getCartRecord.setImage(getCartRecordList.get(i).getImage());
                getCartRecord.setChecked(true);

                Local_getCartRecordList2.add(getCartRecord);

            }


        }


        return Local_getCartRecordList2;
    }


    private void getTailoringHttpCall() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "tailoring";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                tailoringResponse = g.fromJson(response, TailoringResponse.class);
                                if (tailoringResponse.getRecord() != null) {
                                    tailoringRecordArrayList = tailoringResponse.getRecord();
                                    SetCheckedOfTailorRecord();
                                    tailorProductFromCartAdapter = new TailorProductFromCartAdapterListView(getActivity(), tailoringRecordArrayList);
                                    list.setAdapter(tailorProductFromCartAdapter);

                                    if (tailoringRecordArrayList.size() == 0) {
                                        orText.setVisibility(View.GONE);
                                        view.setVisibility(View.GONE);
                                    }


                                } else {
                                    //  Toast.makeText(getActivity(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
                    params.put("access_token", session.getToken());
                    params.put("user_required_meter", TefalApp.getInstance().getMin_meters());
                    params.put("cart_id", session.getKeyCartId());
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
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private void SetCheckedOfTailorRecord() {
        for (int i = 0; i < tailoringRecordArrayList.size(); i++) {
            tailoringRecordArrayList.get(i).setChecked(false);
        }
    }

}
