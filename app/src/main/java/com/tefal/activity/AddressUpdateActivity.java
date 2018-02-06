package com.tefal.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.tefal.Models.AddressRecord;
import com.tefal.R;
import com.tefal.app.TefsalApplication;
import com.tefal.fragment.FragmentMyAddress;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressUpdateActivity extends BaseActivity
{
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.btn_back)
    ImageButton btn_back;

    @Bind(R.id.btn_Save)
    Button btn_Save;

    @Bind(R.id.spin_country)
    Spinner spin_country;

    @Bind(R.id.spin_city)
    Spinner spin_city;

    @Bind(R.id.spin_area)
    Spinner spin_area;

    @Bind(R.id.input_address_name)
    EditText input_address_name;

    @Bind(R.id.input_block)
    EditText input_block;

    @Bind(R.id.input_avenue)
    EditText input_avenue;

    @Bind(R.id.input_floor)
    EditText input_floor;

    @Bind(R.id.input_house)
    EditText input_house;

    @Bind(R.id.input_paci_num)
    EditText input_paci_num;

    @Bind(R.id.input_add_desp)
    EditText input_add_desp;

    @Bind(R.id.areaTxt)
    TextView areaTxt;

    @Bind(R.id.cityTxt)
    TextView cityTxt;

    @Bind(R.id.countryTxt)
    TextView countryTxt;

    @Bind(R.id.input_street)
    EditText input_street;


    @Bind(R.id.input_phone)
    EditText input_phone;

    @Bind(R.id.input_flate)
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

    private int countryPosition;
    private int provincePosition;
    private int areaPosition;



    AddressRecord addressRecord;

    private static Tracker mTracker;
    private static final String TAG = "AddressUpdateActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_update);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();


        toolbar_title.setText("EDIT ADDRESS");

        addressRecord=(AddressRecord)getIntent().getExtras().getSerializable("addressRecord");

        System.out.println("ID==="+addressRecord.getAddress_id());

        System.out.println("PROVINCE==="+addressRecord.getCity());
        System.out.println("PROVINCE==="+addressRecord.getCountry());

        System.out.println("OUT PUT CONUTRY CODE=="+addressRecord.getCountry_iso());
        System.out.println("OUT PUT PROVINCE CODE=="+addressRecord.getProvince_id());
        System.out.println("OUT PUT AREA CODE=="+addressRecord.getArea_id());


        Log.i(TAG, "Setting screen name: " + "AddressUpdateActivity");
        mTracker.setScreenName("Image~" + "AddressUpdateActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //System.out.println("SPINNER DATA===="+spin_area.getSelectedItem()+"==="+spin_area.getSelectedItemPosition());

                if (Contents.isBlank(input_address_name.getText().toString().trim())) {
                    input_address_name.setError(getString(R.string.invalidAddress_name));
                    return;
                }
                else if (Contents.isBlank(input_block.getText().toString().trim())) {
                    input_block.setError(getString(R.string.invalidBlock));
                    return;
                }
                else if (Contents.isBlank(input_house.getText().toString().trim())) {
                    input_house.setError(getString(R.string.invalidHouse));
                    return;
                }
                else if (spin_country.getSelectedItemPosition() ==0) {
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidCountry),Toast.LENGTH_LONG).show();
                    return;
                }
                else if (spin_area.getSelectedItemPosition()==-1 ) {
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidArea),Toast.LENGTH_LONG).show();
                    return;
                }
                else if (spin_area.getSelectedItemPosition()==-1) {
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidCity),Toast.LENGTH_LONG).show();
                    return;
                }
                else if(Contents.isBlank(input_street.getText().toString().trim())) {
                    input_street.setError(getString(R.string.invalidStreet));
                    return;
                }

               /* else if(Contents.isBlank(input_flate.getText().toString().trim())) {
                    input_flate.setError(getString(R.string.invalidFlate));
                    return;
                }
                else if(Contents.isBlank(input_phone.getText().toString().trim())) {
                    input_phone.setError(getString(R.string.invalidPhone));
                    return;
                }
                */

                saveAddress();

                // System.out.println("SAVE");
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(this);

        setData();
        getCountries();




    }
    private void setData()
    {
        input_address_name.setText(addressRecord.getAddress_name());
        input_block.setText(addressRecord.getBlock());
        input_avenue.setText(addressRecord.getAvenue());
        input_floor.setText(addressRecord.getFloor());
        input_house.setText(addressRecord.getHouse());
        input_paci_num.setText(addressRecord.getPaci_number());
        input_add_desp.setText(addressRecord.getAddt_info());
        input_street.setText(addressRecord.getStreet());
        input_phone.setText(addressRecord.getPhone_number());
        input_flate.setText(addressRecord.getFlat_number());
        //params.put("street", input_street.getText()+"");

    }

    public void getCountries() {

        Log.i(TAG, "Setting screen name: " + "AddressUpdateActivity");
        mTracker.setScreenName("Image~" + "AddressUpdateActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());




        SimpleProgressBar.showProgress(AddressUpdateActivity.this);
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
                                    //country_name.add("Select Country");
                                    iso_name = new ArrayList<String>();
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i =0;i<jsonArray.length();i++)
                                    {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        country_name.add(c.getString("name"));
                                        iso_name.add(c.getString("iso"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AddressUpdateActivity.this,android.R.layout.simple_spinner_item,country_name);
                                    getCountryPosition();

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_country.setAdapter(aa);
                                    spin_country.setSelection(countryPosition);

                                    spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                        {
                                            getProvinces(iso_name.get(position));
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });


                                    System.out.println("POSITION OF COUNTRY==="+countryPosition);
                                    System.out.println("POSITION OF COUNTRY==="+country_name.size());
                                 //  int pos =spin_country.getPosi

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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressUpdateActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    private void getCountryPosition()
    {
        for(int i=0;i<iso_name.size();i++)
        {
            if(iso_name.get(i).equals(addressRecord.getCountry_iso()))
            {
                countryPosition=i;

                System.out.println("POSITION country===="+countryPosition);
                System.out.println("POSITION country===="+addressRecord.getCountry_iso());
                break;
            }
        }
    }

    private void getProvincePosition()
    {
        for(int i=0;i<province_id.size();i++) {
            if (province_id.get(i).equals(addressRecord.getProvince_id())) {
                provincePosition = i;
                System.out.println("POSITION prov====" + provincePosition);
                System.out.println("POSITION prov====" + addressRecord.getProvince_id());
                break;


            }
            System.out.println("PROVINCE ID=="+addressRecord.getProvince_id());
        }

    }

    private void getAreaPosition()
    {
        for(int i=0;i<area_id.size();i++)
        {
            if(area_id.get(i).equals(addressRecord.getArea_id()))
            {
                areaPosition=i;
                System.out.println("POSITION area===="+provincePosition);
                System.out.println("POSITION area===="+addressRecord.getArea_id());
                break;

            }
        }
    }



    public void getProvinces(final String isoKey) {

        Log.i(TAG, "Setting screen name: " + "AddressUpdateActivity");
        mTracker.setScreenName("Image~" + "AddressUpdateActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());



        SimpleProgressBar.showProgress(AddressUpdateActivity.this);
        final ArrayAdapter aa=null;
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
                                    area_name=new ArrayList<String>();
                                   // area_name.add("Select Province");


                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i =0;i<jsonArray.length();i++)
                                    {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        province_id.add(c.getString("province_id"));
                                        province_name.add(c.getString("province_name"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AddressUpdateActivity.this,android.R.layout.simple_spinner_item,province_name);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    getProvincePosition();

                                    spin_city.setAdapter(aa);
                                    spin_city.setSelection(provincePosition);
                                    spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                            getAreas(province_id.get(position));

                                            System.out.println("GET  ID PROVINCE==="+province_id.get(position));
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
                            SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressUpdateActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }



    public void saveAddress() {
        SimpleProgressBar.showProgress(AddressUpdateActivity.this);

        Log.i(TAG, "Setting screen name: " + "AddressUpdateActivity");
        mTracker.setScreenName("Image~" + "AddressUpdateActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        province_code=province_id.get(spin_city.getSelectedItemPosition());

        country_iso_code=iso_name.get(spin_country.getSelectedItemPosition());

        area_code=area_id.get(spin_area.getSelectedItemPosition());




        System.out.println("CODE ISO CODE="+country_iso_code);
        System.out.println("CODE PROVINCE CODE="+province_code);


        //province_id
        try {
            final String url = Contents.baseURL + "updateCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            System.out.println("Response==="+response);
                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1"))
                                {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                    new FragmentMyAddress();
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("Error==="+e.getStackTrace());
                                SimpleProgressBar.closeProgress();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            System.out.println("Error==="+error.getStackTrace());

                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("customer_id", session.getCustomerId());
                    params.put("address_name",input_address_name.getText().toString() );
                    params.put("address_id",addressRecord.getAddress_id() );

                    params.put("country", country_iso_code);
                    params.put("city", province_code);
                    params.put("area", area_code);
                    params.put("block", input_block.getText().toString());

                    params.put("street", input_street.getText()+"");
                    params.put("avenue", input_avenue.getText().toString());
                    params.put("floor", input_floor.getText().toString());
                    params.put("house", input_house.getText().toString());
                    params.put("flat_number", input_flate.getText()+"");
                    params.put("phone_number", input_phone.getText()+"");
                    params.put("paci_number", input_paci_num.getText().toString());
                    params.put("addt_info", input_add_desp.getText().toString());

                    params.put("access_token", session.getToken());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Refsal req == ", url + params);



                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AddressUpdateActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {

            System.out.println("Error==="+surError);
            surError.printStackTrace();
        }
    }
    public void getAreas(final String province_id) {

        Log.i(TAG, "Setting screen name: " + "AddressUpdateActivity");
        mTracker.setScreenName("Image~" + "AddressUpdateActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        SimpleProgressBar.showProgress(AddressUpdateActivity.this);
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
                                   // area_name.add("Select Area");
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i =0;i<jsonArray.length();i++)
                                    {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        area_id.add(c.getString("area_id"));
                                        area_name.add(c.getString("area_name"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                    ArrayAdapter aa = new ArrayAdapter(AddressUpdateActivity.this,android.R.layout.simple_spinner_item,area_name);

                                    getAreaPosition();
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_area.setAdapter(aa);
                                    spin_area.setSelection(areaPosition);

                                    spin_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                                        {

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
                            SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddressUpdateActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


}
