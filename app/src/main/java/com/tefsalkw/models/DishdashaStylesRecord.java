package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Hp on 30-10-2017.
 */

public class DishdashaStylesRecord implements Serializable {

    private String neck;

    private String deleted_at;

    private String mobile_pocket = "";

    private String chest;

    private String wrist;

    private String waist = "";

    private String buttons = "";

    private String pen_pocket = "";



    private String wide = "";

    private String collar_buttons = "";

    private String cufflink = "";

    private String id;

    private String front_height = "";

    private String category = "";

    private String narrow = "";

    private String updated_at = "";

    private String back_height = "";

    private String name = "";

    private String shoulder = "";

    private String created_at = "";

    private String user_id = "";

    private String arm = "";

    private String key_pocket = "";



    private String min_meters = "";

    private String collar_button_visibility = "";
    private String shirt_button_visibility = "";
    private String collar_buttons_push = "";

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    private boolean isEditMode = false;

    public String getNeck ()
    {
        return neck;
    }

    public void setNeck (String neck)
    {
        this.neck = neck;
    }

    public String getDeleted_at ()
    {
        return deleted_at;
    }

    public void setDeleted_at (String deleted_at)
    {
        this.deleted_at = deleted_at;
    }

    public String getMobile_pocket ()
    {
        return mobile_pocket;
    }

    public void setMobile_pocket (String mobile_pocket)
    {
        this.mobile_pocket = mobile_pocket;
    }

    public String getChest ()
    {
        return chest;
    }

    public void setChest (String chest)
    {
        this.chest = chest;
    }

    public String getWrist ()
    {
        return wrist;
    }

    public void setWrist (String wrist)
    {
        this.wrist = wrist;
    }

    public String getWaist ()
    {
        return waist;
    }

    public void setWaist (String waist)
    {
        this.waist = waist;
    }

    public String getButtons ()
    {
        return buttons;
    }

    public void setButtons (String buttons)
    {
        this.buttons = buttons;
    }

    public String getPen_pocket ()
    {
        return pen_pocket;
    }

    public void setPen_pocket (String pen_pocket)
    {
        this.pen_pocket = pen_pocket;
    }

    public String getWide ()
    {
        return wide;
    }

    public void setWide (String wide)
    {
        this.wide = wide;
    }

    public String getCollar_buttons ()
    {
        return collar_buttons;
    }

    public void setCollar_buttons (String collar_buttons)
    {
        this.collar_buttons = collar_buttons;
    }

    public String getCufflink ()
    {
        return cufflink;
    }

    public void setCufflink (String cufflink)
    {
        this.cufflink = cufflink;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFront_height ()
    {
        return front_height;
    }

    public void setFront_height (String front_height)
    {
        this.front_height = front_height;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getNarrow ()
    {
        return narrow;
    }

    public void setNarrow (String narrow)
    {
        this.narrow = narrow;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getBack_height ()
    {
        return back_height;
    }

    public void setBack_height (String back_height)
    {
        this.back_height = back_height;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getShoulder ()
    {
        return shoulder;
    }

    public void setShoulder (String shoulder)
    {
        this.shoulder = shoulder;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getArm ()
    {
        return arm;
    }

    public void setArm (String arm)
    {
        this.arm = arm;
    }

    public String getKey_pocket() {
        return key_pocket;
    }

    public void setKey_pocket(String key_pocket) {
        this.key_pocket = key_pocket;
    }

    public String getMin_meters() {
        return min_meters;
    }

    public void setMin_meters(String min_meters) {
        this.min_meters = min_meters;
    }


    public String getCollar_button_visibility() {
        return collar_button_visibility;
    }

    public void setCollar_button_visibility(String collar_button_visibility) {
        this.collar_button_visibility = collar_button_visibility;
    }

    public String getShirt_button_visibility() {
        return shirt_button_visibility;
    }

    public void setShirt_button_visibility(String shirt_button_visibility) {
        this.shirt_button_visibility = shirt_button_visibility;
    }

    public String getCollar_buttons_push() {
        return collar_buttons_push;
    }

    public void setCollar_buttons_push(String collar_buttons_push) {
        this.collar_buttons_push = collar_buttons_push;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [neck = "+neck+", deleted_at = "+deleted_at+", mobile_pocket = "+mobile_pocket+", chest = "+chest+", wrist = "+wrist+", waist = "+waist+", buttons = "+buttons+", pen_pocket = "+pen_pocket+", wide = "+wide+", collar_buttons = "+collar_buttons+", cufflink = "+cufflink+", id = "+id+", front_height = "+front_height+", category = "+category+", narrow = "+narrow+", updated_at = "+updated_at+", back_height = "+back_height+", name = "+name+", shoulder = "+shoulder+", created_at = "+created_at+", user_id = "+user_id+", arm = "+arm+",key_pocket = "+key_pocket+",min_meters="+min_meters+"]";
    }
}

