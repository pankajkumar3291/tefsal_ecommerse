package com.tefsalkw.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.adapter.ImageThumbnailAdapter;
import com.tefsalkw.utils.GridViewCompat;

public class CountryPopUpActivity extends AppCompatActivity {


    private GridViewCompat image_grid;
    private TextView txt_select;
    private ImageView imgv_close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_pop_up);
        image_grid = (GridViewCompat) findViewById(R.id.image_grid);
        txt_select = (TextView) findViewById(R.id.txt_select);
        imgv_close = (ImageView) findViewById(R.id.imgv_close);


        String fontPath2 = "fonts/Lato-Black.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);

        txt_select.setTypeface(tf2);


        image_grid.setLayoutAnimation(new GridLayoutAnimationController(AnimationUtils.loadAnimation(CountryPopUpActivity.this, R.anim.grid_item_fadein), LayoutAnimationController.ORDER_NORMAL, GridLayoutAnimationController.PRIORITY_ROW));
        image_grid.setLayoutAnimation(new GridLayoutAnimationController(AnimationUtils.loadAnimation(CountryPopUpActivity.this, R.anim.grid_item_fadein), 0.2f, 0.2f));

        image_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = getIntent();

                if (intent.getStringExtra("PhoneType").equalsIgnoreCase("1")) {

                    SignupActivity.selectedMobileCountry = SignupActivity.phoneCountryCodeRecordString.get(position);
                }

                if (intent.getStringExtra("PhoneType").equalsIgnoreCase("2")) {
                    SignupActivity.selectedHomeCountry = SignupActivity.phoneCountryCodeRecordString.get(position);
                }


                if (intent.getStringExtra("PhoneType").equalsIgnoreCase("3")) {

                    Intent intent2 = new Intent();
                    setResult(Activity.RESULT_OK, intent2);

                    AddAddresssAfterSignUp.selectedMobileCountry = AddAddresssAfterSignUp.phoneCountryCodeRecordString.get(position);
                }


                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        Intent intent = getIntent();

        if (intent.getStringExtra("PhoneType").equalsIgnoreCase("1")) {
            if (SignupActivity.phoneCountryCodeRecordString.size() > 0) {


                ImageThumbnailAdapter mCustomBookAdapter = new ImageThumbnailAdapter(CountryPopUpActivity.this, SignupActivity.phoneCountryCodeRecordString);
                image_grid.setAdapter(mCustomBookAdapter);
            }
        }

        if (intent.getStringExtra("PhoneType").equalsIgnoreCase("2")) {
            if (SignupActivity.phoneCountryCodeRecordString.size() > 0) {


                ImageThumbnailAdapter mCustomBookAdapter = new ImageThumbnailAdapter(CountryPopUpActivity.this, SignupActivity.phoneCountryCodeRecordString);
                image_grid.setAdapter(mCustomBookAdapter);
            }
        }


        if (intent.getStringExtra("PhoneType").equalsIgnoreCase("3")) {

            if (AddAddresssAfterSignUp.phoneCountryCodeRecordString.size() > 0) {


                ImageThumbnailAdapter mCustomBookAdapter = new ImageThumbnailAdapter(CountryPopUpActivity.this, AddAddresssAfterSignUp.phoneCountryCodeRecordString);
                image_grid.setAdapter(mCustomBookAdapter);
            }
        }


        imgv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

    }


}
