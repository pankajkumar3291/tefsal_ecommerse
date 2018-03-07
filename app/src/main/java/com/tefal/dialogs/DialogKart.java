package com.tefal.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tefal.Models.CountryModel;
import com.tefal.R;
import com.tefal.activity.CartActivity;
import com.tefal.activity.DaraAbayaActivity;
import com.tefal.activity.DishDashaProductActivity;
import com.tefal.activity.TextileDetailActivity;
import com.tefal.adapter.CountryAdapter;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentTextileStore;
import com.tefal.fragment.TailorTextileChooseFragment;

import java.util.ArrayList;

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

        if(!isStore)
        {
            llChooseStore.setVisibility(View.VISIBLE);
        }


        Button btnllContinue = findViewById(R.id.btnllContinue);
        Button btnllChooseStore = findViewById(R.id.btnllChooseStore);
        Button btnllChooseTailor =  findViewById(R.id.btnllChooseTailor);
        Button btnllGotoCart =  findViewById(R.id.btnllGotoCart);



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


//                    String store_id = TefalApp.getInstance().getStoreId();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("store_id", store_id);
//                    bundle.putString("flag", "dish");
//                    bundle.putString("fromWhere", "tailor");
//
//                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    TailorTextileChooseFragment tailorTextileChooseFragment = new TailorTextileChooseFragment();
//                    tailorTextileChooseFragment.setArguments(bundle);
//                    //fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.replace(R.id.fragmentContainer, tailorTextileChooseFragment);
//                    //
//                    fragmentTransaction.commit();

                  //  context.startActivity(new Intent(context, DaraAbayaActivity.class).putExtra("flag","dish").putExtra("fromDialogKart",true));

                }


                dismiss();
            }
        });

        btnllGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.startActivity(new Intent(context, CartActivity.class));
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

    }

    public void dismiss() {
        super.dismiss();
    }


}

