package com.tefsalkw.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tefsalkw.utils.Config;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SessionManagerToken;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jagbirsinghkang on 09/08/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    SessionManagerToken session;

    SessionManager sessionManager;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        //sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);

        try {

            sessionManager = new SessionManager(getApplicationContext());

            final String url = Contents.baseURL + "registerNotificationToken";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.e("FCM Response", response);


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

                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    if (sessionManager.getIsGuestId()) {
                        params.put("unique_id", sessionManager.getCustomerId());
                    } else {
                        params.put("user_id", sessionManager.getCustomerId());
                    }
                    params.put("token", token);

                    Log.e("NotificationToken == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(MyFirebaseInstanceIDService.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }


    }

    private void storeRegIdInPref(String token) {
        session = new SessionManagerToken(getApplicationContext());
        session.setDeviceToken(token);
        Log.e(TAG, "sendRegistrationToServer: " + token);

    }
}