package com.tefsalkw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
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
import com.google.gson.Gson;
import com.tefsalkw.Models.BadgeRecordModel;
import com.tefsalkw.R;
import com.tefsalkw.fragment.FragmentMyAddress;
import com.tefsalkw.fragment.HomeFragment;
import com.tefsalkw.fragment.MailFragment;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;
import com.tefsalkw.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

    @BindView(R.id.btn_menu)
    ImageButton btn_menu;

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
    public static TextView mail_menu,order_menu,total_badge_txt;


    SessionManager session;

    String fromMailArg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        //fromMailArg=getIntent().getStringExtra("Mail");

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer_layout.setDrawerListener(toggle);
//        toggle.syncState();

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
        session = new SessionManager(this);

        System.out.println("TOKEN==="+session.getToken());
        System.out.println("USER ID==="+session.getCustomerId());



        nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.inflateHeaderView(R.layout.nav_header_main);
        user_name = (TextView) headerView.findViewById(R.id.user_name);
        total_badge_txt=(TextView)headerView.findViewById(R.id.total_badge_txt);




        nav_view.setNavigationItemSelectedListener(this);
        // System.out.println("Token==="+session.getToken());
        //System.out.println("Token==="+session.getCustomerId());

//        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/Lato-LightItalic.ttf");
//        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        /*if(!fromMailArg.equals(""))

            fragment = new MailFragment();
        else*/


    //These lines should be added in the OnCreate() of your main activity

        final MenuItem menuItemMail = nav_view.getMenu().findItem(R.id.nav_mail);
        final MenuItem menuItemOrder = nav_view.getMenu().findItem(R.id.nav_my_orders);

        final MenuItem menuItemStyle=nav_view.getMenu().findItem(R.id.nav_styles);
        final MenuItem menuAddress=nav_view.getMenu().findItem(R.id.nav_my_address);
        final MenuItem menuOrder=nav_view.getMenu().findItem(R.id.nav_my_orders);
        final MenuItem menuSetting=nav_view.getMenu().findItem(R.id.nav_settings);
        final MenuItem menuMail=nav_view.getMenu().findItem(R.id.nav_mail);
        final MenuItem menuTextile=nav_view.getMenu().findItem(R.id.nav_textiles);
        final MenuItem menuSignIn=nav_view.getMenu().findItem(R.id.nav_sign_in);
        final MenuItem menuSignUp=nav_view.getMenu().findItem(R.id.nav_sign_up);







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


        if (session.getCustomerId().equals("")) {
            user_name.setText("Guest");
            order_menu.setVisibility(View.GONE);
            mail_menu.setVisibility(View.GONE);
            total_badge_txt.setVisibility(View.GONE);


            menuItemStyle.setVisible(false);
            menuAddress.setVisible(false);
            menuOrder.setVisible(false);
            menuTextile.setVisible(false);
            menuMail.setVisible(false);

            //menuSetting.setVisible(false);


            //nav_home
                   // nav_styles.
           // nav_textiles
                   // nav_mail
            //nav_my_address
                    //nav_my_orders



        }
        else
        {
            user_name.setText(session.getKeyUserName().toString());
            menuTextile.setVisible(false);
           // if(Utility.isInternetConnected(MainActivity.this))
            //{
                httpGetBadgeCall();
           // }
           // else
           // {
               // Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show();
           // }



            menuSignIn.setVisible(false);
            menuSignUp.setVisible(false);


        }

      //  httpGetBadgeCall();
    }

    private void httpGetBadgeCall()
    {
        SimpleProgressBar.showProgress(MainActivity.this);
        try {
            final String url = Contents.baseURL + "getBadges";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("response=="+response.toString());
                            SimpleProgressBar.closeProgress();
                            if (response != null)
                            {
                                Log.e("stores response", response);
                                try
                                {

                                            JSONObject jsonObject=new JSONObject(response);
                                            String status=jsonObject.getString("status");
                                            if(status.equals("1"))
                                            {
                                              String record=jsonObject.getString("record");
                                                Gson g=new Gson();
                                                BadgeRecordModel badgeRecordModel=g.fromJson(record,BadgeRecordModel.class);
                                                initializeCountDrawer(badgeRecordModel);
                                            }

                                } catch (Exception e) {
                                    System.out.println("EX=="+e);
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error=="+error.toString());
                            SimpleProgressBar.closeProgress();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", session.getCustomerId());
                    params.put("appUser","tefsal");
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

    private void initializeCountDrawer(BadgeRecordModel badgeRecordModel)
    {

        /*System.out.println("orders_badge===="+badgeRecordModel.getOrders_badge());
        System.out.println("mails_badge===="+badgeRecordModel.getMails_badge());
        System.out.println("total_badge===="+badgeRecordModel.getOrders_badge());*/

        if(badgeRecordModel.getOrders_badge().equals("0"))
        {
            order_menu.setVisibility(View.GONE);
        }
        else
        {
            order_menu.setText(""+badgeRecordModel.getOrders_badge());
        }
        if(badgeRecordModel.getMails_badge().equals("0"))
        {
            mail_menu.setVisibility(View.GONE);
        }
        else
        {
            mail_menu.setText(badgeRecordModel.getMails_badge());
        }
        if(badgeRecordModel.getTotal_badge().equals("0"))
        {
            total_badge_txt.setVisibility(View.GONE);
        }
        else
        {
            total_badge_txt.setText(""+badgeRecordModel.getTotal_badge());
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
            }
            else
            {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

            if(!session.getCustomerId().equals(""))
            {
                startActivity(new Intent(MainActivity.this, TabbarActivity.class));
            }
            else
            {
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
        }
        else if(id == R.id.nav_sign_up)
        {
           startActivity(new Intent(MainActivity.this,SignupActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
           finish();
        }
        else if(id == R.id.nav_sign_in)
        {
            startActivity(new Intent(MainActivity.this,SigninActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
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
