package com.tefsalkw.models;
import android.graphics.Color;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishdashaTextile {

    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("tefsal_product_id")
    @Expose
    private Integer tefsalProductId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_name_arabic")
    @Expose
    private Object storeNameArabic;
    @SerializedName("dishdasha_product_name")
    @Expose
    private String dishdashaProductName;
    @SerializedName("dishdasha_product_name_arabic")
    @Expose
    private Object dishdashaProductNameArabic;
    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("brand_name_arabic")
    @Expose
    private Object brandNameArabic;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("max_delivery_days")
    @Expose
    private Integer maxDeliveryDays;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("price_pr_miter")
    @Expose
    private String pricePrMiter;
    @SerializedName("default_image")
    @Expose
    private String defaultImage;
    @SerializedName("colors")
    @Expose
    private List<Color> colors = null;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Integer getTefsalProductId() {
        return tefsalProductId;
    }

    public void setTefsalProductId(Integer tefsalProductId) {
        this.tefsalProductId = tefsalProductId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public String getDishdashaProductName() {
        return dishdashaProductName;
    }

    public void setDishdashaProductName(String dishdashaProductName) {
        this.dishdashaProductName = dishdashaProductName;
    }

    public Object getDishdashaProductNameArabic() {
        return dishdashaProductNameArabic;
    }

    public void setDishdashaProductNameArabic(Object dishdashaProductNameArabic) {
        this.dishdashaProductNameArabic = dishdashaProductNameArabic;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getMaxDeliveryDays() {
        return maxDeliveryDays;
    }

    public void setMaxDeliveryDays(Integer maxDeliveryDays) {
        this.maxDeliveryDays = maxDeliveryDays;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPricePrMiter() {
        return pricePrMiter;
    }

    public void setPricePrMiter(String pricePrMiter) {
        this.pricePrMiter = pricePrMiter;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

}