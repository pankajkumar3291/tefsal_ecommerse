package com.tefsalkw.activity;

import android.os.Bundle;

import com.tefsalkw.models.AddressRecordModel;
import com.tefsalkw.R;

public class MyAddressActivity extends BaseActivity
{
    private AddressRecordModel addressRecordModel;
   // input_address_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        addressRecordModel= (AddressRecordModel) getIntent().getExtras().get("addressRecordModel");

      //  System.out.println("ADDRESS NAME==="+addressRecordModel.getAddress_name());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
