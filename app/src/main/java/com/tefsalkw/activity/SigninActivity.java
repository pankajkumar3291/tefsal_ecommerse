package com.tefsalkw.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.tefsalkw.R;
import com.tefsalkw.utils.Config;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.NotificationUtils;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SessionManagerToken;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SigninActivity extends BaseActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SessionManager session;
    SessionManagerToken session2;


    @BindView(R.id.forgot_password_text)
    TextView forgot_password_text;





    @BindView(R.id.input_email)
    EditText input_email;


    @BindView(R.id.input_password)
    EditText input_password;


    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.privacy_policy)
    TextView privacy_policy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);

        privacy_policy.setPaintFlags(privacy_policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, AboutUsActivity.class));
            }
        });
        //FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Regular.ttf");
        // fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        session = new SessionManager(this);
        session2 = new SessionManagerToken(this);

        input_password.setTransformationMethod(new PasswordTransformationMethod());



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                }
            }
        };


        forgot_password_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WebCallService(
                        input_email.getText().toString(),
                        input_password.getText().toString()
                );
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void WebCallService(final String str_email, final String str_password) {
        SimpleProgressBar.showProgress(SigninActivity.this);
        try {
            final String url = Contents.baseURL + "customerLogin";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            Log.e("Login response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");

                                Log.e("Login ", " >>> " + response);
                                if (status.equals("1")) {
                                    JSONObject jsonObject = object.getJSONObject("record");

                                    Log.e("user_id == ", jsonObject.getString("user_id"));
                                    Log.e("access_token == ", jsonObject.getString("access_token"));
                                    session.setCustomerId(jsonObject.getString("user_id"));
                                    session.setToken(jsonObject.getString("access_token"));
                                    session.setKeyUserName(jsonObject.getString("username"));
                                    session.setIsGuestUser(false);
                                    System.out.println("AFTER DAMP ======== CUSTOMER ID===" + session.getCustomerId());
                                    System.out.println("AFTER DAMP ======== TOKEN===" + session.getToken());
                                    System.out.println("AFTER DAMP ======== CUSTOMER NAME===" + session.getKeyUserName());

                                    //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    WebCallServiceSetToken();

                                    Intent mainIntent = new Intent(SigninActivity.this, MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();


                                } else {
                                    SimpleProgressBar.closeProgress();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                            error.printStackTrace();
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal signin == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }


    }

    public void WebCallServiceSetToken() {

        try {
            final String url = Contents.baseURL + "deviceInfo";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1")) {
                                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                                } else {

                                    // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
//                    params.put("device_id", session2.getDeviceToken());
                    params.put("device_id", "fgdjyfkudfk");
                    params.put("device_type", "A");
                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal device == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }

    }

}
