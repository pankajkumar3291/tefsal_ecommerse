package com.tefal.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tefal.Models.GetCartRecord;
import com.tefal.Models.TailoringRecord;
import com.tefal.R;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentTailorProducts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TailorProductActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.qr_code_btn)
    ImageView qr_code_btn;

    @Bind(R.id.view_cart_btn)
    ImageView view_cart_btn;

    @Bind(R.id.subText)
    TextView subText;


    @Bind(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    ArrayList<TailoringRecord> tailoringRecordArrayListOfChecked=new ArrayList<TailoringRecord>();


    List<GetCartRecord> getCartRecordListOfChecked=new ArrayList<GetCartRecord>();
    List<GetCartRecord> getCartRecordListOfCheckedTrue=new ArrayList<GetCartRecord>();

    private String ownTextileString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_product);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar_title.setText(TefalApp.getInstance().getStoreName());
        //subText.setText(TefalApp.getInstance().getWhereFrom());
        subText.setText("Tailors");





        Bundle bundle=getIntent().getExtras();
        tailoringRecordArrayListOfChecked=(ArrayList<TailoringRecord>)bundle.getSerializable("tailoringRecordArrayListOfChecked");

        if(tailoringRecordArrayListOfChecked==null)
        {
            ownTextileString=bundle.getString("ownTextileString");
            System.out.println("OUTPUT==========STORE ID==="+ownTextileString);
        }

        System.out.println("OUTPUT==========STORE ID==="+TefalApp.getInstance().getStoreId());
        System.out.println("OUTPUT==========STORE ID==="+getCartRecordListOfChecked);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        fragmentInflation();
    }
    public void gotoCart(View view)
    {
        startActivity(new Intent(TailorProductActivity.this,CartActivity.class));
    }

    private void fragmentInflation()
    {
        Bundle bundle=new Bundle();

        bundle.putSerializable("tailoringRecordArrayListOfChecked", (Serializable) tailoringRecordArrayListOfChecked);
        bundle.putSerializable("ownTextileString", ownTextileString);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentTailorProducts fragmentTailorProducts=new FragmentTailorProducts();
        fragmentTailorProducts.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainer, fragmentTailorProducts);
        fragmentTransaction.commit();

    }
}
