package com.tefsalkw.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.DishdashaStyleAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.models.DishdashaStylesRecord;
import com.tefsalkw.models.DishdashaStylesResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishdishaStyleActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.btn_back)
    ImageButton btn_back;
    @BindView(R.id.add_new_style)
    Button add_new_style;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private DishdashaStylesRecord mDishdashaStylesRecord;
    EditText input_style_name;
    Button dialog_ok_btn;
    Button dialog_cancel_btn;
    TextInputLayout input_layout_style_name;

    SessionManager session;
    DishdashaStyleAdapter adapter;

    List<DishdashaStylesRecord> styleRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishdash_style);

        ButterKnife.bind(this);


        session = new SessionManager(this);

        setSupportActionBar(toolbar);

        toolbar_title.setText(getString(R.string.btn_dishdasha));
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if (session.getCustomerId() != null) {
            if (!session.getCustomerId().equals("")) {
                WebCallServiceStores();
            } else {
                TefalApp.getInstance().setToolbar_title("DISHDASHA");
                startActivity(new Intent(DishdishaStyleActivity.this, DishDashaStoresActivity.class).putExtra("flag", "dish").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }

        } else {
            TefalApp.getInstance().setToolbar_title("DISHDASHA");
            startActivity(new Intent(DishdishaStyleActivity.this, DishDashaStoresActivity.class).putExtra("flag", "dish").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }


        add_new_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showNamePrompt();
            }
        });


    }

    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(DishdishaStyleActivity.this);
        try {
            final String url = Contents.baseURL + "getAllMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            // System.out.println("RESPONSE=="+response);
                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                DishdashaStylesResponse mResponse = g.fromJson(response, DishdashaStylesResponse.class);

                                if(mResponse != null)
                                {
                                    if (mResponse.getStatus().equals("1")) {

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(DishdishaStyleActivity.this);
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                        recycler.setLayoutManager(layoutManager);
                                        recycler.setItemAnimator(new DefaultItemAnimator());
                                        styleRecords = mResponse.getRecord();
                                        adapter = new DishdashaStyleAdapter(DishdishaStyleActivity.this, mResponse.getRecord());
                                        recycler.setAdapter(adapter);

                                    }
                                    if (mResponse.getStatus().equals("0") || mResponse.getRecord().size() == 0) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(DishdishaStyleActivity.this);
                                        builder.setMessage("You currently do not have any stored\nstyles for Dishdisha.")
                                                .setCancelable(false)
                                                .setPositiveButton("Create New", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        showNamePrompt();
                                                    }
                                                })
                                                .setNegativeButton("CONTINUE", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //  Action for 'NO' Button
                                                        TefalApp.getInstance().setToolbar_title(getString(R.string.dishdasha_stores));
                                                        TefalApp.getInstance().setMin_meters("3");
                                                        TefalApp.getInstance().setStyleName("TefsalDefault");
                                                        startActivity(new Intent(DishdishaStyleActivity.this, DishDashaStoresActivity.class).putExtra("flag", "dish"));

                                                        dialog.cancel();
                                                    }
                                                });

                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        //Setting the title manually
                                        alert.setTitle("No Style Available");
                                        alert.show();
                                    }
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DishdishaStyleActivity.this);
                                    builder.setMessage("You currently do not have any stored\nstyles for Dishdisha.")
                                            .setCancelable(false)
                                            .setPositiveButton("Create New", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    showNamePrompt();
                                                }
                                            })
                                            .setNegativeButton("CONTINUE", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //  Action for 'NO' Button
                                                    TefalApp.getInstance().setToolbar_title(getString(R.string.dishdasha_stores));
                                                    TefalApp.getInstance().setMin_meters("3");
                                                    TefalApp.getInstance().setStyleName("TefsalDefault");
                                                    startActivity(new Intent(DishdishaStyleActivity.this, DishDashaStoresActivity.class).putExtra("flag", "dish"));

                                                    dialog.cancel();
                                                }
                                            });

                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle("No Style Available");
                                    alert.show();
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
                    //params.put("access_token", session.getToken());
                    params.put("user_id", session.getCustomerId());
                    params.put("category", "1");
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
            RequestQueue requestQueue = Volley.newRequestQueue(DishdishaStyleActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void showNamePrompt() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.style_prompt_name_dialog, null);

        input_layout_style_name = (TextInputLayout) dialogView.findViewById(R.id.input_layout_style_name);
        input_style_name = (EditText) dialogView.findViewById(R.id.input_style_name);
        dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_ok_btn);
        dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_cancel_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateStyleName() && isUniqueName()) {
                    measurementActivityGo();
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


    private boolean validateStyleName() {
        if (input_style_name.getText().toString().trim().equals("")) {
            input_layout_style_name.setError(getString(R.string.style_name_required));
            requestFocus(input_style_name);
            return false;
        }

        return true;
    }


    private boolean isUniqueName() {
        boolean isNameUnique = true;
        if (styleRecords != null && styleRecords.size() > 0) {


            String styleName = input_style_name.getText().toString().toLowerCase().trim();
            for (DishdashaStylesRecord dishdashaStylesRecord : styleRecords) {

                if (dishdashaStylesRecord.getName().toLowerCase().trim().equalsIgnoreCase(styleName)) {
                    isNameUnique = false;
                }
            }
        }


        if (!isNameUnique) {
            input_layout_style_name.setError(getString(R.string.duplicate_style_name));
            requestFocus(input_style_name);
        }

        return isNameUnique;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void measurementActivityGo() {
        Bundle bundle = new Bundle();
        mDishdashaStylesRecord = new DishdashaStylesRecord();


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

        mDishdashaStylesRecord.setName(input_style_name.getText().toString());

        mDishdashaStylesRecord.setUser_id(session.getCustomerId());


        bundle.putSerializable("STYLE_DATA", mDishdashaStylesRecord);
        bundle.putString("flow", "DishdishaStyleActivity");

        TefalApp.getInstance().setmCategory("1");
        TefalApp.getInstance().setmAction("create");
       /* // mTefalApp=TefalApp.getInstance();
        if(viewPager.getCurrentItem()==0)
        {
            TefalApp.getInstance().setmCategory("1");
            TefalApp.getInstance().setmAction("create");
        }*/
        // bundle.putString("ACTION","create");
        // bundle.putString("CATEGORY",""+viewPager.getCurrentItem()+1);
        Intent i = new Intent(getApplicationContext(), MeasermentActivity.class);
        i.putExtras(bundle);

        startActivity(i);
        finish();
    }
}
