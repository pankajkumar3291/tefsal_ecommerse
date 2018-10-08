package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tefsalkw.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionStatusActivity extends BaseActivity {


    @BindView(R.id.rlTxnSuccess)
    RelativeLayout rlTxnSuccess;

    @BindView(R.id.rlTxnFailed)
    RelativeLayout rlTxnFailed;


    @BindView(R.id.txtTxnSuccessTitle)
    TextView txtTxnSuccessTitle;

    @BindView(R.id.txtTxnSuccessDesc)
    TextView txtTxnSuccessDesc;

    @BindView(R.id.txtTxnFailedTitle)
    TextView txtTxnFailedTitle;

    @BindView(R.id.txtTxnFailedDesc)
    TextView txtTxnFailedDesc;


    @BindView(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_status);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null) {
            String txnStatus = intent.getStringExtra("TxnStatus");

            if (txnStatus != null) {

                if (txnStatus.equals("1")) {
                    rlTxnSuccess.setVisibility(View.VISIBLE);
                    rlTxnFailed.setVisibility(View.GONE);


                    txtTxnSuccessTitle.setText("Transaction Successful");
                    txtTxnSuccessDesc.setText("Thank you for shopping with Tefsal. Your transaction is successful and you'll be notified about the order status.");

                } else {
                    rlTxnSuccess.setVisibility(View.GONE);
                    rlTxnFailed.setVisibility(View.VISIBLE);

                    txtTxnFailedTitle.setText("Transaction Failed");
                    txtTxnFailedDesc.setText("Sorry! Your transaction is not successful. Please try again");

                }
            }

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(TransactionStatusActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

    }
}
