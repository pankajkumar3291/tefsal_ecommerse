package com.tefsalkw.app;

import android.graphics.Bitmap;

/**
 * Created by Rituparna Khadka on 11/9/2017.
 */

public class TefalApp {


    private String color = "";
    private String subColor = "";


    private String season = "";
    private String country = "";


    private String brand = "";
    private String pattern = "";

    private String storeId = "";

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productId = "";
    private String flage = "";
    private String toolbar_title = "";

    private Bitmap bitmap;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    private String cartId = "";

    //This meber attribute is used to identify the paint of size text in ProductSizeAdapterHorizantal
    private String paintOverSizeText;
    private int position;

    public int getColorPosition() {
        return colorPosition;
    }

    public void setColorPosition(int colorPosition) {
        this.colorPosition = colorPosition;
    }

    private int colorPosition;

    public int getAccColorPosition() {
        return accColorPosition;
    }

    public void setAccColorPosition(int setAccColorPosition) {
        this.accColorPosition = setAccColorPosition;
    }

    private int accColorPosition;

    public int getCurrentSizePositionIs() {
        return currentSizePositionIs;
    }

    public void setCurrentSizePositionIs(int currentSizePositionIs) {
        this.currentSizePositionIs = currentSizePositionIs;
    }

    private int currentSizePositionIs;

    //This member variable is used to store the minimum meter selected style
    private String min_meters = "3";

    // This member variable is used to hold the style name
    private String styleName;


    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    private String styleId;

    // These member varialble is used to hold the info of payemnt method along with its corresponding TC.

    private String payment_method;
    private String payment_method_tc;


    public String getCurrentSizeText() {
        return currentSizeText;
    }

    public void setCurrentSizeText(String currentSizeText) {
        this.currentSizeText = currentSizeText;
    }

    private String currentSizeText;

    public String getCurrentColorText() {
        return currentColorText;
    }

    public void setCurrentColorText(String currentColorText) {
        this.currentColorText = currentColorText;
    }

    private String currentColorText;


    // This holds the store id of tailor store as tailor id
    private String tailor_id;

    // This member varialbe is used to identify the tab.. whther it is from textile or tailor in DishDahsaProductActivity
    private String whereFrom;

    // This meber variable is used to set customer id for tempBasis
    private String customer_id;

    // This meber variable is used to identified the tab of mail screen in MailDetailActivity
    private String whereFromInMail;

    //This member variable is used to hold the information of successfull registration
    private String regSuccessMsg;


    public String getFromPush() {
        return fromPush;
    }

    public void setFromPush(String fromPush) {
        this.fromPush = fromPush;
    }

    private String fromPush = "no";

    public String getRegSuccessMsg() {
        return regSuccessMsg;
    }

    public void setRegSuccessMsg(String regSuccessMsg) {
        this.regSuccessMsg = regSuccessMsg;
    }

    public String getWhereFromInMail() {
        return whereFromInMail;
    }

    public void setWhereFromInMail(String whereFromInMail) {
        this.whereFromInMail = whereFromInMail;
    }


    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_method_tc() {
        return payment_method_tc;
    }

    public void setPayment_method_tc(String payment_method_tc) {
        this.payment_method_tc = payment_method_tc;
    }

    public String getMin_meters() {
        return min_meters;
    }

    public void setMin_meters(String min_meters) {
        this.min_meters = min_meters;
    }

    public String getWhereFrom() {
        return whereFrom;
    }

    public void setWhereFrom(String whereFrom) {
        this.whereFrom = whereFrom;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;


    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    private String storeName = "";

    public String getToolbar_title() {
        return toolbar_title;
    }

    public void setToolbar_title(String toolbar_title) {
        this.toolbar_title = toolbar_title;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getFlage() {
        return flage;
    }

    public void setFlage(String flage) {
        this.flage = flage;
    }

    public String getColor() {
        return color;
    }

    public String getSubColor() {
        return subColor;
    }

    public void setSubColor(String subColor) {
        this.subColor = subColor;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmAction() {
        return mAction;
    }

    public void setmAction(String mAction) {
        this.mAction = mAction;
    }

    private String mCategory;
    private String mAction;

    public TefalApp() {
        mCategory = "1";
    }

    public static TefalApp getInstance() {
        return TefsalApplication.getContext().getInstance();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPaintOverSizeText() {
        return paintOverSizeText;
    }

    public void setPaintOverSizeText(String paintOverSizeText) {
        this.paintOverSizeText = paintOverSizeText;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    public String getTailor_id() {
        return tailor_id;
    }

    public void setTailor_id(String tailor_id) {
        this.tailor_id = tailor_id;
    }


    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }


    public String getNumberDishdashaUserHave() {
        return numberDishdashaUserHave;
    }

    public void setNumberDishdashaUserHave(String numberDishdashaUserHave) {
        this.numberDishdashaUserHave = numberDishdashaUserHave;
    }

    private String numberDishdashaUserHave;
}
