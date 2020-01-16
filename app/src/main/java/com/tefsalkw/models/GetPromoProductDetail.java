package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class GetPromoProductDetail implements Serializable {

    @SerializedName("tefsal_product_id")
    @Expose
    private Integer tefsalProductId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_desc")
    @Expose
    private String productDesc;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("product_name_arabic")
    @Expose
    private Object productNameArabic;
    @SerializedName("product_desc_arabic")
    @Expose
    private Object productDescArabic;
    @SerializedName("store_name_arabic")
    @Expose
    private Object storeNameArabic;
    @SerializedName("store_image")
    @Expose
    private String storeImage;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getTefsalProductId() {
        return tefsalProductId;
    }

    public void setTefsalProductId(Integer tefsalProductId) {
        this.tefsalProductId = tefsalProductId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Object getProductNameArabic() {
        return productNameArabic;
    }

    public void setProductNameArabic(Object productNameArabic) {
        this.productNameArabic = productNameArabic;
    }

    public Object getProductDescArabic() {
        return productDescArabic;
    }

    public void setProductDescArabic(Object productDescArabic) {
        this.productDescArabic = productDescArabic;
    }

    public Object getStoreNameArabic() {
        return storeNameArabic;
    }

    public void setStoreNameArabic(Object storeNameArabic) {
        this.storeNameArabic = storeNameArabic;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
