package com.tefal.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jagbirsinghkang on 14/07/17.
 */

public class SessionManagerToken {

    SharedPreferences Tefsal_pref_token;

    SharedPreferences.Editor editor_token;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TefsalPref_token";

    public static final String KEY_DEVICE_TOKEN = "device_token";



    public SessionManagerToken(Context context) {
        this._context = context;
        Tefsal_pref_token = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor_token = Tefsal_pref_token.edit();
    }

    public void setDeviceToken(String d_token){

        editor_token.putString(KEY_DEVICE_TOKEN, d_token);
        editor_token.commit();
    }
    public String getDeviceToken(){

        // Storing type in pref
        return Tefsal_pref_token.getString(KEY_DEVICE_TOKEN,"");

    }
}
