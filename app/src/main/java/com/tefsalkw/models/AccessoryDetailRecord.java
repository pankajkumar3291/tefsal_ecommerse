package com.tefsalkw.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dell on 03/13/2018.
 */

public class AccessoryDetailRecord implements Serializable {

    @SerializedName("tefsal_product_id")
    @Expose
    private Integer tefsalProductId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("sub_cat_name")
    @Expose
    private String subCatName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("min_delivery_days")
    @Expose
    private Integer minDeliveryDays;
    @SerializedName("max_delivery_days")
    @Expose
    private Integer maxDeliveryDays;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("product_generated_id")
    @Expose
    private String productGeneratedId;
    @SerializedName("default_image")
    @Expose
    private String defaultImage;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("colors")
    @Expose
    private List<AccColor> colors = null;

    public Integer getTefsalProductId() {
        return tefsalProductId;
    }

    public void setTefsalProductId(Integer tefsalProductId) {
        this.tefsalProductId = tefsalProductId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getMinDeliveryDays() {
        return minDeliveryDays;
    }

    public void setMinDeliveryDays(Integer minDeliveryDays) {
        this.minDeliveryDays = minDeliveryDays;
    }

    public Integer getMaxDeliveryDays() {
        return maxDeliveryDays;
    }

    public void setMaxDeliveryDays(Integer maxDeliveryDays) {
        this.maxDeliveryDays = maxDeliveryDays;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getProductGeneratedId() {
        return productGeneratedId;
    }

    public void setProductGeneratedId(String productGeneratedId) {
        this.productGeneratedId = productGeneratedId;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AccColor> getColors() {
        return colors;
    }

    public void setColors(List<AccColor> colors) {
        this.colors = colors;
    }

}
