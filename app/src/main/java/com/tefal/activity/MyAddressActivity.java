package com.tefal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tefal.Models.AddressRecordModel;
import com.tefal.R;

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
}
