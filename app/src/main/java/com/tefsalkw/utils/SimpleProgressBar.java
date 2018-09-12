package com.tefsalkw.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.tefsalkw.R;

/**
 * Created by jagbirsinghkang on 18/07/17.
 */

public class SimpleProgressBar {

    private static ProgressDialog pDialog;

    public static void showProgress(Context context) {
        if (context != null) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(context.getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }


    public static void showProgress(Context context, String message) {
        if (context != null) {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(message);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    public static void closeProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            try {
                pDialog.dismiss();
            } catch (Exception exc) {
                pDialog = null;
            }

        }

    }
}
