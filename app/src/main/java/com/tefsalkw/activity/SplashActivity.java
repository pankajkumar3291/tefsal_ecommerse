package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.tefsalkw.R;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends BaseActivity {

    SessionManager session;

    int SPLASH_DISPLAY_LENGTH = 3000;

    String password, email;

    //SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());
        session = new SessionManager(this);

        password=session.getKeyPass();
        email=session.getKeyEmail();

        if (Contents.isBlank(session.getCustomerId())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // This block of code is used to internal login

                    if (!password.equals("") && !email.equals("") && !password.equals(null) && !email.equals(null) )
                    {
                        WebCallServiceForinternalLogin();
                    }
                    else
                    {
                        Intent mainIntent = new Intent(SplashActivity.this, SelectLanguage.class);
                        startActivity(mainIntent);
                        finish();
                    }


                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }


    public void WebCallServiceForinternalLogin() {

        try {
            final String url = Contents.baseURL + "customerLogin";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


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

                                    Log.d("user_id == ", session.getCustomerId());
                                    Log.d("user_token == ", session.getToken());
                                    Log.d("user_name == ", session.getKeyUserName());
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    WebCallServiceSetToken();

                                } else {
                                    Intent mainIntent = new Intent(SplashActivity.this, SelectLanguage.class);
                                    startActivity(mainIntent);
                                    finish();
                                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.d("JSONException===",e.toString());
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("VolleyError===",error.toString());

                            System.out.println("Volley Error==="+error);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
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
            RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            Log.d("Exception===",surError.toString());
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
                                    //  Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(SplashActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();

                                } else {

                                    // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.d("VolleyError===",error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
                    Log.d("Test access_token",session.getToken());
                    Log.d("Test user_id",session.getCustomerId());

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
            RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {

            Log.d("Exception===",surError.toString());
            surError.printStackTrace();
        }
    }
}
