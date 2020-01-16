package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendItemPromo {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("promo_type")
    @Expose
    private String promoType;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("item_quantity")
    @Expose
    private String itemQuantity="1";
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("promo_gift")
    @Expose
    private String promoGift;
    @SerializedName("promo_discount")
    @Expose
    private String promoDiscount="0";
    @SerializedName("promo_id")
    @Expose
    private String promoId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPromoGift() {
        return promoGift;
    }

    public void setPromoGift(String promoGift) {
        this.promoGift = promoGift;
    }

    public String getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(String promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

}
