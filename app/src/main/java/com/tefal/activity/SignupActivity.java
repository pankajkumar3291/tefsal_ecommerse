package com.tefal.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.tefal.Models.DishdashaStylesResponse;
import com.tefal.Models.PhoneCountryCodeRecord;
import com.tefal.Models.SignUpErrorMessageRecordModel;
import com.tefal.Models.SignUpErrorResponseModel;
import com.tefal.R;
import com.tefal.app.TefalApp;
import com.tefal.utils.Contents;
import com.tefal.utils.FontChangeCrawler;
import com.tefal.utils.MultipartUtility;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SessionManagerToken;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    SessionManager session;
    SessionManagerToken session2;

    @Bind(R.id.input_layout_fname)
    TextInputLayout input_layout_fname;

    @Bind(R.id.input_layout_lname)
    TextInputLayout input_layout_lname;

    @Bind(R.id.input_layout_email)
    TextInputLayout input_layout_email;

    @Bind(R.id.input_layout_password)
    TextInputLayout input_layout_password;

    @Bind(R.id.input_layout_c_password)
    TextInputLayout input_layout_c_password;


    @Bind(R.id.input_layout_mob)
    TextInputLayout input_layout_mob;

    @Bind(R.id.input_layout_home_num)
    TextInputLayout input_layout_home_num;


    @Bind(R.id.buttonSignUp)
    Button buttonSignUp;

    @Bind(R.id.input_fname)
    EditText input_fname;

    @Bind(R.id.input_lname)
    EditText input_lname;

    @Bind(R.id.input_mob)
    EditText input_mob;

    @Bind(R.id.input_home_num)
    EditText input_home_num;

    @Bind(R.id.input_email)
    EditText input_email;

    @Bind(R.id.input_password)
    EditText input_password;

    @Bind(R.id.input_c_password)
    EditText input_c_password;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.signInTxt)
    TextView signInTxt;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.mobile_country_code_sp)
    Spinner mobile_country_code_sp;

    @Bind(R.id.home_country_code_sp)
    Spinner home_country_code_sp;

    private String accessToken;
    private String customer_id;
    private String customer_name;

    private int position;

    private StringBuilder fname=new StringBuilder();

    private ArrayList<String> phoneCountryCodeRecordString;

    private boolean is_exist_email=true;
    private String error_email_exist;
    private boolean is_exist_phone=true;
    private String error_phone_exist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));


        session = new SessionManager(this);
        session2 = new SessionManagerToken(this);

        setSupportActionBar(toolbar);
        toolbar_title.setText(R.string.title_signup);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Lato-Bold.ttf");
        toolbar_title.setTypeface(type);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getCountryCode();
        init();

        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });


        input_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                        String mob_number=s.toString();
                        if(mob_number.length()==8)
                        {
                            phoneCheckedHttpCall(mob_number);
                           // Toast.makeText(SignupActivity.this, "8 char", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            input_layout_mob.setError("");
                        }
            }
        });

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                    String string_email=s.toString();
                    if(validateEmail2(string_email))
                    {
                        checkemailHttpCall(string_email);
                    }

            }
        });
        input_fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

               /* if(s.toString().length()==1 && s.equals(""))
                {
                    input_fname.setText(s.toString().toUpperCase());
                }*/

              /*  System.out.println("TEXT===="+s);

               if(!s.equals(""))
               {
                   if(s.toString().length()==1)
                   {
                       System.out.println("TEXT===="+s.toString().toUpperCase());
                      // fname.append(s.toString().toUpperCase());
                       input_fname.setText(""+s.toString().toUpperCase());
                       //break;

                   }
                   else
                   {
                      // fname.append(s.toString());
                   }
               }*/


            }
        });




    }

    private void getCountryCode()
    {

        SimpleProgressBar.showProgress(SignupActivity.this);
        try {
            final String url = Contents.baseURL + "getCountryCodes";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE==" + response);
                            Log.e("device response", response);
                            try {

                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");

                                phoneCountryCodeRecordString=new ArrayList<String>();

                                if(status.equals("1"))
                                {
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        phoneCountryCodeRecordString.add(c.getString("phonecode"));
                                        //iso_name.add(c.getString("iso"));
                                    }

                                    for(int i=0;i<phoneCountryCodeRecordString.size();i++)
                                    {
                                        if(phoneCountryCodeRecordString.get(i).equals("965"))
                                        {
                                            position=i;
                                            break;
                                        }
                                    }

                                    System.out.println("POSITION==="+position);


                                    ArrayAdapter aa = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_spinner_item, phoneCountryCodeRecordString);

                                    mobile_country_code_sp.setAdapter(aa);
                                    home_country_code_sp.setAdapter(aa);

                                    mobile_country_code_sp.setSelection(position);
                                    home_country_code_sp.setSelection(position);


                                    mobile_country_code_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                        {

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent)
                                        {

                                        }
                                    });


                                    home_country_code_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                    {


                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                        {

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    //mobile_country_code_sp.setOnItemSelectedListener



                                }



                            } catch (Exception e) {
                                System.out.println("EX==" + e);
                                SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("EX==" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Refsal device == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }



    private void checkemailHttpCall(final String string_email)
    {

       // SimpleProgressBar.showProgress(SignupActivity.this);
        try {
            final String url = Contents.baseURL + "customerCheckEmail";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE==" + response);

                            Log.e("device response", response);
                            try {

                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                 error_email_exist = object.getString("message");

                                if(status.equals("0"))
                                {
                                    is_exist_email=true;
                                    input_layout_email.setError(error_email_exist);
                                    requestFocus(input_email);
                                   System.out.println("MESSAGE======"+error_email_exist);
                                }
                                else
                                {
                                    is_exist_email=false;
                                    input_layout_email.setError("");
                                    System.out.println("MESSAGE======"+error_email_exist);
                                }

                            }
                            catch (Exception e) {
                                System.out.println("EX==" + e);
                               // SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("EX==" + error);
                           // SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("email", string_email);

                    Log.e("Refsal device == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    private void phoneCheckedHttpCall(final String string_phone)
    {
// SimpleProgressBar.showProgress(SignupActivity.this);
        try {
            final String url = Contents.baseURL + "customerCheckMobile";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE==" + response);

                            Log.e("device response", response);
                            try {

                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                error_phone_exist = object.getString("message");

                                if(status.equals("0"))
                                {
                                    is_exist_phone=true;
                                    input_layout_mob.setError(error_phone_exist);
                                    requestFocus(input_mob);
                                    System.out.println("MESSAGE======"+error_phone_exist);
                                }
                                else
                                {
                                    is_exist_phone=false;
                                    input_layout_mob.setError("");
                                    //System.out.println("MESSAGE======"+error_phone_exist);
                                }

                            }
                            catch (Exception e) {
                                System.out.println("EX==" + e);
                                // SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("EX==" + error);
                            // SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("mobile", string_phone);


                    Log.e("Refsal device == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    private void init() {

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               /* if (Contents.isBlank(input_fname.getText().toString().trim())) {
                    input_fname.setError(getString(R.string.FirstNameTooshort));
                    return;
                }
                if (Contents.isBlank(input_lname.getText().toString().trim())) {
                    input_lname.setError(getString(R.string.LastNameTooshort));
                    return;
                }
                if (!Contents.isProperEmail(input_email.getText().toString().trim())) {
                    input_email.setError(getString(R.string.invalidEmail));
                    return;
                }
                if (Contents.isBlank(input_mob.getText().toString().trim())
                        && input_mob.getText().toString().trim().length() < 10) {

                    input_mob.setError(getString(R.string.phoneTooshort));
                    return;
                }
                *//*if (Contents.isBlank(input_home_num.getText().toString().trim())
                        && input_home_num.getText().toString().trim().length() < 10) {

                    input_home_num.setError(getString(R.string.homeNoTooshort));
                    return;
                }*//*
                if (Contents.isBlank(input_password.getText().toString().trim())) {

                    input_password.setError(getString(R.string.passwordNotBlank));
                    return;
                }
                if (input_password.getText().toString().trim().length() < 8) {

                    input_password.setError(getString(R.string.passwordTooshort));
                    return;
                }
                if (Contents.isNotMatch(input_password.getText().toString().trim(),
                        input_c_password.getText().toString().trim())) {

                    input_c_password.setError(getString(R.string.passwordNotMatch));
                    return;
                }*/

                // Block of code to eliminate the existing error message while reclicking the submit button
                input_layout_fname.setError("");
                input_layout_lname.setError("");
                input_layout_email.setError("");
                input_layout_password.setError("");
                input_layout_c_password.setError("");
                input_layout_home_num.setError("");
                input_layout_mob.setError("");



                if(validateFirstName(input_fname.getText().toString().trim())
                        /*&& validateLastName(input_lname.getText().toString().trim())*/
                        && validateMobileNumber(input_mob.getText().toString().trim())
                        && validateMobileServer(is_exist_phone)

                       /* && validateHomeNumber(input_home_num.getText().toString().trim())*/
                        && validateEmail(input_email.getText().toString().trim())
                        && validateEmailServer(is_exist_email)
                        && validatePassword(input_password.getText().toString().trim())
                        && validateC_password(input_c_password.getText().toString().trim()))

                WebCallService(input_fname.getText().toString().trim(), input_lname.getText().toString().trim(),
                        input_mob.getText().toString().trim(), input_home_num.getText().toString().trim(),
                        input_email.getText().toString().trim(), input_password.getText().toString().trim(),
                        input_c_password.getText().toString().trim()
                        );
            }
        });

    }
    public void WebCallService(final String str_fname, final String str_lname, final String str_mob, final String str_home_num,
                               final String str_email, final String str_password, final String str_c_password) {
        SimpleProgressBar.showProgress(SignupActivity.this);
        try {
            final String url = Contents.baseURL+"customerRegister";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            Log.e("Signup response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");

                                Log.e("Signup TAG", ">>>" + response);
                                if (status.equals("1")) {
                                    JSONObject jsonObject = object.getJSONObject("record");
                                   // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    System.out.println("CUSTOMER ID==="+jsonObject.getString("customer_id"));
                                    // If
                                   /* session.setCustomerId(jsonObject.getString("customer_id"));
                                    session.setToken(jsonObject.getString("access_token"));
                                    session.setKeyUserName(str_fname+" "+str_lname);*/

                                    accessToken=jsonObject.getString("access_token");
                                    customer_id=jsonObject.getString("customer_id");



                                    TefalApp.getInstance().setCustomer_id(customer_id);
                                    TefalApp.getInstance().setAccessToken(accessToken);
                                    TefalApp.getInstance().setRegSuccessMsg(message);

                                    session.setKeyPass(str_password);
                                    session.setKeyEmail(str_email.trim());

                                    System.out.println("FROM SESSION ======EMAIL==="+session.getKeyEmail());
                                    System.out.println("FROM SESSION ======PASS==="+session.getKeyPass());


                                   // startActivity(new Intent(SignupActivity.this, AdditionalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                   // startActivity(new Intent(SignupActivity.this, AdditionalInfoActivity.class).putExtra("accessToken",accessToken).putExtra("customer_id",customer_id).putExtra("customer_name",str_fname+" "+str_lname).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    startActivity(new Intent(SignupActivity.this,AddAddresssAfterSignUp.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();

                                }
                                else
                                {
                                    Gson g = new Gson();
                                    SignUpErrorMessageRecordModel signUpErrorMessageRecordModel=g.fromJson(message, SignUpErrorMessageRecordModel.class);

                                    System.out.println("Error============="+signUpErrorMessageRecordModel.getFirst_name());
                                    System.out.println("Error============="+signUpErrorMessageRecordModel.getEmail());
                                    System.out.println("Error============="+signUpErrorMessageRecordModel.getMobile());
                                    System.out.println("Error============="+signUpErrorMessageRecordModel.getPassword());
                                    System.out.println("Error============="+signUpErrorMessageRecordModel.getPassword_confirmation());

                                    if(!signUpErrorMessageRecordModel.getPassword_confirmation().equals(null))
                                    {
                                      //  validateC_password(signUpErrorMessageRecordModel.getPassword_confirmation());
                                        passwordConfirmationError(signUpErrorMessageRecordModel.getPassword_confirmation());

                                    }
                                    if(!signUpErrorMessageRecordModel.getPassword().equals(null))
                                    {
                                        passwordError(signUpErrorMessageRecordModel.getPassword());
                                    }
                                    if(!signUpErrorMessageRecordModel.getEmail().equals(null))
                                    {
                                        emailError(signUpErrorMessageRecordModel.getEmail());
                                    }
                                    if(!signUpErrorMessageRecordModel.getMobile().equals(null))
                                    {
                                        mobileNumberError(signUpErrorMessageRecordModel.getMobile());
                                    }

                                    if(!signUpErrorMessageRecordModel.getFirst_name().equals(null))
                                    {
                                        fnameError(signUpErrorMessageRecordModel.getFirst_name());
                                    }


                                   // DishdashaStylesResponse mResponse = g.fromJson(response, DishdashaStylesResponse.class);


                                    // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                SimpleProgressBar.closeProgress();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();



                    System.out.println("OUT PUT =========first_name  "+str_fname);
                    System.out.println("OUT PUT =========last_name  "+str_lname);
                    System.out.println("OUT PUT =========country_code  "+mobile_country_code_sp.getItemAtPosition(position));
                    System.out.println("OUT PUT =========mobile  "+str_mob);
                    System.out.println("OUT PUT =========home_no  "+str_home_num);
                    System.out.println("OUT PUT =========email  "+str_email);
                    System.out.println("OUT PUT =========password  "+str_password);
                    System.out.println("OUT PUT =========password_confirmation  "+str_c_password);



                    params.put("first_name", str_fname);
                    params.put("last_name", str_lname);
                    params.put("country_code", ""+mobile_country_code_sp.getItemAtPosition(position));
                    params.put("mobile",str_mob);
                    params.put("home_no",str_home_num);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("password_confirmation", str_c_password);
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Refsal signup == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private boolean validateFirstName(String fname)
    {

        if(fname.equals(""))
        {
            input_layout_fname.setError("Error: First name should not be empty");
            requestFocus(input_fname);
            return false;
        }
        return true;
    }
    private boolean validateLastName(String lname)
    {
        if(lname.equals(""))
        {
            input_layout_lname.setError("Error: Last name should not be empty");
            requestFocus(input_lname);
            return false;
        }
        return true;
    }
    private boolean validateEmail(String email)
    {
       if(email.equals(""))
       {
           input_layout_email.setError("Error: Email should not be empty");
           requestFocus(input_email);
           return false;
       }
       else
       {
           if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
           {
               input_layout_email.setError("Error: Not valid email");
               requestFocus(input_email);
               return false;
           }

       }
        return true;

    }
// This function is used to validate email while entering from user

    private boolean validateEmail2(String email)
    {
        if(email.equals(""))
        {
            //input_layout_email.setError("Error: Email should not be empty");
            //requestFocus(input_email);
            return false;
        }
        else
        {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                //input_layout_email.setError("Error: Not valid email");
                //requestFocus(input_email);
                return false;
            }

        }
        return true;

    }

    // This function is used to validate email from server response
    private boolean validateEmailServer(boolean is_exist_email)
    {
       if(is_exist_email)
       {
           input_layout_email.setError("The email has already been taken.");
           requestFocus(input_email);
           return false;
       }
       else
       {
           return true;
       }


    }


    private boolean validatePassword(String pass)
    {
        if(pass.equals(""))
        {
            input_layout_password.setError("Error: password should not be empty");
            requestFocus(input_password);
            return false;
        }
        if(pass.length()<=7)
        {
            input_layout_password.setError("Error: password must have more then 8 characters");
            requestFocus(input_password);
            return false;
        }

        return true;
    }
    private boolean validateC_password(String c_pass)
    {
        if(c_pass.equals(""))
        {
            input_layout_c_password.setError("Error: confirm password should not be empty");
            requestFocus(input_c_password);
            return false;
        }
        else if(!c_pass.equals(input_password.getText().toString().trim()))
        {
                input_layout_password.setError("Error: password mismatch");
                requestFocus(input_password);
                return false;
        }
        else
        {
            return true;
        }



       // return true;
    }


    private boolean validateMobileNumber(String mobileNumber)
    {
        if(mobileNumber.equals(""))
        {
            input_layout_mob.setError("Error: Mobile number should not be empty");
            requestFocus(input_mob);
            return false;

        }
        else if(mobileNumber.length()!=8)
        {
            input_layout_mob.setError("Error: Mobile number should be 8 digits");
            requestFocus(input_mob);
            return false;
        }
        else
        {
            return true;
        }

    }


    private boolean validateMobileServer(boolean error_phone_exist)
    {

        if(error_phone_exist)
        {
            input_layout_mob.setError("The mobile has already been taken");
            requestFocus(input_mob);
            return false;
        }
        else
        {
            return true;
        }
    }


    private boolean validateHomeNumber(String homeNumber)
    {
        if(homeNumber.equals(""))
        {
            input_layout_home_num.setError("Error: Home number should not be empty");
            requestFocus(input_home_num);
            return false;
        }
        else if(homeNumber.length()!=8)
        {
            input_layout_home_num.setError("Error: Home number should be 8 digits");
            requestFocus(input_home_num);
            return false;
        }
        else
        {

            return true;
        }

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    // Block of function required to show error message after server response
    //   =====================================================================================

    private void fnameError( String errorString)
    {
        input_layout_fname.setError(errorString);
        requestFocus(input_fname);
    }

    private void emailError(String errorString)
    {
            input_layout_email.setError(errorString);
            requestFocus(input_email);
    }

    private void passwordConfirmationError(String errorString)
    {
        input_layout_c_password.setError(errorString);
        requestFocus(input_c_password);
    }
    private void passwordError(String errorString)
    {
        input_layout_password.setError(errorString);
        requestFocus(input_password);
    }
    private void mobileNumberError(String errorString)
    {
        input_layout_mob.setError(errorString);
        requestFocus(input_mob);
    }



}
