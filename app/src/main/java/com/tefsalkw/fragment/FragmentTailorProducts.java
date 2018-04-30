package com.tefsalkw.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.tefsalkw.adapter.CustomTailorCalculationProduct;
import com.tefsalkw.adapter.DishdashaTailorProductAdapterForListView;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.dialogs.DialogKart;
import com.tefsalkw.dialogs.DialogKartDropdown;
import com.tefsalkw.models.AddCartResponse;
import com.tefsalkw.models.CartItems;
import com.tefsalkw.models.GetAssignedItemsResponse;
import com.tefsalkw.models.SublistCartItems;
import com.tefsalkw.models.TailoringRecord;
import com.tefsalkw.network.BaseHttpClient;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class FragmentTailorProducts extends BaseFragment {


   /* List<GetCartRecord> getCartRecordListOfChecked=new ArrayList<GetCartRecord>();
    List<GetCartRecord> getCartRecordListOfCheckedTrue=new ArrayList<GetCartRecord>();*/

    ArrayList<TailoringRecord> tailoringRecordArrayListOfChecked = new ArrayList<TailoringRecord>();
    ArrayList<TailoringRecord> tailoringRecordArrayListOfCheckedTrue = new ArrayList<TailoringRecord>();


    GetAssignedItemsResponse getAssignedItemsResponse;

    private String ownTextileString;

    private boolean isOwnTextile = false;


    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.tailor_add)
    Button add;

    @BindView(R.id.dishInfoText)
    TextView dishInfoText;


    @BindView(R.id.list)
    ListView list;

    @BindView(R.id.list2)
    ListView list2;

    @BindView(R.id.ownTextileText)
    TextView ownTextileText;


    String store_id;

    SessionManager sessionManager;


    DishdashaTailorProductAdapterForListView dishdashaTailorProductAdapterForListView;

    CustomTailorCalculationProduct customTailorCalculationProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tailor_product, container, false);
        ButterKnife.bind(this, v);

        sessionManager = new SessionManager(getContext());

        System.out.println("SYSTEM STORE ID FRAGMENTTAILORPRODUCT===" + TefalApp.getInstance().getStoreId());

        Bundle bundle = getArguments();
        tailoringRecordArrayListOfChecked = (ArrayList<TailoringRecord>) bundle.getSerializable("tailoringRecordArrayListOfChecked");
        ownTextileString = bundle.getString("ownTextileString");

        dishInfoText.setText(TefalApp.getInstance().getStyleName() + " / " + Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters())) + " meter = 1 Dishdasha");

        System.out.println("SYSTEM STORE ID FRAGMENTTAILORPRODUCT===" + tailoringRecordArrayListOfChecked);

        if (tailoringRecordArrayListOfChecked != null) {

            int count = 0;

            for (int i = 0; i < tailoringRecordArrayListOfChecked.size(); i++) {


                if (tailoringRecordArrayListOfChecked.get(i).getChecked()) {
                    // GetCartRecord getCartRecord=new GetCartRecord();

                    TailoringRecord tailoringRecord = tailoringRecordArrayListOfChecked.get(i);

                    int total = Integer.parseInt(tailoringRecord.getItem_quantity()) / Integer.parseInt(TefalApp.getInstance().getMin_meters());
                    tailoringRecord.setPosition(count);
                    tailoringRecord.setTotal_dishdasha(total);
                    tailoringRecord.setRemaining_dishdasha(total);
                    tailoringRecordArrayListOfCheckedTrue.add(tailoringRecord);

                    count++;

                }

            }

            customTailorCalculationProduct = new CustomTailorCalculationProduct(getActivity(), tailoringRecordArrayListOfCheckedTrue);
            list.setAdapter(customTailorCalculationProduct);

        } else {

            isOwnTextile = true;
            ownTextileText.setText(ownTextileString + " - " + TefalApp.getInstance().getNumberDishdashaUserHave() + "m");
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap != null && dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.size() > 0) {
                    WebCallServiceAddCartNew();
                } else {
                    Toast.makeText(getActivity(), "Please add one or more tailor products.", Toast.LENGTH_LONG).show();
                }


            }
        });

        WebCallServiceStores();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void WebCallServiceStores() {
        SimpleProgressBar.showProgress(getActivity());
        try {
            final String url = Contents.baseURL + "getAssignedItems";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            SimpleProgressBar.closeProgress();
                            try {
                                if (response != null) {

                                    Log.e("stores response", response);
                                    Gson g = new Gson();
                                    getAssignedItemsResponse = g.fromJson(response, GetAssignedItemsResponse.class);

                                    if (getAssignedItemsResponse.getStatus().equals("1")) {


                                        System.out.println("DISHDASHA TAILOR SIZE====" + getAssignedItemsResponse.getRecord().size());

                                        // dishdashaTailorProductsAdapter=new DishdashaTailorProductsAdapter(getActivity(),mResponse.getRecord());
                                        dishdashaTailorProductAdapterForListView = new DishdashaTailorProductAdapterForListView(FragmentTailorProducts.this, getAssignedItemsResponse.getRecord());

                                        list2.setAdapter(dishdashaTailorProductAdapterForListView);


                                        //  recycler.setAdapter(dishdashaTailorProductsAdapter);

                                    } else {
                                        Toast.makeText(getActivity(), getAssignedItemsResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
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

                    System.out.println("CART ID======" + sessionManager.getKeyCartId());
//                  params.put("access_token", session.getToken());
                    params.put("cart_id", sessionManager.getKeyCartId());
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
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }
    }

    public void WebCallServiceAddCartNew() {


        final String url = Contents.baseURL + "addCart";


        JSONObject params = new JSONObject();


        try {
            params.put("access_token", sessionManager.getToken());
            params.put("user_id", sessionManager.getCustomerId());
            params.put("cart_id", sessionManager.getKeyCartId());
            try {
                params.put("items", isOwnTextile ? getItemsOwn() : getFinalCartList());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params.put("appUser", "tefsal");
            params.put("appSecret", "tefsal@123");
            params.put("appVersion", "1.1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Tefsal tailor == ", url + params);

        SimpleProgressBar.showProgress(getActivity());
        BaseHttpClient baseHttpClient = new BaseHttpClient();
        baseHttpClient.doPost(url, params, new BaseHttpClient.TaskCompleteListener<String>() {
            @Override
            public void onFailure() {
                SimpleProgressBar.closeProgress();
            }

            @Override
            public void onSuccess(String object) {

                try {
                    SimpleProgressBar.closeProgress();


                    Log.e("ADD CART RESPONSE", String.valueOf(object));


                    try {

                        AddCartResponse addCartResponse = new Gson().fromJson(object, AddCartResponse.class);

                        if (addCartResponse != null && addCartResponse.getStatus() == 1) {

                            String cart_id = addCartResponse.getCart_id();
                            sessionManager.setKeyCartId(cart_id);

                            String itemType = addCartResponse.getItem_type();

                            String categoryId = "1";
                            DialogKart dg = new DialogKart(getActivity(), false, itemType, categoryId);
                            dg.show();

                        } else {
                            Toast.makeText(getActivity(), addCartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        SimpleProgressBar.closeProgress();
                    }


                } catch (Exception e1) {
                    Toast.makeText(getActivity(), e1.getMessage(), Toast.LENGTH_SHORT).show();
                    e1.printStackTrace();
                    SimpleProgressBar.closeProgress();
                }


            }
        });


    }


    public JSONArray getItemsOwn() {
        JSONArray arry = new JSONArray();
        try {


            if (dishdashaTailorProductAdapterForListView != null && dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap != null) {

                for (int i = 0; i < dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.size(); i++) {

                    if (dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.size() > 0) {

                        List<SublistCartItems> sublistCartItems = dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.get(i);

                        if (sublistCartItems != null && sublistCartItems.size() > 0) {
                            JSONObject obj = new JSONObject();
                            obj.put("category_id", "1");

                            JSONObject item_details = new JSONObject();
                            JSONObject tailor_services = new JSONObject();

                            tailor_services.put("meter", sublistCartItems.size() * Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters())));
                            tailor_services.put("qty", sublistCartItems.size());
                            tailor_services.put("dishdasha_tailor_product_id", getAssignedItemsResponse.getRecord().get(i).getTefsal_product_id());
                            tailor_services.put("tailor_id", TefalApp.getInstance().getTailor_id());
                            item_details.put("tailor_services", tailor_services);

                            item_details.put("order_type", "own_textile");

                            obj.put("item_details", item_details);

                            arry.put(obj);
                        }


                    }


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("JsonArrayItems", String.valueOf(arry));
        return arry;
    }


    public JSONArray getItems() {
        JSONArray arry = new JSONArray();
        try {

            if (dishdashaTailorProductAdapterForListView != null && dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap != null) {

                Log.e("sublistCartItemsHashMap", new Gson().toJson(dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap) + "");




                for (Map.Entry<Integer, List<SublistCartItems>> entry : dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.entrySet()) {

                    List<SublistCartItems> sublistCartItems = (List<SublistCartItems>) entry.getValue();

                    Log.e("sublistCartItems", new Gson().toJson(sublistCartItems) + "");


                    if (sublistCartItems != null) {

                        for (SublistCartItems sublistCartItems1 : sublistCartItems) {

                            JSONObject obj = new JSONObject();


                            obj.put("product_id", sublistCartItems1.getProductId());
                            obj.put("item_id", sublistCartItems1.getItemId());
                            obj.put("category_id", "1");

                            JSONObject item_details = new JSONObject();
                            item_details.put("item_quantity", null);

                            JSONObject tailor_services = new JSONObject();

                            tailor_services.put("meter", Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters())));
                            tailor_services.put("qty", 1);
                            tailor_services.put("dishdasha_tailor_product_id", getAssignedItemsResponse.getRecord().get(entry.getKey()).getTefsal_product_id());
                            tailor_services.put("tailor_id", TefalApp.getInstance().getTailor_id());
                            item_details.put("tailor_services", tailor_services);

                            item_details.put("order_type", "textile");

                            obj.put("item_details", item_details);

                            arry.put(obj);

                        }
                    }
                }



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("JsonArrayItems", String.valueOf(arry));
        return arry;
    }


    public void showDialog(int position) {


        if (tailoringRecordArrayListOfChecked == null) {


            if (validateAssignOwn(position)) {
                Toast.makeText(getActivity(), "Sorry, limit reached!", Toast.LENGTH_SHORT).show();
            } else {
                addItemToTailorItem(null);
            }


        } else {
            DialogKartDropdown dg = new DialogKartDropdown(tailoringRecordArrayListOfCheckedTrue, FragmentTailorProducts.this);
            dg.show();
        }


    }


    public void removeItem(SublistCartItems sublistCartItems, int position) {
        try {
            int getRemaining = tailoringRecordArrayListOfCheckedTrue.get(position).getRemaining_dishdasha();
            getRemaining = getRemaining + 1;
            tailoringRecordArrayListOfCheckedTrue.get(position).setRemaining_dishdasha(getRemaining);
            customTailorCalculationProduct.notifyDataSetChanged();

            removeTailorHttpCall(sublistCartItems);

        } catch (Exception exc) {

        }
    }

    public void addItemToTailorItem(TailoringRecord cartRecord) {


        if (cartRecord != null) {
            // Log.e("dropdownId1",dropdownId);
            SublistCartItems sublistCartItems = new SublistCartItems();
            sublistCartItems.setItemPosition(cartRecord.getPosition());
            sublistCartItems.setItemName(cartRecord.getDishdasha_product_name());
            sublistCartItems.setProductId(cartRecord.getProduct_id());
            sublistCartItems.setItemId(cartRecord.getItem_id());
            dishdashaTailorProductAdapterForListView.addSublistCartItem(sublistCartItems, false);

            dishdashaTailorProductAdapterForListView.notifyDataSetChanged();

            //dishdashaAdapter.updateDishDashacount(dropdownId);

            Integer remaining = tailoringRecordArrayListOfCheckedTrue.get(cartRecord.getPosition()).getRemaining_dishdasha();
            remaining = remaining - 1;
            tailoringRecordArrayListOfCheckedTrue.get(cartRecord.getPosition()).setRemaining_dishdasha(remaining);

            customTailorCalculationProduct.notifyDataSetChanged();
            assignTailorHttpCall(cartRecord);


        } else {

            SublistCartItems sublistCartItems = new SublistCartItems();

            sublistCartItems.setItemName("Own Textile");

            dishdashaTailorProductAdapterForListView.addSublistCartItem(sublistCartItems, true);


            dishdashaTailorProductAdapterForListView.notifyDataSetChanged();

        }


    }


    public JSONArray getFinalCartList() {
        List<CartItems> cartItems = new Gson().fromJson(getItems().toString(), new TypeToken<List<CartItems>>() {
        }.getType());


        List<CartItems> finalCartList = new ArrayList<>();

        Set<String> integerSet = new HashSet<String>();

        for (CartItems cartItems1 : cartItems) {

            if (integerSet.add(cartItems1.getProduct_id() + cartItems1.getItem_details().getTailor_services().getDishdasha_tailor_product_id())) {

                int countIs = getFrequency(cartItems, cartItems1);
                int minMeter = Integer.parseInt(TefalApp.getInstance().getMin_meters());
                System.out.println("a : " + countIs);

                cartItems1.getItem_details().getTailor_services().setQty(countIs + "");
                cartItems1.getItem_details().getTailor_services().setMeter(countIs * minMeter + "");
                finalCartList.add(cartItems1);
            }


        }

        Log.e("finalCartList", new Gson().toJson(finalCartList));

        try {
            return new JSONArray(new Gson().toJson(finalCartList));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    private int getFrequency(List<CartItems> cartItemsList, CartItems cartItems) {
        int count = 0;
        try {

            String comparator = cartItems.getProduct_id() + cartItems.getItem_details().getTailor_services().getDishdasha_tailor_product_id();
            for (CartItems cartItems1 : cartItemsList) {

                String compareWith = cartItems1.getProduct_id() + cartItems1.getItem_details().getTailor_services().getDishdasha_tailor_product_id();

                if (comparator.equalsIgnoreCase(compareWith)) {

                    count++;
                }

            }


        } catch (Exception exc) {

        }

        return count;
    }

    private void assignTailorHttpCall(final TailoringRecord getAssignedItemsRecord) {


        try {
            final String url = Contents.baseURL + "assignTailor";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("OUTPUT=====assignTailor Response" + response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                  params.put("access_token", session.getToken());
                    params.put("user_required_meter", TefalApp.getInstance().getMin_meters());
                    params.put("item_id", getAssignedItemsRecord.getProduct_id());
                    params.put("tailor_id", TefalApp.getInstance().getTailor_id());
                    params.put("cart_id", sessionManager.getKeyCartId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("assignTailorHttpCall", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }

    }


    private void removeTailorHttpCall(final SublistCartItems getAssignedItemsRecord) {


        try {
            final String url = Contents.baseURL + "removeTailor";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            System.out.println("OUTPUT=====removeTailor Response" + response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                  params.put("access_token", session.getToken());
                    params.put("user_required_meter", TefalApp.getInstance().getMin_meters());
                    params.put("item_id", getAssignedItemsRecord.getProductId());
                    params.put("tailor_id", TefalApp.getInstance().getTailor_id());
                    params.put("cart_id", sessionManager.getKeyCartId());
                    params.put("appUser", "tefsal");
                    params.put("appSecret", "tefsal@123");
                    params.put("appVersion", "1.1");

                    Log.e("assignTailorHttpCall", url + new JSONObject(params));

                    return params;
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.add(stringRequest);

        } catch (Exception surError) {
            surError.printStackTrace();
        }

    }


    public boolean validateAssign(TailoringRecord tailoringRecord) {


        if (tailoringRecord != null) {

            Log.e("position", tailoringRecord.getPosition() + "");
            return tailoringRecord.getRemaining_dishdasha() == 0;


        }

        return false;

    }


    public boolean validateAssignOwn(int position) {


        if (dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap != null) {

            int count = 0;
            for (int i = 0; i < dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.size(); i++) {
                List<SublistCartItems> list = dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.get(i);
                if (list != null) {
                    count = count + list.size();
                }

            }

            int sizeIs = Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters()));
            int numberDishdasha = Math.round(Float.parseFloat(TefalApp.getInstance().getNumberDishdashaUserHave()));

            return numberDishdasha < (sizeIs + (sizeIs * count));


//            List<SublistCartItems> list = dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.get(position);
//
//            if (list != null) {
//
//
//                int sizeIs = Math.round(Float.parseFloat(TefalApp.getInstance().getMin_meters()));
//
//                int numberDishdasha = Math.round(Float.parseFloat(TefalApp.getInstance().getNumberDishdashaUserHave()));
//
//                return numberDishdasha < (sizeIs + (sizeIs * list.size()));
//
//            }
        }


        return false;

    }


    public int getSelectedItemCount(int position, String pid) {
        List<SublistCartItems> sublistCartItems = dishdashaTailorProductAdapterForListView.sublistCartItemsHashMap.get(position);

        int count = 0;
        for (SublistCartItems entry : sublistCartItems) {
            if (entry.getProductId().equalsIgnoreCase(pid)) {
                count++;
            }
        }


        return count;

    }


}
