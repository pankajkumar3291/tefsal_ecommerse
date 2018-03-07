package com.tefal.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;

import com.tefal.Models.GetCartRecord;
import com.tefal.Models.TailoringRecord;
import com.tefal.R;
import com.tefal.adapter.StringAdapter;
import com.tefal.fragment.FragmentTailorProducts;
import com.tefal.fragment.FragmentTailorStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 04-03-2018.
 */

public class DialogKartDropdown extends Dialog {


    public DialogKartDropdown(final ArrayList<TailoringRecord> data, final FragmentTailorProducts context, final int position) {
        super(context.getContext());
        this.requestWindowFeature(1);
        this.setContentView(R.layout.dialog_kart_dropdown);
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);


        final Spinner cartitems = findViewById(R.id.cartitems);
        StringAdapter adapter = new StringAdapter(context.getContext(), R.layout.string_item, R.id.item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cartitems.setAdapter(adapter);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TailoringRecord tailoringRecord = data.get(cartitems.getSelectedItemPosition());
               // Log.e("getItem_id1",getCartRecord.getItem_id());
               // Log.e("getSelectedItemPosition",cartitems.getSelectedItemPosition()+"");

                context.addItemToTailorItem(tailoringRecord,position );
                cancel();

            }
        });


    }

    public void dismiss() {
        super.dismiss();
    }


}

