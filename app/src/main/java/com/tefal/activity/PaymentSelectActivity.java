package com.tefal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefal.R;
import com.tefal.app.TefalApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AC 101 on 23-10-2017.
 */

public class PaymentSelectActivity extends BaseActivity {

    @BindView(R.id.header_txt)
    TextView header_txt;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;

    @BindView(R.id.proceed_payment_method)
    Button proceed_payment_method;


    @BindView(R.id.LL_visa_masterPaymentTCContainer)
    LinearLayout LL_visa_masterPaymentTCContainer;

    @BindView( R.id.LL_knetPaymentTCContainer)
    LinearLayout LL_knetPaymentTCContainer;

    @BindView(R.id.knetPaymentCheck)
    CheckBox knetPaymentCheck;

    @BindView( R.id.visa_masterPaymentCheck)
    CheckBox visa_masterPaymentCheck;

    @BindView(R.id.visa_masterPaymentTCCheck)
    CheckBox visa_masterPaymentTCCheck;

    @BindView(R.id.knetPaymentTCCheck)
    CheckBox knetPaymentTCCheck;

    @BindView(R.id.codPaymentCheck)
    CheckBox codPaymentCheck;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_select);

        ButterKnife.bind(this);


        header_txt.setText(getIntent().getStringExtra("header"));
        amount.setText(getIntent().getStringExtra("price"));

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        knetPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(isChecked)
                {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    LL_knetPaymentTCContainer.setVisibility(View.VISIBLE);
                    visa_masterPaymentCheck.setChecked(false);
                    visa_masterPaymentTCCheck.setChecked(false);
                    codPaymentCheck.setChecked(false);
                    TefalApp.getInstance().setPayment_method("KNET");

                }
                else
                {
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);
                    TefalApp.getInstance().setPayment_method("");
                    TefalApp.getInstance().setPayment_method_tc("");
                }

            }
        });

        visa_masterPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.VISIBLE);
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);
                    knetPaymentCheck.setChecked(false);
                    knetPaymentTCCheck.setChecked(false);
                    codPaymentCheck.setChecked(false);

                    TefalApp.getInstance().setPayment_method("VIMA");


                }
                else
                {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    TefalApp.getInstance().setPayment_method("");
                    TefalApp.getInstance().setPayment_method_tc("");



                }

            }
        });

        knetPaymentTCCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
               if(isChecked)
               {
                   TefalApp.getInstance().setPayment_method_tc("KNET_AGREE");
               }
               else
               {
                   TefalApp.getInstance().setPayment_method_tc("");
               }
            }
        });

        visa_masterPaymentTCCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    TefalApp.getInstance().setPayment_method_tc("VIMA_AGREE");
                }
                else
                {
                    TefalApp.getInstance().setPayment_method_tc("");
                }
            }
        });
        codPaymentCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    LL_visa_masterPaymentTCContainer.setVisibility(View.GONE);
                    LL_knetPaymentTCContainer.setVisibility(View.GONE);

                    knetPaymentCheck.setChecked(false);
                    knetPaymentTCCheck.setChecked(false);
                    visa_masterPaymentCheck.setChecked(false);
                    visa_masterPaymentTCCheck.setChecked(false);

                    TefalApp.getInstance().setPayment_method_tc("COD_AGREE");
                    TefalApp.getInstance().setPayment_method("COD");


                }
                else
                {
                    TefalApp.getInstance().setPayment_method_tc("");
                    TefalApp.getInstance().setPayment_method("");
                }
            }
        });
        proceed_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("OUTPUT==== PAYMENT METHOD===="+TefalApp.getInstance().getPayment_method());
                System.out.println("OUTPUT==== PAYMENT TC===="+TefalApp.getInstance().getPayment_method_tc());
            }
        });


    }
}