package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.CartAddressListAdapter;
import com.tefsalkw.adapter.MyCartAdapter;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.eventmodels.CartAddressEvent;
import com.tefsalkw.models.GetCartResponse;
import com.tefsalkw.models.MyAddressesModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAddressSelectionActivity extends BaseActivity implements MyCartAdapter.OnCartItemDeletedListener {

    @BindView(R.id.header_txt)
    TextView header_txt;

    @BindView(R.id.amount)
    TextView amount;

    @BindView(R.id.edit_btn)
    TextView edit_btn;

    @BindView(R.id.btn_purchase)
    Button btn_purchase;


    @BindView(R.id.cancel_btn)
    ImageView cancel_btn;


    MyCartAdapter adapter;
    SessionManager session;

    private ImageView cart_item_delete;

    String totalPrice = null;

    GetCartResponse cartResponse;
    boolean isDelete = true;

    private static Tracker mTracker;
    private static final String TAG = "CartActivity";

    public int currentItemsCount = 0;

    MyCartAdapter.OnCartItemDeletedListener onCartItemDeletedListener;


    @BindView(R.id.recycler)
    RecyclerView recycler;


    CartAddressListAdapter myAddressAdapter;

    @BindView(R.id.empty_view)
    TextView empty_view;

    @BindView(R.id.llEmpty)
    LinearLayout llEmpty;

    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;

    @BindView(R.id.llRegisteredUser)
    LinearLayout llRegisteredUser;

    @BindView(R.id.llGuestMode)
    LinearLayout llGuestMode;


    public String defaultAddressId = "";


    @BindView(R.id.spin_country)
    Spinner spin_country;

    @BindView(R.id.spin_city)
    Spinner spin_city;

    @BindView(R.id.spin_area)
    Spinner spin_area;

    @BindView(R.id.input_address_name)
    EditText input_address_name;

    @BindView(R.id.input_first_name)
    EditText input_first_name;


    @BindView(R.id.input_last_name)
    EditText input_last_name;

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


    @BindView(R.id.input_email)
    EditText input_email;

    @BindView(R.id.input_flate)
    EditText input_flate;


    List<String> country_name;
    List<String> iso_name;

    List<String> province_id;
    List<String> province_name;


    List<String> area_id;
    List<String> area_name;
    private String country_iso_code;
    private String province_code;
    private String area_code;

    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_address_selection);

        ButterKnife.bind(this);

        session = new SessionManager(this);
        TefsalApplication application = (TefsalApplication) getApplication();
        mTracker = application.getDefaultTracker();

        init();

        onCartItemDeletedListener = this;

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartAddressSelectionActivity.this, AddressesActivity.class));
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCartAddressEvent(CartAddressEvent event) {


        WebCallServiceAddresses();
    }


    public void WebCallServiceAddresses() {
        SimpleProgressBar.showProgress(this);
        try {
            final String url = Contents.baseURL + "getCustomerSavedAddresses";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e("stores response", response);
                                Gson g = new Gson();
                                MyAddressesModel mResponse = g.fromJson(response, MyAddressesModel.class);

                                if (mResponse.getStatus().equals("1")) {
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(CartAddressSelectionActivity.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                    recycler.setLayoutManager(layoutManager);
                                    recycler.setItemAnimator(new DefaultItemAnimator());
                                    myAddressAdapter = new CartAddressListAdapter(CartAddressSelectionActivity.this, mResponse.getRecord());
                                    recycler.setAdapter(myAddressAdapter);

                                    if (mResponse.getRecord().size() == 0) {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        recycler.setVisibility(View.GONE);
                                        empty_view.setText("No address found, please add a new address!");

                                    } else {
                                        llEmpty.setVisibility(View.GONE);
                                        recycler.setVisibility(View.VISIBLE);
                                    }

                                } else {

                                    recycler.setVisibility(View.GONE);
                                    llEmpty.setVisibility(View.VISIBLE);
                                    empty_view.setText("No address found, please add a new address!");
                                    // Toast.makeText(getActivity(),mResponse.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }      SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("access_token", session.getToken());

                    // System.out.println("ACCESS TOCKEN=="+session.getToken());
                    params.put("customer_id", session.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (NullPointerException surError) {
            surError.printStackTrace();
            //SimpleProgressBar.closeProgress();
            SimpleProgressBar.closeProgress();
        }
    }


    //************************** CART FUNCTIONALITY **********************************//


    private void init() {

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here you need to flush payment method info....
                TefalApp.getInstance().setPayment_method_tc("");
                TefalApp.getInstance().setPayment_method("");

                if (currentItemsCount == 0) {

                    Toast.makeText(CartAddressSelectionActivity.this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(CartAddressSelectionActivity.this, PaymentSelectActivity.class);

                if (session.getIsGuestId()) {

                    if (Contents.isBlank(input_address_name.getText().toString().trim())) {
                        input_address_name.setError(getString(R.string.invalidAddress_name));
                        return;
                    } else if (Contents.isBlank(input_block.getText().toString().trim())) {
                        input_block.setError(getString(R.string.invalidBlock));
                        return;
                    } else if (Contents.isBlank(input_house.getText().toString().trim())) {
                        input_house.setError(getString(R.string.invalidHouse));
                        return;
                    } else if (spin_country.getSelectedItemPosition() == 0) {
                        Toast.makeText(getApplicationContext(), getString(R.string.invalidCountry), Toast.LENGTH_LONG).show();
                        return;
                    } else if (Contents.isBlank(input_email.getText().toString().trim())) {
                        input_email.setError("Email Required");
                        return;
                    } else if (Contents.isBlank(input_street.getText().toString().trim())) {
                        input_street.setError(getString(R.string.invalidStreet));
                        return;
                    }

                    HashMap<String, Object> guest = new HashMap<String, Object>();


                    try {

                        guest.put("first_name", input_first_name.getText().toString());
                        guest.put("last_name", input_last_name.getText().toString());
                        guest.put("email", input_email.getText().toString());
                        Map<String, String> address = new HashMap<String, String>();
                        address.put("address_name", input_address_name.getText().toString());
                        address.put("country", spin_country.getSelectedItem().toString());
                        address.put("province", spin_city.getSelectedItem().toString());
                        address.put("area", spin_area.getSelectedItem().toString());
                        address.put("block", input_block.getText().toString());
                        address.put("street", input_street.getText().toString());
                        address.put("avenue", input_avenue.getText().toString());
                        address.put("floor", input_floor.getText().toString());
                        address.put("house", input_house.getText().toString());
                        address.put("flat_number", input_flate.getText().toString());
                        address.put("phone_number", input_phone.getText().toString());

                        address.put("paci_number", input_paci_num.getText().toString());
                        address.put("addt_info", input_add_desp.getText().toString());

                        guest.put("address", address);


                    } catch (Exception ex) {

                    }

                    intent.putExtra("guest", guest);


                } else {

                    if (defaultAddressId == null || defaultAddressId.equalsIgnoreCase("")) {
                        Toast.makeText(CartAddressSelectionActivity.this, "Please select delivery address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    intent.putExtra("defaultAddressId", defaultAddressId);
                }

                intent.putExtra("price", totalPrice);
                intent.putExtra("CartResponse", cartResponse);
                intent.putExtra("header", header_txt.getText().toString());
                startActivity(intent);


            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }


        });

        try {


            WebCallServiceCart();

            if (session.getIsGuestId()) {

                llRegisteredUser.setVisibility(View.GONE);
                llGuestMode.setVisibility(View.VISIBLE);

                getCountries();

            } else {
                llRegisteredUser.setVisibility(View.VISIBLE);
                llGuestMode.setVisibility(View.GONE);
                WebCallServiceAddresses();
            }


            //=====For getting crash Analytics==================================


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    public void WebCallServiceCart() {

        Log.i(TAG, "Setting screen name: " + "CartActivity");
        mTracker.setScreenName("Image~" + "CartActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        //================================================================================

        // SimpleProgressBar.showProgress(CartAddressSelectionActivity.this);
        try {
            final String url = Contents.baseURL + "getCart";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //SimpleProgressBar.closeProgress();

                            if (response != null) {

                                Log.e(CartActivity.class.getSimpleName(), response);
                                Gson g = new Gson();
                                cartResponse = g.fromJson(response, GetCartResponse.class);
                                if (!cartResponse.getStatus().equals("0")) {
                                    if (cartResponse.getRecord().size() <= 1)
                                        header_txt.setText(cartResponse.getRecord().size() + " " + getString(R.string.item_in_your_cart));
                                    else
                                        header_txt.setText(cartResponse.getRecord().size() + " " + getString(R.string.items_in_your_cart));


                                    amount.setText("TOTAL : " + cartResponse.getTotal_amount_cart() + " KWD");
                                    currentItemsCount = cartResponse.getRecord().size();
                                    totalPrice = cartResponse.getTotal_amount_cart();
                                } else {
                                    Toast.makeText(getApplicationContext(), cartResponse.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    if (session.getIsGuestId()) {
                        params.put("unique_id", session.getCustomerId());
                    } else {
                        params.put("user_id", session.getCustomerId());
                        params.put("access_token", session.getToken());
                    }
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("Tefsal store == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    @Override
    public void onCartItemDeleted(int currentCount) {

        currentItemsCount = currentCount;

        if (currentItemsCount == 0) {
            edit_btn.setVisibility(View.GONE);
        } else {
            edit_btn.setVisibility(View.VISIBLE);
        }
    }


    public void getCountries() {


        SimpleProgressBar.showProgress(CartAddressSelectionActivity.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(CartAddressSelectionActivity.this, android.R.layout.simple_spinner_item, country_name);
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
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
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

        SimpleProgressBar.showProgress(CartAddressSelectionActivity.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(CartAddressSelectionActivity.this, android.R.layout.simple_spinner_item, province_name);

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
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }       SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
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


        SimpleProgressBar.showProgress(CartAddressSelectionActivity.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(CartAddressSelectionActivity.this, android.R.layout.simple_spinner_item, area_name);

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
                                Toast.makeText(getApplicationContext(), "Server error. Please try again in some time.", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                            }      SimpleProgressBar.closeProgress();
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
            RequestQueue requestQueue = Volley.newRequestQueue(CartAddressSelectionActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

}
