package com.tefal.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefal.Models.GetCartRecord;
import com.tefal.Models.GetCartResponse;
import com.tefal.R;
import com.tefal.adapter.PagerDishdashaAdapter;
import com.tefal.adapter.TailorProductFromCartAdapter;
import com.tefal.app.TefalApp;
import com.tefal.fragment.FragmentTextileProducts;
import com.tefal.fragment.TailorTextileChooseFragment;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DishDashaProductActivity extends BaseActivity {


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

    SessionManager session;
    GetCartResponse mResponse;
    List<GetCartRecord> getCartRecordList;

    //This list is used to stored the filter data of Tailor product having itemtype DTA from GetCart Response
    List<GetCartRecord> getCartRecordList2;


    String store_id,flag,fromWhere,color,sub_color,season,country;
  //  public static TabLayout tabLayout;
   //public static ViewPager viewPager;


    /*
   * This dialog is used to show the image which can zoom in zoom out from view pager
   * */
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_dasha_product);

        ButterKnife.bind(this);

        store_id = TefalApp.getInstance().getStoreId();
        flag =  TefalApp.getInstance().getFlage();
        fromWhere=TefalApp.getInstance().getWhereFrom();

        color=TefalApp.getInstance().getColor();
        season=TefalApp.getInstance().getSeason();
        country=TefalApp.getInstance().getCountry();
        sub_color=TefalApp.getInstance().getSubColor();
        session=new SessionManager(this);

        System.out.println("OUTPUT========STORE ID"+store_id);
        System.out.println("OUTPUT========FLAG"+flag);
        System.out.println("OUTPUT========FROMWHERE"+fromWhere);
        System.out.println("OUTPUT========COLOR"+color);
        System.out.println("OUTPUT========SEASON"+season);
        System.out.println("OUTPUT========COUNTRY"+country);
        System.out.println("OUTPUT========SUB COLOR"+sub_color);





        /*TefalApp.getInstance().setFlage(flag);
        TefalApp.getInstance().setStoreId(store_id);*/



        setSupportActionBar(toolbar);
        //toolbar_title.setText(getIntent().getStringExtra("title"));

        qr_code_btn.setVisibility(View.GONE);
        view_cart_btn.setVisibility(View.VISIBLE);
        //====This block of code is responsible for restarting activity while searching by color season, etc--======

        if(getIntent().getStringExtra("store_name")!=null)
        {
            TefalApp.getInstance().setStoreName(getIntent().getStringExtra("store_name"));
        }
        toolbar_title.setText(TefalApp.getInstance().getStoreName());


       // TefalApp.getInstance().setStoreName(getIntent().getStringExtra("store_name"));
       // System.out.println("HELLO=="+getIntent().getStringExtra("store_name"));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
               onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);




        fragmentInflate();



    }

    public  void gotoCart(View v)
    {
        try
        {
            startActivity(new Intent(DishDashaProductActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fragmentInflate()
    {



        if(fromWhere.equals("textile"))
        {
            Bundle bundle=new Bundle();
            bundle.putString("store_id", store_id);
            bundle.putString("flag", "dish");

            subText.setText("Textiles");

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentTextileProducts fragmentTextileProducts = new FragmentTextileProducts();
            fragmentTextileProducts.setArguments(bundle);
           // fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentTextileProducts);
            fragmentTransaction.commit();
        }
        if(fromWhere.equals("tailor"))
        {
          //  WebCallServiceCart() ;

            subText.setText("Tailors");

            Bundle bundle=new Bundle();
            bundle.putString("store_id", store_id);
            bundle.putString("flag", "dish");

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TailorTextileChooseFragment tailorTextileChooseFragment = new TailorTextileChooseFragment();
            tailorTextileChooseFragment.setArguments(bundle);
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.fragmentContainer, tailorTextileChooseFragment);
           //
            fragmentTransaction.commit();



           // Toast.makeText(this, "HEllo Tailor", Toast.LENGTH_SHORT).show();
        }


    }






   // public interface FragmentActivityCommunication

}
