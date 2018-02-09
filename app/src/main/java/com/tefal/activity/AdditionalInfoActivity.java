package com.tefal.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.analytics.HitBuilders;
import com.tefal.R;
import com.tefal.app.TefalApp;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SessionManagerToken;
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
    private String mGender="M";
    private String mDob="";
    private int position;

    SessionManager session;

    private String accessToken;
    private String customer_id;
    private String customer_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(this);

        accessToken=getIntent().getStringExtra("accessToken");

        System.out.println("Customer id from Singletone===="+TefalApp.getInstance().getCustomer_id());
        customer_id=""+ TefalApp.getInstance().getCustomer_id();
        customer_name=getIntent().getStringExtra("customer_name");



        submintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(validateDob())
                {
                    httpAdditionalInfoCall();
                }

            }
        });



        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        mGender="M";
                        break;
                    case 1:
                        mGender="F";
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

               int mYear=0;
               int mMonth=0;
               int mDay=0;

                try
                {


                        Calendar c = Calendar.getInstance();
                        mYear=c.get(Calendar.YEAR);
                        mMonth=c.get(Calendar.MONTH);
                        mDay=c.get(Calendar.DAY_OF_MONTH);
                      //  System.out.println("DOB===FROM NULL"+records.get("dob"));


                }
                catch(Exception ex)
                {
                    System.out.println("EXCEPTION===="+ex);
                }



                DatePickerDialog datePickerDialog = new DatePickerDialog(AdditionalInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                input_dob.setText(year + "-" + (monthOfYear+1) + "-" +dayOfMonth);
                                mDob=input_dob.getText().toString();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        skipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdditionalInfoActivity.this, SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
                                    for (int i =0;i<jsonArray.length();i++)
                                    {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        country_name.add(c.getString("name"));
                                        iso_name.add(c.getString("iso"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AdditionalInfoActivity.this,android.R.layout.simple_spinner_item,country_name);

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    nationalitySpinner.setAdapter(aa);

                                    nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                        {
                                            //else
                                            // ww;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                    for(int i=0;i<iso_name.size();i++)
                                    {
                                        if(iso_name.get(i).contains("KW"))
                                        {
                                            position=i;
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

    public void httpAdditionalInfoCall() {
        SimpleProgressBar.showProgress(AdditionalInfoActivity.this);
        try {
            final String url = Contents.baseURL+"customerAddtInfo";

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


                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                   /* session.setCustomerId(jsonObject.getString("customer_id"));
                                    session.setToken(jsonObject.getString("access_token"));
                                    session.setKeyUserName(str_fname+" "+str_lname);*/

                                     startActivity(new Intent(AdditionalInfoActivity.this, SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                   // startActivity(new Intent(AdditionalInfoActivity.this, AdditionalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();

                                } else {

                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                SimpleProgressBar.closeProgress();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            System.out.println("Error===="+error);
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

                    System.out.println("OUT PUT DATE OF BIRTH=="+mDob);
                    System.out.println("OUT PUT NATIONALITY=="+iso_name.get(position));
                    System.out.println("OUT PUT GENDER=="+mGender);

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

    private  boolean validateDob()
    {
        if(mDob.equals(""))
        {
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
}
