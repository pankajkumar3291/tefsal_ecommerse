package com.tefal.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.tefal.R;

/**
 * Created by jagbirsinghkang on 18/07/17.
 */

public class SimpleProgressBar {

    private static ProgressDialog pDialog;

    public static void showProgress(Context context)
    {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getString(R.string.loading));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public static void closeProgress()
    {
        pDialog.dismiss();
    }
}
