package com.tefsalkw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class ChangePasswordActivity extends BaseActivity
{



    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.btn_chng_pwd)
    Button btn_chng_pwd;

    @BindView(R.id.input_layout_current_pwd)
    TextInputLayout input_layout_current_pwd;

    @BindView(R.id.input_layout_new_pwd)
    TextInputLayout input_layout_new_pwd;

    @BindView(R.id.input_layout_confirm_pwd)
    TextInputLayout input_layout_confirm_pwd;


    @BindView(R.id.current_pwd)
    EditText current_pwd;

    @BindView(R.id.input_new_pwd)
    EditText input_new_pwd;

    @BindView(R.id.input_confirm_pwd)
    EditText input_confirm_pwd;


    private SessionManager mSessionManager;

    private String newPassword;
    private String confirmPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        //().hide();
       // getSupportActionBar(null)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_title.setText("Change Password");

        mSessionManager = new SessionManager(getApplicationContext());

        //toolbar_title.setText();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_chng_pwd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                newPassword= input_new_pwd.getText().toString();
                confirmPassword=input_confirm_pwd.getText().toString();
                input_layout_new_pwd.setError("");
                input_layout_confirm_pwd.setError("");

                if(newPasswordValidate(newPassword) && confirmPasswordValidate(confirmPassword) && passwordMismatchValidate(newPassword,confirmPassword))
                    changePasswordHttpCall();

              /*  if(confirmValidate())
                {
                    if(input_confirm_pwd.getText().toString().trim().isEmpty())
                    {
                        input_layout_confirm_pwd.setError("Confirm password should not be empty");
                        requestFocus(input_confirm_pwd);
                    }
                    if(input_new_pwd.getText().toString().trim().isEmpty())
                    {
                        input_layout_new_pwd.setError("New password should not be empty");
                        requestFocus(input_new_pwd);
                    }
                    if(!input_confirm_pwd.getText().toString().trim().isEmpty() && !input_new_pwd.getText().toString().trim().isEmpty())
                    {
                       // input_layout_new_pwd.setEnabled(false);
                        //input_layout_confirm_pwd.setEnabled(false);
                        changePasswordHttpCall();

                   // Toast.makeText(getApplicationContext(),"You are onn", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    input_layout_new_pwd.setError("");
                    input_layout_confirm_pwd.setError("Password mismatch");
                    requestFocus(input_confirm_pwd);
                }*/
                //Toast.makeText(ChangePasswordActivity.this, "ello", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean confirmValidate()
    {
        if(input_new_pwd.getText().toString().equals(input_confirm_pwd.getText().toString()))
        {
            return true;
        }
        else
        {
            return  false;
        }

    }


    private boolean newPasswordValidate(String newPassword)
    {
        if(newPassword.equals(""))
        {
           input_layout_new_pwd.setError(getString(R.string.emptyNewPassword));
            return false;
        }
        if(newPassword.length()<8)
        {
            input_layout_new_pwd.setError(getString(R.string.invalidPasswordFormat));
            return false;
        }

        return true;
    }
    private boolean passwordMismatchValidate(String newPassword, String confirmPassword)
    {
        if(!newPassword.equals(confirmPassword))
        {
            input_layout_confirm_pwd.setError(getString(R.string.mismatchPassword));
            requestFocus(input_confirm_pwd);
            return false;

        }
        return true;
    }
    private boolean confirmPasswordValidate(String confirmPassword)
    {
        if(confirmPassword.equals(""))
        {
            input_layout_confirm_pwd.setError(getString(R.string.emptyConfirmPassword));
            requestFocus(input_confirm_pwd);
            return false;
        }
        if(confirmPassword.length()<8)
        {
            input_layout_confirm_pwd.setError(getString(R.string.invalidPasswordFormat));
            requestFocus(input_confirm_pwd);
            return false;

        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void changePasswordHttpCall()
    {
        SimpleProgressBar.showProgress(ChangePasswordActivity.this);
        try {
            final String url = Contents.baseURL + "changePassword";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response=="+response.toString());

                            mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null)
                            {
                                mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try
                                {

                                    JSONObject object = new JSONObject(response);

                                    String status=object.getString("status");
                                    if(status.equals("0"))
                                    {
                                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    }



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", mSessionManager.getCustomerId());
                    params.put("old_password", current_pwd.getText().toString());
                    params.put("password", input_new_pwd.getText().toString());
                    params.put("password_confirmation", input_confirm_pwd.getText().toString());
                    params.put("access_token", mSessionManager.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal tailor == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }
}
