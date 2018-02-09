package com.tefal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tefal.Models.ProductMeasurement;
import com.tefal.Models.ProductRecord;
import com.tefal.R;
import com.tefal.adapter.SizeGuideAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SizeGuideActivirty extends BaseActivity {
   //@BindView(R.id.back_btn)
    ImageButton back_btn;
    RelativeLayout toolBar;

    String sizeGuideResponseHtml;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_size_guide_activirty);
            toolBar = (RelativeLayout) findViewById(R.id.toolBar);
            back_btn = (ImageButton) findViewById(R.id.btn_back);
            webView = (WebView) findViewById(R.id.webView);
            sizeGuideResponseHtml = getIntent().getExtras().getString("sizeGuideResponseHtml");

           // sizeGuideResponseHtml = g
            System.out.println("sizeGuideResponseHtml==="+sizeGuideResponseHtml);

            String html = new String(sizeGuideResponseHtml);
            String mime = "text/html";
            String encoding = "utf-8";


            try {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadDataWithBaseURL(null, html, mime, encoding, null);
            } catch (Exception ex) {
                System.out.println("Error==" + ex);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error===="+ex);
        }




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
