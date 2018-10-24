package com.tefsalkw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tefsalkw.R;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.eventmodels.CartAddressEvent;
import com.tefsalkw.fragment.FragmentMyAddress;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.btn_Save)
    Button btn_Save;

    @BindView(R.id.spin_country)
    Spinner spin_country;

    @BindView(R.id.spin_city)
    Spinner spin_city;

    @BindView(R.id.spin_area)
    Spinner spin_area;

    @BindView(R.id.input_address_name)
    EditText input_address_name;


    @BindView(R.id.input_blockLayout)
    TextInputLayout input_blockLayout;

    @BindView(R.id.input_block)
    EditText input_block;

    @BindView(R.id.input_avenue)
    EditText input_avenue;

    @BindView(R.id.input_floor)
    EditText input_floor;

    @BindView(R.id.input_house)
    EditText input_house;

    @BindView(R.id.input_paci_num)
    EditText input_paci_num;

    @BindView(R.id.input_add_desp)
    EditText input_add_desp;

    @BindView(R.id.areaTxt)
    TextView areaTxt;

    @BindView(R.id.cityTxt)
    TextView cityTxt;

    @BindView(R.id.countryTxt)
    TextView countryTxt;

    @BindView(R.id.input_street)
    EditText input_street;


    @BindView(R.id.input_phone)
    EditText input_phone;

    @BindView(R.id.input_flate)
    EditText input_flate;


    List<String> country_name;
    List<String> iso_name;

    List<String> province_id;
    List<String> province_name;


    List<String> area_id;
    List<String> area_name;

    SessionManager session;


    private String country_iso_code;
    private String province_code;
    private String area_code;

    private int position;

    private static Tracker mTracker;
    private static final String TAG = "AddressesActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar_title.setText(R.string.add_address);
        // toolbar_title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("SPINNER DATA===="+spin_area.getSelectedItem()+"==="+spin_area.getSelectedItemPosition());

                if (Contents.isBlank(input_address_name.getText().toString().trim())) {
                    input_address_name.requestFocus();
                    input_address_name.setError(getString(R.string.invalidAddress_name));
                    return;
                } else if (Contents.isBlank(input_block.getText().toString().trim())) {
                    input_block.requestFocus();
                    input_block.setError(getString(R.string.invalidBlock));
                    return;
                } else if (Contents.isBlank(input_house.getText().toString().trim())) {
                    input_house.requestFocus();
                    input_house.setError(getString(R.string.invalidHouse));
                    return;
                } else if (spin_country.getSelectedItemPosition() == 0) {

                    Toast.makeText(getApplicationContext(), getString(R.string.invalidCountry), Toast.LENGTH_LONG).show();
                    return;
                } else if (Contents.isBlank(input_street.getText().toString().trim())) {
                    input_street.requestFocus();
                    input_street.setError(getString(R.string.invalidStreet));
                    return;
                }


                saveAddress();


            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(this);


        getCountries();

    }


    // This one updated code----
    public void getCountries() {


        SimpleProgressBar.showProgress(AddressesActivity.this);
        try {
            final String url = Contents.baseURL + "getAllCountries";

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
                                    ArrayAdapter aa = new ArrayAdapter(AddressesActivity.this, android.R.layout.simple_spinner_item, country_name);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_country.setAdapter(aa);

                                    spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            System.out.println("OSO NAME===" + iso_name.get(position));
                                            getProvinces(iso_name.get(position));
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

                                    spin_country.setSelection(position);

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
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }       SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressesActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    public void getProvinces(final String isoKey) {

        Log.i(TAG, "Setting screen name: " + "AddressesActivity");
        mTracker.setScreenName("Image~" + "AddressesActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        SimpleProgressBar.showProgress(AddressesActivity.this);
        final ArrayAdapter aa = null;
        try {
            final String url = Contents.baseURL + "getProvinces";

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

                                    province_id = new ArrayList<String>();
                                    province_name = new ArrayList<String>();
                                    area_name = new ArrayList<String>();
                                    //area_name.add("Select Province");
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        province_id.add(c.getString("province_id"));
                                        province_name.add(c.getString("province_name"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AddressesActivity.this, android.R.layout.simple_spinner_item, province_name);

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_city.setAdapter(aa);

                                    spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            getAreas(province_id.get(position));
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                } else {

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    //clearSpinnerData(aa);
                                }
                            } catch (JSONException e) {
                                SimpleProgressBar.closeProgress();
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
                            }   SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("country_iso", isoKey);
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressesActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void getAreas(final String province_id) {

        Log.i(TAG, "Setting screen name: " + "AddressesActivity");
        mTracker.setScreenName("Image~" + "AddressesActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        SimpleProgressBar.showProgress(AddressesActivity.this);
        try {
            final String url = Contents.baseURL + "getAreas";

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

                                    area_id = new ArrayList<String>();
                                    area_name = new ArrayList<String>();
                                    //area_name.add("Select Area");
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        area_id.add(c.getString("area_id"));
                                        area_name.add(c.getString("area_name"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AddressesActivity.this, android.R.layout.simple_spinner_item, area_name);

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_area.setAdapter(aa);

                                    spin_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

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
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }     SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("province_id", province_id);
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressesActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void saveAddress() {
        SimpleProgressBar.showProgress(AddressesActivity.this);
        province_code = province_id.get(spin_city.getSelectedItemPosition());

        country_iso_code = iso_name.get(spin_country.getSelectedItemPosition());
        area_code = area_id.get(spin_area.getSelectedItemPosition());

        System.out.println("CODE ISO CODE=" + country_iso_code);
        System.out.println("CODE PROVINCE CODE=" + province_code);


        Log.i(TAG, "Setting screen name: " + "AddressesActivity");
        mTracker.setScreenName("Image~" + "AddressesActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


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
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    new FragmentMyAddress();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new CartAddressEvent());
                                        }
                                    }, 3000);


                                    finish();
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
                    try {

                        params.put("customer_id", session.getCustomerId());
                        params.put("address_name", input_address_name.getText() + "");
                        params.put("country", spin_country.getSelectedItem().toString());
                        params.put("province", spin_city.getSelectedItem().toString());
                        //params.put("city", province_code);
                        params.put("area", spin_area.getSelectedItem().toString());
                        params.put("block", input_block.getText() + "");
                        params.put("street", input_street.getText() + "");
                        params.put("avenue", input_avenue.getText() + "");
                        params.put("floor", input_floor.getText() + "");
                        params.put("house", input_house.getText() + "");
                        params.put("flat_number", input_flate.getText() + "");
                        params.put("phone_number", input_phone.getText() + "");

                        params.put("paci_number", input_paci_num.getText() + "");
                        params.put("addt_info", input_add_desp.getText() + "");

                        params.put("access_token", session.getToken());
                        params.put("appUser", "tefsal");
                        params.put("appSecret", "tefsal@123");
                        params.put("appVersion", "1.1");
                    } catch (Exception ex) {

                    }


                    Log.e("Refsal req == ", url + new JSONObject(params));


                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AddressesActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {

            System.out.println("Error===" + surError);
            surError.printStackTrace();
        }
    }

    public void clearSpinnerData(ArrayAdapter aa) {
        province_id.clear();
        province_name.clear();
        aa.notifyDataSetChanged();
    }
}
