package com.tefsalkw.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;

import com.tefsalkw.models.CountryModel;
import com.tefsalkw.R;
import com.tefsalkw.adapter.CountryAdapter;

import java.util.ArrayList;

/**
 * Created by Dell on 15-02-2018.
 */

public class CountryDialog extends Dialog  {


    private RecyclerView recyclerView;
    private ArrayList<CountryModel> data;
    private CountryAdapter adapter;


    public CountryDialog(final ArrayList<CountryModel> data, final Activity context,String phoneType) {
        super(context);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.dialog_country);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(true);

        this.data = data;
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);


        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CountryAdapter(this.data, context,CountryDialog.this,phoneType);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        EditText etCountrySearch = findViewById(R.id.etCountrySearch);
        etCountrySearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text ["+s+"]");

                adapter.getFilter().filter(s.toString());
               // adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }

    public void dismiss() {
        super.dismiss();
    }



}

