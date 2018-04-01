package com.tefsalkw.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.tefsalkw.R;

public class DialogAlert extends Dialog {


    public DialogAlert(final Context context, String title, String text) {
        super(context);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.alert_dialog);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);


        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);


        Button buttonOK = findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });


    }

    public void dismiss() {
        super.dismiss();
    }



}


