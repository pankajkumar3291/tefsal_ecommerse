package com.tefal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tefal.Models.DishdashaStylesRecord;
import com.tefal.R;
import com.tefal.app.TefalApp;
import com.tefal.utils.Contents;
import com.tefal.utils.SessionManager;
import com.tefal.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.view.View.VISIBLE;

public class MeasermentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.next_txt)
    TextView next_txt;
    @BindView(R.id.btn_back)
    ImageButton btn_back;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.Video_Btn)
    Button Video_Btn;
    @BindView(R.id.textValue)
    EditText textValue;
    @BindView(R.id.textSubValue)
    TextView textSubValue;


    @BindView(R.id.ll_neck)
    RelativeLayout ll_neck;
    @BindView(R.id.ll_chest)
    RelativeLayout ll_chest;
    @BindView(R.id.ll_sldr)
    RelativeLayout ll_sldr;
    @BindView(R.id.ll_waist)
    RelativeLayout ll_waist;
    @BindView(R.id.ll_arm)
    RelativeLayout ll_arm;
    @BindView(R.id.ll_wrist)
    RelativeLayout ll_wrist;
    @BindView(R.id.ll_frnt_height)
    RelativeLayout ll_frnt_height;
    @BindView(R.id.ll_back_height)
    RelativeLayout ll_back_height;
    @BindView(R.id.ll_custom)
    RelativeLayout ll_custom;
    @BindView(R.id.ll_sectionOne)
    LinearLayout ll_sectionOne;
    @BindView(R.id.ll_sectionTwo)
    LinearLayout ll_sectionTwo;


    @BindView(R.id.ic_custom)
    ImageView ic_custom;

    @BindView(R.id.ic_b_height)
    ImageView ic_b_height;

    @BindView(R.id.ic_f_height)
    ImageView ic_f_height;

    @BindView(R.id.ic_wrist)
    ImageView ic_wrist;

    @BindView(R.id.ic_arm)
    ImageView ic_arm;

    @BindView(R.id.ic_waist)
    ImageView ic_waist;

    @BindView(R.id.ic_chest)
    ImageView ic_chest;

    @BindView(R.id.ic_shoulder)
    ImageView ic_shoulder;

    @BindView(R.id.ic_neck)
    ImageView ic_neck;

    //-------------------------------------------------------------------------------

    @BindView(R.id.tv_custom)
    TextView tv_custom;

    @BindView(R.id.tv_b_height)
    TextView tv_b_height;

    @BindView(R.id.tv_f_height)
    TextView tv_f_height;

    @BindView(R.id.tv_wrist)
    TextView tv_wrist;

    @BindView(R.id.tv_arm)
    TextView tv_arm;

    @BindView(R.id.tv_waist)
    TextView tv_waist;

    @BindView(R.id.tv_chest)
    TextView tv_chest;

    @BindView(R.id.tv_shoulder)
    TextView tv_shoulder;

    @BindView(R.id.tv_neck)
    TextView tv_neck;

    @BindView(R.id.btn_finish)
    Button btn_finish;


    //Custom design components====================

    @BindView(R.id.buttonsImage)
    ImageView buttonsImage;

    @BindView(R.id.buttonHiddenText)
    TextView buttonHiddenText;

    @BindView(R.id.noOfButtonText)
    TextView noOfButtonText;

    @BindView(R.id.button_hidden_switch)
    Switch button_hidden_switch;

    @BindView(R.id.noOfButtonSpinner)
    Spinner noOfButtonSpinner;

    @BindView(R.id.collor_button_hidden_text)
    TextView collor_button_hidden_text;

    @BindView(R.id.no_of_collor_btn_text)
    TextView no_of_collor_btn_text;

    @BindView(R.id.collar_btn_spinner)
    Spinner collar_btn_spinner;

    @BindView(R.id.collar_btn_switch)
    Switch collar_btn_switch;

    @BindView(R.id.collor_button_image)
    ImageView collor_button_image;

    @BindView(R.id.collor_push_visibilty_switch)
    Switch collor_push_visibilty_switch;

    //=======================================
    @BindView(R.id.pen_pocket_image)
    ImageView pen_pocket_image;

    @BindView(R.id.mobile_pocket_image)
    ImageView mobile_pocket_image;

    @BindView(R.id.key_pocket_image)
    ImageView key_pocket_image;


    boolean pen_pocket_image_flag = false;
    boolean mobile_pocket_image_flage = false;
    boolean key_pocket_image_flage = false;

    //============================================
    @BindView(R.id.culffSwitch_switch)
    Switch culffSwitch_switch;

    @BindView(R.id.cufLinkText)
    TextView cufLinkText;

    @BindView(R.id.cufflink_image)
    ImageView cufflink_image;

    //========View tip screen control

    @BindView(R.id.viewtip_btn)
    Button viewtip_btn;

    @BindView(R.id.tip_image)
    ImageView tip_image;


    private int min = 1, max = 100, current = 0;
    private SessionManager mSessionManager;
    private int flag_chest = 0;
    private int flag_waist = 0;
    private int flag_sldr = 0;
    private int flag_arm = 0;
    private int flag_wrist = 0;
    private int flag_front_height = 0;
    private int flag_back_height = 0;
    private int flag_customized = 0;
    private int flag_neck = 1;


    private String flow;
    private int count = 1;
    private int back_count = 0;

    //This member variable is used to track the user current measurment on which it ids going on
    private int viewTipTrackCount = 1;

    private DishdashaStylesRecord mDishdashaStylesRecord;
    private String mAction;
    private String mCategory;

    private String decimalString = "00";


    private String min_meters = "";


    //=====Tips view screen controller--------------------------------
    @BindView(R.id.tipsLayout)
    RelativeLayout tipsLayout;

    @BindView(R.id.btn_back_tips)
    ImageButton btn_back_tips;

   /* @BindView(R.id.viewPagerIndicator)
    RelativeLayout viewPagerIndicator;

    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;*/

  /*  @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;*/

    /*@BindView(R.id.leftArrow)
    ImageView leftArrow;

    @BindView(R.id.rightArrow)
    ImageView rightArrow;
*/


  /*  MainPagerAdapter adapter;
    private int dotsCount;*/

  /*  private ImageView[] dots;
    private Integer[] tips_array = {R.drawable.memo1, R.drawable.memo2, R.drawable.memo3, R.drawable.memo4,
            R.drawable.memo5, R.drawable.memo6, R.drawable.memo7, R.drawable.memo8, R.drawable.memo9};
//--------------------------------------------*/


    //Those member variable are used to in video play

    private MediaController mediaController;
    private Uri videoUri;
    private String videoLink;

    // = Uri.parse(LINK);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measerment);

        ButterKnife.bind(this);

        mDishdashaStylesRecord = (DishdashaStylesRecord) getIntent().getExtras().getSerializable("STYLE_DATA");
        flow = getIntent().getExtras().getString("flow");


        //App Video playing block======================================
//        mediaController=new MediaController(this,null);
        videoLink = "http://tefsalkw.com/public/videos/app.3gp";
        videoUri = Uri.parse(videoLink);

        // This function call is for showing video of APP
        //showAppVideoDialog();

        showIndividualVideo(0);
        //showIndividualVideoDialog(1);
        //================================================================


        // mAction=getIntent().getExtras().getString("ACTION");
        // mCategory=getIntent().getExtras().getString("CATEGORY");


        //System.out.println("USER ID=="+mDishdashaStylesRecord.getUser_id());


        // This block of code is used to show next text when Activity is launched from edit section

        if (TefalApp.getInstance().getmAction().equals("edit")) {
            next_txt.setVisibility(VISIBLE);
        }
        //=========================================================================


        ll_sectionOne = (LinearLayout) findViewById(R.id.ll_sectionOne);


        // init();

        mSessionManager = new SessionManager(getApplicationContext());
        //System.out.println("Token==="+mSessionManager.getToken());
        setPrefData(mDishdashaStylesRecord);

        testCase();

        setCustomDesignData();


        setSupportActionBar(toolbar);
        toolbar_title.setText("1/9");

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 210;
        ic_neck.setImageResource(R.drawable.a);
        tv_neck.setVisibility(VISIBLE);


        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;


        seekBar.setMax(max - min);
        init();

        textValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("XXXX");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("XXXX");
            }

            @Override
            public void afterTextChanged(Editable s) {


                int initValue;
                try {
                    initValue = Integer.parseInt(s.toString());
                    if (initValue == 0) {
                        next_txt.setVisibility(View.INVISIBLE);
                    } else {
                        next_txt.setVisibility(View.VISIBLE);


                        if (flag_chest == 1) {
                            mSessionManager.setKeyChestSize("" + initValue + "." + textSubValue.getText());
                            flag_waist = flag_neck = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                            System.out.println("CHEST DATA=====" + mSessionManager.getKeyChestSize());
                        }
                        if (flag_neck == 1) {
                            mSessionManager.setKeyNeckSize("" + initValue + "." + textSubValue.getText());

                            // Log.d("SIZE", ""+mSessionManager.getKeyNeckSize().toString());
                            // Toast.makeText(MeasermentActivity.this,textValue.getText(),Toast.LENGTH_SHORT).show();
                            flag_waist = flag_chest = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                        }
                        if (flag_sldr == 1) {
                            mSessionManager.setKeySldrSize("" + initValue + "." + textSubValue.getText());
                            flag_waist = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                        }
                        if (flag_waist == 1) {
                            mSessionManager.setKeyWaistSize("" + initValue + "." + textSubValue.getText());
                            flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                        }

                        if (flag_arm == 1) {
                            mSessionManager.setKeyArmSize("" + initValue + "." + textSubValue.getText());
                            flag_sldr = flag_chest = flag_neck = flag_waist = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

                        }

                        if (flag_wrist == 1) {

                            mSessionManager.setKeyWristSize("" + initValue + "." + textSubValue.getText());

                            flag_sldr = flag_chest = flag_neck = flag_arm = flag_waist = flag_front_height = flag_back_height = flag_customized = 0;

                        }
                        if (flag_front_height == 1) {

                            mSessionManager.setKeyFrontHeightSize("" + initValue + "." + textSubValue.getText());
                            flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_waist = flag_back_height = flag_customized = 0;

                        }
                        if (flag_back_height == 1) {

                            mSessionManager.setKeyBackHeightSize("" + initValue + "." + textSubValue.getText());
                            flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_waist = flag_customized = 0;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("EXCEPTION ====" + ex);
                    next_txt.setVisibility(View.INVISIBLE);

                }

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // current = progress + min;

                textSubValue.setText("" + progress);
                //  textSubValue.setText("00");

                if (flag_chest == 1) {
                    mSessionManager.setKeyChestSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_waist = flag_neck = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                }
                if (flag_neck == 1) {
                    mSessionManager.setKeyNeckSize(textValue.getText().toString() + "." + textSubValue.getText().toString());

                    // Log.d("SIZE", ""+mSessionManager.getKeyNeckSize().toString());
                    // Toast.makeText(MeasermentActivity.this,textValue.getText(),Toast.LENGTH_SHORT).show();
                    flag_waist = flag_chest = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                }
                if (flag_sldr == 1) {
                    mSessionManager.setKeySldrSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_waist = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                }
                if (flag_waist == 1) {
                    mSessionManager.setKeyWaistSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
                }

                if (flag_arm == 1) {
                    mSessionManager.setKeyArmSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_sldr = flag_chest = flag_neck = flag_waist = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

                }

                if (flag_wrist == 1) {

                    mSessionManager.setKeyWristSize(textValue.getText().toString() + "." + textSubValue.getText().toString());

                    flag_sldr = flag_chest = flag_neck = flag_arm = flag_waist = flag_front_height = flag_back_height = flag_customized = 0;

                }
                if (flag_front_height == 1) {

                    mSessionManager.setKeyFrontHeightSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_waist = flag_back_height = flag_customized = 0;

                }
                if (flag_back_height == 1) {

                    mSessionManager.setKeyBackHeightSize(textValue.getText().toString() + "." + textSubValue.getText().toString());
                    flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_waist = flag_customized = 0;
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // count=count+1;

                switch (back_count) {
                    case 0:
                        ll_neckDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 1:
                        ll_sldrDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 2:
                        ll_chestDesign();
                        // ll_waistDesign();
                        View targetView = findViewById(R.id.ll_neck);
                        targetView.getParent().requestChildFocus(targetView, targetView);
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 3:
                        ll_waistDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 4:
                        ll_armDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 5:
                        ll_wristDesign();

                        View targetView2 = findViewById(R.id.ll_waist);
                        targetView2.getParent().requestChildFocus(targetView2, targetView2);
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 6:
                        ll_frnt_heightDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 7:
                        ll_back_heightDesign();
                        textValue.requestFocus();
                        back_count--;
                        break;
                    case 8:
                        ll_customDesign();
                        // textValue.requestFocus();
                        next_txt.setText("FINISH");
                        back_count--;
                        break;
                    case -1:
                        onBackPressed();
                    default:
                        // ll_back_heightDesign();
                        //nOTHING
                }

            }
        });

        next_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = "";


                s = textValue.getText().toString();
                switch (count) {

                    case 0:
                        System.out.println("OUT PUT====" + textValue.getText());

                        if (!s.equals("0") && !s.equals("")) {
                            ll_neckDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                        }

                        break;
                    case 1:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {
                            ll_sldrDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                        }
                        break;
                    case 2:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {
                            ll_chestDesign();
                            View targetView = findViewById(R.id.ll_wrist);
                            targetView.getParent().requestChildFocus(targetView, targetView);
                            textValue.requestFocus();
                            count = count + 1;
                            break;

                        }


                        // ll_waistDesign();

                        break;
                    case 3:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {
                            ll_waistDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                        }

                        break;
                    case 4:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {
                            ll_armDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;

                        }
                        break;
                    case 5:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {

                            ll_wristDesign();
                            View targetView2 = findViewById(R.id.ll_custom);
                            targetView2.getParent().requestChildFocus(targetView2, targetView2);
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                        }

                        break;
                    case 6:
                        System.out.println("OUT PUT====" + textValue.getText());
                        if (!s.equals("0") && !s.equals("")) {
                            ll_frnt_heightDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                        }

                        break;
                    case 7:
                        System.out.println("OUT PUT====" + textValue.getText());

                        if (!s.equals("0") && !s.equals("")) {
                            ll_back_heightDesign();
                            textValue.requestFocus();
                            count = count + 1;
                            break;
                            //
                        }


                        break;
                    case 8:
                        ll_customDesign();
                        //  textValue.requestFocus();
                        next_txt.setText("FINISH");
                        count = count + 1;
                        break;
                    default:
                        if (validateNecksize() && validateShoulderSize() &&
                                validateChestSize() && validateWaistSize()
                                && validateArmSize() && validateWristSize()
                                && validateFrontLenght() && validateBackHeightLenght()
                                /*&& validateCollorNoButton()
                                && validateNoButtons()*/) {

                            if (TefalApp.getInstance().getmAction().equals("edit")) {
                                WebCallServiceUpdateStyle();
                                //setPrefData(mDishdashaStylesRecord);
                                System.out.println("===============================");
                                System.out.println("Your are from Edit");
                                System.out.println("Category== " + TefalApp.getInstance().getmCategory());
                            }

                            if (TefalApp.getInstance().getmAction().equals("create")) {
                                // WebCallServiceCreateStyle();

                                getmeasureMinMetersHttpCall();
                                System.out.println("===============================");
                                System.out.println("Your are from create");
                                System.out.println("Category== " + TefalApp.getInstance().getmCategory());
                            }
                        }


                        //here write the API call code:
                        // Toast.makeText(getApplicationContext(),"READY TO GO", Toast.LENGTH_SHORT).show();

                }

                if (count < 9) {
                    showIndividualVideo(count);
                }

                /*if(TefalApp.getInstance().getmAction().equals("edit"))
                {
                    WebCallServiceUpdateStyle();
                    //setPrefData(mDishdashaStylesRecord);
                    System.out.println("===============================");
                    System.out.println("Your are from Edit");
                    System.out.println("Category== "+TefalApp.getInstance().getmCategory());
                }

                if(TefalApp.getInstance().getmAction().equals("create"))
                {
                    WebCallServiceCreateStyle();
                    System.out.println("===============================");
                    System.out.println("Your are from create");
                    System.out.println("Category== "+TefalApp.getInstance().getmCategory());
                }*/

                // WebCallServiceCreateStyle();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateNecksize() && validateShoulderSize() &&
                        validateChestSize() && validateWaistSize()
                        && validateArmSize() && validateWristSize()
                        && validateFrontLenght() && validateBackHeightLenght()
                       /* && validateCollorNoButton()
                        && validateNoButtons()*/) {

                    if (TefalApp.getInstance().getmAction().equals("edit")) {
                        WebCallServiceUpdateStyle();
                        //setPrefData(mDishdashaStylesRecord);
                        System.out.println("===============================");
                        System.out.println("Your are from Edit");
                        System.out.println("Category== " + TefalApp.getInstance().getmCategory());
                    }

                    if (TefalApp.getInstance().getmAction().equals("create")) {
                        //WebCallServiceCreateStyle();

                        getmeasureMinMetersHttpCall();
                        System.out.println("===============================");
                        System.out.println("Your are from create");
                        System.out.println("Category== " + TefalApp.getInstance().getmCategory());
                    }
                }

                // WebCallServiceCreateStyle();
            }
        });


        Video_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIndividualVideo(viewTipTrackCount);

                //Toast.makeText(MeasermentActivity.this,"POSITION==="+viewTipTrackCount, Toast.LENGTH_SHORT).show();
                // playVideo();

                // https://www.youtube.com/watch?v=BX9m0wEE5NQ&t=1s
            }
        });


        viewtip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTipDialog();

                //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
            }
        });

     /*-----------------Updated 10-23-2017------------------------*/


        ll_neck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))

                    ll_neckDesign();
                // mSessionManager.getKeyChestSize();
                //Toast.makeText(MeasermentActivity.this,mSessionManager.getKeyNeckSize().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        ll_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))

                    ll_chestDesign();
                // Toast.makeText(MeasermentActivity.this,"H",Toast.LENGTH_SHORT).show();
            }
        });

        ll_sldr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))

                    ll_sldrDesign();

                //Toast.makeText(MeasermentActivity.this,"H",Toast.LENGTH_SHORT).show();
            }
        });
        ll_waist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_waistDesign();

                // Toast.makeText(MeasermentActivity.this,"H",Toast.LENGTH_SHORT).show();
            }
        });


        ll_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_armDesign();


            }
        });

        ll_wrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_wristDesign();
            }
        });

        ll_frnt_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_frnt_heightDesign();
            }
        });

        ll_back_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_back_heightDesign();
            }
        });


        ll_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = textValue.getText().toString();
                if (!s.equals("0") && !s.equals(""))
                    ll_customDesign();


                // showDialog();
            }
        });


        //Customize components event handling------------------

        culffSwitch_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mSessionManager.setKeyCufflink("yes");
                    //cufLinkText.setText("CUFFLINK");
                    cufflink_image.setImageResource(R.drawable.icon_cuff_link_yes);

                } else {
                    mSessionManager.setKeyCufflink("no");
                    // cufLinkText.setText("NO CUFFLINK");
                    cufflink_image.setImageResource(R.drawable.icon_cuff_link_no);

                }
            }
        });


        button_hidden_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    //button_hidden_switch.setChecked(true);


                    //buttonHiddenText.setText("VISIBLE");
                    mSessionManager.setKeyButtonsVisibility("yes");

                    switch (mSessionManager.getKeyButtons()) {
                        case "0":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                            button_hidden_switch.setChecked(false);
                            break;

                        case "1":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show1);
                            break;
                        case "2":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show2);
                            break;
                    }


                    /*noOfButtonSpinner.setEnabled(true);
                    mSessionManager.setKeyButtons("0");*/
                } else {
                    // buttonHiddenText.setText("HIDDEN");
                    mSessionManager.setKeyButtonsVisibility("no");

                    switch (mSessionManager.getKeyButtons()) {
                        case "0":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                            // button_hidden_switch.setChecked(false);
                            break;

                        case "1":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                            break;

                        case "2":
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                            break;

                    }
                    //  noOfButtonSpinner.setEnabled(false);
                    // mSessionManager.setKeyButtons("1");
                }
            }
        });

        /*
        * N.B: Here is naming conflication collar_btn_switch--Chest button
        *       button_hidden_switch--collor button */


        collar_btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    //c dfb
                    // collor_button_hidden_text.setText("VISIBLE");
                    mSessionManager.setKeyCollarButtonVisibility("yes");

                    switch (mSessionManager.getKeyCollarButton()) {
                        case "3":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show3);
                            break;
                        case "4":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show3);

                            break;
                        case "5":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show3);

                    }


                    // collar_btn_spinner.setEnabled(true);
                    // mSessionManager.setKeyCollarButton("3");
                    //collar_btn_switch.setChecked(false);
                } else {
                    // collor_button_hidden_text.setText("HIDDEN"); // Chest button
                    mSessionManager.setKeyCollarButtonVisibility("no"); // chest button

                    switch (mSessionManager.getKeyCollarButton()) {
                        case "3":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden3);
                            break;
                        case "4":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden4);

                            break;
                        case "5":
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden5);

                    }

                }
            }
        });


        collor_push_visibilty_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSessionManager.setKeyButtonsPushVisibilty("yes");
                } else {
                    mSessionManager.setKeyButtonsPushVisibilty("no");
                }
            }
        });


        pen_pocket_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pen_pocket_image_flag) {
                    pen_pocket_image.setImageResource(R.drawable.pocket_icon_pen);
                    pen_pocket_image_flag = false;
                    mSessionManager.setKeyPenPocket("yes");
                    System.out.println("IMAGE PUT ON");
                } else {
                    //pen_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorNavy));
                    pen_pocket_image.setImageResource(R.drawable.pocket_icon_pen_blur);
                    pen_pocket_image_flag = true;
                    mSessionManager.setKeyPenPocket("no");
                    // System.out.println("IMAGE PUT OFF");

                }
            }
        });

        mobile_pocket_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile_pocket_image_flage) {
                    mobile_pocket_image.setImageResource(R.drawable.pocket_icon_mobile);
                    mobile_pocket_image_flage = false;
                    mSessionManager.setKeyMobilePocket("yes");
                } else {
                    //mobile_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorNavy));
                    mobile_pocket_image.setImageResource(R.drawable.pocket_icon_mobile_blur);
                    mobile_pocket_image_flage = true;
                    mSessionManager.setKeyMobilePocket("no");
                }
            }
        });

        key_pocket_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key_pocket_image_flage) {
                    key_pocket_image.setImageResource(R.drawable.pocket_icon_key);
                    key_pocket_image_flage = false;
                    mSessionManager.setKeyPocket("yes");
                } else {
                    //mobile_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorNavy));
                    key_pocket_image.setImageResource(R.drawable.pocket_icon_key_blur);
                    key_pocket_image_flage = true;
                    mSessionManager.setKeyPocket("no");
                }
            }
        });

        noOfButtonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(),"Hii from buttons",Toast.LENGTH_SHORT).show();
                System.out.println("HII BUTTON==" + noOfButtonSpinner.getItemAtPosition(position).toString());
                mSessionManager.setKeyButtons(noOfButtonSpinner.getItemAtPosition(position).toString());

                switch (noOfButtonSpinner.getItemAtPosition(position).toString()) {
                    case "1":
                        if (button_hidden_switch.isChecked()) {
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show1);
                        } else {
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                        }
                        break;

                    case "2":
                        if (button_hidden_switch.isChecked()) {
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show2);
                        } else {
                            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);
                        }
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        collar_btn_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("HII BUTTON COLLAR==" + collar_btn_spinner.getItemAtPosition(position).toString());

                switch (collar_btn_spinner.getItemAtPosition(position).toString()) {
                    case "3":
                        mSessionManager.setKeyCollarButton(collar_btn_spinner.getItemAtPosition(position).toString());
                        if (collar_btn_switch.isChecked()) {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show3);
                        } else {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden3);
                        }
                        break;
                    case "4":
                        mSessionManager.setKeyCollarButton(collar_btn_spinner.getItemAtPosition(position).toString());
                        if (collar_btn_switch.isChecked()) {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show4);
                        } else {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden4);
                        }
                        break;
                    case "5":
                        mSessionManager.setKeyCollarButton(collar_btn_spinner.getItemAtPosition(position).toString());
                        if (collar_btn_switch.isChecked()) {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show5);
                        } else {
                            collor_button_image.setImageResource(R.drawable.icon_chest_btn_hidden5);
                        }
                        break;

                }
                // mSessionManager.setKeyCollarButton(collar_btn_spinner.getItemAtPosition(position).toString());
                // Toast.makeText(getApplicationContext(),"Hii from Collor_buttons",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Cancel Measurement")
                .setMessage("Are you sure you want to cancel?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete


                        finish();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                })
                .show();

    }

    public void setCustomDesignData() {
           /* System.out.println("OUTPUT  BUTTONS=="+mSessionManager.getKeyButtons());
            System.out.println("OUTPUT  CUFFLINK=="+mSessionManager.getKeyCufflink());
            System.out.println("OUTPUT COLLOR BUTTON=="+mSessionManager.getKeyCollarButton());
            System.out.println("OUTPUT  PEN POCKET==="+mSessionManager.getKeyPenPocket());
            System.out.println("OUTPUT  MOBILE POCKET=="+mSessionManager.getKeyMobilePocket());*/
        //System.out.println("OUTPUT");

        decimalString = "00";
        int pos = 0;

        if (mSessionManager.getKeyCufflink().equals("no")) {
            culffSwitch_switch.setChecked(false);
            // cufLinkText.setText("NO CUFFLINK");
            cufflink_image.setImageResource(R.drawable.icon_cuff_link_no);
            // cufflink_image.setVisibility(View.GONE);

        } else {
            culffSwitch_switch.setChecked(true);
            // cufLinkText.setText("CUFFLINK");
            cufflink_image.setImageResource(R.drawable.icon_cuff_link_yes);

            // cufflink_image.setVisibility(View.VISIBLE);
        }


        //This block of code used to handle collor button -----

        if (mSessionManager.getKeyButtons().equals("0")) {
            noOfButtonSpinner.setSelection(0);
            buttonsImage.setImageResource(R.drawable.icon_collar_btn_hidden);

            // buttonHiddenText.setText("VISIBILTY");

        } else if (mSessionManager.getKeyButtons().equals("1")) {
            noOfButtonSpinner.setSelection(1);
            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show1);

        } else if (mSessionManager.getKeyButtons().equals("2")) {
            noOfButtonSpinner.setSelection(2);
            buttonsImage.setImageResource(R.drawable.icon_collar_btn_show2);
        }

        // This block of code is used handle the visibilty of Collar button

        if (mSessionManager.getKeyButtonsVisibility().equals("yes")) {
            button_hidden_switch.setChecked(true);
            //.setText("VISIBILE");
        } else {
            button_hidden_switch.setChecked(false);
            // buttonHiddenText.setText("HIDDEN");
        }






       /* buttonHiddenText.setText("VISIBLE");
        button_hidden_switch.setChecked(true);*/


//======================================================================

        // This block of code used to handle chest button===============================

        if (mSessionManager.getKeyCollarButton().equals("3")) {
            pos = 0;
            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show3);
            collar_btn_spinner.setSelection(pos);
            System.out.println("POSITION===" + pos);
        }
        if (mSessionManager.getKeyCollarButton().equals("4")) {
            pos = 1;
            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show4);
            collar_btn_spinner.setSelection(pos);
            System.out.println("POSITION===" + pos);
        }
        if (mSessionManager.getKeyCollarButton().equals("5")) {
            pos = 2;
            System.out.println("POSITION===" + pos);
            collor_button_image.setImageResource(R.drawable.icon_chest_btn_show5);
            collar_btn_spinner.setSelection(pos);
        }


        // This block of code is used to handle the visibility of shirtButton/Chest Button

        if (mSessionManager.getKeyCollarButtonVisibility().equals("yes")) {
            collar_btn_switch.setChecked(true);
        } else {
            collar_btn_switch.setChecked(false);
        }


        // This bock of code used to handle tthe collor button push visibilty

        if (mSessionManager.getKeyButtonsPushVisibility().equals("yes")) {
            collor_push_visibilty_switch.setChecked(true);

            /*collar_btn_spinner.setSelection(pos);
            collar_btn_switch.setChecked(true);
           // collor_button_hidden_text.setText("VISIBLE");*/
        } else {
            collor_push_visibilty_switch.setChecked(false);
            /*collar_btn_spinner.setSelection(pos);
            collar_btn_switch.setChecked(false);
            //collor_button_hidden_text.setText("HIDDEN");*/
        }


        //============================================================================

        if (mSessionManager.getKeyMobilePocket().equals("no")) {
            mobile_pocket_image_flage = true;
            mobile_pocket_image.setImageResource(R.drawable.pocket_icon_mobile_blur);
            // mobile_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDivider));
            // setColorFilter(ContextCompat.getColor(getContext(), R.color.green_500))
        } else {
            mobile_pocket_image_flage = false;
            mobile_pocket_image.setImageResource(R.drawable.pocket_icon_mobile);

            // mobile_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorNavy));
        }


        if (mSessionManager.getKeyPenPocket().equals("no")) {
            // pen_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDivider));
            pen_pocket_image.setImageResource(R.drawable.pocket_icon_pen_blur);
            pen_pocket_image_flag = true;
        } else {

            // pen_pocket_image_flag=true;
            pen_pocket_image.setImageResource(R.drawable.pocket_icon_pen);
            pen_pocket_image_flag = false;


            //pen_pocket_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorNavy));

        }

        if (mSessionManager.getKeyPocket().equals("no")) {
            key_pocket_image.setImageResource(R.drawable.pocket_icon_key_blur);
            key_pocket_image_flage = true;
        } else {
            key_pocket_image.setImageResource(R.drawable.pocket_icon_key);
            key_pocket_image_flage = false;
        }

    }

    private void ll_neckDesign() {


        toolbar_title.setText("1/9");
        next_txt.setText("NEXT");

        tv_neck.setVisibility(VISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.a);
        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);
        flag_neck = 1;
        viewToogle();
        flag_waist = flag_chest = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

        String size = (mSessionManager.getKeyNeckSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];


        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }


        count = 0;
        back_count = 0;
        viewTipTrackCount = 1;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 210;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;


    }

    private void ll_chestDesign() {
        decimalString = "00";
        toolbar_title.setText("3/9");
        next_txt.setText("NEXT");
        flag_chest = 1;


        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.VISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.c);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);

        viewToogle();


        flag_waist = flag_neck = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;
        //String[] parts={};


        String size = (mSessionManager.getKeyChestSize());
        String[] parts = size.split("\\.");
        String intValue = "" + parts[0];
        String decimalValue = "" + parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        //textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);
        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }

        count = 2;
        back_count = 2;
        viewTipTrackCount = 3;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 210;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;

    }

    private void ll_sldrDesign() {


        next_txt.setText("NEXT");
        toolbar_title.setText("2/9");
        flag_sldr = 1;

        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.VISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.b);


        flag_waist = flag_neck = flag_chest = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

        viewToogle();

        String size = (mSessionManager.getKeySldrSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);


        System.out.println("lenght======    CM===" + lenghtCM);
        System.out.println("lenght======    CM===" + lenghtMM);
        seekBar.setProgress(lenghtMM);
        textSubValue.setText("" + lenghtMM);
        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }
        count = 1;
        back_count = 1;
        viewTipTrackCount = 2;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 210;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;
    }

    private void ll_waistDesign() {
        // decimalString="00";
        next_txt.setText("NEXT");
        toolbar_title.setText("4/9");
        flag_waist = 1;

        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.VISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.d);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);


        viewToogle();
        flag_chest = flag_neck = flag_sldr = flag_arm = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

        String size = (mSessionManager.getKeyWaistSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }

        count = 3;
        back_count = 3;
        viewTipTrackCount = 4;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 210;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;
    }

    private void ll_armDesign() {
        // decimalString="00";
        next_txt.setText("NEXT");
        flag_sldr = flag_chest = flag_neck = flag_waist = flag_wrist = flag_front_height = flag_back_height = flag_customized = 0;

        toolbar_title.setText("5/9");
        flag_arm = 1;

        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.VISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.e);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);


        viewToogle();

        String size = (mSessionManager.getKeyArmSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);
        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }

        count = 4;
        back_count = 4;
        viewTipTrackCount = 5;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 210;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;

    }

    private void ll_wristDesign() {
        decimalString = "00";
        next_txt.setText("NEXT");
        flag_sldr = flag_chest = flag_neck = flag_arm = flag_waist = flag_front_height = flag_back_height = flag_customized = 0;
        toolbar_title.setText("6/9");
        flag_wrist = 1;


        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.VISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.f);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);

        viewToogle();

        String size = (mSessionManager.getKeyWristSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }

        count = 5;
        back_count = 5;
        viewTipTrackCount = 6;

        //flag_chest=flag_neck=flag_sldr=flag_arm=flag_wrist=flag_front_height=flag_back_height=flag_customized=0;
        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 210;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;
    }

    private void ll_frnt_heightDesign() {
        //decimalString="00";
        next_txt.setText("NEXT");
        flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_waist = flag_back_height = flag_customized = 0;
        toolbar_title.setText("7/9");
        flag_front_height = 1;


        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.VISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.g);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);


        viewToogle();

        String size = (mSessionManager.getKeyFrontHeightSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }


        count = 6;
        back_count = 6;
        viewTipTrackCount = 7;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 210;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;
    }

    private void ll_back_heightDesign() {
        // decimalString="00";
        next_txt.setText("NEXT");
        flag_sldr = flag_chest = flag_neck = flag_arm = flag_wrist = flag_front_height = flag_waist = flag_customized = 0;
        toolbar_title.setText("8/9");
        flag_back_height = 1;

        tv_neck.setVisibility(View.INVISIBLE);


        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.VISIBLE);
        tv_custom.setVisibility(View.INVISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);

        ic_custom.setImageResource(R.drawable.icon_custom_blur);
        ic_b_height.setImageResource(R.drawable.h);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);


        viewToogle();
        //flag_chest=flag_neck=flag_sldr=flag_arm=flag_wrist=flag_front_height=flag_back_height=flag_customized=0;

        String size = (mSessionManager.getKeyBackHeightSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];


        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);

        seekBar.setProgress(lenghtMM);
        // textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }


        count = 7;
        back_count = 7;
        viewTipTrackCount = 8;

        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 210;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 190;
    }

    private void ll_customDesign() {
        // This line of code is used to hide the soft key while entering the custom screen
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_custom.getWindowToken(), 0);

        tv_neck.setVisibility(View.INVISIBLE);
        tv_shoulder.setVisibility(View.INVISIBLE);
        tv_chest.setVisibility(View.INVISIBLE);
        tv_waist.setVisibility(View.INVISIBLE);
        tv_arm.setVisibility(View.INVISIBLE);
        tv_wrist.setVisibility(View.INVISIBLE);
        tv_f_height.setVisibility(View.INVISIBLE);
        tv_b_height.setVisibility(View.INVISIBLE);
        tv_custom.setVisibility(View.VISIBLE);


        ic_neck.setImageResource(R.drawable.icon_neck_blur);
        ic_custom.setImageResource(R.drawable.i);
        ic_b_height.setImageResource(R.drawable.icon_height_back_blur);
        ic_f_height.setImageResource(R.drawable.icon_height_front_blur);
        ic_wrist.setImageResource(R.drawable.icon_wrist_blur);
        ic_arm.setImageResource(R.drawable.icon_arm_blur);
        ic_waist.setImageResource(R.drawable.icon_waist_blur);
        ic_chest.setImageResource(R.drawable.icon_chest_blur);
        ic_shoulder.setImageResource(R.drawable.icon_sldr_blur);


        decimalString = "00";
        next_txt.setText("FINISH");
        count = 8;
        viewTipTrackCount = 9;


        tv_neck.setTextSize(12);
        ic_neck.requestLayout();
        ic_neck.getLayoutParams().height = 190;

        tv_shoulder.setTextSize(12);
        ic_shoulder.requestLayout();
        ic_shoulder.getLayoutParams().height = 190;

        tv_chest.setTextSize(12);
        ic_chest.requestLayout();
        ic_chest.getLayoutParams().height = 190;

        tv_waist.setTextSize(12);
        ic_waist.requestLayout();
        ic_waist.getLayoutParams().height = 190;

        tv_arm.setTextSize(12);
        ic_arm.requestLayout();
        ic_arm.getLayoutParams().height = 190;

        tv_wrist.setTextSize(12);
        ic_wrist.requestLayout();
        ic_wrist.getLayoutParams().height = 190;

        tv_f_height.setTextSize(12);
        ic_f_height.requestLayout();
        ic_f_height.getLayoutParams().height = 190;

        tv_b_height.setTextSize(12);
        ic_b_height.requestLayout();
        ic_b_height.getLayoutParams().height = 190;

        tv_custom.setTextSize(12);
        ic_custom.requestLayout();
        ic_custom.getLayoutParams().height = 210;

        ll_sectionOne.setVisibility(View.GONE);
        ll_sectionTwo.setVisibility(View.VISIBLE);
        toolbar_title.setText("9/9");

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    public void viewToogle() {
        ll_sectionTwo.setVisibility(View.GONE);
        ll_sectionOne.setVisibility(View.VISIBLE);
    }

    /*public  void playVideo()
    {
        String url = "https://www.youtube.com/watch?v=7p1Ii2p7nyg";

        Dialog dialog = new Dialog(MeasermentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_view);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


        VideoView videoView =(VideoView)dialog.findViewById(R.id.videoView);
        String uriPath = "android.resource://"+ getPackageName() +"/"+R.raw.sample_video2;

        Uri uri = Uri.parse(uriPath);

      //  videoView.setMediaController(new MediaController(getApplicationContext()));
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        dialog.show();
    }*/

    public void setPrefData(DishdashaStylesRecord stylesRecord) {
        System.out.println("=====  stylesRecord.getNeck()" + stylesRecord.getNeck());
        System.out.println("=====  stylesRecord.getShoulder()" + stylesRecord.getShoulder());
        System.out.println("=====  stylesRecord.getChest()" + stylesRecord.getChest());
        System.out.println("=====  stylesRecord.getWaist()" + stylesRecord.getWaist());
        System.out.println("=====  stylesRecord.getNeck()" + stylesRecord.getNeck());
        System.out.println("=====  stylesRecord.getNeck()" + stylesRecord.getNeck());
        System.out.println("=====  stylesRecord.getNeck()" + stylesRecord.getNeck());
        System.out.println("=====  stylesRecord.getNeck()" + stylesRecord.getNeck());

        mSessionManager.setKeyNeckSize(stylesRecord.getNeck());
        mSessionManager.setKeySldrSize(stylesRecord.getShoulder());
        mSessionManager.setKeyChestSize(stylesRecord.getChest());
        mSessionManager.setKeyWaistSize(stylesRecord.getWaist());
        mSessionManager.setKeyWristSize(stylesRecord.getWrist());
        mSessionManager.setKeyArmSize(stylesRecord.getArm());
        mSessionManager.setKeyFrontHeightSize(stylesRecord.getFront_height());
        mSessionManager.setKeyBackHeightSize(stylesRecord.getBack_height());

        mSessionManager.setKeyButtons(stylesRecord.getButtons());
        mSessionManager.setKeyPenPocket(stylesRecord.getPen_pocket());
        mSessionManager.setKeyMobilePocket(stylesRecord.getMobile_pocket());
        mSessionManager.setKeyPocket(stylesRecord.getKey_pocket());
        mSessionManager.setKeyCollarButton(stylesRecord.getCollar_buttons());
        mSessionManager.setKeyCufflink(stylesRecord.getCufflink());

        mSessionManager.setKeyButtonsPushVisibilty(stylesRecord.getCollar_buttons_push());
        mSessionManager.setKeyButtonsVisibility(stylesRecord.getCollar_button_visibility());
        mSessionManager.setKeyCollarButtonVisibility(stylesRecord.getShirt_button_visibility());


    }

    public void init() {

        String size = (mSessionManager.getKeyNeckSize());
        String[] parts = size.split("\\.");
        String intValue = parts[0];
        String decimalValue = parts[1];

        int lenghtCM = Integer.parseInt(intValue);
        int lenghtMM = Integer.parseInt(decimalValue);


        System.out.println("lenght======    CM===" + lenghtCM);
        System.out.println("lenght======    CM===" + lenghtMM);

        seekBar.setProgress(lenghtMM);
        //  textValue.setText(""+lenghtCM);
        textSubValue.setText("" + lenghtMM);

        if (lenghtCM == 0) {
            if (TefalApp.getInstance().getmAction().equals("create")) {
                textValue.setText("");
            } else {
                textValue.setText("" + lenghtCM);
            }
        } else {
            textValue.setText("" + lenghtCM);
        }
    }

    public void WebCallServiceCreateStyle() {


        System.out.println("======waist" + mSessionManager.getKeyWaistSize());
        System.out.println("======wrist" + mSessionManager.getKeyWristSize());
        System.out.println("======arm" + mSessionManager.getKeyArmSize());
        System.out.println("======shoulder" + mSessionManager.getKeySldrSize());
        System.out.println("======front_height" + mSessionManager.getKeyFrontHeightSize());
        System.out.println("======back_height" + mSessionManager.getKeyBackHeightSize());
        System.out.println("======chest" + mSessionManager.getKeyChestSize());
        System.out.println("======cufflink" + mSessionManager.getKeyCufflink());
        System.out.println("======collar_buttons" + mSessionManager.getKeyCollarButton());
        System.out.println("======neck" + mSessionManager.getKeyNeckSize());
        System.out.println("======mobile_pocket" + mSessionManager.getKeyMobilePocket());
        System.out.println("======pen_pocket" + mSessionManager.getKeyPenPocket());
        System.out.println("======key_pocket" + mSessionManager.getKeyPocket());
        System.out.println("======buttons" + mSessionManager.getKeyButtons());
        System.out.println("======name" + mDishdashaStylesRecord.getName());
        SimpleProgressBar.showProgress(MeasermentActivity.this);
        try {
            final String url = Contents.baseURL + "createMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {


                                mSessionManager.clearSizes();

                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();

                                    if (flow.equals("TabbarActivity")) {
                                        startActivity(new Intent(getApplicationContext(), TabbarActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    }
                                    if (flow.equals("DishdishaStyleActivity")) {
                                        startActivity(new Intent(getApplicationContext(), DishdishaStyleActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
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
                            System.out.println("Error==" + error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("access_token", mSessionManager.getToken());
                    params.put("user_id", mSessionManager.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("wide", "1");
                    params.put("category", TefalApp.getInstance().getmCategory().toString());
                    params.put("narrow", "1");
                    params.put("waist", mSessionManager.getKeyWaistSize());
                    params.put("wrist", mSessionManager.getKeyWristSize());
                    params.put("arm", mSessionManager.getKeyArmSize());
                    params.put("shoulder", mSessionManager.getKeySldrSize());
                    params.put("front_height", mSessionManager.getKeyFrontHeightSize());
                    params.put("back_height", mSessionManager.getKeyBackHeightSize());
                    params.put("chest", mSessionManager.getKeyChestSize());
                    params.put("cufflink", mSessionManager.getKeyCufflink());
                    params.put("collar_buttons", mSessionManager.getKeyCollarButton());
                    params.put("neck", mSessionManager.getKeyNeckSize());
                    params.put("mobile_pocket", mSessionManager.getKeyMobilePocket());
                    params.put("pen_pocket", mSessionManager.getKeyPenPocket());
                    params.put("key_pocket", mSessionManager.getKeyPocket());
                    params.put("buttons", mSessionManager.getKeyButtons());
                    params.put("name", mDishdashaStylesRecord.getName());
                    params.put("min_meters", min_meters);

                    params.put("collar_button_visibility", mSessionManager.getKeyButtonsVisibility());
                    params.put("shirt_button_visibility", mSessionManager.getKeyButtonsVisibility());
                    params.put("collar_buttons_push", mSessionManager.getKeyButtonsPushVisibility());


                    System.out.println("OUTPUT  BUTTONS==" + mSessionManager.getKeyButtons());
                    System.out.println("OUTPUT  CUFFLINK==" + mSessionManager.getKeyCufflink());
                    System.out.println("OUTPUT COLLOR BUTTON==" + mSessionManager.getKeyCollarButton());
                    System.out.println("OUTPUT  PEN POCKET===" + mSessionManager.getKeyPenPocket());
                    System.out.println("OUTPUT  MOBILE POCKET==" + mSessionManager.getKeyMobilePocket());

                    Log.d("Tefsal", url + params);

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
            System.out.println("Error======" + surError);
            surError.printStackTrace();
        }
    }


    private void getmeasureMinMetersHttpCall() {
        System.out.println("======waist" + mSessionManager.getKeyWaistSize());
        System.out.println("======wrist" + mSessionManager.getKeyWristSize());
        System.out.println("======arm" + mSessionManager.getKeyArmSize());
        System.out.println("======shoulder" + mSessionManager.getKeySldrSize());
        System.out.println("======front_height" + mSessionManager.getKeyFrontHeightSize());
        System.out.println("======back_height" + mSessionManager.getKeyBackHeightSize());
        System.out.println("======chest" + mSessionManager.getKeyChestSize());
        System.out.println("======cufflink" + mSessionManager.getKeyCufflink());
        System.out.println("======collar_buttons" + mSessionManager.getKeyCollarButton());
        System.out.println("======neck" + mSessionManager.getKeyNeckSize());
        System.out.println("======mobile_pocket" + mSessionManager.getKeyMobilePocket());
        System.out.println("======pen_pocket" + mSessionManager.getKeyPenPocket());
        System.out.println("======key_pocket" + mSessionManager.getKeyPocket());
        System.out.println("======buttons" + mSessionManager.getKeyButtons());
        System.out.println("======name" + mDishdashaStylesRecord.getName());
        SimpleProgressBar.showProgress(MeasermentActivity.this);
        try {
            final String url = Contents.baseURL + "measureMinMeters";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    //System.out.println("OUT PUT====OBJECT"+jsonObject.toString());


                                    String status = jsonObject.getString("status");


                                    System.out.println("MIN MITER STATUS===" + status);
                                    if (status.equals("1")) {
                                        min_meters = jsonObject.getString("min_meters");
                                        showNamePrompt(min_meters);


                                        System.out.println("MINIMUM METER OF DISHDAHSA====" + min_meters);
                                    } else {
                                        Toast.makeText(MeasermentActivity.this, "Unable to fetch minimum metter for dishdasha", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception ex) {
                                    System.out.println("Error==" + ex);
                                    SimpleProgressBar.closeProgress();
                                }


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error==" + error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    // params.put("access_token", mSessionManager.getToken());

                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("front_height", mSessionManager.getKeyFrontHeightSize());

                    System.out.println("OUTPUT  BUTTONS==" + mSessionManager.getKeyButtons());
                    System.out.println("OUTPUT  CUFFLINK==" + mSessionManager.getKeyCufflink());
                    System.out.println("OUTPUT COLLOR BUTTON==" + mSessionManager.getKeyCollarButton());
                    System.out.println("OUTPUT  PEN POCKET===" + mSessionManager.getKeyPenPocket());
                    System.out.println("OUTPUT  MOBILE POCKET==" + mSessionManager.getKeyMobilePocket());


                    System.out.println("OUTPUT  BUTTONS==" + mSessionManager.getKeyButtons());
                    System.out.println("OUTPUT  CUFFLINK==" + mSessionManager.getKeyCufflink());
                    System.out.println("OUTPUT COLLOR BUTTON==" + mSessionManager.getKeyCollarButton());
                    System.out.println("OUTPUT  PEN POCKET===" + mSessionManager.getKeyPenPocket());
                    System.out.println("OUTPUT  MOBILE POCKET==" + mSessionManager.getKeyMobilePocket());

                    Log.d("Tefsal", url + params);

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
            System.out.println("Error======" + surError);
            surError.printStackTrace();
        }
    }

    public void WebCallServiceUpdateStyle() {
        SimpleProgressBar.showProgress(MeasermentActivity.this);
        try {
            final String url = Contents.baseURL + "updateMyStyle";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), TabbarActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error==" + error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    System.out.println("======waist" + mSessionManager.getKeyWaistSize());
                    System.out.println("======wrist" + mSessionManager.getKeyWristSize());
                    System.out.println("======arm" + mSessionManager.getKeyArmSize());
                    System.out.println("======shoulder" + mSessionManager.getKeySldrSize());
                    System.out.println("======front_height" + mSessionManager.getKeyFrontHeightSize());
                    System.out.println("======back_height" + mSessionManager.getKeyBackHeightSize());
                    System.out.println("======chest" + mSessionManager.getKeyChestSize());
                    System.out.println("======cufflink" + mSessionManager.getKeyCufflink());
                    System.out.println("======collar_buttons" + mSessionManager.getKeyCollarButton());
                    System.out.println("======neck" + mSessionManager.getKeyNeckSize());
                    System.out.println("======mobile_pocket" + mSessionManager.getKeyMobilePocket());
                    System.out.println("======pen_pocket" + mSessionManager.getKeyPenPocket());
                    System.out.println("======key_pocket" + mSessionManager.getKeyPocket());
                    System.out.println("======buttons" + mSessionManager.getKeyButtons());
                    System.out.println("======name" + mDishdashaStylesRecord.getName());
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("access_token", mSessionManager.getToken());
                    params.put("user_id", mSessionManager.getCustomerId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");
                    params.put("wide", "1");
                    params.put("category", TefalApp.getInstance().getmCategory().toString());
                    params.put("narrow", "1");
                    params.put("waist", mSessionManager.getKeyWaistSize());
                    params.put("wrist", mSessionManager.getKeyWristSize());
                    params.put("arm", mSessionManager.getKeyArmSize());
                    params.put("shoulder", mSessionManager.getKeySldrSize());
                    params.put("front_height", mSessionManager.getKeyFrontHeightSize());
                    params.put("back_height", mSessionManager.getKeyBackHeightSize());
                    params.put("chest", mSessionManager.getKeyChestSize());
                    params.put("cufflink", mSessionManager.getKeyCufflink());
                    params.put("collar_buttons", mSessionManager.getKeyCollarButton());
                    params.put("neck", mSessionManager.getKeyNeckSize());
                    params.put("mobile_pocket", mSessionManager.getKeyMobilePocket());
                    params.put("pen_pocket", mSessionManager.getKeyPenPocket());
                    params.put("key_pocket", mSessionManager.getKeyPocket());
                    params.put("buttons", mSessionManager.getKeyButtons());
                    params.put("name", mDishdashaStylesRecord.getName());

                    params.put("id", mDishdashaStylesRecord.getId());

                    params.put("collar_button_visibility", mSessionManager.getKeyButtonsVisibility());
                    params.put("shirt_button_visibility", mSessionManager.getKeyCollarButtonVisibility());
                    params.put("collar_buttons_push", mSessionManager.getKeyButtonsPushVisibility());

                    //System.out.println("Button====="+)
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

    //====================================  validation portion=================================================


    private boolean validateNecksize() {
        if (mSessionManager.getKeyNeckSize().equals("0.00") || mSessionManager.getKeyNeckSize().equals("0.0") || mSessionManager.getKeyNeckSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Neck size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateShoulderSize() {
        if (mSessionManager.getKeySldrSize().equals("0.00") || mSessionManager.getKeySldrSize().equals("0.0") || mSessionManager.getKeySldrSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Shoulder Size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateChestSize() {
        if (mSessionManager.getKeyChestSize().equals("0.00") || mSessionManager.getKeyChestSize().equals("0.0") || mSessionManager.getKeyChestSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Chest Size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateWaistSize() {
        if (mSessionManager.getKeyWaistSize().equals("0.00") || mSessionManager.getKeyWaistSize().equals("0.0") || mSessionManager.getKeyWaistSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Waist Size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateArmSize() {
        if (mSessionManager.getKeyArmSize().equals("0.00") || mSessionManager.getKeyArmSize().equals("0.0") || mSessionManager.getKeyArmSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Arm Size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    private boolean validateWristSize() {
        if (mSessionManager.getKeyWristSize().equals("0.00") || mSessionManager.getKeyWristSize().equals("0.0") || mSessionManager.getKeyWristSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Wrist Size is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFrontLenght() {
        if (mSessionManager.getKeyFrontHeightSize().equals("0.00") || mSessionManager.getKeyFrontHeightSize().equals("0.0") || mSessionManager.getKeyFrontHeightSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Front height Lenght is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateBackHeightLenght() {
        if (mSessionManager.getKeyBackHeightSize().equals("0.00") || mSessionManager.getKeyBackHeightSize().equals("0.0") || mSessionManager.getKeyBackHeightSize().equals("0")) {
            Toast.makeText(getApplicationContext(), "Back Height Lenght is Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateCollorNoButton() {
        if (collar_btn_switch.isChecked() && mSessionManager.getKeyCollarButton().equals("0")) {
            //System.out.println("CHECKED STATUS=="+collar_btn_switch.isChecked()+"==BUTTONSC"+mSessionManager.getKeyCollarButton());
            Toast.makeText(getApplicationContext(), "You must select the collor button number more than 0", Toast.LENGTH_SHORT).show();
            return false;
        }

        // System.out.println("CHECKED STATUS=="+collar_btn_switch.isChecked()+"==BUTTONSC"+mSessionManager.getKeyCollarButton());

        return true;
    }

    private boolean validateNoButtons() {
        if (button_hidden_switch.isChecked() && mSessionManager.getKeyButtons().equals("0")) {
            System.out.println("CHECKED STATUS==" + button_hidden_switch.isChecked() + "==BUTTONSC" + mSessionManager.getKeyButtons());

            Toast.makeText(getApplicationContext(), "You must select the button number more than 0", Toast.LENGTH_SHORT).show();
            return false;
        }
        // System.out.println("CHECKED STATUS=="+button_hidden_switch.isChecked()+"==BUTTONSC"+ mSessionManager.getKeyButtons());

        return true;
    }


    private void showTipDialog() {
        // System.out.println("POSITION======"+viewTipTrackCount);
        tipsLayout.setVisibility(View.VISIBLE);

        switch (viewTipTrackCount) {
            case 1:
                tip_image.setImageResource(R.drawable.memo1);
                break;
            case 2:
                tip_image.setImageResource(R.drawable.memo2);
                break;
            case 3:
                tip_image.setImageResource(R.drawable.memo3);
                break;
            case 4:
                tip_image.setImageResource(R.drawable.memo4);
                break;
            case 5:
                tip_image.setImageResource(R.drawable.memo5);
                break;
            case 6:
                tip_image.setImageResource(R.drawable.memo6);
                break;
            case 7:
                tip_image.setImageResource(R.drawable.memo7);
                break;
            case 8:
                tip_image.setImageResource(R.drawable.memo8);
                break;
            default:

        }
        btn_back_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipsLayout.setVisibility(View.GONE);
            }
        });


    }

    public void showNamePrompt(String min_dishdasha) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.min_meter_dishdasha_dialog, null);
        TextView txt_min_meter = (TextView) dialogView.findViewById(R.id.txt_min_meter);

        txt_min_meter.setText("You are " + min_dishdasha + " meter(s) as per our \n is that Correct?");

        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_yes_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_no_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                WebCallServiceCreateStyle();

                // Toast.makeText(MeasermentActivity.this, "You clicked OK", Toast.LENGTH_SHORT).show();

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showInputPrompt();

            }
        });

    }


    public void showInputPrompt() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.min_meter_dishdasha_prompt_dialog, null);
        TextView txt_min_meter = (TextView) dialogView.findViewById(R.id.txt_min_meter);
        final EditText inputmeter = (EditText) dialogView.findViewById(R.id.inputmeter);


        //txt_min_meter.setText("You are "+min_dishdasha+"meter as per our \n is that Correct?");

        Button dialog_ok_btn = (Button) dialogView.findViewById(R.id.dialog_yes_btn);
        Button dialog_cancel_btn = (Button) dialogView.findViewById(R.id.dialog_no_btn);
        // ButterKnife.bind(this, dialogView);

        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (android.text.TextUtils.isDigitsOnly(inputmeter.getText().toString())) {
                    alertDialog.dismiss();
                    min_meters = inputmeter.getText().toString();
                    WebCallServiceCreateStyle();
                } else {
                    Toast.makeText(MeasermentActivity.this, "Input must be digit", Toast.LENGTH_SHORT).show();
                }
                //min_meters=inputmeter.getText().toString();
                //WebCallServiceCreateStyle();

                // Toast.makeText(MeasermentActivity.this, "You clicked OK", Toast.LENGTH_SHORT).show();

            }
        });
        dialog_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    //This block of code is responsible for showing video for For entire App.

    private void showAppVideoDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.video_view_dialog, null);
        final ProgressBar video_loading_progress = (ProgressBar) dialogView.findViewById(R.id.video_loading_progress);
        final VideoView videoView = (VideoView) dialogView.findViewById(R.id.app_video_view);
        TextView skipTxt = (TextView) dialogView.findViewById(R.id.skipTxt);
        // final View view_video_container=(View)dialogView.findViewById(R.id.view_video_container);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        videoView.requestFocus();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);


        } catch (Exception ex) {
            System.out.println("Video play Exception ex===" + ex);
            Toast.makeText(this, "Unable to play video", Toast.LENGTH_SHORT).show();
        }


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                // alertDialog.show();
                video_loading_progress.setVisibility(View.GONE);
                // view_video_container.setVisibility(View.GONE);
                videoView.start();

            }
        });

        skipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //videoView.st
                alertDialog.dismiss();

            }
        });

    }


    // This block is responsible for showing video for individual Item like neck, shoulder etc.....

    private void showIndividualVideo(int position) {

        startActivity(new Intent(MeasermentActivity.this, CustomVideoPlayerActivity.class).putExtra("position", position));


    }

    private void showIndividualVideoDialog1(int position) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.video_view_dialog, null);
        final VideoView videoView = (VideoView) dialogView.findViewById(R.id.app_video_view);
        final ProgressBar video_loading_progress = (ProgressBar) dialogView.findViewById(R.id.video_loading_progress);
        TextView skipTxt = (TextView) dialogView.findViewById(R.id.skipTxt);

        // final View view_video_container=(View)dialogView.findViewById(R.id.view_video_container);


        switch (position) {

            case 1:
                videoLink = "http://tefsalkw.com/public/videos/app_video_neck.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 2:
                videoLink = "http://tefsalkw.com/public/videos/app_video_custom.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 3:
                videoLink = "http://tefsalkw.com/public/videos/app_video_chest.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 4:
                videoLink = "http://tefsalkw.com/public/videos/app_video_waist.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 5:
                videoLink = "http://tefsalkw.com/public/videos/app_video_arm.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 6:
                videoLink = "http://tefsalkw.com/public/videos/app_video_wrist.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 7:
                videoLink = "http://tefsalkw.com/public/videos/app_video_f_height.3gp";
                videoUri = Uri.parse(videoLink);
                break;
            case 8:
                videoLink = "http://tefsalkw.com/public/videos/app_video_b_height.3gp";
                videoUri = Uri.parse(videoLink);
                break;

        }

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        videoView.requestFocus();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        try {
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoUri);


        } catch (Exception ex) {
            System.out.println("Video play Exception ex===" + ex);
            Toast.makeText(this, "Unable to play video", Toast.LENGTH_SHORT).show();
        }


        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {

                video_loading_progress.setVisibility(View.GONE);
                //view_video_container.setVisibility(View.GONE);
                videoView.start();
            }
        });
        skipTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_loading_progress.setVisibility(View.GONE);
                alertDialog.dismiss();
            }
        });
    }


    private void testCase() {
        System.out.println("VISIBILIY=========collar_button_visibility=======" + mSessionManager.getKeyButtonsVisibility());
        System.out.println("VISIBILIY=========shirt_button_visibility======" + mSessionManager.getKeyCollarButtonVisibility());
        System.out.println("VISIBILIY=========collar_buttons_push======" + mSessionManager.getKeyButtonsPushVisibility());

    }

}
