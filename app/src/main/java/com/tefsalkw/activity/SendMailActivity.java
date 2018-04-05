package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.R;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.tefsalkw.activity.MainActivity.mail_menu;
import static com.tefsalkw.activity.MainActivity.order_menu;
import static com.tefsalkw.activity.MainActivity.total_badge_txt;

public class SendMailActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;


    @BindView(R.id.btn_sndmail)
    TextView btn_snd;

    /*@BindView(R.id.input_layout_email_message)
    TextInputLayout input_layout_email__message;*/


    @BindView(R.id.txt_email_message)
    EditText txt_email_message;

    @BindView(R.id.txt_email_title)
            EditText txt_email_title;
    private SessionManager session;

    private String emailTitle;
    private String emailMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("SEND EMAIL");

        session = new SessionManager(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailTitle=txt_email_title.getText().toString().trim();
                emailMessage=txt_email_message.getText().toString().trim();
              //  txt_email_title.setError("");
                //input_layout_email__message.setError("");

                if( validateMailTitle(emailTitle) && validateMailMessage(emailMessage))
                {
                    httpSendEmailCall( );
                }
                else
                {
                    return;
                }

                //httpSendEmailCall( );
            }
        });

    }
    private boolean validateMailTitle(String emailTitle)
    {
        if(emailTitle.equals(""))
        {
            txt_email_title.setError(getString(R.string.invalidEmailTitle));
            requestFocus(txt_email_title);
            return false;

        }
        return true;
    }
    private boolean validateMailMessage(String emailMessage)
    {
        if(emailMessage.equals(""))
        {
          //  input_layout_email__message.setError(getString(R.string.invalidEmailMessage));
            requestFocus(txt_email_title);
            return false;

        }
        return  true;
    }
    private void httpSendEmailCall()
    {
        SimpleProgressBar.showProgress(SendMailActivity.this);
        try {
            final String url = Contents.baseURL + "customerSendMail";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response=="+response.toString());

                            // mSessionManager.setStyleStatus("true");
                           // SimpleProgressBar.closeProgress();

                            if (response != null)
                            {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try
                                {
                                    JSONObject object = new JSONObject(response);
                                    String status=object.getString("status");

                                    String message=object.getString("message");
                                    //JSONObject records=object.getJSONObject("record");

                                      httpGetBadgesCall();


                                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(SendMailActivity.this, MailingSystemActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } catch (JSONException e) {
                                    System.out.println("EX=="+e);
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error=="+error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customer_id", session.getCustomerId());
                    params.put("subject",emailTitle);
                    params.put("message", emailMessage);
                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appVersion", "1.1");
                    params.put("appSecret", "tefsal@123");
                    params.put("sender_id", session.getCustomerId());


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
           // System.out.println("HTTP CALL BEGAN");
    }

    private void httpGetBadgesCall()
    {
       // SimpleProgressBar.showProgress(SendMailActivity.this);
        try {
            final String url = Contents.baseURL + "getBadges";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response=="+response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null)
                            {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    if (status.equals("1")) {
                                        String record = jsonObject.getString("record");
                                        Gson g = new Gson();
                                        BadgeRecordModel badgeRecordModel = g.fromJson(record, BadgeRecordModel.class);
                                        initializeCountDrawer(badgeRecordModel);
                                    }
                                }
                                catch (Exception ex)
                                {

                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error=="+error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appVersion", "1.1");
                    params.put("appSecret", "tefsal@123");

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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void initializeCountDrawer(BadgeRecordModel badgeRecordModel)
    {

        /*System.out.println("orders_badge===="+badgeRecordModel.getOrders_badge());
        System.out.println("mails_badge===="+badgeRecordModel.getMails_badge());
        System.out.println("total_badge===="+badgeRecordModel.getOrders_badge());*/

        if(badgeRecordModel.getOrders_badge().equals("0"))
        {
            order_menu.setVisibility(View.GONE);
        }
        else
        {
            order_menu.setText(""+badgeRecordModel.getOrders_badge());
        }
        if(badgeRecordModel.getMails_badge().equals("0"))
        {
            mail_menu.setVisibility(View.GONE);
        }
        else
        {
            mail_menu.setText(badgeRecordModel.getMails_badge());
        }
        if(badgeRecordModel.getTotal_badge().equals("0"))
        {
            total_badge_txt.setVisibility(View.GONE);
        }
        else
        {
            total_badge_txt.setText(""+badgeRecordModel.getTotal_badge());
        }
    }

}
