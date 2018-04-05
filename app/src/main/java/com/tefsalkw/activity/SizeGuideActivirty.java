package com.tefsalkw.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tefsalkw.R;

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
