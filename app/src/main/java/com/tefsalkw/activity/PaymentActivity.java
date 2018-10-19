package com.tefsalkw.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tefsalkw.R;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tefsalkw.utils.Contents.paymentBaseUrl;

public class PaymentActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;


    SessionManager sessionManager;

    private static Tracker mTracker;

    String payment_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);
        //Init Additional
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        sessionManager = new SessionManager(this);
        toolbar_title.setText("Checkout");
        cancel_btn.setVisibility(View.GONE);
        setUpWebView();


        try {

            String userId = sessionManager.getCustomerId();
            String cartId = sessionManager.getKeyCartId();

            Intent intent = getIntent();

            String amount = intent.getStringExtra("Amount");

            long first11 = (long) (Math.random() * 100000000000L);
            String paymentUrl = paymentBaseUrl + "/knet/buy.php?user_id=" + userId + "&cart_id=" + cartId + "&amount=" + amount+"&track_id="+first11;

            SimpleProgressBar.showProgress(PaymentActivity.this);
            webView.loadUrl(paymentUrl);


        } catch (Exception exc) {

        }


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setMessage(R.string.cancel_confirmation)
                .setCancelable(false)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Cancel Transaction");
        alert.show();

    }

    private void setUpWebView() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webSettings.setBuiltInZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {

            Log.d("CONTENT", String.format("%s @ %d: %s", cm.message(), cm.lineNumber(), cm.sourceId()));

            return true;
        }


        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("PaymentActivity", "Url shouldOverrideUrlLoading : " + url);

            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Log.e("url", url);
            SimpleProgressBar.closeProgress();
            if (url.contains(paymentBaseUrl + "/knet/result.php")) {

                try {

                    Uri uri = Uri.parse(url);

                    String result = uri.getQueryParameter("Result");

                    payment_id = uri.getQueryParameter("PaymentID");

                    if (result != null && result.equals("CAPTURED")) {

                        //Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_LONG).show();

                        WebCallServiceOrder();

                    } else {

                        startActivity(new Intent(PaymentActivity.this, TransactionStatusActivity.class).putExtra("TxnStatus", "0").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                        finish();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            if (url.contains(paymentBaseUrl + "error")) {

                try {

                    Toast.makeText(PaymentActivity.this, "Payment didn't go through, please try again", Toast.LENGTH_LONG).show();

                    finish();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            SimpleProgressBar.closeProgress();

            Toast.makeText(PaymentActivity.this, "Error occurred. Please try again!", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    public void WebCallServiceOrder() {


        final String url = Contents.baseURL + "checkout";


        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        SimpleProgressBar.showProgress(this);


        Map<String, Object> params = new HashMap<String, Object>();

        try {

            Intent intent = getIntent();

            if (sessionManager.getIsGuestId()) {

                params.put("unique_id", sessionManager.getCustomerId());
                params.put("guest", intent.getSerializableExtra("guest"));

            } else {
                params.put("user_id", sessionManager.getCustomerId());
                params.put("access_token", sessionManager.getToken());
                params.put("address_id", intent.getStringExtra("defaultAddressId"));
            }


            params.put("appUser", "tefsal");
            params.put("appSecret", "tefsal@123");
            params.put("appVersion", "1.1");

            //Api specific
            params.put("cart_id", sessionManager.getKeyCartId());
            params.put("payment_method", "KNET");
            params.put("payment_id", payment_id);
            //params.put("promo_id", intent.getStringExtra("delivery_charge"));
            params.put("delivery_charge", intent.getStringExtra("delivery_charge"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Tefsal tailor == ", url + new JSONObject(params));


        BaseHttpClient baseHttpClient = new BaseHttpClient();
        baseHttpClient.doPost(url, new JSONObject(params), new BaseHttpClient.TaskCompleteListener<String>() {
            @Override
            public void onFailure() {
                SimpleProgressBar.closeProgress();
            }

            @Override
            public void onSuccess(String object) {

                try {

                    SimpleProgressBar.closeProgress();
                    Log.e("Order Response", object);

                    JSONObject jsonObject = null;
                    try {

                        jsonObject = new JSONObject(object);

                        if (jsonObject.getInt("status") == 1) {

                            SimpleProgressBar.closeProgress();

                            // Toast.makeText(PaymentActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            startActivity(new Intent(PaymentActivity.this, TransactionStatusActivity.class).putExtra("TxnStatus", "1").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));



                        }
                        else
                        {
                            startActivity(new Intent(PaymentActivity.this, TransactionStatusActivity.class).putExtra("TxnStatus", "0").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                        }

                    } catch (JSONException e) {
                        SimpleProgressBar.closeProgress();
                        e.printStackTrace();

                        startActivity(new Intent(PaymentActivity.this, TransactionStatusActivity.class).putExtra("TxnStatus", "0").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                        // Toast.makeText(PaymentActivity.this, "Error in processing transaction!", Toast.LENGTH_LONG).show();

                    }


                } catch (Exception e1) {
                    e1.printStackTrace();
                    SimpleProgressBar.closeProgress();

                    startActivity(new Intent(PaymentActivity.this, TransactionStatusActivity.class).putExtra("TxnStatus", "0").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                    //  Toast.makeText(PaymentActivity.this, "Error in processing transaction!", Toast.LENGTH_LONG).show();

                }

            }
        });


    }


}
