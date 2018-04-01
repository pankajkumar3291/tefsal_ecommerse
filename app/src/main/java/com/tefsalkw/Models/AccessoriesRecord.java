package com.tefsalkw.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hp on 20-11-2017.
 */

public class AccessoriesRecord implements Serializable{
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("sub_color")
    @Expose
    private String subColor;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("brand_image")
    @Expose
    private String brandImage;
    @SerializedName("store_name")
    @Expose
    private String storeName;

    private String tefsal_product_id;

    private String store_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    @SerializedName("accessory_product_image")
    @Expose
   private String[] accessory_product_image;

    @SerializedName("min_delivery_days")
    @Expose
    private String min_delivery_days;


    @SerializedName("max_delivery_days")
    @Expose
    private String max_delivery_days;


    @SerializedName("product_discount")
    @Expose
    private String product_discount;



    public String getMin_delivery_days() {
        return min_delivery_days;
    }

    public void setMin_delivery_days(String min_delivery_days) {
        this.min_delivery_days = min_delivery_days;
    }

    public String getMax_delivery_days() {
        return max_delivery_days;
    }

    public void setMax_delivery_days(String max_delivery_days) {
        this.max_delivery_days = max_delivery_days;
    }

    public String getTefsal_product_id() {
        return tefsal_product_id;
    }

    public void setTefsal_product_id(String tefsal_product_id) {
        this.tefsal_product_id = tefsal_product_id;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    private String attribute_id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubColor() {
        return subColor;
    }

    public void setSubColor(String subColor) {
        this.subColor = subColor;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String[] getAccessory_product_image() {
        return accessory_product_image;
    }

    public void setAccessory_product_image(String[] accessory_product_image) {
        this.accessory_product_image = accessory_product_image;
    }


    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }


}