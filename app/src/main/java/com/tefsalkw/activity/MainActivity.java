package com.tefsalkw.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.appupdater.AppUpdater;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.app.TefsalApplication;
import com.tefsalkw.fragment.FragmentMyAddress;
import com.tefsalkw.fragment.HomeFragment;
import com.tefsalkw.models.BadgeRecordModel;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.PreferencesUtil;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SessionManagerToken;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    Fragment fragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btnClose)
    public TextView btnClose;


    @BindView(R.id.btn_menu)
    public ImageButton btn_menu;

    @BindView(R.id.btn_write_mail)
    ImageButton btn_write_mail;

    @BindView(R.id.btn_ADD)
    ImageButton btn_ADD;

    @BindView(R.id.footer_facebook)
    ImageView footer_facebook;

    @BindView(R.id.footer_instagram)
    ImageView footer_instagram;

    @BindView(R.id.footer_twitter)
    ImageView footer_twitter;

    TextView user_name;
    public static TextView mail_menu, order_menu, total_badge_txt;


    SessionManager session;

    SessionManagerToken sessionManagerToken;
    String fromMailArg = "";

    public static MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        mainActivity = this;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        sessionManagerToken = new SessionManagerToken(getApplicationContext());
        session = new SessionManager(this);

        if (session != null) {
            if (!session.getIsGuestId()) {
                checkUserExists();
            }
        }


        footer_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_url)));
                startActivity(browserIntent);
            }
        });
        footer_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_url)));
                startActivity(browserIntent);
            }
        });
        footer_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_url)));
                startActivity(browserIntent);
            }
        });


        System.out.println("TOKEN===" + session.getToken());
        System.out.println("USER ID===" + session.getCustomerId());


        nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.inflateHeaderView(R.layout.nav_header_main);
        user_name = (TextView) headerView.findViewById(R.id.user_name);
        total_badge_txt = (TextView) headerView.findViewById(R.id.total_badge_txt);


        nav_view.setNavigationItemSelectedListener(this);

        final MenuItem menuItemMail = nav_view.getMenu().findItem(R.id.nav_mail);
        final MenuItem menuItemOrder = nav_view.getMenu().findItem(R.id.nav_my_orders);

        final MenuItem menuItemStyle = nav_view.getMenu().findItem(R.id.nav_styles);
        final MenuItem menuAddress = nav_view.getMenu().findItem(R.id.nav_my_address);
        final MenuItem menuOrder = nav_view.getMenu().findItem(R.id.nav_my_orders);
        final MenuItem menuSetting = nav_view.getMenu().findItem(R.id.nav_settings);
        final MenuItem menuMail = nav_view.getMenu().findItem(R.id.nav_mail);
        final MenuItem menuTextile = nav_view.getMenu().findItem(R.id.nav_textiles);
        final MenuItem menuSignIn = nav_view.getMenu().findItem(R.id.nav_sign_in);
        final MenuItem menuSignUp = nav_view.getMenu().findItem(R.id.nav_sign_up);


        View actionView = MenuItemCompat.getActionView(menuItemMail);
        mail_menu = (TextView) actionView.findViewById(R.id.card_badge);

        View view = MenuItemCompat.getActionView(menuItemOrder);
        order_menu = (TextView) view.findViewById(R.id.card_badge);


        fragment = new HomeFragment();

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commit();
        }

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        btn_ADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddressesActivity.class));
            }
        });

        btn_write_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendMailActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                // Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });


        if (session.getIsGuestId()) {
            user_name.setText("Guest");
            order_menu.setVisibility(View.GONE);
            mail_menu.setVisibility(View.GONE);
            total_badge_txt.setVisibility(View.GONE);
            menuItemStyle.setVisible(false);
            menuAddress.setVisible(false);
            //  menuOrder.setVisible(true);
            menuTextile.setVisible(false);
            menuMail.setVisible(false);


        } else {
            user_name.setText(session.getKeyUserName().toString());
            menuTextile.setVisible(false);
            httpGetBadgeCall();
            menuSignIn.setVisible(false);
            menuSignUp.setVisible(false);


        }

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        boolean isFirstTimeLaunch = PreferencesUtil.getBool(getApplicationContext(), "isFirstTimeLaunch1", true);
        if (!isFirstTimeLaunch) {

            btn_menu.setVisibility(View.VISIBLE);
            btnClose.setVisibility(View.GONE);


        } else {

            btn_menu.setVisibility(View.GONE);
            btnClose.setVisibility(View.VISIBLE);

        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesUtil.putBool(getApplicationContext(), "isFirstTimeLaunch1", false);

                btn_menu.setVisibility(View.VISIBLE);
                btnClose.setVisibility(View.GONE);

                // HomeFragment.homeFragment.


            }
        });


        logAnalytics();


        if(session.getKeyLang().equals("Arabic"))
        {
            AppUpdater appUpdater = new AppUpdater(this)
                    .setTitleOnUpdateAvailable("يوجد تحديث جديد")
                    .setContentOnUpdateAvailable("التحديث نسخة متوفرة الآن للتنزيل. قم بتنزيل آخر تحديث لتحصل على آخر التحسينات للتطبيق")
                    .setButtonUpdate("تحديث")
                    .setButtonDoNotShowAgain(null)
                    .setButtonDismiss(null)
                    .setCancelable(false);

            appUpdater.start();
        }
        else
        {
            AppUpdater appUpdater = new AppUpdater(this)
                    .setButtonDoNotShowAgain(null)
                    .setButtonDismiss(null)
                    .setCancelable(false);

            appUpdater.start();
        }


    }


    private void logAnalytics() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, session.getCustomerId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, session.getIsGuestId() ? "GUEST" : "REGISTERED");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "user_type");
        TefsalApplication.getmFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void sendRegistrationToServer() {

        try {

            String androidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


            final String token = sessionManagerToken.getDeviceToken();

            final String url = Contents.baseURL + "registerNotificationToken";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.e("FCM Response", response);


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
                    if (session.getIsGuestId()) {
                        params.put("unique_id", androidDeviceId);
                    } else {
                        params.put("user_id", session.getCustomerId());
                        params.put("unique_id", androidDeviceId);
                    }
                    // params.put("device_id", androidDeviceId);
                    params.put("token", token);
                    Log.e("NotificationToken == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }


    }

    private void checkUserExists() {

        try {


            final String url = Contents.baseURL + "checkCustomerStatus";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.e("checkUserStatus", response);

                            if (response != null) {

                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");


                                    if (status.contains("1")) {

                                        sendRegistrationToServer();

                                        JSONObject statusData = object.getJSONObject("data");

                                        if (statusData != null) {
                                            String userStatus = statusData.getString("status");

                                            if (userStatus != null) {

                                                if (userStatus.equals("inactive") || userStatus.equals("deleted")) {

                                                    Toast.makeText(MainActivity.this, R.string.please_sign_in_to_continue, Toast.LENGTH_SHORT).show();
                                                    session.user_logout();



                                                    startActivity(new Intent(MainActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


                                                }
                                            }

                                        }


                                    } else {

                                        Toast.makeText(MainActivity.this, R.string.please_sign_in_to_continue, Toast.LENGTH_SHORT).show();
                                        session.user_logout();
                                        startActivity(new Intent(MainActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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

                    params.put("user_id", session.getCustomerId());

                    Log.e("NotificationToken == ", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }


    }


    private void httpGetCustomerProfileCall() {
        //SimpleProgressBar.showProgress(MainActivity.this);
        try {
            final String url = Contents.baseURL + "getCustomerProfile";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            if (response != null) {

                                Log.e("getCustomerProfile", response);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    String status = object.getString("status");


                                    if (status.contains("1")) {

                                        sendRegistrationToServer();

                                    } else {
                                        Toast.makeText(MainActivity.this, "Please sign in again to continue...", Toast.LENGTH_SHORT).show();
                                        session.user_logout();
                                        startActivity(new Intent(MainActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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
                    System.out.println(" Access Token===" + session.getToken());
                    params.put("access_token", session.getToken());
                    params.put("customer_id", session.getCustomerId() != null ? session.getCustomerId() : "");
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");


                    Log.e("User Profile == ", url + new JSONObject(params));

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


    private void httpGetBadgeCall() {
        SimpleProgressBar.showProgress(MainActivity.this);
        try {
            final String url = Contents.baseURL + "getBadges";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response==" + response.toString());
                            SimpleProgressBar.closeProgress();
                            if (response != null) {
                                Log.e("stores response", response);
                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    if (status.equals("1")) {
                                        String record = jsonObject.getString("record");
                                        Gson g = new Gson();
                                        BadgeRecordModel badgeRecordModel = g.fromJson(record, BadgeRecordModel.class);
                                        initializeCountDrawer(badgeRecordModel);
                                    }

                                } catch (Exception e) {
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
                    if (session.getIsGuestId()) {
                        params.put("unique_id", session.getCustomerId());
                    } else {
                        params.put("user_id", session.getCustomerId());
                    }
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


    private void initializeCountDrawer(BadgeRecordModel badgeRecordModel) {

        if (badgeRecordModel != null) {
            order_menu.setText("" + badgeRecordModel.getOrders_badge());
            mail_menu.setText("" + badgeRecordModel.getMails_badge());
            total_badge_txt.setText("" + badgeRecordModel.getTotal_badge());
        } else {
            order_menu.setText("0");
            mail_menu.setText("0");
            total_badge_txt.setText("0");
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            fragment = new HomeFragment();

            if (fragment != null) {

                toolbar_title.setText("");

                btn_menu.setImageResource(R.drawable.ic_menu_white_24dp);
                btn_ADD.setVisibility(View.GONE);
                btn_write_mail.setVisibility(View.GONE);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.commit();
            } else {
                super.onBackPressed();
            }
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            toolbar_title.setText("");
            fragment = new HomeFragment();
            btn_menu.setImageResource(R.drawable.ic_menu_white_24dp);
            btn_ADD.setVisibility(View.GONE);
            btn_write_mail.setVisibility(View.GONE);
        } else if (id == R.id.nav_styles) {
            // Handle the camera action

            if (!session.getCustomerId().equals("")) {
                startActivity(new Intent(MainActivity.this, TabbarActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }

        } else if (id == R.id.nav_textiles) {
            // Handle the camera action
            btn_ADD.setVisibility(View.GONE);
            //toolbar_title.setText(item.getTitle());
            btn_write_mail.setVisibility(View.GONE);
            //startActivity(new Intent(MainActivity.this,DishDashaProductActivity.class).putExtra("Flag","1"));
        } else if (id == R.id.nav_mail) {
            // Handle the camera action
            if (!session.getCustomerId().equals("")) {
               /* btn_write_mail.setVisibility(View.VISIBLE);
                btn_ADD.setVisibility(View.GONE);
                toolbar_title.setText(item.getTitle());
                btn_menu.setImageResource(R.drawable.ic_menu_black);
                toolbar_title.setTextColor(Color.BLACK);
                fragment = new MailFragment();*/

                startActivity(new Intent(MainActivity.this, MailingSystemActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }
        } else if (id == R.id.nav_my_address) {
            // Handle the camera action
            if (!session.getCustomerId().equals("")) {
                btn_ADD.setVisibility(View.VISIBLE);
                //toolbar_title.setText(item.getTitle());
                btn_menu.setImageResource(R.drawable.ic_menu_black);
                toolbar_title.setTextColor(Color.BLACK);
                toolbar_title.setText(item.getTitle());
                fragment = new FragmentMyAddress();
                btn_write_mail.setVisibility(View.GONE);
                //  startActivity(new Intent(MainActivity.this, AddressesActivity.class));
                //startActivity(new Intent(MainActivity.this, MyAddressListActivity.class));

            } else {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }
        } else if (id == R.id.nav_my_orders) {
            // Handle the camera action
            if (!session.getCustomerId().equals("")) {
                // btn_ADD.setVisibility(View.GONE);
                //toolbar_title.setText(item.getTitle());
                btn_write_mail.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, SigninActivity.class));
            }
        } else if (id == R.id.nav_settings) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_sign_up) {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));

        } else if (id == R.id.nav_sign_in) {
            startActivity(new Intent(MainActivity.this, SigninActivity.class));

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
