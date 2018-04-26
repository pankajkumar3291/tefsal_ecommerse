package com.tefsalkw.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jagbirsinghkang on 14/07/17.
 */

public class SessionManager {

    SharedPreferences Tefsal_pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TefsalPref";


    public static final String KEY_CUS_ID = "id";
    public static final String KEY_TOKEN = "token";

    public static final String KEY_STYLE = "style";

    public static final String KEY_USER_NAME = "key_user_name";


    private boolean isCountryCodeDownloaded = false;
    /*--------Upadated 10-23-2017---------------------------*/


    public static final String KEY_NECK_SIZE = "neck_size";
    public static final String KEY_SLDR_SIZE = "sldr_size";
    public static final String KEY_CHEST_SIZE = "chest_size";
    public static final String KEY_WAIST_SIZE = "waist_size";
    public static final String KEY_ARM_SIZE = "arm_size";
    public static final String KEY_WRIST_SIZE = "wrist_size";
    public static final String KEY_FRONT_HEIGHT_SIZE = "front_height_size";
    public static final String KEY_BACK_HEIGHT_SIZE = "back_height_size";

    public static final String KEY_PEN_POCKET = "pen_pocket";
    public static final String KEY_POCKET = "ke_pocket";

    public static final String KEY_MOBILE_POCKET = "mobile_pocket";
    public static final String KEY_BUTTONS = "buttons";
    public static final String KEY_BUTTONS_VISIBILITY = "buttons_visibilty";


    public static final String KEY_BUTTONS_PUSH_VISIBILITY = "button_visibility";

    public static final String KEY_COLLAR_BUTTON = "collar_button";
    public static final String KEY_COLLAR_BUTTON_VISIBILITY = "collar_button_visibilty";
    public static final String KEY_CUFFLINK = "cufflink";


    public static final String KEY_PASS = "key_pass";
    public static final String KEY_EMAIL = "key_email";


    public static final String KEY_CART_ID = "key_cart_id";

    public static final String KEY_LANG = "key_lang";

    public String getKeyPass() {
        return Tefsal_pref.getString(KEY_PASS, "");
    }

    public void setKeyPass(String pass) {
        editor.putString(KEY_PASS, pass);
        editor.commit();
    }

    public String getKeyEmail() {
        return Tefsal_pref.getString(KEY_EMAIL, "");
    }

    public void setKeyEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }


    public SharedPreferences getTefsal_pref() {
        return Tefsal_pref;
    }

    public String getKeyButtons() {
        return Tefsal_pref.getString(KEY_BUTTONS, "");
    }


    public boolean isCountryCodeDownloaded() {
        return isCountryCodeDownloaded;
    }

    public void setCountryCodeDownloaded(boolean countryCodeDownloaded) {
        isCountryCodeDownloaded = countryCodeDownloaded;
    }


    public void setKeyPocket(String keyPocket) {
        editor.putString(KEY_POCKET, keyPocket);
        editor.commit();
    }

    public String getKeyPocket() {

        return Tefsal_pref.getString(KEY_POCKET, "");
        // return KEY_POCKET;
    }

    public void setKeyButtons(String buttons) {
        editor.putString(KEY_BUTTONS, buttons);
        editor.commit();
    }

    public String getKeyCollarButton() {
        return Tefsal_pref.getString(KEY_COLLAR_BUTTON, "");
    }

    public void setKeyCollarButton(String collor_button) {
        editor.putString(KEY_COLLAR_BUTTON, collor_button);
        editor.commit();
    }

    public String getKeyCufflink() {
        return Tefsal_pref.getString(KEY_CUFFLINK, "");
    }

    public void setKeyCufflink(String cufflink) {
        editor.putString(KEY_CUFFLINK, cufflink);
        editor.commit();
    }

    public void setTefsal_pref(SharedPreferences tefsal_pref) {
        Tefsal_pref = tefsal_pref;
    }

    public String getKeyPenPocket() {

        return Tefsal_pref.getString(KEY_PEN_POCKET, "");
        //return KEY_PEN_POCKET;
    }

    public void setKeyPenPocket(String pen_pocket) {
        editor.putString(KEY_PEN_POCKET, pen_pocket);
        editor.commit();
    }

    public String getKeyMobilePocket() {
        return Tefsal_pref.getString(KEY_MOBILE_POCKET, "");
    }

    public void setKeyMobilePocket(String keyMobilePocket) {
        editor.putString(KEY_MOBILE_POCKET, keyMobilePocket);
        editor.commit();
    }


    //public
    // public static final String


    public SessionManager(Context context) {
        this._context = context;
        Tefsal_pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = Tefsal_pref.edit();
    }


    public void setCustomerId(String id) {

        editor.putString(KEY_CUS_ID, id);
        editor.commit();
    }

    public String getCustomerId() {

        // Storing type in pref
        return Tefsal_pref.getString(KEY_CUS_ID, "");

    }

    public void setToken(String token) {

        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken() {

        // Storing type in pref
        return Tefsal_pref.getString(KEY_TOKEN, "");

    }


    public void user_logout() {
        editor.clear();
        editor.commit();
    }


    public String getKeyNeckSize() {
        return Tefsal_pref.getString(KEY_NECK_SIZE, "0");
    }

    public void setKeyNeckSize(String size) {
        editor.putString(KEY_NECK_SIZE, size);
        editor.commit();
    }

    public String getKeySldrSize() {
        return Tefsal_pref.getString(KEY_SLDR_SIZE, "0");
    }

    public void setKeySldrSize(String size) {
        editor.putString(KEY_SLDR_SIZE, size);
        editor.commit();
    }

    public String getKeyChestSize() {
        return Tefsal_pref.getString(KEY_CHEST_SIZE, "0");
    }

    public void setKeyChestSize(String size) {
        editor.putString(KEY_CHEST_SIZE, size);
        editor.commit();
    }

    public String getKeyWaistSize() {
        return Tefsal_pref.getString(KEY_WAIST_SIZE, "0");
    }

    public void setKeyWaistSize(String size) {
        editor.putString(KEY_WAIST_SIZE, size);
        editor.commit();
    }


    //----------------------------KEY_ARM_SIZE-------------------------------
    public String getKeyArmSize() {
        return Tefsal_pref.getString(KEY_ARM_SIZE, "0");
    }

    public void setKeyArmSize(String size) {
        editor.putString(KEY_ARM_SIZE, size);
        editor.commit();
    }


    //----------------------------------KEY_WRIST_SIZE----------------------------------
    public String getKeyWristSize() {
        return Tefsal_pref.getString(KEY_WRIST_SIZE, "0");
    }

    public void setKeyWristSize(String size) {
        editor.putString(KEY_WRIST_SIZE, size);
        editor.commit();
    }


    public String getStyleStatus() {
        return Tefsal_pref.getString(KEY_STYLE, "0");
    }

    public void setStyleStatus(String style) {
        editor.putString(KEY_STYLE, style);
        editor.commit();
    }

    //---------------------------------KEY_FRONT_HEIGHT_SIZE-------------------------------------
    public String getKeyFrontHeightSize() {

        return Tefsal_pref.getString(KEY_FRONT_HEIGHT_SIZE, "0");

    }

    public void setKeyFrontHeightSize(String size) {
        editor.putString(KEY_FRONT_HEIGHT_SIZE, size);
        editor.commit();
    }

    //--------------------------------KEY_BACK_HEIGHT_SIZE---------------------------------------------------
    public String getKeyBackHeightSize() {
        return Tefsal_pref.getString(KEY_BACK_HEIGHT_SIZE, "0");
    }

    public void setKeyBackHeightSize(String size) {
        editor.putString(KEY_BACK_HEIGHT_SIZE, size);
        editor.commit();
    }


    public String getKeyUserName() {
        return Tefsal_pref.getString(KEY_USER_NAME, "");
    }

    public void setKeyUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    //------------------------------SET/GET VISIBILITY OF BUTTONS-----------------------------------

    public String getKeyButtonsVisibility() {
        return Tefsal_pref.getString(KEY_BUTTONS_VISIBILITY, "");
    }

    public void setKeyButtonsVisibility(String IsVisibility) {
        editor.putString(KEY_BUTTONS_VISIBILITY, IsVisibility);
        editor.commit();
    }

    //---------------------SET/GET VISIBILTY OF COLLAR BUTTONS-----------------------------------------

    public String getKeyCollarButtonVisibility() {
        return Tefsal_pref.getString(KEY_COLLAR_BUTTON_VISIBILITY, "");
    }

    public void setKeyCollarButtonVisibility(String IsVisibility) {
        editor.putString(KEY_COLLAR_BUTTON_VISIBILITY, IsVisibility);
        editor.commit();
    }

    //---------------------------------------------------------------------------------------------------

    // -----------------------------------SET/GET VISIBILTY OF COLLAR PUSH--------------------------------
    public String getKeyButtonsPushVisibility() {
        return Tefsal_pref.getString(KEY_BUTTONS_PUSH_VISIBILITY, "");
    }

    public void setKeyButtonsPushVisibilty(String isPushVisibilty) {
        editor.putString(KEY_BUTTONS_PUSH_VISIBILITY, isPushVisibilty);
        editor.commit();
    }


//--------------SET/GET CART ID------------------------------------

    public String getKeyCartId() {
        return Tefsal_pref.getString(KEY_CART_ID, "");
    }


    public void setKeyCartId(String cart_id) {
        editor.putString(KEY_CART_ID, cart_id);
        editor.commit();
    }


    //--------------SET/GET LANG------------------------------------

    public String getKeyLang() {
        return Tefsal_pref.getString(KEY_LANG, "English");
    }


    public void setKeyLang(String lang) {
        editor.putString(KEY_LANG, lang);
        editor.commit();
    }


    public boolean isRTL() {

        String keyLang = getKeyLang();
        return keyLang.equalsIgnoreCase("Arabic");


    }


    public void clearSizes() {
        editor.putString(KEY_BACK_HEIGHT_SIZE, "0");
        editor.putString(KEY_FRONT_HEIGHT_SIZE, "0");
        editor.putString(KEY_WRIST_SIZE, "0");
        editor.putString(KEY_ARM_SIZE, "0");
        editor.putString(KEY_WAIST_SIZE, "0");
        editor.putString(KEY_CHEST_SIZE, "0");
        editor.putString(KEY_SLDR_SIZE, "0");
        editor.putString(KEY_NECK_SIZE, "0");
        editor.commit();
    }
}
