package com.tefsalkw.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tefsalkw.R;
import com.tefsalkw.dialogs.CountryDialog;
import com.tefsalkw.models.CountryModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.PreferencesUtil;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SessionManagerToken;
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

public class SignupActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SessionManager session;
    SessionManagerToken session2;

    @BindView(R.id.input_layout_fname)
    TextInputLayout input_layout_fname;

    @BindView(R.id.input_layout_lname)
    TextInputLayout input_layout_lname;

    @BindView(R.id.input_layout_email)
    TextInputLayout input_layout_email;

    @BindView(R.id.input_layout_password)
    TextInputLayout input_layout_password;

    @BindView(R.id.input_layout_c_password)
    TextInputLayout input_layout_c_password;


    @BindView(R.id.input_layout_mob)
    TextInputLayout input_layout_mob;

    @BindView(R.id.input_layout_home_num)
    TextInputLayout input_layout_home_num;


    @BindView(R.id.buttonSignUp)
    Button buttonSignUp;

    @BindView(R.id.input_fname)
    EditText input_fname;

    @BindView(R.id.input_lname)
    EditText input_lname;

    @BindView(R.id.input_mob)
    EditText input_mob;

    @BindView(R.id.input_home_num)
    EditText input_home_num;

    @BindView(R.id.input_email)
    EditText input_email;

    @BindView(R.id.input_password)
    EditText input_password;

    @BindView(R.id.input_c_password)
    EditText input_c_password;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.signInTxt)
    TextView signInTxt;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.mobile_country_code_sp)
    Spinner mobile_country_code_sp;

    @BindView(R.id.home_country_code_sp)
    Spinner home_country_code_sp;

    private String accessToken;
    private String customer_id;
    private String customer_name;

    private int position;

    private StringBuilder fname = new StringBuilder();

    public static ArrayList<CountryModel> phoneCountryCodeRecordString;

    private boolean is_exist_email = true;
    private String error_email_exist;
    private boolean is_exist_phone = true;
    private String error_phone_exist;


    public static CountryModel selectedMobileCountry = null;
    public static CountryModel selectedHomeCountry = null;

    @BindView(R.id.llHome)
    LinearLayout llHome;

    @BindView(R.id.llMobile)
    LinearLayout llMobile;

    @BindView(R.id.imgv_contry_flag1)
    ImageView imgv_contry_flag1;

    @BindView(R.id.imgv_contry_flag2)
    ImageView imgv_contry_flag2;


    @BindView(R.id.txtCountryCode1)
    TextView txtCountryCode1;

    @BindView(R.id.txtCountryCode2)
    TextView txtCountryCode2;

    @BindView(R.id.privacy_policy)
    TextView privacy_policy;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);


        privacy_policy.setPaintFlags(privacy_policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, AboutUsActivity.class));
            }
        });

        //FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-Regular.ttf");
        // fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));


        session = new SessionManager(this);
        session2 = new SessionManagerToken(this);


        setSupportActionBar(toolbar);
        toolbar_title.setText(R.string.title_signup);

        input_password.setTransformationMethod(new PasswordTransformationMethod());
        input_c_password.setTransformationMethod(new PasswordTransformationMethod());


        //Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Lato-Bold.ttf");
        // toolbar_title.setTypeface(type);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setData();


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                input_layout_fname.setError("");
                input_layout_lname.setError("");
                input_layout_email.setError("");
                input_layout_password.setError("");
                input_layout_c_password.setError("");
                input_layout_home_num.setError("");
                input_layout_mob.setError("");

                if (validateFirstName(input_fname.getText().toString().trim())

                        && validateMobileNumber(input_mob.getText().toString().trim())
                        && validateMobileServer(is_exist_phone)
                        && validateEmail(input_email.getText().toString().trim())
                        && validateEmailServer(is_exist_email)
                        && validatePassword(input_password.getText().toString().trim())
                        && validateC_password(input_c_password.getText().toString().trim())) {

                    // startActivity(new Intent(SignupActivity.this, AddAddresssAfterSignUp.class));
                    startActivity(new Intent(SignupActivity.this, AdditionalInfoActivity.class));

                }


            }
        });

        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        input_mob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (input_mob.getText().toString().length() >= 8) {

                    phoneCheckedHttpCall(input_mob.getText().toString());

                }

            }
        });
        input_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String string_email = input_email.getText().toString();
                if (validateEmail2(string_email)) {
                    checkMailHttpCall(string_email);
                }

            }
        });

        input_mob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (input_mob.getText().toString().length() >= 7) {

                    phoneCheckedHttpCall(input_mob.getText().toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String string_email = input_email.getText().toString();
                if (validateEmail2(string_email)) {
                    checkMailHttpCall(string_email);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        llMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CountryDialog countryDialog = new CountryDialog(phoneCountryCodeRecordString, SignupActivity.this, "1");
                countryDialog.show();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryDialog countryDialog = new CountryDialog(phoneCountryCodeRecordString, SignupActivity.this, "2");
                countryDialog.show();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        llRoot.requestFocus();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    protected void onPause() {
        super.onPause();


        try {

            JSONObject outState = new JSONObject();
            outState.put("input_fname", input_fname.getText().toString().trim());
            outState.put("input_lname", input_lname.getText().toString().trim());
            outState.put("input_mob", input_mob.getText().toString().trim());
            outState.put("input_home_num", input_home_num.getText().toString().trim());
            outState.put("input_email", input_email.getText().toString().trim());
            outState.put("input_password", input_password.getText().toString().trim());
            outState.put("selectedMobileCountry", selectedMobileCountry);
            outState.put("selectedHomeCountry", selectedHomeCountry);

            String inputJson = new Gson().toJson(outState);

            Log.e("inputJson", inputJson);
            PreferencesUtil.putString(this, "SignupBundle", inputJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            Gson gson = new Gson();

            JSONObject jsonObject = gson.fromJson(PreferencesUtil.getString(this, "SignupBundle", ""), new TypeToken<JSONObject>() {
            }.getType());


            if (jsonObject != null) {

                input_fname.setText(jsonObject.getString("input_fname"));
                input_lname.setText(jsonObject.getString("input_lname"));
                input_mob.setText(jsonObject.getString("input_mob"));
                input_home_num.setText(jsonObject.getString("input_home_num"));
                input_email.setText(jsonObject.getString("input_email"));
                input_password.setText(jsonObject.getString("input_password"));
                input_c_password.setText(jsonObject.getString("input_password"));

                selectedMobileCountry = gson.fromJson(jsonObject.get("selectedMobileCountry").toString(), new TypeToken<CountryModel>() {
                }.getType());
                selectedHomeCountry = gson.fromJson(jsonObject.get("selectedHomeCountry").toString(), new TypeToken<CountryModel>() {
                }.getType());

                txtCountryCode1.setText(selectedMobileCountry.getPhonecode());
                txtCountryCode2.setText(selectedHomeCountry.getPhonecode());

                imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());
                imgv_contry_flag2.setImageResource(selectedMobileCountry.getFlag());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void setCountryFlag1() {
        txtCountryCode1.setText(selectedMobileCountry.getPhonecode());

        imgv_contry_flag1.setImageResource(selectedMobileCountry.getFlag());
    }

    public void setCountryFlag2() {

        txtCountryCode2.setText(selectedHomeCountry.getPhonecode());

        imgv_contry_flag2.setImageResource(selectedHomeCountry.getFlag());
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
        selectedHomeCountry = phoneCountryCodeRecordString.get(0);

    }


    private void getCountryCode() {

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

                                phoneCountryCodeRecordString = new ArrayList<CountryModel>();

                                if (status.equals("1")) {

                                    JSONArray jsonArray = object.getJSONArray("record");

                                    phoneCountryCodeRecordString = new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<List<CountryModel>>() {
                                    }.getType());


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


    private void checkMailHttpCall(final String string_email) {


        try {
            final String url = Contents.baseURL + "customerCheckEmail";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("RESPONSE==" + response);

                            Log.e("device response", response);
                            try {

                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                error_email_exist = object.getString("message");

                                if (status.equals("0")) {
                                    is_exist_email = true;
                                    input_layout_email.setError(error_email_exist);
                                    requestFocus(input_email);
                                    System.out.println("MESSAGE======" + error_email_exist);
                                } else {
                                    is_exist_email = false;
                                    input_layout_email.setError("");
                                    System.out.println("MESSAGE======" + error_email_exist);
                                }

                            } catch (Exception e) {
                                is_exist_email = true;
                                requestFocus(input_email);
                                System.out.println("EX==" + e);
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

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    private void phoneCheckedHttpCall(final String string_phone) {
        try {
            final String url = Contents.baseURL + "customerCheckMobile";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.e("customerCheckMobile", response);
                            try {

                                JSONObject object = new JSONObject(response);
                                Integer status = object.getInt("status");
                                error_phone_exist = object.getString("message");

                                if (status == 1) {

                                    is_exist_phone = false;
                                    input_layout_mob.setError("");


                                } else {

                                    is_exist_phone = true;
                                    input_layout_mob.setError(error_phone_exist);
                                    requestFocus(input_mob);
                                }

                            } catch (Exception e) {

                                is_exist_phone = true;
                                input_layout_mob.setError("Error: " + e.getMessage());
                                requestFocus(input_mob);
                                //Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            is_exist_phone = true;
                            if (error != null && error.networkResponse != null) {
                                Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("mobile", string_phone);


                    Log.e("Check mobile == ", url + new JSONObject(params));
                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private boolean validateFirstName(String fname) {

        if (fname.equals("")) {
            input_layout_fname.setError(getString(R.string.validate_first_name));
            requestFocus(input_fname);
            return false;
        }
        return true;
    }

    private boolean validateLastName(String lname) {
        if (lname.equals("")) {
            input_layout_lname.setError("Error: Last name should not be empty");
            requestFocus(input_lname);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (email.equals("")) {
            input_layout_email.setError(getString(R.string.validate_email));
            requestFocus(input_email);
            return false;
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                input_layout_email.setError(getString(R.string.validate_email));
                requestFocus(input_email);
                return false;
            }

        }
        return true;

    }
// This function is used to validate email while entering from user

    private boolean validateEmail2(String email) {
        if (email.equals("")) {
            //input_layout_email.setError("Error: Email should not be empty");
            //requestFocus(input_email);
            return false;
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                //input_layout_email.setError("Error: Not valid email");
                //requestFocus(input_email);
                return false;
            }

        }
        return true;

    }

    // This function is used to validate email from server response
    private boolean validateEmailServer(boolean is_exist_email) {
        if (is_exist_email) {
            input_layout_email.setError("The email has already been taken.");
            requestFocus(input_email);
            return false;
        } else {
            return true;
        }


    }


    private boolean validatePassword(String pass) {
        if (pass.equals("")) {
            input_layout_password.setError("Error: password should not be empty");
            requestFocus(input_password);
            return false;
        }
        if (pass.length() <= 7) {
            input_layout_password.setError(getString(R.string.validate_password));
            requestFocus(input_password);
            return false;
        }

        return true;
    }

    private boolean validateC_password(String c_pass) {
        if (c_pass.equals("")) {
            input_layout_c_password.setError("Error: confirm password should not be empty");
            requestFocus(input_c_password);
            return false;
        } else if (!c_pass.equals(input_password.getText().toString().trim())) {
            input_layout_password.setError(getString(R.string.validate_password_mismatch));
            requestFocus(input_password);
            return false;
        } else {
            return true;
        }


        // return true;
    }


    private boolean validateMobileNumber(String mobileNumber) {
        if (mobileNumber.equals("")) {
            input_layout_mob.setError("Error: Mobile number should not be empty");
            requestFocus(input_mob);
            return false;

        } else {
            return true;
        }

    }


    private boolean validateMobileServer(boolean error_phone_exist) {

        if (error_phone_exist) {
            input_layout_mob.setError("The mobile has already been taken");
            requestFocus(input_mob);
            return false;
        } else {
            return true;
        }
    }


    private boolean validateHomeNumber(String homeNumber) {
        if (homeNumber.equals("")) {
            input_layout_home_num.setError("Error: Home number should not be empty");
            requestFocus(input_home_num);
            return false;
        } else if (homeNumber.length() != 8) {
            input_layout_home_num.setError("Error: Home number should be 8 digits");
            requestFocus(input_home_num);
            return false;
        } else {

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

    private void fnameError(String errorString) {
        input_layout_fname.setError(errorString);
        requestFocus(input_fname);
    }

    private void emailError(String errorString) {
        input_layout_email.setError(errorString);
        requestFocus(input_email);
    }

    private void passwordConfirmationError(String errorString) {
        input_layout_c_password.setError(errorString);
        requestFocus(input_c_password);
    }

    private void passwordError(String errorString) {
        input_layout_password.setError(errorString);
        requestFocus(input_password);
    }

    private void mobileNumberError(String errorString) {
        input_layout_mob.setError(errorString);
        requestFocus(input_mob);
    }


}
