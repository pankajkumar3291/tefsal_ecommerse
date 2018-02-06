package com.tefal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonObject;
import com.tefal.Models.NormalResponseModel;
import com.tefal.Models.RegisterResponseModel;
import com.tefal.R;
import com.tefal.network.RestClient;
import com.tefal.utils.Config;
import com.tefal.utils.Contents;
import com.tefal.utils.FontChangeCrawler;
import com.tefal.utils.NotificationUtils;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SessionManagerToken;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class SigninActivity extends BaseActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    SessionManager session;
    SessionManagerToken session2;


    @Bind(R.id.forgot_password_text)
    TextView forgot_password_text;


    @Bind(R.id.signup_text)
    TextView signup_text;


    @Bind(R.id.input_email)
    EditText input_email;


    @Bind(R.id.input_password)
    EditText input_password;


    @Bind(R.id.buttonLogin)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ButterKnife.bind(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        session = new SessionManager(this);
        session2 = new SessionManagerToken(this);

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

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
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

                                    System.out.println("AFTER DAMP ======== CUSTOMER ID==="+session.getCustomerId());
                                    System.out.println("AFTER DAMP ======== TOKEN==="+session.getToken());
                                    System.out.println("AFTER DAMP ======== CUSTOMER NAME==="+session.getKeyUserName());

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    WebCallServiceSetToken();

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

                    Log.e("Tefsal signin == ", url + params);

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




    /*    SimpleProgressBar.showProgress(SigninActivity.this);

        Call<RegisterResponseModel> loginCall = RestClient.getApiService(SigninActivity.this).customerLogin(createLoginObject(str_email,str_password));

        loginCall.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, retrofit2.Response<RegisterResponseModel> response) {

                SimpleProgressBar.closeProgress();
                if (response.body() != null) {
                    if (response.code() == 200) {
                        session.setCustomerId(response.body().getRecord().getUser_id().toString());
                        Log.e("Customer_id == ",""+response.body().getRecord().getUser_id().toString());
                        session.setToken(response.body().getRecord().getAccess_token().toString());
                        Log.e("AccessToken == ",""+response.body().getRecord().getAccess_token().toString());
                        WebCallServiceSetToken();
                    }
                    else {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                SimpleProgressBar.closeProgress();
            }
        });*/
    }
   /* JsonObject createLoginObject(final String str_email, final String str_password) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", str_email);
        jsonObject.addProperty("password", str_password);
        jsonObject.addProperty("appUser", "tefsal");
        jsonObject.addProperty("appSecret", "tefsal@123");
        jsonObject.addProperty("appVersion", "1.1");

        Log.e("tefsal signin == ",""+jsonObject);
        return jsonObject;

    }*/

    public void WebCallServiceSetToken() {
        SimpleProgressBar.showProgress(SigninActivity.this);
        try {
            final String url = Contents.baseURL + "deviceInfo";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1")) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(SigninActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();

                                } else {

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
                            SimpleProgressBar.closeProgress();
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
       /* SimpleProgressBar.showProgress(SigninActivity.this);

        Call<NormalResponseModel> deviceCall = RestClient.getApiService(SigninActivity.this).deviceInfo(createDeviceObject());

        deviceCall.enqueue(new Callback<NormalResponseModel>() {
            @Override
            public void onResponse(Call<NormalResponseModel> call, retrofit2.Response<NormalResponseModel> response) {

                SimpleProgressBar.closeProgress();
                if (response.body() != null) {
                    if (response.code() == 200) {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        startActivity(new Intent(SigninActivity.this, MainActivity.class));
                        finish();
                    }
                    else {

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<NormalResponseModel> call, Throwable t) {
                SimpleProgressBar.closeProgress();
            }
        });*/
    }
   /* JsonObject createDeviceObject() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", session.getCustomerId());
        jsonObject.addProperty("device_id", session.getDeviceToken());
        jsonObject.addProperty("device_type", "A");
        jsonObject.addProperty("access_token", session.getToken());
        jsonObject.addProperty("appUser", "tefsal");
        jsonObject.addProperty("appSecret", "tefsal@123");
        jsonObject.addProperty("appVersion", "1.1");

        Log.e("tefsal device == ",""+jsonObject);

        return jsonObject;

    }*/
}
