package com.tefal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Rituparna Khadka on 1/18/2018.
 */

public class Utility
{

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info == null)
            return false;

        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public static boolean isInternetConnected(Context context)
    {
        if (isNetworkAvailable(context))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}
