package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class DaraaAndAbaya implements Serializable {

    @SerializedName("default_price")
    @Expose
    private String defaultPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("tefsal_product_id")
    @Expose
    private Integer tefsalProductId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_name_arabic")
    @Expose
    private Object productNameArabic;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("brand_name_arabic")
    @Expose
    private Object brandNameArabic;
    @SerializedName("max_delivery_days")
    @Expose
    private Integer maxDeliveryDays;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_name_arabic")
    @Expose
    private String storeNameArabic;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("default_product_image")
    @Expose
    private String defaultProductImage;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("min_delivery_days")
    @Expose
    private Integer minDeliveryDays;
    @SerializedName("promo")
    @Expose
    private String promo;
    @SerializedName("item_type")
    @Expose
    private String itemType;

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(String defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Object getBrandNameArabic() {
        return brandNameArabic;
    }

    public void setBrandNameArabic(Object brandNameArabic) {
        this.brandNameArabic = brandNameArabic;
    }

    public Integer getMaxDeliveryDays() {
        return maxDeliveryDays;
    }

    public void setMaxDeliveryDays(Integer maxDeliveryDays) {
        this.maxDeliveryDays = maxDeliveryDays;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNameArabic() {
        return storeNameArabic;
    }

    public void setStoreNameArabic(String storeNameArabic) {
        this.storeNameArabic = storeNameArabic;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getDefaultProductImage() {
        return defaultProductImage;
    }

    public void setDefaultProductImage(String defaultProductImage) {
        this.defaultProductImage = defaultProductImage;
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

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}