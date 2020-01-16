package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Accessory implements Serializable {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("tefsal_product_id")
    @Expose
    private Integer tefsalProductId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_name_arabic")
    @Expose
    private Object productNameArabic;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("product_desc_arabic")
    @Expose
    private Object productDescArabic;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("brand_image")
    @Expose
    private String brandImage;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_name_arabic")
    @Expose
    private Object storeNameArabic;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("min_delivery_days")
    @Expose
    private Integer minDeliveryDays;
    @SerializedName("max_delivery_days")
    @Expose
    private Integer maxDeliveryDays;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("product_generated_id")
    @Expose
    private String productGeneratedId;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("promo")
    @Expose
    private String promo;
    @SerializedName("default_image")
    @Expose
    private String defaultImage;
    @SerializedName("default_price")
    @Expose
    private String defaultPrice;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getTefsalProductId() {
        return tefsalProductId;
    }

    public void setTefsalProductId(Integer tefsalProductId) {
        this.tefsalProductId = tefsalProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Object getProductNameArabic() {
        return productNameArabic;
    }

    public void setProductNameArabic(Object productNameArabic) {
        this.productNameArabic = productNameArabic;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Object getProductDescArabic() {
        return productDescArabic;
    }

    public void setProductDescArabic(Object productDescArabic) {
        this.productDescArabic = productDescArabic;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Object getStoreNameArabic() {
        return storeNameArabic;
    }

    public void setStoreNameArabic(Object storeNameArabic) {
        this.storeNameArabic = storeNameArabic;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(String defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

}