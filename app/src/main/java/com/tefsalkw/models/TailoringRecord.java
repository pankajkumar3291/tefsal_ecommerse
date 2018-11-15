package com.tefsalkw.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 1/31/2018.
 */

public class TailoringRecord implements Serializable
{

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;





    private String remain_textile;



    private String total_textile;

    private float total_dishdasha;

    public float getTotal_dishdasha() {
        return total_dishdasha;
    }

    public void setTotal_dishdasha(float total_dishdasha) {
        this.total_dishdasha = total_dishdasha;
    }

    public float getRemaining_dishdasha() {
        return remaining_dishdasha;
    }

    public void setRemaining_dishdasha(float remaining_dishdasha) {
        this.remaining_dishdasha = remaining_dishdasha;
    }

    private float remaining_dishdasha;





    private Boolean isChecked;

    public Boolean getChecked()
    {
        return isChecked;
    }

    public void setChecked(Boolean checked)
    {
        isChecked = checked;
    }



    public String getRemain_textile ()
    {
        return remain_textile;
    }

    public void setRemain_textile (String remain_textile)
    {
        this.remain_textile = remain_textile;
    }



    public String getTotal_textile ()
    {
        return total_textile;
    }

    public void setTotal_textile (String total_textile)
    {
        this.total_textile = total_textile;
    }



    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("item_id")
    @Expose
    private String itemId;

    @SerializedName("dishdasha_product_name")
    @Expose
    private String dishdashaProductName;



    public String getDishdashaProductNameArabic() {
        return dishdashaProductNameArabic;
    }

    public void setDishdashaProductNameArabic(String dishdashaProductNameArabic) {
        this.dishdashaProductNameArabic = dishdashaProductNameArabic;
    }

    @SerializedName("dishdasha_product_name_arabic")
    @Expose
    private String dishdashaProductNameArabic;

    @SerializedName("item_quantity")
    @Expose
    private String itemQuantity;
    @SerializedName("img")
    @Expose
    private String img;




    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDishdashaProductName() {
        return dishdashaProductName;
    }

    public void setDishdashaProductName(String dishdashaProductName) {
        this.dishdashaProductName = dishdashaProductName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
