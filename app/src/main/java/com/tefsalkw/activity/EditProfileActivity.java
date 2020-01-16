package com.tefsalkw.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tefsalkw.R;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.camera.CustomCameraActivity;
import com.tefsalkw.utils.CircleTransform;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.ExifTransformation;
import com.tefsalkw.utils.GetImagePath;
import com.tefsalkw.utils.MultipartUtility;
import com.tefsalkw.utils.RoundImage;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class EditProfileActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_back)
    ImageButton btn_back;

    @BindView(R.id.editBtn)
    ImageView editBtn;


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    SessionManager session;


    @BindView(R.id.input_layout_first_name)
    TextInputLayout input_layout_first_name;


    @BindView(R.id.input_layout_last_name)
    TextInputLayout input_layout_last_name;

    @BindView(R.id.input_layout_mobile_no)
    TextInputLayout input_layout_mobile_no;

    @BindView(R.id.input_layout_home_no)
    TextInputLayout input_layout_home_no;

    @BindView(R.id.input_layout_nationality)
    TextInputLayout input_layout_nationality;

    @BindView(R.id.input_layout_dob)
    TextInputLayout input_layout_dob;

    @BindView(R.id.input_layout_gender)
    TextInputLayout input_layout_gender;

    @BindView(R.id.input_first_name)
    EditText input_first_name;

    @BindView(R.id.input_last_name)
    EditText input_last_name;

    @BindView(R.id.input_mobile_no)
    EditText input_mobile_no;

    @BindView(R.id.input_home_no)
    EditText input_home_no;

    @BindView(R.id.input_nationality)
    EditText input_nationality;

    @BindView(R.id.input_dob)
    EditText input_dob;

    @BindView(R.id.input_gender)
    EditText input_gender;

    @BindView(R.id.profile_image)
    ImageView profile_image;

    @BindView(R.id.save_btn)
    Button save_btn;

    @BindView(R.id.LL_radio_container)
    LinearLayout LL_radio_container;


    @BindView(R.id.radio_femmale_btn)
    RadioButton radio_femmale_btn;

    @BindView(R.id.radio_male_btn)
    RadioButton radio_male_btn;

    @BindView(R.id.radio_btn_group)
    RadioGroup radio_btn_group;

    @BindView(R.id.LL_nationality)
    LinearLayout LL_nationality;

    private String mGender;

    @BindView(R.id.spin_country)
    Spinner spin_country;

    ArrayAdapter aa;

    List<String> country_name;
    List<String> iso_name;
    String country_iso_code;

    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    private static final int GALLERY_REQUEST_CODE = 102;
    public static final int CAMERA_REQUEST_CODE = 103;


    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    private Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "profile";
    private Button camera;
    private Button gallery;
    private String imageFile;
    private String ImagePath = "";
    private String firstname;
    private String lastName;
    private String mobileNo;
    private String homeNo;
    private String dob;
    private int rotation;

    JSONObject records;
    Bitmap b;

    private int mYear, mMonth, mDay;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        session = new SessionManager(this);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_title.setText(R.string.edit_profile);
                inputFocus();
                getCountries();
                //httpUpdateCustomerProfile();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input_layout_mobile_no.setError("");
                input_layout_dob.setError("");
                input_layout_last_name.setError("");
                input_layout_first_name.setError("");
                input_layout_nationality.setError("");

                firstname = input_first_name.getText() + "".trim();
                lastName = input_last_name.getText() + "".trim();
                mobileNo = input_mobile_no.getText() + "".trim();
                homeNo = input_home_no.getText() + "".trim();
                dob = input_dob.getText() + "".trim();


                if (validateFirstName() && validateLastName() && validateMobileNo() && validateNationality() && validateDOB()) {
                    if (ImagePath.equals("")) {
                        System.out.println("Image Path from=" + ImagePath);
                        new UpdateProfileData("").execute();
                    } else {

                        System.out.println("Image Path from=" + ImagePath);
                        new UpdateProfileData(ImagePath).execute();
                    }
                    //   httpUpdateCustomerProfile();
                } else
                    System.out.println("Something wrong");


            }
        });

        radio_btn_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                switch (checkedRadioButton.getId()) {
                    case R.id.radio_femmale_btn:
                        mGender = "F";
                        return;
                    case R.id.radio_male_btn:
                        mGender = "M";
                        return;

                }
            }
        });

        input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(!input_dob.isClickable())
                    {
                        return;
                    }

                    input_dob.setClickable(false);

                    dob = records.get("dob").toString();
                    String[] tokens = dob.split("-");
                    mYear = Integer.parseInt(tokens[0]);
                    mMonth = Integer.parseInt(tokens[1]) - 1;
                    mDay = Integer.parseInt(tokens[2]);
                    System.out.println("DOB===" + records.get("dob"));

                } catch (Exception ex) {
                    System.out.println("EXCEPTION====" + ex);
                }



                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                input_dob.setClickable(true);
                                input_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.setCanceledOnTouchOutside(false);
                datePickerDialog.setCancelable(true);
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        input_dob.setClickable(true);
                    }
                });
                datePickerDialog.show();



            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionControl();
            }
        });

        initView();
        getCountries();

        httpGetCustomerProfileCall();

        inputFocus();

    }

    private void permissionControl() {
        if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(EditProfileActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, permissionsRequired[1])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Permission");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(EditProfileActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Permissions");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(EditProfileActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }


            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void proceedAfterPermission() {
        // txtPermissions.setText("We've got all permissions");

        showImagePickerDialog();
        // goToGallery();
        //Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }


    private void goToGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_REQUEST_CODE);
    }

    private void goToCamera()
    {

        /*Intent intent=new Intent(EditProfileActivity.this, CustomCameraActivity.class);
        startActivity(intent);*/


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);




    }


    private String getImagePathFromGallery(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            ImagePath = cursor.getString(columnIndex);

            System.out.println("Image Path from gallery===" + ImagePath);

            Bitmap bm = BitmapFactory.decodeFile(ImagePath);
            bm = Bitmap.createScaledBitmap(bm, 120, 120, true);

            RoundImage roundedImage = new RoundImage(bm);


            profile_image.setImageDrawable(roundedImage);
            //imageFile=new File(ImagePath.toString());

            System.out.println("File path==" + ImagePath);
            cursor.close();
            return ImagePath;
        } else {
            return "";
        }

    }
    private String getImagePathFromCamera(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {


            Uri selectedImage = data.getData();
            // String[] filePathColumn = {MediaStore.Images.Media.DATA};
            File destination = new File(GetImagePath.getPath(getApplicationContext(), selectedImage));

            Bitmap bitmap=BitmapFactory.decodeFile(GetImagePath.getPath(getApplicationContext(), selectedImage));
            bitmap=Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            RoundImage roundedImage=new RoundImage(bitmap);

            profile_image.setImageDrawable(roundedImage);

            return destination.getPath();
        } else {
            return "";
        }


    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                ImagePath = getImagePathFromGallery(resultCode, data);
                //pickImageFromGallery();
                break;
            case CAMERA_REQUEST_CODE:

                if (resultCode == RESULT_OK )
                {

                    b=(Bitmap)data.getExtras().get("data");
                    b = Bitmap.createScaledBitmap(b, 120, 120, true);
                    RoundImage roundedImage = new RoundImage( b);
                    profile_image.setImageDrawable(roundedImage);
                    ImagePath=savePhotoTosdCard(b);
                }

                break;
            case REQUEST_PERMISSION_SETTING: {
                if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                    //Got Permission
                    proceedAfterPermission();
                }
            }
            break;

        }


        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    public String savePhotoTosdCard(Bitmap b)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String pname=sdf.format(new Date());


        String root= Environment.getExternalStorageDirectory().toString();
        File folder=new File(root+"/Tafel");

        folder.mkdirs();
        File my_file=new File(folder, pname+".png");

        try {
            FileOutputStream stream = new FileOutputStream(my_file);
            b.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();

            System.out.println("FILE NAME=="+my_file.getPath());
            //Toast.makeText(getApplicationContext(),"Photo saved "+my_file.getPath(),Toast.LENGTH_SHORT).show();

            Bitmap myBitmap = BitmapFactory.decodeFile(my_file.getAbsolutePath());

            System.out.println("FILE NAME=="+myBitmap);
            return my_file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       return "";
    }

    public int getOriantion(String filepath)
    {
        int orientation = 0;
        System.out.println("orientation=" + filepath);
        try
        {
            ExifInterface exif = new ExifInterface(filepath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        }
        catch(Exception ex)
        {
            System.out.println("ERROR=="+ex);
        }
        return orientation;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, permissionsRequired[1])
                    ) {
                //  txtPermissions.setText("Permissions Required");
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
                builder.setTitle("Permission");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(EditProfileActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void initView() {


        toolbar_title.setText(R.string.profile);
    }

    public void getCountries() {
        //SimpleProgressBar.showProgress(EditProfileActivity.this);
        try {
            final String url = Contents.baseURL + "getAllCountries";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                          //  SimpleProgressBar.closeProgress();

                            System.out.println("RESPONSE==" + response);
                            Log.e("device response", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String status = object.getString("status");
                                String message = object.getString("message");
                                if (status.equals("1")) {

                                    country_name = new ArrayList<String>();
                                    // country_name.add("Select Country");
                                    iso_name = new ArrayList<String>();
                                    JSONArray jsonArray = object.getJSONArray("record");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        country_name.add(c.getString("name"));
                                        iso_name.add(c.getString("iso"));
                                    }

                                    //Creating the ArrayAdapter instance having the country list
                                  aa  = new ArrayAdapter(EditProfileActivity.this, android.R.layout.simple_spinner_item, country_name);

                                   /* {
                                        @Override
                                        public boolean isEnabled(int position){
                                            if(position == 0)
                                            {
                                                // Disable the first item from Spinner
                                                // First item will be use for hint
                                                return false;
                                            }
                                            else
                                            {
                                                return true;
                                            }
                                        }
                                        @Override
                                        public View getDropDownView(int position, View convertView,
                                                                    ViewGroup parent) {
                                            View view = super.getDropDownView(position, convertView, parent);
                                            TextView tv = (TextView) view;
                                            if(position == 0){
                                                // Set the hint text color gray
                                                tv.setTextColor(Color.GRAY);
                                            }
                                            else {
                                                tv.setTextColor(Color.BLACK);
                                            }
                                            return view;
                                        }
                                    };*/
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spin_country.setAdapter(aa);

                                    System.out.println("NATIONALITY====" + input_nationality.getText().toString().trim());
                                    String nationality = input_nationality.getText().toString().trim();

                                    System.out.println("NATIONALITY====" + getSpinnerIndex(spin_country, nationality));
                                    spin_country.setSelection(getSpinnerIndex(spin_country, nationality));
                                    spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            country_iso_code = iso_name.get(position);
                                            //Toast.makeText(getApplicationContext(),country_iso_code,Toast.LENGTH_SHORT).show();
                                            //else
                                            // ww;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });

                                } else {

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                System.out.println("EX==" + e);
                               // SimpleProgressBar.closeProgress();
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

                    Log.e("Refsal device == ", url + params);

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(EditProfileActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }


    private void httpUpdateCustomerProfile() {
        // System.out.println("Hello Rituparna===");
        SimpleProgressBar.showProgress(EditProfileActivity.this);
        try {
            final String url = Contents.baseURL + "updateCustomerProfile";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");
                                    String message = "";
                                    if (status.equals("1")) {
                                        message = object.getString("message");
                                        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    } else {
                                        message = object.getString("errors");
                                    }


                                    //status.contains("1")? fillCustomerProfileData(records):toastMe(message);
                                    //tc_text.setText(records.getString("description"));
                                    //toolbar_title.setText(records.getString("title"));

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                                    //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(getApplicationContext(), SettingsActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    // finish();
                                } catch (JSONException e) {
                                    System.out.println("EX==" + e);
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
                    params.put("customer_id", session.getCustomerId());
                    params.put("first_name", input_first_name.getText().toString().trim());
                    params.put("last_name", input_last_name.getText().toString().trim());
                    params.put("name", input_first_name.getText().toString().trim() + " " + input_last_name.getText().toString().trim());
                    params.put("mobile", input_mobile_no.getText().toString().trim());
                    params.put("home_no", input_home_no.getText().toString().trim());
                    params.put("profile_image", "");
                    params.put("gender", mGender);
                    params.put("dob", "XX-XX-XXXX");
                    params.put("nationality", country_iso_code);
                    params.put("access_token", session.getToken());
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

    private void httpGetCustomerProfileCall() {
        SimpleProgressBar.showProgress(EditProfileActivity.this);
        try {
            final String url = Contents.baseURL + "getCustomerProfile";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response====" + response.toString());

                            // mSessionManager.setStyleStatus("true");
                            SimpleProgressBar.closeProgress();

                            if (response != null) {
                                // mSessionManager.clearSizes();
                                Log.e("stores response", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");

                                    String message = object.getString("message");
                                    records = object.getJSONObject("record");

                                    //status.contains("1")? fillCustomerProfileData(records):toastMe(message);
                                    //tc_text.setText(records.getString("description"));
                                    //toolbar_title.setText(records.getString("title"));
                                    if (status.contains("1")) {
                                        fillCustomerProfileData(records);
                                    } else {
                                        Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();

                                    }

                                    //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                                    //startActivity(new Intent(getApplicationContext(), SettingsActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                                    // finish();
                                } catch (JSONException e) {
                                    System.out.println("EX==" + e);
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
                    System.out.println(" Access Token===" + session.getToken());
                    params.put("access_token", session.getToken());
                    params.put("customer_id", session.getCustomerId());
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

    private void fillCustomerProfileData(JSONObject records) {
        try {

            if (!records.get("first_name").equals(null) ? true : false)
                input_first_name.setText(records.get("first_name").toString());

            if (!records.get("last_name").equals(null) ? true : false)
                input_last_name.setText(records.get("last_name").toString());

            if (!records.get("mobile").equals(null) ? true : false)
                input_mobile_no.setText(records.get("mobile").toString());

            if (!records.get("home_no").equals(null) ? true : false)
                input_home_no.setText(records.get("home_no").toString());

            if (!records.get("nationality").equals(null) ? true : false)
                input_nationality.setText(records.get("nationality").toString());
//            int spinnerPosition = .getPosition(compareValue);
            int spinnerPosition = aa.getPosition(records.get("nationality").toString());
            spin_country.setSelection(spinnerPosition);

            if (!records.get("dob").equals(null) ? true : false)
                input_dob.setText(records.get("dob").toString());

            System.out.println("DOB======= FROM INPUT TEXT" + input_dob.getText());
            System.out.println("DOB======= FROM RECORD" + records.get("dob"));


            if (!records.get("gender").equals(null) ? true : false)
                input_gender.setText(records.get("gender").toString());
            try {
                Picasso.with(this)
                        .load("http://" + records.get("profile_image").toString())
                        .placeholder(R.drawable.imgpsh_fullsize)

                        .transform(new CircleTransform())
                      //  .transform(new ExifTransformation(this, Uri.parse("http://" + records.get("profile_image").toString())))

                        .into(profile_image);
            }
            catch(Exception ex)
            {
               System.out.println("ERROR============="+ex);
            }


            if (!records.get("gender").equals(null) ? true : false)
                mGender = records.get("gender").toString().equals("Female") ? "F" : "M";

            // System.out.println("Gender===="+mGender);
            if (mGender.equals("F")) {
                radio_femmale_btn.setChecked(true);
            } else {
                radio_male_btn.setChecked(true);
            }


            System.out.println("PROFILE IMAGE ===" + records.get("profile_image"));

        } catch (Exception ex) {
            System.out.println("Error==" + ex);
        }
    }

    public void inputFocus() {
        input_first_name.setFocusableInTouchMode(true);
        input_last_name.setFocusableInTouchMode(true);
        input_mobile_no.setFocusableInTouchMode(true);
        input_home_no.setFocusableInTouchMode(true);
        input_nationality.setFocusableInTouchMode(true);
        //  input_dob.setFocusableInTouchMode(true);
        input_gender.setFocusableInTouchMode(true);
        save_btn.setEnabled(true);
        profile_image.setEnabled(true);
        //LL_radio_container.setVisibility(View.VISIBLE);
        input_layout_gender.setVisibility(View.GONE);
       // LL_nationality.setVisibility(View.VISIBLE);
        //input_layout_nationality.setVisibility(View.GONE);

    }

    private void requestFocus(View view) {

        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateFirstName() {
        if (input_first_name.getText().toString().trim().equals("")) {
            input_layout_first_name.setError("Error: first name");
            requestFocus(input_first_name);

            return false;
        }

        return true;

    }

    private boolean validateLastName() {
        if (input_last_name.getText().toString().trim().equals("")) {
            input_layout_last_name.setError("Error: First name");
            requestFocus(input_last_name);
            return false;
        }

        return true;

    }

    private boolean validateMobileNo() {
        if (input_mobile_no.getText().toString().trim().equals("") || input_mobile_no.getText().toString().trim().length() <= 7) {
            System.out.println("WRONG MOBILE");
            input_layout_mobile_no.setError("Error Mobile No");
            requestFocus(input_mobile_no);
            return false;
        }

        return true;


    }

    private boolean validateNationality() {
        if (input_mobile_no.getText().toString().trim().equals("")) {
            input_layout_nationality.setError("Error Nationality");
            requestFocus(input_mobile_no);
            return false;
        }

        return true;

    }

    private boolean validateDOB() {
        if (input_dob.getText().toString().trim().equals("")) {
            input_layout_dob.setError("Error DOB");
            requestFocus(input_dob);
            return false;

        }

        return true;

    }

    private boolean validateGender() {
        return true;
    }


    private int getSpinnerIndex(Spinner spin_country, String nationality) {
        int index = 0;

        for (int i = 0; i < spin_country.getCount(); i++) {
            if (spin_country.getItemAtPosition(i).toString().equalsIgnoreCase(nationality)) {
                index = i;
                break;
            }
        }
        return index;
    }




    public class UpdateProfileData extends AsyncTask<String, String, String> {
        File uploadFile;

        ProgressDialog pDialog;

        public UpdateProfileData(String uploadFile) {
            if (uploadFile.equals("")) {

            } else {
                this.uploadFile = new File(uploadFile);

                System.out.println("Image Path from Size===" + ImagePath);

            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfileActivity.this);
            pDialog.setMessage("Data uploading please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... params) {

            String result = null;
            String charset = "UTF-8";
            String requestURL = Contents.baseURL + "updateCustomerProfile";

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);


                if (uploadFile != null) {
                    try {
                        android.util.Log.e("Send Data", "Setting Image for profile");

                        multipart.addFilePart("profile_image", uploadFile);
                    } catch (FileNotFoundException e) {
                        System.out.println("EXCEPTION===" + e);

                        e.printStackTrace();
                    }
                } else {
                    multipart.addFormField("profile_image", "");
                }

                multipart.addFormField("customer_id", session.getCustomerId());
                multipart.addFormField("first_name", firstname);
                multipart.addFormField("last_name", lastName);
                multipart.addFormField("name", firstname + " " + lastName);
                multipart.addFormField("mobile", mobileNo);
                multipart.addFormField("home_no", homeNo);
                multipart.addFormField("gender", mGender);
                multipart.addFormField("dob", dob);
                multipart.addFormField("nationality", country_iso_code);
                multipart.addFormField("access_token", session.getToken());
                multipart.addFormField("appUser", "tefsal");
                multipart.addFormField("appSecret", "tefsal@123");
                multipart.addFormField("appVersion", "1.1");
                System.out.println("RESPONSE MULTIPART===" + multipart.finish());

            } catch (Exception ex) {
                System.out.println("EXCEPTION===" + ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            System.out.println("POST EXECUTION");

            Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
            SessionManager sessionManager = new SessionManager(EditProfileActivity.this);
            sessionManager.setKeyUserName(firstname + " " + lastName);
            pDialog.dismiss();


        }


    }

    private void showImagePickerDialog() {
        final android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater LayoutInflater = this.getLayoutInflater();
        final View dialogView = LayoutInflater.inflate(R.layout.image_choose_dialog, null);


        Button dialog_gallery_btn = (Button) dialogView.findViewById(R.id.dialog_gallery_btn);
        Button dialog_camera_btn = (Button) dialogView.findViewById(R.id.dialog_camera_btn);


        dialogBuilder.setView(dialogView);

        final android.support.v7.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog_gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery();
                alertDialog.dismiss();
            }
        });

        dialog_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToCamera();
                alertDialog.dismiss();

                //Toast.makeText(getApplicationContext(),"Camera not build up", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
