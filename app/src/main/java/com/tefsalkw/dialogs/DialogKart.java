package com.tefsalkw.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tefsalkw.R;
import com.tefsalkw.activity.CartActivity;
import com.tefsalkw.activity.DaraAbayaActivity;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.app.TefalApp;

/**
 * Created by Dell on 24-02-2018.
 */

public class DialogKart extends Dialog {


    public DialogKart(final Context context,boolean isStore,String itemType,final String categoryId) {
        super(context);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.dialog_cart_view);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);


        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        LinearLayout llContinue = findViewById(R.id.llContinue);
        LinearLayout llChooseStore = findViewById(R.id.llChooseStore);
        LinearLayout llChooseTailor =  findViewById(R.id.llChooseTailor);
        LinearLayout llGotoCart =  findViewById(R.id.llGotoCart);

        Button btnllContinue = findViewById(R.id.btnllContinue);
        Button btnllChooseStore = findViewById(R.id.btnllChooseStore);
        Button btnllChooseTailor =  findViewById(R.id.btnllChooseTailor);
        Button btnllGotoCart =  findViewById(R.id.btnllGotoCart);

        Log.e("isStore",isStore+"");
        if(isStore)
        {

            llChooseTailor.setVisibility(View.VISIBLE);
        }
        else
        {
            llChooseStore.setVisibility(View.GONE);
            llChooseTailor.setVisibility(View.GONE);
        }







        btnllContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnllChooseStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(categoryId.equalsIgnoreCase("1"))
                {

                    context.startActivity(new Intent(context, DaraAbayaActivity.class).putExtra("flag","dish").putExtra("fromDialogKart",true));

                }


                dismiss();
            }
        });

        btnllChooseTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(categoryId.equalsIgnoreCase("1"))
                {

                    TefalApp.getInstance().setWhereFrom("tailor");
                    context.startActivity(new Intent(context, DishDashaProductActivity.class).putExtra("flag","dish").putExtra("fromDialogKart",true));




                }


                dismiss();
            }
        });

        btnllGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.startActivity(new Intent(context, CartActivity.class).putExtra("fromDialogKart",true));
               dismiss();
            }
        });


        Log.e("itemType",itemType);
        if(!itemType.equalsIgnoreCase("DTE"))
        {
            llChooseStore.setVisibility(View.GONE);
            llChooseTailor.setVisibility(View.VISIBLE);

        }
        else
        {
            llChooseStore.setVisibility(View.VISIBLE);
            llChooseTailor.setVisibility(View.GONE);
        }

        if(!categoryId.equalsIgnoreCase("1"))
        {
            llChooseStore.setVisibility(View.GONE);
            llChooseTailor.setVisibility(View.GONE);

            btnllChooseStore.setVisibility(View.GONE);
            btnllChooseTailor.setVisibility(View.GONE);
        }


        if(itemType.equalsIgnoreCase("DTA"))
        {
            llChooseStore.setVisibility(View.GONE);
            llChooseTailor.setVisibility(View.GONE);

        }

    }

    public void dismiss() {
        super.dismiss();
    }


}

