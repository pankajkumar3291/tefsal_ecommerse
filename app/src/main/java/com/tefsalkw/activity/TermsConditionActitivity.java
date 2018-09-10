package com.tefsalkw.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefsalkw.R;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsConditionActitivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.tc_text)
    TextView tc_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition);

        ButterKnife.bind(this);
        //().hide();
        // getSupportActionBar(null)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //toolbar.setTitle("Terms & Conditions");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        httpCallTC();

    }

    private void httpCallTC() {
        SimpleProgressBar.showProgress(TermsConditionActitivity.this);
        try {
            final String url = Contents.baseURL + "getStaticPages";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    String message = object.getString("message");
                                    JSONObject records = object.getJSONObject("record");

                                    tc_text.setText(Html.fromHtml(records.getString("description")));
                                    toolbar_title.setText(records.getString("title"));


                                    // Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(getApplicationContext(), SettingsActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    // finish();
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
                    params.put("slug", "tc");
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");


                    Log.e("Tefsal tailor == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }
}
