package com.tefal.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.gson.reflect.TypeToken;
import com.tefal.Models.CountryModel;
import com.tefal.Models.SignUpErrorMessageRecordModel;
import com.tefal.R;
import com.tefal.app.TefalApp;
import com.tefal.kotlin.SignupThankYouActivity;
import com.tefal.utils.Contents;
import com.tefal.utils.PreferencesUtil;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditionalInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.skipTxt)
    TextView skipTxt;

    @BindView(R.id.input_dob)
    EditText input_dob;

    @BindView(R.id.nationalitySpinner)
    Spinner nationalitySpinner;

    @BindView(R.id.genderSpinner)
    Spinner genderSpinner;

    @BindView(R.id.submintBtn)
    Button submintBtn;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.dobInputLayout)
    TextInputLayout dobInputLayout;


    List<String> country_name;
    List<String> iso_name;
    private String iso_string;
    private String mGender = "M";
    private String mDob = "";
    private int position;

    SessionManager session;

    private String accessToken;
    private String customer_id;
    private String customer_name;
    String input_email = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(this);

        accessToken = getIntent().getStringExtra("accessToken");

        System.out.println("Customer id from Singletone====" + TefalApp.getInstance().getCustomer_id());
        customer_id = "" + TefalApp.getInstance().getCustomer_id();
        customer_name = getIntent().getStringExtra("customer_name");


        submintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              doFullSignUp();


            }
        });


        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mGender = "M";
                        break;
                    case 1:
                        mGender = "F";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear = 0;
                int mMonth = 0;
                int mDay = 0;

                try {


                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    //  System.out.println("DOB===FROM NULL"+records.get("dob"));


                } catch (Exception ex) {
                    System.out.println("EXCEPTION====" + ex);
                }


                DatePickerDialog datePickerDialog = new DatePickerDialog(AdditionalInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                input_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                mDob = input_dob.getText().toString();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        skipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdditionalInfoActivity.this, SignupThankYouActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                //finish();

                doFullSignUp();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getCountries();
    }

    public void getCountries() {


        SimpleProgressBar.showProgress(AdditionalInfoActivity.this);
        try {
            final String url = Contents.baseURL + "getCountries";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1")) {

                                    country_name = new ArrayList<String>();
                                    iso_name = new ArrayList<String>();
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        country_name.add(c.getString("name"));
                                        iso_name.add(c.getString("iso"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AdditionalInfoActivity.this, android.R.layout.simple_spinner_item, country_name);

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    nationalitySpinner.setAdapter(aa);

                                    nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //else
                                            // ww;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    for (int i = 0; i < iso_name.size(); i++) {
                                        if (iso_name.get(i).contains("KW")) {
                                            position = i;
                                            //mDob=iso_name.get(position);
                                            break;
                                        }
                                    }

                                    nationalitySpinner.setSelection(position);

                                } else {

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(AdditionalInfoActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private boolean validateDob() {
        if (mDob.equals("")) {
            dobInputLayout.setError(getString(R.string.dateRequireMessage));
            requestFocus(input_dob);
            return false;
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private void doFullSignUp() {

        String input_fname = "";
        String input_lname = "";
        String input_mob = "";


        String input_home_num = "";
        String input_password = "";
        String country_code = "";

        try {

            Gson gson = new Gson();

            JSONObject jsonObject = gson.fromJson(PreferencesUtil.getString(this, "SignupBundle", ""), new TypeToken<JSONObject>() {
            }.getType());


            if (jsonObject != null) {


                input_fname = jsonObject.getString("input_fname");
                input_lname = jsonObject.getString("input_lname");
                input_mob = jsonObject.getString("input_mob");
                input_email = jsonObject.getString("input_email");
                input_home_num = jsonObject.getString("input_home_num");
                input_password = jsonObject.getString("input_password");

                CountryModel selectedMobileCountry = gson.fromJson(jsonObject.get("selectedMobileCountry").toString(), new TypeToken<CountryModel>() {
                }.getType());

                country_code = selectedMobileCountry.getPhonecode();

                WebCallService(input_fname, input_lname, input_mob, input_home_num, input_email, input_password, country_code);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    //Step 1
    public void WebCallService(final String str_fname, final String str_lname, final String str_mob, final String str_home_num,
                               final String str_email, final String str_password, final String country_code) {
        SimpleProgressBar.showProgress(AdditionalInfoActivity.this,"Registration in progress...Step 1 of 3");
        try {
            final String url = Contents.baseURL + "customerRegister";

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
                                    System.out.println("CUSTOMER ID===" + jsonObject.getString("customer_id"));
                                    // If
                                   /* session.setCustomerId(jsonObject.getString("customer_id"));
                                    session.setToken(jsonObject.getString("access_token"));
                                    session.setKeyUserName(str_fname+" "+str_lname);*/

                                    accessToken = jsonObject.getString("access_token");
                                    customer_id = jsonObject.getString("customer_id");


                                    TefalApp.getInstance().setCustomer_id(customer_id);
                                    TefalApp.getInstance().setAccessToken(accessToken);
                                    TefalApp.getInstance().setRegSuccessMsg(message);

                                    session.setKeyPass(str_password);
                                    session.setKeyEmail(str_email.trim());

                                    System.out.println("FROM SESSION ======EMAIL===" + session.getKeyEmail());
                                    System.out.println("FROM SESSION ======PASS===" + session.getKeyPass());
                                    saveAddress();

                                } else {
                                    Gson g = new Gson();
                                    SignUpErrorMessageRecordModel signUpErrorMessageRecordModel = g.fromJson(message, SignUpErrorMessageRecordModel.class);

                                    System.out.println("Error=============" + signUpErrorMessageRecordModel.getFirst_name());
                                    System.out.println("Error=============" + signUpErrorMessageRecordModel.getEmail());
                                    System.out.println("Error=============" + signUpErrorMessageRecordModel.getMobile());
                                    System.out.println("Error=============" + signUpErrorMessageRecordModel.getPassword());
                                    System.out.println("Error=============" + signUpErrorMessageRecordModel.getPassword_confirmation());


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


                    System.out.println("OUT PUT =========first_name  " + str_fname);
                    System.out.println("OUT PUT =========last_name  " + str_lname);
                    System.out.println("OUT PUT =========country_code  " + country_code);
                    System.out.println("OUT PUT =========mobile  " + str_mob);
                    System.out.println("OUT PUT =========home_no  " + str_home_num);
                    System.out.println("OUT PUT =========email  " + str_email);
                    System.out.println("OUT PUT =========password  " + str_password);
                    System.out.println("OUT PUT =========password_confirmation  " + str_password);


                    params.put("first_name", str_fname);
                    params.put("last_name", str_lname);
                    params.put("country_code", "" + country_code);
                    params.put("mobile", str_mob);
                    params.put("home_no", str_home_num);
                    params.put("email", str_email);
                    params.put("password", str_password);
                    params.put("password_confirmation", str_password);
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
            RequestQueue requestQueue = Volley.newRequestQueue(AdditionalInfoActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    //Step 2
    public void saveAddress() {

        SimpleProgressBar.showProgress(AdditionalInfoActivity.this,"Registration in progress...Step 2 of 3");


        //province_id
        try {
            final String url = Contents.baseURL + "customerSaveAddress";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            System.out.println("Response===" + response);
                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1")) {

                                    httpAdditionalInfoCall();

                                } else {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("Error===" + e);
                                SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error===" + error);

                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("access_token", TefalApp.getInstance().getAccessToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    try {

                        Gson gson = new Gson();

                        JSONObject jsonObject = gson.fromJson(PreferencesUtil.getString(AdditionalInfoActivity.this, "SignupBundle2", ""), new TypeToken<JSONObject>() {
                        }.getType());


                        params.put("customer_id", TefalApp.getInstance().getCustomer_id());

                        params.put("address_name", jsonObject.getString("input_address_name"));



                        params.put("city", jsonObject.getString("spin_city_txt")+ "");
                        params.put("area", jsonObject.getString("spin_area_txt")+ "");
                        params.put("block", jsonObject.getString("input_block")+ "");
                        params.put("street", jsonObject.getString("input_street")+ "");
                        params.put("avenue", jsonObject.getString("input_avenue")+ "");
                        params.put("floor", jsonObject.getString("input_floor")+ "");
                        params.put("house", jsonObject.getString("input_house")+ "");
                        params.put("flat_number", jsonObject.getString("input_flate") + "");
                        params.put("phone_number", jsonObject.getString("input_phone") + "");

                        params.put("paci_number", jsonObject.getString("input_paci_num") + "");
                        params.put("addt_info", jsonObject.getString("input_add_desp") + "");


                        CountryModel   selectedMobileCountry =  gson.fromJson(jsonObject.get("selectedMobileCountry").toString(), new TypeToken<CountryModel>() {
                        }.getType());

                        params.put("country", selectedMobileCountry.getNicename()+ "");


                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }


                    Log.e(AdditionalInfoActivity.class.getSimpleName(), String.valueOf(new JSONObject(params)));

                    Log.e("Refsal req == ", url + params);


                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AdditionalInfoActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {

            System.out.println("Error===" + surError);
            surError.printStackTrace();
        }
    }


    //Step 3

    public void httpAdditionalInfoCall() {
        SimpleProgressBar.showProgress(AdditionalInfoActivity.this,"Registration in progress...Step 3 of 3");

        try {
            final String url = Contents.baseURL + "customerAddtInfo";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            Log.e("AdditionalInfo response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");

                                Log.e("AdditionalInfo", ">>>" + response);
                                if (status.equals("1")) {


                                    PreferencesUtil.putString(AdditionalInfoActivity.this, "SignupBundle", "");
                                    PreferencesUtil.putString(AdditionalInfoActivity.this, "SignupBundle2", "");

                                    //Toast.makeText(getApplicationContext(), "Registration successful...Sign in to continue", Toast.LENGTH_LONG).show();

                                   /* session.setCustomerId(jsonObject.getString("customer_id"));
                                    session.setToken(jsonObject.getString("access_token"));
                                    session.setKeyUserName(str_fname+" "+str_lname);*/

                                    startActivity(new Intent(AdditionalInfoActivity.this, SignupThankYouActivity.class).putExtra("email",input_email).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    // startActivity(new Intent(AdditionalInfoActivity.this, AdditionalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();

                                } else {

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                SimpleProgressBar.closeProgress();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("Error====" + error);
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customer_id", customer_id);
                    params.put("gender", mGender);
                    params.put("dob", mDob);
                    params.put("nationality", iso_name.get(position));

                    System.out.println("OUT PUT DATE OF BIRTH==" + mDob);
                    System.out.println("OUT PUT NATIONALITY==" + iso_name.get(position));
                    System.out.println("OUT PUT GENDER==" + mGender);

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
            RequestQueue requestQueue = Volley.newRequestQueue(AdditionalInfoActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }



}

