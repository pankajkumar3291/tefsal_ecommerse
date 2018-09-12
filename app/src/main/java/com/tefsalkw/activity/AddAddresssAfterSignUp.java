package com.tefsalkw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.gson.reflect.TypeToken;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.dialogs.CountryDialog;
import com.tefsalkw.models.CountryModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.PreferencesUtil;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddresssAfterSignUp extends BaseActivity {
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

    public static ArrayList<CountryModel> phoneCountryCodeRecordString;
    public static CountryModel selectedMobileCountry = null;

    @BindView(R.id.llCountry)
    RelativeLayout llCountry;

    @BindView(R.id.imgv_contry_flag1)
    ImageView imgv_contry_flag1;


    @BindView(R.id.input_mob)
    EditText input_mob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_addresss_after_sign_up);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar_title.setText("STEP 2");
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
                }
                /*else if (spin_area.getSelectedItemPosition()==-1 ) {
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidArea),Toast.LENGTH_LONG).show();
                    return;
                }*/
               /* else if (spin_area.getSelectedItemPosition()==-1) {
                    Toast.makeText(getApplicationContext(),getString(R.string.invalidCity),Toast.LENGTH_LONG).show();
                    return;
                }*/
                else if (Contents.isBlank(input_street.getText().toString().trim())) {
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
               }*/
                //saveAddress();
                startActivity(new Intent(AddAddresssAfterSignUp.this, AdditionalInfoActivity.class));


                // System.out.println("SAVE");
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(this);


        llCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CountryDialog countryDialog = new CountryDialog(phoneCountryCodeRecordString, AddAddresssAfterSignUp.this, "3");
                countryDialog.show();

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //getCountries();

        setData();

    }


    @Override
    protected void onPause() {
        super.onPause();

        try {

            JSONObject outState = new JSONObject();
            outState.put("input_address_name", input_address_name.getText().toString().trim());
            outState.put("input_mob", input_mob.getText().toString().trim());
            outState.put("spin_city", spin_city.getSelectedItemPosition());
            outState.put("spin_area", spin_area.getSelectedItemPosition());

            outState.put("spin_city_txt", "" + spin_city.getSelectedItem().toString());
            outState.put("spin_area_txt", "" + spin_area.getSelectedItem().toString());


            outState.put("input_block", input_block.getText().toString().trim());
            outState.put("input_street", input_street.getText().toString().trim());
            outState.put("input_avenue", input_avenue.getText().toString().trim());
            outState.put("input_floor", input_floor.getText().toString().trim());
            outState.put("input_house", input_house.getText().toString().trim());
            outState.put("input_flate", input_flate.getText().toString().trim());
            outState.put("input_phone", input_phone.getText().toString().trim());
            outState.put("input_paci_num", input_paci_num.getText().toString().trim());
            outState.put("input_add_desp", input_add_desp.getText().toString().trim());
            outState.put("selectedMobileCountry", selectedMobileCountry);

            String inputJson = new Gson().toJson(outState);

            Log.e("inputJson", inputJson);
            PreferencesUtil.putString(this, "SignupBundle2", inputJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

            Gson gson = new Gson();

            JSONObject jsonObject = gson.fromJson(PreferencesUtil.getString(this, "SignupBundle2", ""), new TypeToken<JSONObject>() {
            }.getType());


            if (jsonObject != null) {

                getProvinces("KW");


                input_address_name.setText(jsonObject.getString("input_address_name"));
                input_mob.setText(jsonObject.getString("input_mob"));

                spin_city.setSelection(jsonObject.getInt("spin_city"));
                spin_area.setSelection(jsonObject.getInt("spin_area"));
                input_block.setText(jsonObject.getString("input_block"));
                input_street.setText(jsonObject.getString("input_street"));
                input_avenue.setText(jsonObject.getString("input_avenue"));

                input_floor.setText(jsonObject.getString("input_floor"));
                input_house.setText(jsonObject.getString("input_house"));
                input_flate.setText(jsonObject.getString("input_flate"));
                input_phone.setText(jsonObject.getString("input_phone"));
                input_paci_num.setText(jsonObject.getString("input_paci_num"));
                input_add_desp.setText(jsonObject.getString("input_add_desp"));


                selectedMobileCountry = gson.fromJson(jsonObject.get("selectedMobileCountry").toString(), new TypeToken<CountryModel>() {
                }.getType());

                input_mob.setText(selectedMobileCountry.getNicename());
                imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    public void setCountryFlag() {
        input_mob.setText(selectedMobileCountry.getNicename());
        imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());
    }

    public void setData()

    {

        phoneCountryCodeRecordString = new ArrayList<CountryModel>();


        CountryModel c1;

        c1 = new CountryModel();

        c1.setNicename("Kuwait");
        c1.setPhonecode("+965");
        c1.setFlag(R.drawable.kuwait);

        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();

        c1.setNicename("Afghanistan");
        c1.setPhonecode("+93");
        c1.setFlag(R.drawable.afghanistan);

        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();

        c1.setNicename("Albania");
        c1.setPhonecode("+355");
        c1.setFlag(R.drawable.albania);

        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("UAE");
        c1.setPhonecode("+971");
        c1.setFlag(R.drawable.andorra_bb);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Angola");
        c1.setPhonecode("+244");
        c1.setFlag(R.drawable.angola);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Argentina");
        c1.setPhonecode("+54");
        c1.setFlag(R.drawable.argentina);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Armenia");
        c1.setPhonecode("+374");
        c1.setFlag(R.drawable.armenia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Aruba");
        c1.setPhonecode("+297");
        c1.setFlag(R.drawable.aruba);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Australia");
        c1.setPhonecode("+61");
        c1.setFlag(R.drawable.australia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Austria");
        c1.setPhonecode("+43");
        c1.setFlag(R.drawable.austria);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Azerbaijan");
        c1.setPhonecode("+994");
        c1.setFlag(R.drawable.azerbaijan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Bahrain");
        c1.setPhonecode("+973");
        c1.setFlag(R.drawable.bahrain);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Bangladesh");
        c1.setPhonecode("+880");
        c1.setFlag(R.drawable.bangladesh);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Belarus");
        c1.setPhonecode("+375");
        c1.setFlag(R.drawable.belarus);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Belgium");
        c1.setPhonecode("+32");
        c1.setFlag(R.drawable.belgium);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Benin");
        c1.setPhonecode("+229");
        c1.setFlag(R.drawable.benin);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Bhutan");
        c1.setPhonecode("+975");
        c1.setFlag(R.drawable.bhutan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Bolivia");
        c1.setPhonecode("+591");
        c1.setFlag(R.drawable.bolivia);
        phoneCountryCodeRecordString.add(c1);

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Bosnia and Herzegovina");
        c1.setPhonecode("+387");
        c1.setFlag(R.drawable.bosniaandherzegovina);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Botswana");
        c1.setPhonecode("+267");
        c1.setFlag(R.drawable.botswana);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Brazil");
        c1.setPhonecode("+55");
        c1.setFlag(R.drawable.brazil);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Brunei");
        c1.setPhonecode("+673");
        c1.setFlag(R.drawable.brunei);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Bulgaria");
        c1.setPhonecode("+359");
        c1.setFlag(R.drawable.bulgaria);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Burundi");
        c1.setPhonecode("+257");
        c1.setFlag(R.drawable.burundi);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Cambodia");
        c1.setPhonecode("+855");
        c1.setFlag(R.drawable.cambodia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Cameroon");
        c1.setPhonecode("+237");
        c1.setFlag(R.drawable.cameroon);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Canada");
        c1.setPhonecode("+1");
        c1.setFlag(R.drawable.canada);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Chile");
        c1.setPhonecode("+56");
        c1.setFlag(R.drawable.chile);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/


        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("China");
        c1.setPhonecode("+86");
        c1.setFlag(R.drawable.china);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Colombia");
        c1.setPhonecode("+57");
        c1.setFlag(R.drawable.colombia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Costa Rica");
        c1.setPhonecode("+506");
        c1.setFlag(R.drawable.costarica);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Croatia ");
        c1.setPhonecode("+385");
        c1.setFlag(R.drawable.croatia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Cuba ");
        c1.setPhonecode("+53");
        c1.setFlag(R.drawable.cuba);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Czech Republic ");
        c1.setPhonecode("+420");
        c1.setFlag(R.drawable.czechrepublic);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Denmark ");
        c1.setPhonecode("+45");
        c1.setFlag(R.drawable.denmark);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Ecuador ");
        c1.setPhonecode("+593");
        c1.setFlag(R.drawable.ecuador);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Egypt ");
        c1.setPhonecode("+20");
        c1.setFlag(R.drawable.egypt);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("El Salvador ");
        c1.setPhonecode("+503");
        c1.setFlag(R.drawable.eisalvador);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Estonia ");
        c1.setPhonecode("+372");
        c1.setFlag(R.drawable.estonia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Ethiopia ");
        c1.setPhonecode("+251");
        c1.setFlag(R.drawable.ethiopia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Finland ");
        c1.setPhonecode("+358");
        c1.setFlag(R.drawable.finland);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("France ");
        c1.setPhonecode("+33");
        c1.setFlag(R.drawable.france);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Gambia ");
        c1.setPhonecode("+220");
        c1.setFlag(R.drawable.gambia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Germany ");
        c1.setPhonecode("+49");
        c1.setFlag(R.drawable.germany);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Ghana ");
        c1.setPhonecode("+233");
        c1.setFlag(R.drawable.ghana);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Greece ");
        c1.setPhonecode("+30");
        c1.setFlag(R.drawable.greece);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Guinea ");
        c1.setPhonecode("+224");
        c1.setFlag(R.drawable.guinea);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Guyana ");
        c1.setPhonecode("+592");
        c1.setFlag(R.drawable.guyana);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Haiti ");
        c1.setPhonecode("+509");
        c1.setFlag(R.drawable.haiti);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Honduras ");
        c1.setPhonecode("+504");
        c1.setFlag(R.drawable.honduras);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Hong Kong ");
        c1.setPhonecode("+852");
        c1.setFlag(R.drawable.hongkong);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Hungary ");
        c1.setPhonecode("+36");
        c1.setFlag(R.drawable.hungary);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Iceland ");
        c1.setPhonecode("+354");
        c1.setFlag(R.drawable.iceland);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("India");
        c1.setPhonecode("+91");
        c1.setFlag(R.drawable.india);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Indonesia ");
        c1.setPhonecode("+62");
        c1.setFlag(R.drawable.indonesia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Iran ");
        c1.setPhonecode("+98");
        c1.setFlag(R.drawable.iran);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Iraq ");
        c1.setPhonecode("+964");
        c1.setFlag(R.drawable.iraq);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Ireland ");
        c1.setPhonecode("+353");
        c1.setFlag(R.drawable.ireland);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/


        c1 = new CountryModel();
        c1.setNicename("Italy ");
        c1.setPhonecode("+39");
        c1.setFlag(R.drawable.italy);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Japan ");
        c1.setPhonecode("+81");
        c1.setFlag(R.drawable.japan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Jordan ");
        c1.setPhonecode("+962");
        c1.setFlag(R.drawable.jordan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Kazakhstan ");
        c1.setPhonecode("+7");
        c1.setFlag(R.drawable.kazakhstan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Kenya ");
        c1.setPhonecode("+254");
        c1.setFlag(R.drawable.kenya);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Kyrgyzstan ");
        c1.setPhonecode("+996");
        c1.setFlag(R.drawable.kyrgyzstan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Lebanon ");
        c1.setPhonecode("+961");
        c1.setFlag(R.drawable.lebanon);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Liberia ");
        c1.setPhonecode("+231");
        c1.setFlag(R.drawable.liberia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Libya ");
        c1.setPhonecode("+218");
        c1.setFlag(R.drawable.libya);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Lithuania ");
        c1.setPhonecode("+370");
        c1.setFlag(R.drawable.lithuania);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Luxembourg ");
        c1.setPhonecode("+352");
        c1.setFlag(R.drawable.luxembourg);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Malaysia ");
        c1.setPhonecode("+60");
        c1.setFlag(R.drawable.malaysia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Maldives ");
        c1.setPhonecode("+960");
        c1.setFlag(R.drawable.maldives);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Mali ");
        c1.setPhonecode("+223");
        c1.setFlag(R.drawable.mali);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Mauritius ");
        c1.setPhonecode("+230");
        c1.setFlag(R.drawable.mauritius);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Mexico ");
        c1.setPhonecode("+52");
        c1.setFlag(R.drawable.mexico);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Monaco ");
        c1.setPhonecode("+377");
        c1.setFlag(R.drawable.monaco);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Mongolia ");
        c1.setPhonecode("+976");
        c1.setFlag(R.drawable.mongolia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Morocco ");
        c1.setPhonecode("+212");
        c1.setFlag(R.drawable.morocco);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Mozambique ");
        c1.setPhonecode("+258");
        c1.setFlag(R.drawable.mozambique);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Myanmar ");
        c1.setPhonecode("+95");
        c1.setFlag(R.drawable.myanmar);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Namibia ");
        c1.setPhonecode("+264");
        c1.setFlag(R.drawable.namibia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Nepal ");
        c1.setPhonecode("+977");
        c1.setFlag(R.drawable.nepal);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Netherlands ");
        c1.setPhonecode("+31");
        c1.setFlag(R.drawable.netherlands);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("New Zealand ");
        c1.setPhonecode("+64");
        c1.setFlag(R.drawable.newzealand);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Niger ");
        c1.setPhonecode("+227");
        c1.setFlag(R.drawable.niger);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Nigeria ");
        c1.setPhonecode("+234");
        c1.setFlag(R.drawable.nigeria);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Norway ");
        c1.setPhonecode("+47");
        c1.setFlag(R.drawable.norway);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Oman ");
        c1.setPhonecode("+968");
        c1.setFlag(R.drawable.oman);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Pakistan ");
        c1.setPhonecode("+92");
        c1.setFlag(R.drawable.pakistan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Palestine ");
        c1.setPhonecode("+970");
        c1.setFlag(R.drawable.palastine);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Panama ");
        c1.setPhonecode("+507");
        c1.setFlag(R.drawable.panama);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Paraguay ");
        c1.setPhonecode("+595");
        c1.setFlag(R.drawable.paraguay);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Peru ");
        c1.setPhonecode("+51");
        c1.setFlag(R.drawable.peru);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Poland ");
        c1.setPhonecode("+48");
        c1.setFlag(R.drawable.poland);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Portugal ");
        c1.setPhonecode("+351");
        c1.setFlag(R.drawable.portugal);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Qatar ");
        c1.setPhonecode("+974");
        c1.setFlag(R.drawable.qatar);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Romania ");
        c1.setPhonecode("+40");
        c1.setFlag(R.drawable.romania);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Russian ");
        c1.setPhonecode("+7");
        c1.setFlag(R.drawable.russian);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Rwanda ");
        c1.setPhonecode("+250");
        c1.setFlag(R.drawable.rwanda);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/


        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Samoa ");
        c1.setPhonecode("+685");
        c1.setFlag(R.drawable.samoa);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Saudi Arabia ");
        c1.setPhonecode("+966");
        c1.setFlag(R.drawable.saudiarabia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Senegal ");
        c1.setPhonecode("+221");
        c1.setFlag(R.drawable.senegal);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Serbia ");
        c1.setPhonecode("+381");
        c1.setFlag(R.drawable.serbia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Seychelles ");
        c1.setPhonecode("+248");
        c1.setFlag(R.drawable.seychelles);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Sierra Leone ");
        c1.setPhonecode("+232");
        c1.setFlag(R.drawable.sierraleone);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Singapore ");
        c1.setPhonecode("+65");
        c1.setFlag(R.drawable.singapore);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Slovakia ");
        c1.setPhonecode("+421");
        c1.setFlag(R.drawable.slovakia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Slovenia ");
        c1.setPhonecode("+386");
        c1.setFlag(R.drawable.slovenia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Somalia ");
        c1.setPhonecode("+252");
        c1.setFlag(R.drawable.somalia);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("South Africa ");
        c1.setPhonecode("+27");
        c1.setFlag(R.drawable.southafrica);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("South Sudan ");
        c1.setPhonecode("+211");
        c1.setFlag(R.drawable.southsudan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Spain ");
        c1.setPhonecode("+34");
        c1.setFlag(R.drawable.spain);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Sri Lanka ");
        c1.setPhonecode("+94");
        c1.setFlag(R.drawable.srilanka);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Sudan ");
        c1.setPhonecode("+249");
        c1.setFlag(R.drawable.sudan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Swaziland ");
        c1.setPhonecode("+268");
        c1.setFlag(R.drawable.swaziland);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Sweden ");
        c1.setPhonecode("+46");
        c1.setFlag(R.drawable.sweden);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Switzerland ");
        c1.setPhonecode("+41");
        c1.setFlag(R.drawable.switzerland);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Taiwan ");
        c1.setPhonecode("+886");
        c1.setFlag(R.drawable.taiwan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Tajikistan ");
        c1.setPhonecode("+992");
        c1.setFlag(R.drawable.tajikistan);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Tanzania ");
        c1.setPhonecode("+255");
        c1.setFlag(R.drawable.tanzania);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Thailand ");
        c1.setPhonecode("+66");
        c1.setFlag(R.drawable.thailand);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Togo ");
        c1.setPhonecode("+228");
        c1.setFlag(R.drawable.togo);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Tunisia  ");
        c1.setPhonecode("+216");
        c1.setFlag(R.drawable.tunisia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Turkey ");
        c1.setPhonecode("+90");
        c1.setFlag(R.drawable.turkey);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Turkmenistan ");
        c1.setPhonecode("+993");
        c1.setFlag(R.drawable.turkmenistan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Uganda ");
        c1.setPhonecode("+256");
        c1.setFlag(R.drawable.uganda);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Ukraine ");
        c1.setPhonecode("+380");
        c1.setFlag(R.drawable.ukraine);
        phoneCountryCodeRecordString.add(c1);


//            c1 = new CountryModel();
//            c1.setNicename("UAE ");
//            c1.setPhonecode("+971");
//            c1.setFlag(R.drawable.ae);
//            phoneCountryCodeRecordString.add(c1);


        c1 = new CountryModel();
        c1.setNicename("United Kingdom ");
        c1.setPhonecode("+44");
        c1.setFlag(R.drawable.unitedkingdom);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("United States ");
        c1.setPhonecode("+1");
        c1.setFlag(R.drawable.unitedstates);
        phoneCountryCodeRecordString.add(c1);

        /************************************************************/

        /**************************************************************/

        c1 = new CountryModel();
        c1.setNicename("Uruguay ");
        c1.setPhonecode("+598");
        c1.setFlag(R.drawable.uruguay);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Uzbekistan ");
        c1.setPhonecode("+998");
        c1.setFlag(R.drawable.uzbekistan);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Vanuatu ");
        c1.setPhonecode("+678");
        c1.setFlag(R.drawable.vanuatu);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Viet Nam ");
        c1.setPhonecode("+84");
        c1.setFlag(R.drawable.vietnam);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Yemen ");
        c1.setPhonecode("+967");
        c1.setFlag(R.drawable.yemen);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Zambia ");
        c1.setPhonecode("+260");
        c1.setFlag(R.drawable.zambia);
        phoneCountryCodeRecordString.add(c1);

        c1 = new CountryModel();
        c1.setNicename("Zimbabwe ");
        c1.setPhonecode("+263");
        c1.setFlag(R.drawable.zimbabwe);
        phoneCountryCodeRecordString.add(c1);


        selectedMobileCountry = phoneCountryCodeRecordString.get(0);

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        AlertDialog.Builder builder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//        } else {
//            builder = new AlertDialog.Builder(this);
//        }
//        builder.setTitle("Cancel Signup")
//                .setMessage("Are you sure you want to cancel sign-up?")
//                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        finish();
//                    }
//                })
//                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//
//                    }
//                })
//                .show();

    }


    // This one updated code----
    public void getCountries() {


        SimpleProgressBar.showProgress(AddAddresssAfterSignUp.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(AddAddresssAfterSignUp.this, android.R.layout.simple_spinner_item, country_name);
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
            RequestQueue requestQueue = Volley.newRequestQueue(AddAddresssAfterSignUp.this);
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

        SimpleProgressBar.showProgress(AddAddresssAfterSignUp.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(AddAddresssAfterSignUp.this, android.R.layout.simple_spinner_item, province_name);

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
            RequestQueue requestQueue = Volley.newRequestQueue(AddAddresssAfterSignUp.this);
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


        SimpleProgressBar.showProgress(AddAddresssAfterSignUp.this);
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
                                    ArrayAdapter aa = new ArrayAdapter(AddAddresssAfterSignUp.this, android.R.layout.simple_spinner_item, area_name);

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
            RequestQueue requestQueue = Volley.newRequestQueue(AddAddresssAfterSignUp.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void saveAddress() {
        SimpleProgressBar.showProgress(AddAddresssAfterSignUp.this);
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
                                    Toast.makeText(getApplicationContext(), TefalApp.getInstance().getRegSuccessMsg(), Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(AddAddresssAfterSignUp.this, AdditionalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    // new FragmentMyAddress();
                                    // finish();
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
                    try {

                        params.put("customer_id", TefalApp.getInstance().getCustomer_id());
                        params.put("address_name", input_address_name.getText() + "");

                        System.out.println("CODE COUNTRY===" + country_iso_code);
                        System.out.println("CODE CITY===" + province_code);
                        params.put("country", spin_country.getSelectedItem().toString());
                        params.put("province", spin_city.getSelectedItem().toString());
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

                        params.put("access_token", TefalApp.getInstance().getAccessToken());
                        params.put("appUser", "tefsal");
                        params.put("appSecret", "tefsal@123");
                        params.put("appVersion", "1.1");
                    } catch (Exception ex) {

                    }


                    Log.e("Refsal req == ", url + params);


                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(AddAddresssAfterSignUp.this);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                input_mob.setText(selectedMobileCountry.getNicename());

                imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());

                // getProvinces("KW");
            }
        }

    }

    public void loadAreas() {
        input_mob.setText(selectedMobileCountry.getNicename());

        imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());

        getProvinces("KW");
    }
}
